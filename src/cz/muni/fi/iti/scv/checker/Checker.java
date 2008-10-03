/*
 * Checker.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.checker;

import cz.muni.fi.iti.scv.xml2cfg.ControlFlowGraph;

import java.util.Set;

/**
 * Represent a checker.
 * Any concrete checker implementation must implement all abstract methods
 */
public interface Checker {

    /**
     * Add function to checker
     * @param cfg control-flow graph of function
     */    
    void addCFG(ControlFlowGraph cfg);
    
    /**
     * Add all functions in parameter to checker
     * @param cfgs control-flow graphs of functions
     */   
    void addAllCFG(Set<ControlFlowGraph> cfgs);
    
    /**
     * Remove function from checker
     * @param cfg control-flow graph of function
     */
    void removeCFG(ControlFlowGraph cfg);
    
    /**
     * Remove all functions in parameter from checker
     * @param cfgs control-flow graphs of functions
     */  
    void removeAllCFG(Set<ControlFlowGraph> cfgs);
    
    /**
     * Remove all functions from checker
     */  
    void clearCFG();
    
    /**
     * Check all functions (cfgs)
     * @return set of found errors
     */
    Set<CheckerError> check();
    
}
