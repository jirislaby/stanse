package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;

import org.dom4j.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public final class DocumentToFileWriter {

    // public section

    public static boolean write(final Document data, final String outFile) {
        final File pathName = new File(outFile);
        pathName.getParentFile().mkdirs();
        FileOutputStream os;
        try {
            os = new FileOutputStream(pathName);
        } catch (FileNotFoundException ex) {
            System.err.println("Can't write '" + outFile + "':");
            ex.printStackTrace();
            return false;
        }
        XMLAlgo.outputXML(data,os);
        return true;
    }

    // private section

    private DocumentToFileWriter() {
    }
}
