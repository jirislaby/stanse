/*
 * CFGNode.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.xml2cfg;

import java.util.Set;
import java.util.HashSet;

/**
 * Represents a node in control-flow graph (class ControlFlowGraph in this package)
 */
public class CFGNode {    
    
    private static int numberCounter=0; // for generating an unique node number
    
    private int number; // node number, used in print 
    
    private ControlFlowGraph cfg; // corresponding control-flow graph
          
    private Set<CFGNode> predecessors = new HashSet<CFGNode>(); // represents predecessors of this node
    private Set<CFGNode> successors = new HashSet<CFGNode>(); // represents successors of this node
    
    /**
     * Creates a new instance of CFGNode
     */
    protected CFGNode(ControlFlowGraph cfg) {
        number = numberCounter++; // generating an unique node number
        this.cfg = cfg;
    }
    
    /**
     * Get unique node number
     * @return unique node number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Get all predecessors of node
     * @return (read only) set of this node's predecessors
     */
    public Set<CFGNode> getPredecessors() {
        Set<CFGNode> copy = new HashSet<CFGNode>();
        copy.addAll(predecessors);
        return copy;
    }

    /**
     * Get all successors of node
     * @return (read only) set of this node's successors
     */
    public Set<CFGNode> getSuccessors() {
        Set<CFGNode> copy = new HashSet<CFGNode>();
        copy.addAll(successors);
        return copy;
    }
    
    /**
     * Insert node to predecessors set
     * @param pred new predecesor
     */
    protected void addPred(CFGNode pred) {
        predecessors.add(pred);
    }
    
    /**
     * Insert node to successors set
     * @param succ new successor
     */
    protected void addSucc(CFGNode succ) {
        successors.add(succ);
    }
    
    /**
     * Get corresponding control-flow graph
     * @return control-flow graph
     */
    public ControlFlowGraph getCFG() {
        return cfg;
    }
    
    /**
     * Join node with another node
     * @param temp node to join with
     */
    protected void joinWithNode(CFGNode temp) {
        
        // correct all edges to "temp"
        predecessors.addAll(temp.predecessors);        
        for (CFGNode tempPred : temp.predecessors) {
            if (tempPred.successors.remove(temp)) {
                tempPred.successors.add(this);
            }                                
        }
        
        // correct all edges from "temp"
        successors.addAll(temp.successors);
        for (CFGNode tempSucc : temp.successors) {
            if (tempSucc.predecessors.remove(temp)) {
                tempSucc.predecessors.add(this);                        
            }        
        }
   
    } 
    
    
}

