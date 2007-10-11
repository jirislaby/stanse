package cz.muni.fi.iti.scv.preprocesor;

import cz.muni.fi.iti.scv.c2xml.CParser;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.List;

public class PrintTypeDefs {
    
    public PrintTypeDefs() {
    }
    

    public static void main(final String[] args) {
        
        Document document = null;

        if (args.length == 0 || args[0] == null) {
            System.err.println("Nebyl zadan parametr.");
            return;
        } 

        File file = new File(args[0]);
        
        //File file = new File("/home/jarek/sc_km_patch.c-final.c");
        
        try {
            CParser parser = new CParser(new FileInputStream(file));
            document = parser.runXmlParser();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (RecognitionException ex) {
            ex.printStackTrace();
        } catch (TokenStreamException ex) {
            ex.printStackTrace();
        }
       
        Element rootElement = document.getRootElement();
        
        for (Element declaration : (List<Element>) rootElement.selectNodes(".//declaration")) {
            
            Node storageClass = declaration.selectSingleNode("declarationSpecifiers/@storageClass");
            
            if (storageClass != null && storageClass.getStringValue().equals("typedef")) {
                //je to typedef
                
                System.out.println("typedef int " + declaration.selectSingleNode("initDeclarator//declarator/id").getText() + ";");
                
            } else {
                //System.out.println("nema storageClass");
            }
            
            //System.out.println(type.getTextTrim());
        }
        
     
    }
    
}
