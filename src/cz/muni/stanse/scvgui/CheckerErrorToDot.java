package cz.muni.stanse.scvgui;

import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.parser.CFG;
import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.utils.Trinity;

import org.dom4j.Element;
import java.util.HashSet;


final class CheckerErrorToDot {

    public static String run(final ErrorTrace trace) {

        final StringBuilder output = new StringBuilder();
        final HashSet<CFGNode> nodesToDo = new HashSet<CFGNode>();
        final HashSet<CFGNode> nodesDone = new HashSet<CFGNode>();
        final CFGNode errNode = trace.getErrorTrace().get(
                                   trace.getErrorTrace().size() - 1).getFirst();
        final HashSet<CFGNode> traceNodes = new HashSet<CFGNode>();
        for (Trinity<CFGNode,String,CFG> nodeID : trace.getErrorTrace())
            traceNodes.add(nodeID.getFirst());

        output.append("digraph CFG { \n");
        
        nodesToDo.add(trace.getErrorTrace().get(0).getThird().getStartNode());
        
        while (!nodesToDo.isEmpty()) {
            final CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            final boolean inTrace = traceNodes.contains(node);

            output.append("   ")
                  .append(node.getNumber())
                  .append(" [label=")
                  .append("\"" + node.getNumber() + ": "
                               + elementToString(node.getElement()) + "\"")
                  .append(",color=")
                  .append((inTrace) ? "green" + ((errNode == node)?
                                                        ",style = filled" :
                                                        "") :
                                      "black")
                  .append("]; \n");

            nodesDone.add(node);
            
            for (CFGNode succ : node.getSuccessors()) { 
                output.append("   " + edgeToDot(trace,node,succ,inTrace,
                                                traceNodes.contains(succ)) +
                              "; \n");
                if (!nodesDone.contains(succ))
                    nodesToDo.add(succ);
            }
        }
        
        output.append("} \n");
        return output.toString();
        
    }
    
    
    private static String edgeToDot(final ErrorTrace trace,
                                    final CFGNode from, final CFGNode to,
                                    final boolean fromIsInTrace,
                                    final boolean toIsInTrace) {
        return from.getNumber() + " -> " + to.getNumber() + " [color=" +
               ((fromIsInTrace && toIsInTrace) ? "green" : "black") +
               ",label=\"\"]";
    }     
    
    private static String elementToString(final Element element) {
// TODO: remove this check, when ALL the CFG nodes will contain related XML
//       element. 
if (element == null)
    return "null";
        if (element.getName().equals("functionCall")) {
            final Element e = (Element) element.node(0);   
            return e.getText()+"()";
        }
        
        if (element.getName().equals("continueStatement")) {            
            return "continue";
        }
        
        if (element.getName().equals("breakStatement")) {            
            return "break";
        }
        
        return element.getName();        
    }

}
