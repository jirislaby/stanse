/*
 * SaxonTest.java
 *
 * Created on 20. srpen 2007, 11:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.sourceInformation;

import cz.muni.fi.iti.scv.c2xml.CParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import net.sf.saxon.Configuration;
import net.sf.saxon.dom4j.DocumentWrapper;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

/**
 *
 * @author stastny
 */
public class SaxonTest {
    
    /** Creates a new instance of SaxonTest */
    public SaxonTest() {
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
            
            
            DocumentWrapper wrapper = new DocumentWrapper(document, "", new Configuration());
            NodeInfo wrapperRoot = wrapper.getRoot();
            
//            XPathFactory xpathFactory = XPathFactoryImpl.newInstance();
//            XPath xpath = xpathFactory.newXPath();
//            InputSource is = new InputSource(new StringReader(document.asXML()));
//            SAXSource ss = new SAXSource(is);
//        
//            
//        try {
//            NodeInfo doc = ((XPathEvaluator)xpath).setSource(ss);
//            XPathExpression expr = xpath.compile("//*");
//            expr.evaluate(doc, XPathConstants.NODESET);
//        } catch (XPathExpressionException ex) {
//            ex.printStackTrace();
//        } catch (XPathException ex) {
//            ex.printStackTrace();
//        }
            
        
            XPathEvaluator evaluator = new XPathEvaluator();
        XPathExpression xpathExpr;
        try {
            //xpathExpr = evaluator.createExpression("//functionCall[matches(., \"^pthread_.*$\")]");
            xpathExpr = evaluator.createExpression("replace(\"pthread_create\", \"pthread_(.*)\", \"$1\")");
            List<String> list = (List<String>)xpathExpr.evaluate(wrapper);
            for(String node : list) {
                System.out.println(node);
            }
            
        } catch (XPathException ex) {
            ex.printStackTrace();
        }
            
            
            
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
            
        
    }
    
}
