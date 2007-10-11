package cz.muni.fi.iti.scv.scvgui;

import java.io.*;
import java.util.*;

public class GraphViz
{
   /**
    * The dir where temporary files will be created.
    */
   private static String TEMP_DIR   = System.getProperty("java.io.tmpdir");
   

   /**
    * Where is your dot program located? It will be called externally.
    */
   private static String DOT        = "/usr/bin/dot";

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
    * @param dot_source Source of the graph to be drawn.
    * @return A byte array containing the image of the graph.
    */
   public byte[] getGraph(String dot_source)
   {
      File dot;
      byte[] img_stream = null;
   
      try {
         dot = writeDotSourceToFile(dot_source);
         if (dot != null)
         {
            img_stream = get_img_stream(dot);
            if (dot.delete() == false) 
               System.err.println("Warning: "+dot.getAbsolutePath()+" could not be deleted!");
            return img_stream;
         }
         return null;
      } catch (java.io.IOException ioe) { return null; }
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
      } catch (java.io.IOException ioe) { return -1; }
      return 1;
   }

   /**
    * It will call the external dot program, and return the image in
    * binary format.
    * @param dot Source of the graph (in dot language).
    * @return The image of the graph in .gif format.
    */
   private byte[] get_img_stream(File dot)
   {
      File img;
      byte[] img_stream = null;

      try {
         img = File.createTempFile("graph_", ".png", new File(this.TEMP_DIR));
         String temp = img.getAbsolutePath();

         Runtime rt = Runtime.getRuntime();
         String cmd = DOT + " -Tpng "+dot.getAbsolutePath()+" -o"+img.getAbsolutePath();
         Process p = rt.exec(cmd);
         p.waitFor();

         FileInputStream in = new FileInputStream(img.getAbsolutePath());
         img_stream = new byte[in.available()];
         in.read(img_stream);
         // Close it if we need to
         if( in != null ) in.close();

         if (img.delete() == false) 
            System.err.println("Warning: "+img.getAbsolutePath()+" could not be deleted!");
      }
      catch (java.io.IOException ioe) {
         System.err.println("Error:    in I/O processing of tempfile in dir "+this.TEMP_DIR+"\n");
         System.err.println("       or in calling external command");
         ioe.printStackTrace();
      }
      catch (java.lang.InterruptedException ie) {
         System.err.println("Error: the execution of the external program was interrupted");
         ie.printStackTrace();
      }

      return img_stream;
   }

   /**
    * Writes the source of the graph in a file, and returns the written file
    * as a File object.
    * @param str Source of the graph (in dot language).
    * @return The file (as a File object) that contains the source of the graph.
    */
   private File writeDotSourceToFile(String str) throws java.io.IOException
   {
      File temp;
      try {
         temp = File.createTempFile("graph_", ".dot.tmp", new File(this.TEMP_DIR));
         FileWriter fout = new FileWriter(temp);
         fout.write(str);
         fout.close();
      }
      catch (Exception e) {
         System.err.println("Error: I/O error while writing the dot source to temp file!");
         return null;
      }
      return temp;
   }

   
}
