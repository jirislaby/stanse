/*
 * Licensed under the GPLv2
 */

package cparser;

import cparser.AST.Node;
import cparser.AST.TranslationUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

		int ret = cp.parse();

		if (ret != 0)
			System.err.println("Parsing failed with error " + ret);

		final Node n = cp.getRoot();
		System.err.println("Node:\n\tcls=" + n.getClass().getName());
		System.err.println("\tstr=" + n.toString());
		System.err.println("\tXML:");
		try {
			Document doc = DocumentHelper.parseText(n.toXML());
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
