/*
 * InterproceduralStaticCheckerError.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.staticchecker;

import cz.muni.fi.iti.scv.checker.*;
import cz.muni.fi.iti.scv.xml2cfg.*;
import cz.muni.fi.iti.scv.checker.CheckerError;

import org.dom4j.Element;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents a error in procedure found by static checker
 */
public class StaticCheckerError extends CheckerError {
    
    private CFGNode errorNode; // node (in control flow graph) with error
   
    private StaticChecker checker; // satic checker which found this error
    private StaticCheckerDef def; // static checker definition which define this error
    private Map<StaticCheckerDefVar,List<String>> defAssign; // variables assign which violates this error
    private StaticCheckerDefError errorRule; // definition's error rule which violates this error
    
    private Set<ErrorTrace> errorTraces; // set of coresponding error traces (for simulating)
    
    private Map<CFGNode,Set<Character>> nodeToStateSet; 
    
    private Set<Character> startState; 
    
    private String description; // human readable description of error
    
    /**
     * Creates a new instance of StaticCheckerError
     * @param node node (in control flow graph) with error
     * @param checker satic checker which found this error
     * @param def static checker definition which define this error
     * @param defAssign variables assign which violates this error
     * @param errorRule definition's error rule which violates this error
     */
    public StaticCheckerError(CFGNode node, StaticChecker checker, StaticCheckerDef def, Map<StaticCheckerDefVar,List<String>> defAssign, StaticCheckerDefError errorRule, Map<CFGNode,Set<Character>> nodeToStateSet, Set<Character> startState) {
        
        this.errorNode = node;
        this.nodeToStateSet = nodeToStateSet;
       
        this.checker = checker;
        this.def = def;
        this.defAssign = defAssign;
        this.errorRule = errorRule;
        this.startState = startState;
        
        // create error traces
        errorTraces = new HashSet<ErrorTrace>();
        if (errorRule.getContainsState() != null) { 
            for (Character ch : errorRule.getContainsState()) {
                errorTraces.add(new ErrorTrace(getErrorTrace(ch), "Violates the rule: " + errorRule.getName()));
            }
        }
        
        // create description
        description = new String();
        if (defAssign != null) {
            description += " (";
            for (StaticCheckerDefVar var : defAssign.keySet()) {
                description += var.getName() + "=" + defAssign.get(var) + ";";
            }
            description += ")";
        }
        description = errorNode.getCFG().getFunctionName() + " :: " + def.getName() +description + " :: " + errorRule.getName() + " (node=" + errorNode.getNumber() + ")";
        
    }   
       
    /**
     * Get node (in control flow graph) where the error is
     * @return error node
     */ 
    public CFGNode getNode() {
        return errorNode;
    } 
    
    /** 
     * Get coresponding error traces (for simulating)
     * @return set of error traces
     */ 
    public Set<ErrorTrace> getErrorTraces() { 
        return errorTraces;
    }
    
    /**
     * Get human readable description of this error
     * @return string error description
     */    
    public String getDescription() {
        return description;
    }    
    
    /**
     * Get string notice to node. Not exactly only for nodes in error traces.
     * @param node cfg node
     * @return text note to node 
     */     
    public String nodeToText(CFGNode node) {
        return (nodeToStateSet.containsKey(node)) ? this.nodeToStateSet.get(node).toString() : "";
    }
    
    /**
     * Get string notice to edge in error trace. Don't forget that one edge in control-flow graph may be included in more than one error trace.
     * @param errorTrace error trace the edge belongs to
     * @param from the begining of the edge
     * @param to the end of the edge
     * @return text node to edge
     */ 
    public String errorTraceEdgeToText(ErrorTrace errorTrace, CFGNode from, CFGNode to) {
        return "";
    }

   /**
     * Get static checker definition which define this error
     * @return static checker definition
     */ 
    protected StaticCheckerDef getDef() {
        return def;
    }  
    
    /**
     * Get variables assign whitch violates this error
     * @return variables assign
     */ 
    protected Map<StaticCheckerDefVar,List<String>> getDefAssign() {
        return defAssign;
    }
    
    /**
     * Get definition's error rule which violates this error
     * @return error rule
     */ 
    protected StaticCheckerDefError getErrorRule() {
        return errorRule;
    }  
    
    /**
     * Find error trace
     * @param ch "bad" state which violates error
     * @return one coresponding error trace
     */
    private List<CFGNode> getErrorTrace(Character ch) {
        
        List<CFGNode> errorTrace = new ArrayList<CFGNode>();
        
        Stack<CFGNode> nodesToDo = new Stack<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
                 
        nodesToDo.push(errorNode);           
        
        int minNum;
        CFGNode min;
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.pop();
            nodesDone.add(node);
            errorTrace.add(node);

            minNum=Integer.MAX_VALUE;
            min = null;
            for (CFGNode pred : node.getPredecessors()) {                
                if (checker.getState(this,pred,startState).contains(ch) && !nodesDone.contains(pred)) {
                     if (minNum > pred.getNumber()) {
                        minNum = pred.getNumber();
                        min = pred;
                    }              
                }
            }
            if (min!=null) {
                nodesToDo.add(min);
            }
        } 
        java.util.Collections.reverse(errorTrace);
        return errorTrace;
    }   
           
}
