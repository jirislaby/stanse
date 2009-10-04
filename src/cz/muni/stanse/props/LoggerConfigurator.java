/*
 * LoggerConfigurator.java
 *
 * Class configuring the log4j logger
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.props;

import cz.muni.stanse.Stanse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author stastny
 * This should be called once at the program's startup to set the log4j properties.
 */
public class LoggerConfigurator {

    private static LoggerConfigurator singletonInstance = null;
    
    public static LoggerConfigurator doConfigure() {
        if(LoggerConfigurator.singletonInstance == null) {
            LoggerConfigurator.singletonInstance = new LoggerConfigurator();
        }
        return LoggerConfigurator.singletonInstance;
    }
    /** Creates a new instance of LoggerConfigurator */
    private LoggerConfigurator() {
        java.util.Properties properties = new java.util.Properties();
        Level loggingLevel = null;
        switch (Stanse.getInstance().getVerbosityLevel()) {
	case SILENT:
	    Logger.getRootLogger().setLevel(Level.OFF);
	    loggingLevel = Level.OFF;
	    break;
	case LOW:
	    Logger.getRootLogger().setLevel(Level.WARN);
	    loggingLevel = (Level)Level.WARN;
	    break;
	case MIDDLE:
	    Logger.getRootLogger().setLevel(Level.INFO);
	    loggingLevel = (Level)Level.INFO;
	    break;
	case HIGH:
	    Logger.getRootLogger().setLevel(Level.ALL);
	    loggingLevel = (Level)Level.ALL;
	    break;
        }

        try {
	    InputStream bis = new BufferedInputStream(
		    new FileInputStream("log4j.properties"));
	    properties.load(bis);
	    bis.close();
	    PropertyConfigurator.configure(properties);
	} catch (IOException ex) {
        	ex.printStackTrace();
	}
    }
}
