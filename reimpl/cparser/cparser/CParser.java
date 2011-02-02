/*
 * Licensed under the GPLv2
 */

package cparser;

import java.io.File;

/**
 * @author Jiri Slaby
 */
public class CParser {
	public static void main(final String argv[]) {
		System.loadLibrary("cparser");
		init();

		if (argv.length < 1)
			System.exit(1);

		int ret = parse(new File(argv[0]).getAbsolutePath());

		if (ret != 0)
			System.err.println("Parsing failed with error %d");
	}

	private static native void init();
	private static native int parse(final String file);
}
