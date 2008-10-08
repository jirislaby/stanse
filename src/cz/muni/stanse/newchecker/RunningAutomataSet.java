/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.newchecker;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;


/**
 *
 * @author xstastn
 */
public class RunningAutomataSet {
    
    private Set<RunningAutomaton> automata = new HashSet<RunningAutomaton>();

    private static Logger logger = Logger.getLogger(RunningAutomataSet.class);

    
    public RunningAutomataSet() {
    }

    public synchronized Set<RunningAutomaton> getAutomata() {
        return automata;
    }
    
    
    public RunningAutomataSet getCopy() {
        RunningAutomataSet returnSet = new RunningAutomataSet();
        for(RunningAutomaton automaton : automata) {
            returnSet.automata.add(automaton.getCopy());
        }
        
        return returnSet;
    }
    

    public boolean isEmpty() {
        return automata.isEmpty();
    }
    

    @Override
    public String toString() {
        return automata.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RunningAutomataSet other = (RunningAutomataSet) obj;
        if (this.automata != other.automata && (this.automata == null || !this.automata.equals(other.automata))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.automata != null ? this.automata.hashCode() : 0);
        return hash;
    }
    
    
    
    
}
