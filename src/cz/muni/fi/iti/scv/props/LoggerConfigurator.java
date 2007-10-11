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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.PropertyConfigurator;

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
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
}
