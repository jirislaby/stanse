/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.xml2cfg.CFGNode;

import java.util.List;
import java.util.Set;

/**
 *
 * @author xstastn
 */
public interface TransitionTrigger {

    /**
     * Called right after class instantiation. 
     * @param pass Argument of the trigger.
     */
    public void loadTrigger(String pass);
    
    /**
     * Decides whether to trigger the trigger for the given pair of nodes.
     * @param from First node
     * @param to Second node
     * @return True if the trigger is trigger for these nodes, false otherwise
     */
    public boolean isTriggered(CFGNode from, CFGNode to);
    
    /**
     * Returns the list of params of this trigger
     * @return the list of params of this trigger
     */
    public List<AutomatonParam> getTriggerParams();
    
    /**
     * Add one param
     * @param param Param to be added to the transition
     */
    public void addTriggerParam(AutomatonParam param);
    
    /**
     * Assigns the values of the params based on the data it loads from the nodes.
     * AST is accesible from the CFG.
     * @param from From node
     * @param to To node
     * @return Set of assigned params.
     */
    public Set<AssignedParam> assignParams(CFGNode from, CFGNode to);
    
    /**
     * Returns the pass string of the trigger = the string passed to the loadTrigger method
     * @return the pass string of the trigger
     */
    public String getPassString();
}
