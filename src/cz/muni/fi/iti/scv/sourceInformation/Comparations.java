/*
 * Comparations.java
 *
 * Created on 16. srpen 2007, 12:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.sourceInformation;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;


/**
 *
 * @author stastny
 */
public class Comparations {
    
    /** Creates a new instance of Comparations */
    public Comparations() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        org.dom4j.Document document = null;
        
//        if (args.length == 0 || args[0] == null) {
//            System.err.println("Nebyl zadan parametr.");
//            return;
//        }
//        File file = new File(args[0]);
        File file = new java.io.File("/home/stastny/iti/prep/out/sc_common.c-final-prep.c");
        
        try {
            cz.muni.fi.iti.scv.c2xml.CParser parser = new cz.muni.fi.iti.scv.c2xml.CParser(new java.io.FileInputStream(file));
            
            
            document = parser.runXmlParser();
            
            XMLWriter writer = new XMLWriter(new BufferedOutputStream(new FileOutputStream(new File("testingDalsi.xml"))), org.dom4j.io.OutputFormat.createPrettyPrint());
            writer.write(document);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
        
        
        org.dom4j.Element rootElement = document.getRootElement();
        
        String functionName = "";
        for(Node function : (List<Node>) rootElement.selectNodes("//functionDefinition//functionDefinition")) {
            functionName = function.selectSingleNode("./declarator/id").getStringValue();
            System.out.println("Function " + functionName);
            
        }
        
    }
    
}
