/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.newchecker;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author xstastn
 */
public class AutomatonState {
    private String name;
    private String description;
    private Set<AutomatonTransition> transitions = new HashSet<AutomatonTransition>();

    public AutomatonState(String name, String description) {
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
    
    public AutomatonTransition getTransitionByTrigger(String trigger) {
        for(AutomatonTransition transition: transitions) {
            if(transition.getTrigger() != null && transition.getTrigger().equals(trigger)) {
                return transition;
            }
        }
        return null;
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
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    
}
