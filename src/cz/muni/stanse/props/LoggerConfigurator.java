/*
 * LoggerConfigurator.java
 *
 * Created on 27. srpen 2007, 8:58
 *
 * Class configuring the log4j logger
 *
 */
package cz.muni.stanse.props;

import cz.muni.stanse.Stanse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;

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
        switch (Stanse.getVerbosityLevel()) {
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
        	try {

        		if(Stanse.getDebug()) {
        			ConsoleAppender consoleAppender = new ConsoleAppender(new SimpleLayout(), ConsoleAppender.SYSTEM_ERR);
        			consoleAppender.setThreshold(loggingLevel);
        			Logger.getRootLogger().addAppender(consoleAppender);
        		}

        		InputStream bis=new BufferedInputStream(new FileInputStream("log4j.properties"));             
        		properties.load(bis);
        		bis.close();
        		PropertyConfigurator.configure(properties);
        	} catch (FileNotFoundException ex) {
        		System.err.println("log4j.properties file not found. Default logging properties will be used. Exception says: "+ex);
        		ConsoleAppender defaultAppender = new ConsoleAppender(new SimpleLayout(), ConsoleAppender.SYSTEM_ERR);
        		defaultAppender.setThreshold(loggingLevel);
        		Logger.getRootLogger().addAppender(defaultAppender);
        	}
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        } 


    }
}
