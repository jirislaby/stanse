/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.automatonchecker.exceptions.AutomatonException;
import cz.muni.stanse.xml2cfg.CFGEdge;
import cz.muni.stanse.xml2cfg.CFGNode;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

/**
 *
 * @author xstastn
 */
public class RunningAutomaton {
    
    private static AtomicInteger globalId = new AtomicInteger(0);
    
    /**
     * Unique identificator.
     */
    private final int id;
    
    /**
     * Ids of all predescesors including self.
     */
    private Set<Integer> ids = new HashSet<Integer>();
    
    private Automaton automaton;
    
    private Set<AssignedParam> assignedParams = new HashSet<AssignedParam>();
    
    private AutomatonState currentState = null;
    
    private boolean inErrorState = false;
    
    private static Logger logger = Logger.getLogger(Automaton.class);
    
        
    public RunningAutomaton(Automaton automaton, AutomatonState state) throws AutomatonException {
        this();
        if(automaton == null) {
            throw new AutomatonException(new NullPointerException("Automaton is null"));
        }
        if(state == null) {
            throw new AutomatonException(new NullPointerException("State is null"));
        }
        
        this.automaton = automaton;
        this.currentState = state;
    }
    
    private RunningAutomaton() {
        this.id = globalId.incrementAndGet();
        this.ids.add(id);
    };

    public RunningAutomaton getCopy() {
        RunningAutomaton retAutomaton = new RunningAutomaton();
        retAutomaton.ids.addAll(this.ids);
        retAutomaton.automaton = this.automaton;
        retAutomaton.currentState = this.currentState;
        retAutomaton.assignedParams.addAll(this.assignedParams);
        
        return retAutomaton;
    }

    
    public AutomatonState getCurrentState() {
        return currentState;
    }
    
    /**
     * Returns the ID of the current state of the running automaton.
     * If the current state is null, 0 is rerurned
     * @return ID of the current state. 0 if the current state is null.
     */
    public int getCurrentStateId() {
        if(currentState == null) {
            return 0;
        } else {
            return currentState.getId();
        }
    }

    public void setCurrentState(AutomatonState currentState) {
        this.currentState = currentState;
    }
    

    public Set<AssignedParam> getAssignedParams() {
        return assignedParams;
    }

    public boolean isInErrorState() {
        return inErrorState;
    }

    public void setInErrorState(boolean inErrorState) {
        this.inErrorState = inErrorState;
    }
    
    

    public void addAssignedParams(Set<AssignedParam> params) {
        this.assignedParams.addAll(params);
    }
    
    public AutomatonTransition getTransitionByTrigger(CFGNode from, CFGNode to) {
        AutomatonTransition transitionByTriggeredTrigger = this.currentState.getTransitionByTriggeredTrigger(from, to);
        if(transitionByTriggeredTrigger != null) {
            Set<AssignedParam> assignParams = transitionByTriggeredTrigger.getTrigger().assignParams(from, to);
            if(this.assignedParams.containsAll(assignParams)) {
                return transitionByTriggeredTrigger;
            }
        }
        return null;
    }
    
    public AutomatonTransition getTransitionByTrigger(CFGEdge edge) {
        return getTransitionByTrigger(edge.getFrom(), edge.getTo());
    }

    public int getId() {
        return id;
    }

    public Set<Integer> getIds() {
        return ids;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RunningAutomaton other = (RunningAutomaton) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id;
        return hash;
    }
    
    
    @Override
    public String toString() {
        return "IDs: "+ids+ " HashCode: "+hashCode()+" Current state: "+currentState+", params: "+assignedParams;
    }
    
    
    
}
