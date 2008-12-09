/**
 * @brief
 *
 */
package cz.muni.stanse.automatonchecker;

import java.util.Vector;

/**
 * @brief
 *
 * @see
 */
final class AutomatonState {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((AutomatonState)obj);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((CFG == null) ? 0 : CFG.hashCode());

        result = PRIME * result + ((automatonIDs == null) ?
                                                   0 : automatonIDs.hashCode());

        result = PRIME * result + symbolID;
        return result;
    }

    // package-private section

    AutomatonState(final cz.muni.stanse.parser.ControlFlowGraph CFG,
                   final int symbolID, final Vector<Integer> automatonIDs) {
        this.CFG = CFG;
        this.symbolID = symbolID;
        this.automatonIDs = automatonIDs;
    }

    AutomatonState(final cz.muni.stanse.parser.ControlFlowGraph CFG,
                   final int symbolID, final Integer automatonID) {
        this.CFG = CFG;
        this.symbolID = symbolID;
        this.automatonIDs = new Vector<Integer>(1);
        automatonIDs.add(automatonID);
    }

    cz.muni.stanse.parser.ControlFlowGraph getCFG() {
        return CFG;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    int getSymbolID() {
        return symbolID;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    Vector<Integer> getAutomatonIDs() {
        return automatonIDs;
    }


    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    boolean isEqualWith(AutomatonState other) {
        return getCFG() == other.getCFG() &&
               getSymbolID() == other.getSymbolID() &&
               getAutomatonIDs().equals(other.getAutomatonIDs());
    }

    // private section

    /**
     * @brief
     */
    private final cz.muni.stanse.parser.ControlFlowGraph CFG;
    /**
     * @brief
     */
    private final int symbolID;
    /**
     * @brief
     */
    private final Vector<Integer> automatonIDs;
}
