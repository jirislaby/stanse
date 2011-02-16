/*
 * Licensed under the GPLv2
 */

package cparser;

import cparser.AST.Node;
import cparser.AST.TranslationUnit;
import cparser.rewriter.ASTTypeSimplifier;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author Jiri Slaby
 */
public class CParser {
	private final String file;
	private TranslationUnit root;

	private static native int init();
	private static native void fini();
	private native int parse();

	public CParser(final String file) {
		this.file = file;
	}

	public static void main(final String argv[]) {
		System.loadLibrary("cparser");
		init();

		if (argv.length < 1)
			System.exit(1);

		final CParser cp =
			new CParser(new File(argv[0]).getAbsolutePath());

		System.out.println("parsing");
		int ret = cp.parse();

		if (ret != 0)
			System.err.println("Parsing failed with error " + ret);

		final Node n = cp.getRoot();
		n.compute();
		ASTTypeSimplifier.optimize(n);
		System.out.println("Generating XML");
		final String xml = n.toXML();
		System.out.println("Writing XML text");
		try {
			final FileWriter fw = new FileWriter("xml.text");
			fw.write(xml);
			fw.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.err.println("Node:\n\tcls=" + n.getClass().getName());
		System.err.println("\tstr=" + n.toString());
		System.err.println("\tXML:");
		try {
			System.out.println("Generating XML tree");
			Document doc = DocumentHelper.parseText(xml);
			System.out.println("Writing XML");
			OutputFormat format = OutputFormat.createPrettyPrint();
			OutputStream os = new FileOutputStream("out.xml");
			try {
				XMLWriter writer = new XMLWriter(os, format);
				writer.write(doc);
				writer.close();
				os.close();
/*				writer = new XMLWriter(System.err, format);
				writer.write(doc);
				writer.close();*/
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		fini();
	}

	private Node getRoot() {
		return root;
	}
}
