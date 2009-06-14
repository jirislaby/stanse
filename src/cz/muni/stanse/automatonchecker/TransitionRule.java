/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
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
    TransitionRule(final XMLTransitionRule XMLrule,
                   final SimpleAutomatonID automatonID) {
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
    SimpleAutomatonID getAutomatonID() {
        return automatonID;
    }

    // private section

    private XMLTransitionRule getXMLrule() {
        return XMLrule;
    }

    private final XMLTransitionRule XMLrule;
    private final SimpleAutomatonID automatonID;
}
