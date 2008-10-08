/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.newchecker;

import cz.muni.stanse.xml2cfg.CFGEdge;
import cz.muni.stanse.xml2cfg.CFGNode;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author xstastn
 */
public class AutomatonState {
    
    private static AtomicInteger globalId = new AtomicInteger(0);
    
    private int id;
    
    private String name;
    private String description;
    private Set<AutomatonTransition> transitions = new HashSet<AutomatonTransition>();

    public AutomatonState(String name, String description) {
        this.id = globalId.incrementAndGet();
        this.name = name;
        this.description = description;
    }
    
    public void addTransition(AutomatonTransition transition) {
        this.transitions.add(transition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
    
    
    
    /**
     * 
     * @return EOR transition if exists. Null otherwise
     */
    public AutomatonTransition getEOR() {
        for(AutomatonTransition transition: transitions) {
            if(transition.isEOR()) {
                return transition;
            }
        }
        return null;
    }
    
    
    /**
     * Gets the transition, where trigger is triggered.
     * @param from The CFG Node from, passed as parameter to isTriggered method
     * @param to The CFG Node from, passed as parameter to isTriggered method
     * @return The transition trigger found by the criteria (if any), null otherwise
     */
    public AutomatonTransition getTransitionByTriggeredTrigger(CFGNode from, CFGNode to) {
        for(AutomatonTransition transition: transitions) {
            if(transition.getTrigger() != null && transition.getTrigger().isTriggered(from, to)) {
                return transition;
            }
        }
        return null;
    }
    
    /**
     * @see #getTransitionByTriggeredTrigger(CFGNode, CFGNode) 
     */
    public AutomatonTransition getTransitionByTriggeredTrigger(CFGEdge edge) {
        return getTransitionByTriggeredTrigger(edge.getFrom(), edge.getTo());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AutomatonState other = (AutomatonState) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        return hash;
    }

    
    @Override
    public String toString() {
        return id + " ("+name+")";
    }
    
    
    
    
    
}
