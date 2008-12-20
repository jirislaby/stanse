/**
 * @brief Here are defined automata states for all the automata defined in XML
 *        automata definition file of automatonchecker package.   
 */
package cz.muni.stanse.automatonchecker;

import java.util.Vector;

/**
 * @brief This class represents automaton state for each automata class defined
 *        in XML automata definition file.
 * 
 *  Automata states represents the data, which are delivered between
 *  PatternLocation instances, which is the main idea in whole automatonchecker
 *  package. So each automaton state presented in some PatternLocation tells as,
 *  that related automaton (for that state) already was in the location in that
 *  state.
 *
 * @see cz.muni.stanse.automatonchecker.PatternLocation
 */
final class AutomatonState {

    // public section

    /**
     * @brief Required to be compatible with Java Object interface.
     */
    @Override
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((AutomatonState)obj);
    }

    /**
     * @brief Required to properly work in hashed containers.
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((cfg == null) ? 0 : cfg.hashCode());

        result = PRIME * result + ((automatonIDs == null) ?
                                                   0 : automatonIDs.hashCode());

        result = PRIME * result + symbolID;
        return result;
    }

    // package-private section

    /**
     * @brief Initializes automaton state for combined automata (i.e. automata
     *        which combine simpler ones). 
     *
     * @param cfg CFG the state belongs to (used only when the analysis is
     *            interprocedural).
     * @param symbolID Identifies exactly one state symbol in XML automaton
     *                 definition file.
     * @param automatonIDs Unique IDs of automata the state belongs to.
     * @see cz.muni.stanse.automatonchecker.AutomatonState#getAutomatonIDs()
     */
    AutomatonState(final cz.muni.stanse.codestructures.CFG cfg,
                   final int symbolID, final Vector<Integer> automatonIDs) {
        this.cfg = cfg;
        this.symbolID = symbolID;
        this.automatonIDs = automatonIDs;
    }

    /**
     * @brief Initializes automaton state for simple automata (i.e. automata
     *        which do not combine simpler ones). 
     *
     * @param cfg CFG the state belongs to (used only when the analysis is
     *            interprocedural).
     * @param symbolID Identifies exactly one state symbol in XML automaton
     *                 definition file.
     * @param automatonID Unique ID of automaton the state belongs to.
     * @see cz.muni.stanse.automatonchecker.AutomatonState#getAutomatonIDs()
     */
    AutomatonState(final cz.muni.stanse.codestructures.CFG cfg,
                   final int symbolID, final Integer automatonID) {
        this.cfg = cfg;
        this.symbolID = symbolID;
        this.automatonIDs = new Vector<Integer>(1);
        automatonIDs.add(automatonID);
    }

    /**
     * @brief Gets CFG the automaton state belongs to. (It is necessary for
     *        interprocedural analysis, when states must be translated between
     *        different functions (i.e. different CFGs)).
     *
     * @return CFG the automaton state belongs to.
     */
    cz.muni.stanse.codestructures.CFG getCFG() {
        return cfg;
    }

    /**
     * @brief Gets unique symbol of the automaton state.
     * 
     * Such symbol ('int' here) is unigue and 'one to one' relation to automata
     * symbols defined in XML automata definition file.  
     *
     * @return Unique symbol of the automaton state.
     */
    int getSymbolID() {
        return symbolID;
    }

    /**
     * @brief Gets vector of unique IDs of automata the state is related to.
     *
     * Because transition rules in XML automata definition file can combine 
     * automata states (i.e. can create between states of different automata),
     * so such combination is represented by another states of anofter new
     * automata, which is defined in terms of combination of simple automata.
     * And that is the reason, why states do not identifies their automaton by
     * single value, but whole vector of IDs of simple automata. States for
     * of automata, which are simple (do not combine other automata), returns
     * vector with exactly one ID in it.
     * 
     * Combined states can be quickly found in XML automaton definition file,
     * where there are those ones who has specified with more then one variable
     * (i.e. LL[A][B],...) 
     *
     * @return Vector of unique IDs of automata the state is related to.
     */
    Vector<Integer> getAutomatonIDs() {
        return automatonIDs;
    }


    /**
     * @brief Tells as if passed automata state is equal to this one. 
     *
     * @param other Automaton state to be checked if it is equal to this state
     *              or not.
     * @return True, if passed automaton state is equal to this one. False
     *         otherwise.
     */
    boolean isEqualWith(AutomatonState other) {
        return getCFG() == other.getCFG() &&
               getSymbolID() == other.getSymbolID() &&
               getAutomatonIDs().equals(other.getAutomatonIDs());
    }

    // private section

    private final cz.muni.stanse.codestructures.CFG cfg;
    private final int symbolID;
    private final Vector<Integer> automatonIDs;
}
