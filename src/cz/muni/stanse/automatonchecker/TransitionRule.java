/**
 * @brief
 *
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;

/**
 * @brief
 *
 * @see
 */
final class TransitionRule {

    // package-private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    TransitionRule(final XMLTransitionRule XMLrule, final int automatonID) {
        this.XMLrule = XMLrule;
        this.automatonID = automatonID;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    Pair< Boolean,AutomatonState>
    transformAutomatonState(final AutomatonState state) {
        return getXMLrule().transformAutomatonState(state,getAutomatonID());
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    int getAutomatonID() {
        return automatonID;
    }

    // private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private XMLTransitionRule getXMLrule() {
        return XMLrule;
    }

    /**
     * @brief
     */
    private final XMLTransitionRule XMLrule;
    /**
     * @brief
     */
    private final int automatonID;
}
