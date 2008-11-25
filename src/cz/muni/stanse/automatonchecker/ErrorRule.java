package cz.muni.stanse.automatonchecker;

import java.util.HashSet;

final class ErrorRule {

    // package-private section

    ErrorRule(final XMLErrorRule XMLrule, final int automatonID) {
        this.XMLrule = XMLrule;
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
