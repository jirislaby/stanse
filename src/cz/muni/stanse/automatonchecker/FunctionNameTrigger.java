
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.xml2cfg.CFGNode;

import org.dom4j.Element;


/**
 * FunctionNameTrigger is triggered when a function names match
 * @author xstastn
 */
public class FunctionNameTrigger extends AbstractTrigger {

    
    // Empty public constructor needed for reflection.
    public FunctionNameTrigger() {}
    
    public boolean isTriggered(CFGNode from, CFGNode to) {
        Element edge = from.getCFG().getEdgeElement(from, to);
        return edge.getName().equals("functionCall") &&
                pass.equals(edge.selectSingleNode("./id").getText());
    }

    public void loadTrigger(String param) {
        pass = param;
    }

    
    
    

    
}
