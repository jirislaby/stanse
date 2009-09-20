package cz.muni.stanse.statistics;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class StatisticalDatabaseBuilder {

    // public section

    public static void run(final String outputFile) {
        final Vector<CheckerError> errors = new Vector<CheckerError>();
        final BasicEvaluationStatistic evalStats=new BasicEvaluationStatistic();

        System.out.print("Statistical database build\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/3) Executing checking procedure on passed " +
                           "configuration.\nThis may take very long time...");

        Stanse.getInstance().getConfiguration().evaluateWait_EachUnitSeparately(
            new CheckerErrorReceiver() {
                @Override
                public void receive(final CheckerError error) {
                    errors.add(error);
                }
            },
            new CheckerProgressMonitor() {
                @Override
                public void write(final String s) {
                    System.out.print(s);
                }
            },
            evalStats);

        System.out.println("Checking done.\n\n(2/3) Starting conversion of " +
                           "collected data into XML format...");

	Document doc = DocumentHelper.createDocument();
	Element db = doc.addElement("database");
	for (Element e: evalStats.xmlDump())
	    db.add(e);
	db.add(xmlDump(errors));

        System.out.println("Conversion done.\n\n(3/3) Writing statistics in " +
                           "XML format into output file...");

	try {
	    FileOutputStream os = new FileOutputStream(new File(outputFile));
	    XMLAlgo.outputXML(doc, os);
	} catch (FileNotFoundException ex) {
	    System.err.println("Can't write '" + outputFile + "':");
	    ex.printStackTrace();
	    return;
	}

        System.out.println("Output written.\n\n\nSee results in " +
                           "file:\n    " + outputFile + "\n\n\n");
    }

    // private section

    private static Element xmlDump(final Vector<CheckerError> errors) {
        Element result = DocumentFactory.getInstance().createElement("errors");
        for (final CheckerError error: errors)
            result.add(error.xmlDump());
        return result;
    }

    private StatisticalDatabaseBuilder() {
    }
}
