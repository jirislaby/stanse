/*
 * Licensed under GPLv2
 */

package cparser.tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jiri Slaby
 */
public class DotHelper {
	public static void showDot(final String dot) {
		final ProcessBuilder pb = new ProcessBuilder("dot", "-Tpdf",
			"-ocfg.pdf");
		Process dotProcess;
		try {
			dotProcess = pb.start();
			final OutputStream stdin = dotProcess.getOutputStream();
			final OutputStreamWriter writer =
				new OutputStreamWriter(stdin);
			writer.write(dot);
			writer.close();
			int retval = dotProcess.waitFor();
			if (retval != 0) {
				byte buf[] = new byte[256];
				int len;
				final InputStream stderr =
					new BufferedInputStream(
						dotProcess.getErrorStream());

				System.err.println("Dot failed with " + retval);
				while ((len = stderr.read(buf)) > 0)
					System.err.write(buf, 0, len);
				stderr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
