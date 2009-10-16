package cz.muni.stanse.utils;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StreamAlgo {
    public static void copy(InputStream in, OutputStream out)
		throws IOException {
	byte[] buf = new byte[4096];
	int len;
	 
	while ((len = in.read(buf)) > 0)
	    out.write(buf, 0, len);
	out.flush();
    }

}
