/*
 * Properties.java
 *
 * Created on 14. 8. 2007, 15:40
 *
 * 
 * This is a set of static classes that take case of properties settings.
 * Typical use is Properties.getProperty("desiredKey", "defaultValue") or Properties.getProperty("desiredKey")
 */

package cz.muni.fi.iti.scv.props;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author stastny
 */
public class Properties {
    
    private static java.util.Properties properties = null;
    
    // Path to the properties XML file
    private static final String FILENAME = "properties.xml";
    
    /** Only static methods are supported */
    private Properties() {
    }
    
    /**
     * Loads the properties XML file and parses it.
     * @param forceReload Force XML file reloading
     */
    private static void loadProperties(boolean forceReload) {
        if(properties == null || forceReload) {
            properties = new java.util.Properties();
            File propertiesXml = new File(FILENAME);
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(propertiesXml));
                properties.loadFromXML(is);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Same as loadProperties(false)
     */
    private static void loadProperties() {
        loadProperties(false);
    }
    
    /**
     * Stores the properties in the XML file
     */
    private static void store() {
        File xml = new File(FILENAME);
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(xml));
            properties.storeToXML(os, "SCV properties");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    /**
     * Gets a property. The properties XML file is loaded automatically.
     * @param key Desired key
     * @see getProperty(String key, String defaultValue)
     */
    private static String getProperty(String key) {
        loadProperties();
        return properties.getProperty(key);
    }
    
    /**
     * Gets a property. The properties XML file is loaded automatically
     * @param key Desired key
     * @param defaultValue Default value, if the key is not found in the properties.
     */
    public static String getProperty(String key, String defaultValue) {
        loadProperties();
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Sets a property pair. 
     * This method has package friendly access and is only ment for testing or setting a new property pair.
     * @param key Key of the property
     * @param value Value of the property
     * @return Original value or null, if the value was not present in the properties.
     */
    static Object setProperty(String key, String value) {
        loadProperties(true);
        Object ret = properties.setProperty(key, value);
        store();
        
        return ret;
    }
    
    public enum VerbosityLevel {
        SILENT,
        LOW,
        HIGH
    
    }
    
}
