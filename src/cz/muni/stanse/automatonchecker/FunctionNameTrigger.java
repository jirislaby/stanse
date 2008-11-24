
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.xml2cfg.CFGNode;

import org.dom4j.Element;
import org.dom4j.Node;

/**
 * FunctionNameTrigger is triggered when a function names match
 * @author xstastn
 */
public class FunctionNameTrigger extends AbstractTrigger {

    
    // Empty public constructor needed for reflection.
    public FunctionNameTrigger() {}
    
    public boolean isTriggered(CFGNode from, CFGNode to) {
        Element edge = from.getCFG().getEdgeElement(from, to);

	if (!edge.getName().equals("functionCall"))
	    return false;

	Node id = edge.selectSingleNode("./expression/id");
	/* call not by name */
	if (id == null)
	    return false;

        return pass.equals(id.getText());
    }

    public void loadTrigger(String param) {
        pass = param;
    }

    
    
    

    
}
