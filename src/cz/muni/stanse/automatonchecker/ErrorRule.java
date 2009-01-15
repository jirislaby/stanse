/**
 * @file ErrorRule.java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import java.util.HashSet;

/**
 * @brief
 *
 * @see
 */
final class ErrorRule {

    // package-private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    ErrorRule(final XMLErrorRule XMLrule, final int automatonID) {
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
    String getErrorDescription() {
        return getXMLrule().getErrorDescription();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    int getErrorLevel() {
        return getXMLrule().getErrorLevel();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorEntryMessage() {
        return getXMLrule().getErrorEntryMessage();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorBeginMessage() {
        return getXMLrule().getErrorBeginMessage();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorPropagMessage() {
        return getXMLrule().getErrorPropagMessage();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorEndMessage() {
        return getXMLrule().getErrorEndMessage();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    boolean checkForError(final HashSet<AutomatonState> state) {
        return getXMLrule().checkForError(state,getAutomatonID());
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    boolean isExitRule() {
        return getXMLrule().isExitRule();
    }

    // private section

    private XMLErrorRule getXMLrule() {
        return XMLrule;
    }

    private int getAutomatonID() {
        return automatonID;
    }

    private final XMLErrorRule XMLrule;
    private final int automatonID;
}
