/*
 * NestedDefinitions.java
 *
 * Created on 16. srpen 2007, 9:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.sourceInformation;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;

/**
 *
 * @author stastny
 */
public class NestedDefinitions {
    
    /** Creates a new instance of NestedDefinitions */
    public NestedDefinitions() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        org.dom4j.Document document = null;

        if (args.length == 0 || args[0] == null) {
            System.err.println("Nebyl zadan parametr.");
            return;
        }
            File file = new File(args[0]);
            //java.io.File file = new java.io.File("/home/stastny/iti/mujTest.c");

            try {
                cz.muni.fi.iti.scv.c2xml.CParser parser = new cz.muni.fi.iti.scv.c2xml.CParser(new java.io.FileInputStream(file));
                document = parser.runXmlParser();
            } catch (java.io.FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (java.lang.NullPointerException ex) {
                ex.printStackTrace();
            } catch (antlr.RecognitionException ex) {
                ex.printStackTrace();
            } catch (antlr.TokenStreamException ex) {
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
