package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;

import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

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

    public static void writeErrorReports(final Collection<Element> elements,
                                         final String outFile) {
        final Document doc = DocumentHelper.createDocument();
        final Element db = doc.addElement("database");
        db.addElement("files");
        db.addElement("internals");
        db.addElement("checkers");
        db.addElement("checkfails");
        final Element errElem = db.addElement("errors");
        for (final Element elem : elements)
            errElem.add(elem.createCopy());
        write(doc,outFile);
    }

    // private section

    private DocumentToFileWriter() {
    }
}
