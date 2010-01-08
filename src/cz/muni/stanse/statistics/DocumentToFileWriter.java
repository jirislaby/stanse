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
	if (pathName.getParentFile() != null &&
		!pathName.getParentFile().exists() &&
		!pathName.getParentFile().mkdirs()) {
	    System.err.println("Can't create directory structure");
	    return false;
	}
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

    public static void writeErrorReports(final Collection<Element> files,
                                         final Collection<Element> internals,
                                         final Collection<Element> checkers,
                                         final Collection<Element> checkfails,
                                         final Collection<Element> errors,
                                         final String outFile) {
        final Document doc = DocumentHelper.createDocument();
        final Element db = doc.addElement("database");

        buildDatabaseSection(db,"files",files);
        buildDatabaseSection(db,"internals",internals);
        buildDatabaseSection(db,"checkers",checkers);
        buildDatabaseSection(db,"checkfails",checkfails);
        buildDatabaseSection(db,"errors",errors);

        write(doc,outFile);
    }

    public static void writeErrorReports(final Collection<Element> errors,
                                         final String outFile) {
        writeErrorReports(null,null,null,null,errors,outFile);
    }

    // private section

    private static void
    buildDatabaseSection(final Element db, final String secName,
                         final Collection<Element> content) {
        final Element secElem = db.addElement(secName);
        if (content != null)
            for (final Element elem : content)
                secElem.add(elem.createCopy());
    }

    private DocumentToFileWriter() {
    }
}
