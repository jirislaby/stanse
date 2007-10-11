/*
 * FunctionVariables.java
 *
 * Created on 15. srpen 2007, 9:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.sourceInformation;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import cz.muni.fi.iti.scv.c2xml.CParser;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author stastny
 */
public class FunctionVariables {

    /** Creates a new instance of FunctionVariables */
    public FunctionVariables(File file, String functionName) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            Document document = null;

//        if (args.length == 0 || args[0] == null) {
//            System.err.println("Nebyl zadan parametr.");
//            return;
//        }
            //File file = new File(args[0]);
            java.io.File file = new java.io.File("/home/stastny/iti/mujTest.c");

            try {
                CParser parser = new CParser(new java.io.FileInputStream(file));
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
//            org.dom4j.io.XMLWriter writer = new org.dom4j.io.XMLWriter(new BufferedOutputStream(new FileOutputStream(new File("testing.xml"))), org.dom4j.io.OutputFormat.createPrettyPrint());
//            writer.write(document);
            
            ArrayList<Node> staticVars = new ArrayList<Node>();
            ArrayList<String> staticVarsIds = new ArrayList<String>();
            
            for (Element declaration : (List<org.dom4j.Element>) rootElement.selectNodes("//declaration/declarationSpecifiers[@storageClass=\"static\"]")) {
                Node id = declaration.selectSingleNode("parent::node()//id");
                staticVars.add(id);
                staticVarsIds.add(id.getStringValue());
                
                // hledam, jestli je deklarovana uvnitr funkce
                Node function = id.selectSingleNode("ancestor::functionDefinition");
                if(function != null) {
                    System.out.println("Promenna "+id.getStringValue() + " je deklarovana uvnitr funkce "+ function.selectSingleNode("./declarator/id").getStringValue());
                }
                
            }
            
            
            // Vsechny funkce v dokumentu -- odecteme od static.
            ArrayList<Node> allFunctions = new ArrayList<Node>();
            ArrayList<String> allFunctionsIds = new ArrayList<String>();
            for (Element declaration : (List<org.dom4j.Element>) rootElement.selectNodes("//functionDefinition")) {
                Node id = declaration.selectSingleNode("./declarator/id");
                allFunctions.add(id);
                allFunctionsIds.add(id.getStringValue());
//                System.out.println("ID function: "+id.getStringValue());
            }
//            
//            for(String node : staticVarsIds) {
//                if(allFunctionsIds.contains(node)) {
////                    System.out.println("Smazal bych: "+ node);
//                }
//            }
            
            staticVarsIds.removeAll(allFunctionsIds);
            
            
            for(Node function : (List<Node>) rootElement.selectNodes("//functionDefinition")) {
                List<Node> variablesUsedInFunction = (List<Node>) function.selectNodes("//id[parent::node()[node() != \"functionCall\"]]");
                for(Node variable : variablesUsedInFunction) {
                    if(staticVars.contains(variable) && !allFunctionsIds.contains(variable.getStringValue())) {
                        System.out.println("Function: "+ function.selectSingleNode("./declarator/id").getStringValue()+ 
                            " is using static variable: "+ variable.getStringValue());
                    }
                    
                }
            }
            
            for(String node : staticVarsIds) {
                System.out.println("Static variable ID: "+ node);
            }
//            
//            for(Node node : staticVars) {
//                List<Node> inFunction = rootElement.selectNodes(".//functionDefinition//id[text()=\""+node.getStringValue()+"\"]");
//                for(Node n : inFunction) {
//                    Node anc = n.selectSingleNode("ancestor::functionDefinition/declarator/id");
////                    System.out.println("Function: "+anc.getStringValue()+" is using static variable: " +n.getStringValue());
////                    System.out.println(n.getStringValue());
//                }
//                System.out.println(node.getStringValue());
//            }
            
            
    }
}
