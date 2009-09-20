package cz.muni.stanse.statistics;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public final class StatisticalDatabaseLoader {
    
    // public section

    public static Document run(final String xmlFile) {
        try {
            return (new SAXReader()).read(new java.io.File(xmlFile));
        } catch (final DocumentException e) {
            System.out.println("Cannot load statistical XML database file:\n" +
                               xmlFile + "\n\n");
            System.out.println("Exception: " + e.getLocalizedMessage() +"\n\n");
            e.printStackTrace();
            return null;
        }
    }

    // private section

    private StatisticalDatabaseLoader() {
    }
}
