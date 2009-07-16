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

    ErrorRule(final XMLErrorRule XMLrule, final SimpleAutomatonID automatonID) {
        this.XMLrule = XMLrule;
        this.automatonID = automatonID;
    }

    ErrorRule(final ErrorRule source, final SimpleAutomatonID automatonID) {
        this.XMLrule = source.getXMLrule();
        this.automatonID = automatonID;
    }

    String getErrorDescription() {
        return getXMLrule().getErrorDescription();
    }

    int getErrorLevel() {
        return getXMLrule().getErrorLevel();
    }

    String getErrorEntryMessage() {
        return getXMLrule().getErrorEntryMessage();
    }

    String getErrorBeginMessage() {
        return getXMLrule().getErrorBeginMessage();
    }

    String getErrorPropagMessage() {
        return getXMLrule().getErrorPropagMessage();
    }

    String getErrorEndMessage() {
        return getXMLrule().getErrorEndMessage();
    }

    boolean checkForError(final HashSet<AutomatonState> state) {
        return getXMLrule().checkForError(state,getAutomatonID());
    }

    boolean isExitRule() {
        return getXMLrule().isExitRule();
    }

    SimpleAutomatonID getAutomatonID() {
        return automatonID;
    }

    // private section

    private XMLErrorRule getXMLrule() {
        return XMLrule;
    }

    private final XMLErrorRule XMLrule;
    private final SimpleAutomatonID automatonID;
}
