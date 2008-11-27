package cz.muni.stanse.scvgui;

import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.checker.CheckerErrorAlgo;
import cz.muni.stanse.xml2cfg.ControlFlowGraph;
import cz.muni.stanse.xml2cfg.CFGNode;
import org.dom4j.Element;
import java.util.HashSet;


final class CheckerErrorToDot {

    public static String run(final ErrorTrace trace) {

        final StringBuilder output = new StringBuilder();
        String label = "";
        String color = "";
        final HashSet<CFGNode> nodesToDo = new HashSet<CFGNode>();
        final HashSet<CFGNode> nodesDone = new HashSet<CFGNode>();
        final CFGNode errNode = trace.getErrorTrace().get(
                                   trace.getErrorTrace().size() - 1).getFirst();
        final ControlFlowGraph cfg = errNode.getCFG();

        output.append("digraph CFG { \n")
                .append("   node [shape=circle, fixedsize=true, height=0.4]; \n")
                .append("   node [fontsize=12]; \n");
        
        nodesToDo.add(cfg.getEntryNode());       
        
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            label = "\"" + node.toString() + "\"";
           
            color = "black";
            if (CheckerErrorAlgo.findFirstNode(trace,node) != null) {
                color = "green";
                if (errNode == node) color += ",style = filled";
            }
                        
            output.append("   ")
                    .append(node.getNumber())
                    .append(" [label=")
                    .append(label)
                    .append(",color=")
                    .append(color)
                    .append("]; \n");
            
            nodesDone.add(node);
            
            for (CFGNode succ : node.getSuccessors()) { 
                output.append("   " + edgeToDot(trace,cfg,node,succ) + "; \n");
                
                if (!nodesDone.contains(succ)) nodesToDo.add(succ);
            }
        }
        
        output.append("} \n");
        return output.toString();
        
    }
    
    
    private static String edgeToDot(final ErrorTrace trace,
                                    final ControlFlowGraph cfg,
                                    final CFGNode from, final CFGNode to) {

        final String color =
                (CheckerErrorAlgo.findFirstEdge(trace,from,to) == null) ?
                    "black" : "green";

        final Element element = cfg.getEdgeElement(from, to);
        final StringBuilder label = new StringBuilder();
        label.append("\"").append(elementToString(element)).append("\"");
        
        if (cfg.getEdgeConditionType(from, to) != null) {
            if (cfg.getEdgeConditionType(from, to) == true) {
                label.append(",fontcolor=blue");
            } else {
                label.append(",fontcolor=red");
            }
        }

        final StringBuilder output = new StringBuilder(); 
        output.append(from.getNumber())
                .append(" -> ")
                .append(to.getNumber())
                .append(" [color=")
                .append(color)
                .append(",label=")
                .append(label)
                .append("]");
        
        return output.toString();
    }     
    
    private static String elementToString(final Element element) {
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
