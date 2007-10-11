/*
 * Checker.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.checker;

import cz.muni.fi.iti.scv.xml2cfg.ControlFlowGraph;

import java.util.Set;
import java.util.HashSet;

/**
 * Represent a checker.
 * Any concrete checker implementation must implement all abstract methods
 */
public abstract class Checker {

    /**
     * Add function to checker
     * @param cfg control-flow graph of function
     */    
    public abstract void addCFG(ControlFlowGraph cfg);
    
    /**
     * Add all functions in parameter to checker
     * @param cfgs control-flow graphs of functions
     */   
    public abstract void addAllCFG(Set<ControlFlowGraph> cfgs);
    
    /**
     * Remove function from checker
     * @param cfg control-flow graph of function
     */
    public abstract void removeCFG(ControlFlowGraph cfg);
    
    /**
     * Remove all functions in parameter from checker
     * @param cfgs control-flow graphs of functions
     */  
    public abstract void removeAllCFG(Set<ControlFlowGraph> cfgs);
    
    /**
     * Remove all functions from checker
     */  
    public abstract void clearCFG();
    
    /**
     * Check all functions (cfgs)
     * @return set of found errors
     */
    public abstract Set<CheckerError> check();     
    
}
