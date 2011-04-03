/*
 * Licensed under the GPLv2
 */

package cz.muni.stanse.codestructures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.dom4j.Element;

/**
 * Constructs, writes and otherwise handles the linear code
 *
 * A linear code is the one generated back from CFG. It is to be verified by the
 * symbolic execution engine.
 *
 * @author Jiri Slaby
 */
public final class LinearCode {
	private static Logger logger = Logger.getLogger(LinearCode.class);
	private static String sep = System.getProperty("line.separator");
	private final String linearCode;
	private int level = 0;

	private void nodeToLinear(final StringBuilder sb, final CFGNode node) {
            final Element el = node.getElement();
            if (el != null) {
                final String elName = el.getName();
                final String code = node.getCode();

                if (elName.equals("assert")) {
                        level++;
                        final String cropped = code.substring(7,
                                code.length() - 1);
                        sb.append("if (").append(cropped).append(") {").
                                append(sep);
                } else if (elName.equals("initDeclarator")) {
                        final int eqIdx = code.indexOf('=');
                        final String right = code.substring(eqIdx + 1);
                        sb.append("typeof(").append(right).append(") ").
                                append(code).append(';').append(sep);
                } else
                        sb.append(node.getCode()).append(';').append(sep);
            }
	}

	public LinearCode(final Stack<CFGNode> context,
			final List<CFGNode> path) {
		final StringBuilder sb = new StringBuilder();
		for (final CFGNode node : path)
			nodeToLinear(sb, node);
		sb.append("stanse_should_reach_here();").append(sep);
		for (int a = 0; a < level; a++)
			sb.append('}');
		linearCode = sb.toString();
	}

	public void dump(OutputStream os) {
		final BufferedWriter bw = new BufferedWriter(
			new OutputStreamWriter(os));
		try {
			bw.write("int main(void) {");
			bw.write(sep);
			bw.write(linearCode);
			bw.write('}');
			bw.write(sep);
			bw.close();
		} catch (final IOException e) {
			logger.error("Linear code writing failed", e);
		}
	}

	public void dump(final File file) {
		try {
			final FileOutputStream fw = new FileOutputStream(file);
			dump(fw);
		} catch (final FileNotFoundException e) {
			logger.error("Cannot open linear code file", e);
		}
	}

	public void dump(final String file) {
		dump(new File(file));
	}
}
