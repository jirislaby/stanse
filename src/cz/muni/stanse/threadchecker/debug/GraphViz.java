package cz.muni.stanse.threadchecker.debug;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

public class GraphViz {
   /**
    * Where is your dot program located? It will be called externally.
    */
   private static String DOT        = "/usr/bin/dot";
   private static Logger logger = Logger.getLogger(GraphViz.class);

   /**
    * The source of the graph written in dot language.
    */
   public StringBuffer graph = new StringBuffer();

   /**
    * Constructor: creates a new GraphViz object that will contain
    * a graph.
    */
   public GraphViz() {
   }

   /**
    * Returns the graph's source description in dot language.
    * @return Source of the graph in dot language.
    */
   public String getDotSource() {
      return graph.toString();
   }

	/**
	 * Returns the graph as an image in binary format.
	 * @param dot Source of the graph to be drawn.
	 * @return A byte array containing the image of the graph.
	 */
	public byte[] getGraph(String dot) {
		byte[] img_stream = null;
		final Runtime rt = Runtime.getRuntime();
		final String cmd = DOT + " -Tpng";

		try {
			final Process p = rt.exec(cmd);
			final BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			final OutputStream out = p.getOutputStream();
			final StringBuffer sb = new StringBuffer(dot);
			out.write(dot.getBytes());
			out.flush();
			out.close();
			p.waitFor();
			img_stream = new byte[in.available()];
			in.read(img_stream);
			in.close();
		} catch (IOException e) {
			logger.error("calling external command failed", e);
		} catch (java.lang.InterruptedException e) {
			logger.error("the execution of the external program was interrupted", e);
		}

		return img_stream;
	}

   /**
    * Writes the graph's image in a file.
    * @param img   A byte array containing the image of the graph.
    * @param file  Name of the file to where we want to write.
    * @return Success: 1, Failure: -1
    */
   public int writeGraphToFile(byte[] img, String file)
   {
      File to = new File(file);
      return writeGraphToFile(img, to);
   }

   /**
    * Writes the graph's image in a file.
    * @param img   A byte array containing the image of the graph.
    * @param to    A File object to where we want to write.
    * @return Success: 1, Failure: -1
    */
   public int writeGraphToFile(byte[] img, File to)
   {
      try {
         FileOutputStream fos = new FileOutputStream(to);
         fos.write(img);
         fos.close();
      } catch (IOException ioe) { return -1; }
      return 1;
   }
}
