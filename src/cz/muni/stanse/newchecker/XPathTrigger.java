/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.newchecker;

import cz.muni.stanse.xml2cfg.CFGNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.saxon.Configuration;
import net.sf.saxon.dom4j.DocumentWrapper;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;
import org.dom4j.Element;

/**
 *
 * @author xstastn
 */
public class XPathTrigger extends AbstractTrigger {

    
    public XPathTrigger() {};
    

    public boolean isTriggered(CFGNode from, CFGNode to) {
            Element edge = from.getCFG().getEdgeElement(from, to);
            DocumentWrapper wrapper = new DocumentWrapper(edge.getDocument(), "", new Configuration());
            XPathEvaluator evaluator = new XPathEvaluator();
            XPathExpression xpathExpr;
            net.sf.saxon.xpath.XPathEvaluator evaluatorIn = new net.sf.saxon.xpath.XPathEvaluator();
            Object selectedObject = null;
        try {
            xpathExpr = evaluator.createExpression(pass);
            for (Element element : (List<Element>) xpathExpr.evaluate(wrapper)) {
                
            }
        } catch (XPathException ex) {
            Logger.getLogger(XPathTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        throw new UnsupportedOperationException("Not implemented yet");    
        
    }

    public void loadTrigger(String param) {
        pass = param;
    }
    
    
}
