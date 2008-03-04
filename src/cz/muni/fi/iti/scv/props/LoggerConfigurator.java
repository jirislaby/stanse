/*
 * LoggerConfigurator.java
 *
 * Created on 27. srpen 2007, 8:58
 *
 * Class configuring the log4j logger
 *
 */

package cz.muni.fi.iti.scv.props;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    
    /** Creates a new instance of LoggerConfigurator */
    public LoggerConfigurator() {
        java.util.Properties properties = new java.util.Properties();
        try {
            properties.load(new BufferedInputStream(new FileInputStream("log4j.properties")));
            PropertyConfigurator.configure(properties);
        } catch (FileNotFoundException ex) {
            System.err.println("log4j.properties file not found. Default logging properties will be used");
            Logger.getRootLogger().setLevel(Level.ERROR);
            Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout(), ConsoleAppender.SYSTEM_ERR));
                    
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
}
