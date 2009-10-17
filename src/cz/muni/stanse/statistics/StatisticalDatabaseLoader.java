package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.ClassLogger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public final class StatisticalDatabaseLoader {
    
    // public section

    public static Document run(final String xmlFile) {
        return run(new java.io.File(xmlFile));
    }

    public static Document run(final java.io.File xmlFile) {
        try {
            return (new SAXReader()).read(xmlFile);
        } catch (final DocumentException e) {
            ClassLogger.error("Cannot load statistical XML database file '" +
                               xmlFile + "'", e);
            return null;
        }
    }

    // private section

    private StatisticalDatabaseLoader() {
    }
}
