/*
 * Licensed under the GPLv2
 */

package cparser;

import cparser.AST.Node;
import cparser.AST.TranslationUnit;
import java.io.File;

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
			System.err.println("Parsing failed with error %d");

		final Node n = cp.getRoot();
		System.err.println("Node:\n\tcls=" + n.getClass().getName());
		System.err.println("\tstr=" + n.toString());
		System.err.println("\tXML=" + n.toXML());

		fini();
	}

	private Node getRoot() {
		return root;
	}
}
