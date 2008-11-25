package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;

final class TransitionRule {

    // package-private section

    TransitionRule(final XMLTransitionRule XMLrule, final int automatonID) {
        this.XMLrule = XMLrule;
        this.automatonID = automatonID;
    }

    Pair< Boolean,AutomatonState>
    transformAutomatonState(final AutomatonState state) {
        return getXMLrule().transformAutomatonState(state,getAutomatonID());
    }

    int getAutomatonID() {
        return automatonID;
    }

    // private section

    private XMLTransitionRule getXMLrule() {
        return XMLrule;
    }

    private final XMLTransitionRule XMLrule;
    private final int automatonID;
}
