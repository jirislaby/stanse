package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.xml2cfg.CFGEdge;

import java.util.Iterator;
import java.util.Set;

/**
 * Represents an CFGEdge with automata. Automata can be an empty set, if there are
 * no associated automata.
 * @author xstastn
 */
class EdgeWithAutomata {

    private CFGEdge edge;
    private RunningAutomataSet automataSet = new RunningAutomataSet();

    /**
     * Constructor that sets both edge and automata.
     * @param edge Edge
     * @param automata Automata
     */
    public EdgeWithAutomata(CFGEdge edge, RunningAutomataSet automata) {
        this.edge = edge;
        this.automataSet = automata;
    }

    /**
     * Creates a new instance of EdgeWithAutomata, where automataSet left empty.
     * @param edge Edge
     */
    public EdgeWithAutomata(CFGEdge edge) {
        this.edge = edge;
    }

    public CFGEdge getEdge() {
        return edge;
    }

    public RunningAutomataSet getAutomataSet() {
        return automataSet;
    }

    /**
     * Deletes the duplicate automata from the set.
     * The automaton is considered duplicate if any of its predescessors have run at the current edge 
     * and were in the same state.
     * @param pairs Pairs automaton---state that are to be deleted from the current set of automata
     */
    public void deleteDuplicateAutomata(Set<Pair<Integer, Integer>> pairs) {
        for (Iterator<RunningAutomaton> it = automataSet.getAutomata().iterator(); it.hasNext();) {
            RunningAutomaton runningAutomaton = it.next();
            for (Integer id : runningAutomaton.getIds()) {
                if (pairs.contains(Pair.getInstance(id, runningAutomaton.getCurrentStateId()))) {
                    it.remove();
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Edge " + edge + " Automata: " + automataSet;
    }
}
