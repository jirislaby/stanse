/*
 * CheckerErrorOld.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.stanse.ownershipchecker;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.TreeSet;

import org.dom4j.Element;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Comparator;


/**
 * Represent a checker error.
 * Any concrete checker error implementation must implement all abstract methods
 */
public abstract class CheckerErrorOld {    
    
    class ErrorTraceByDescriptionComparator implements Comparator<ErrorTraceOld> {

        public int compare(ErrorTraceOld a, ErrorTraceOld b) {
            return a.getDescription().compareTo(b.getDescription());
        }

    }

    /** Local trace cache */
    private Set<ErrorTraceOld> traceCache = new TreeSet<ErrorTraceOld>(new ErrorTraceByDescriptionComparator());
    
    /**
     * Get node (in control flow graph) where the error is
     * @return error node
     */ 
    public abstract CFGNode getNode();  
    
    /**
     * Get coresponding error traces (for simulating)
     * @return set of error traces
     */
    protected abstract Set<ErrorTraceOld> getErrorTraces();  
    
    /**
     * Read error traces from local traces cache
     * 
     * @return Set of traces
     */
    public Set<ErrorTraceOld> getErrorTracesFromCache() {
        if(traceCache.isEmpty()) {
           this.traceCache = this.getErrorTraces(); 
        }
           
        return traceCache;
    }
    
    /**
     * Get human readable description of this error
     * @return string error description
     */ 
    public abstract String getDescription();
    
    /**
     * Get string notice to node. Not exactly only for nodes in error traces.
     * @param node cfg node
     * @return text note to node 
     */
    public abstract String nodeToText(CFGNode node);  
  
    /**
     * Get string notice to edge in error trace. Don't forget that one edge in control-flow graph may be included in more than one error trace.
     * @param errorTrace error trace the edge belongs to
     * @param from the begining of the edge
     * @param to the end of the edge
     * @return text node to edge
     */ 
    public abstract String errorTraceEdgeToText(ErrorTraceOld errorTrace, CFGNode from, CFGNode to);     
    
    /**
     * Is node in any error trace of this error?
     * @param node node
     * @return true if yes otherwise false
     */ 
    public final boolean isInErrorTraces(CFGNode node) {
        for (ErrorTraceOld errorTrace : this.getErrorTracesFromCache()) {
            if (errorTrace.contains(node)) return true;           
        }
        return false;
    }    
    
    /**
     * Is edge in any error trace of this error?
     * @param from the begining of the edge
     * @param to the end of the edge
     * @return true if yes otherwise false
     */ 
    public boolean isInErrorTraces(CFGNode from, CFGNode to) {
        CFGNode last;
        for (List<CFGNode> errorTrace : this.getErrorTracesFromCache()) {
            last = null;
            for (CFGNode node : errorTrace) {
                if (node == from && last == to) return true;   
                last = node;
            }            
        }
        return false;
    }
    
    public String toDot() {
        StringBuilder output = new StringBuilder();
        String label = "";
        String color = "";
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
        
        output.append("digraph CFG { \n")
                .append("   node [shape=circle, fixedsize=true, height=0.4]; \n")
                .append("   node [fontsize=12]; \n");
        
        nodesToDo.add(getNode().getCFG().getStartNode());       
        
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            label = "\"" + nodeToText(node) + node.getNumber() + "\"";
           
            color = "black";
            if (isInErrorTraces(node)) {
                color = "green";
                if (getNode() == node) color += ",style = filled";
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
                output.append("   " + edgeToDot(node, succ) + "; \n");
                
                if (!nodesDone.contains(succ)) nodesToDo.add(succ);
            }
        }
        
        output.append("} \n");
        return output.toString();
        
    }
    
    
    /**
     * Get Graphviz source of edge
     * @param from the begining of the edge
     * @param to the end of the edge
     * @return graphviz source code
     */    
    private String edgeToDot(CFGNode from, CFGNode to) {
        StringBuilder output = new StringBuilder(); 
        String color;
        StringBuilder label = new StringBuilder();
        
        Element element = getNode().getCFG().getEdgeElement(from, to);
        
        color = "black";
        if (isInErrorTraces(to, from)) {
            color = "green";            
        }
        
        label.append("\"").append(elementToString(element)).append("\"");
        
        if (getNode().getCFG().getEdgeConditionType(from, to) != null) {
            if (getNode().getCFG().getEdgeConditionType(from, to) == true) {
                label.append(",fontcolor=blue");
            } else {
                label.append(",fontcolor=red");
            }
        }
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
    
    /**
     * Get human readable reprezentation of edge element
     * @param element element on edge
     * @return string reprezentation
     */
    private static String elementToString(Element element) {
        if (element.getName().equals("functionCall")) {
            Element e = (Element) element.node(0);   
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

    
    public String toString() {
        return this.getDescription();
    }
    
 
}
