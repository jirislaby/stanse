/*
 * StaticCheckerDefVar.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.staticchecker;

import javax.xml.xpath.XPathExpressionException;
import net.sf.saxon.Configuration;
import net.sf.saxon.dom4j.DocumentWrapper;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.trans.XPathException;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Represents a variable in static checker definition
 */
public class StaticCheckerDefVar {
    
    private String name; // variable's name
    private String findXPath;
    private List<String> getXPath;
    
    private static Logger logger = Logger.getLogger(StaticCheckerDefVar.class);
   
    /**
     * Creates a new instance of StaticCheckerDefVar
     * @param element element with variable definition
     */   
    public StaticCheckerDefVar(Element varElement) {     
        
        getXPath=new ArrayList<String>();
        
        name = ((Element) varElement.selectSingleNode("name")).getTextTrim();
        findXPath = ((Element) varElement.selectSingleNode("find")).getText();
        
        for (Element element : (List<Element>) varElement.selectNodes("get")) { 
            getXPath.add(element.getText());          
        }          
       
    }     
    
    protected Set<List<String>> init(Document source) {
        Set<List<String>> assigns = new HashSet<List<String>>();        
        Set<String> isSame = new HashSet<String>();
        
        DocumentWrapper wrapper = new DocumentWrapper(source, "", new Configuration());
        XPathEvaluator evaluator = new XPathEvaluator();
        XPathExpression xpathExpr;
        net.sf.saxon.xpath.XPathEvaluator evaluatorIn = new net.sf.saxon.xpath.XPathEvaluator();
        Object selectedObject = null;
        try {
            xpathExpr = evaluator.createExpression(findXPath);
            for(Element element : (List<Element>)xpathExpr.evaluate(wrapper)) {
                List<String> value = new ArrayList<String>();
                String string = "";
                
                for(int i = 0; i < getXPath.size(); i++) {
                    try {
                        selectedObject = evaluatorIn.evaluate(getXPath.get(i),element);
                        value.add((String) selectedObject);
                        
                        string += "#" + (String) selectedObject;
                    } catch (XPathExpressionException ex) {
                        ex.printStackTrace();
                    }
                    logger.debug("Got variable get "+selectedObject);
                    
                }
                if (!isSame.contains(string)) {
                    assigns.add(value);
                    isSame.add(string);
                }
            }
        } catch (XPathException ex) {
            ex.printStackTrace();
        }
        
//        for (Element element : (List<Element>) source.selectNodes(findXPath)) {            
//        
//            List<String> value = new ArrayList<String>();
//            String string = "";
//            
//            for (int i = 0; i < getXPath.size(); i++) {   
//                // TODO: XPath2.0
//                value.add(((DefaultText) element.selectObject(getXPath.get(i))).getText());
//                
//                string += "#" + ((DefaultText) element.selectObject(getXPath.get(i))).getText();
//            }  
//            
//            if (!isSame.contains(string)) {
//                assigns.add(value);
//                isSame.add(string);
//            }
//        }
//        
        return assigns;
    }
    
    
    /**
     * Get name of this variable
     * @return name
     */   
    protected String getName() {
        return this.name;
    }
    
    protected static void varComb(Map<StaticCheckerDefVar,Set<List<String>>> varAssigns, Map<StaticCheckerDefVar,List<String>> defAssign, Set<Map<StaticCheckerDefVar,List<String>>> defAssigns) {
        if (varAssigns.isEmpty()) {
            defAssigns.add(defAssign);
            return;
        }
        
        StaticCheckerDefVar var = varAssigns.keySet().iterator().next();
        Set<List<String>> varAssign = varAssigns.get(var);
        Map<StaticCheckerDefVar,Set<List<String>>> newVarAssigns = new HashMap<StaticCheckerDefVar,Set<List<String>>>();
        newVarAssigns.putAll(varAssigns);
        newVarAssigns.remove(var);
        for (List<String> value : varAssign) {
            Map<StaticCheckerDefVar,List<String>> newDefAssign = new HashMap<StaticCheckerDefVar,List<String>>();
            newDefAssign.putAll(defAssign);
            newDefAssign.put(var,value);
            varComb(newVarAssigns, newDefAssign, defAssigns);
        }
    }
    
}
