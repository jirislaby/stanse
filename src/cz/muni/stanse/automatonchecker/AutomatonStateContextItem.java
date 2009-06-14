package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

final class AutomatonStateContextItem {

    // public section

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((AutomatonStateContextItem)obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.cfgNode != null ? this.cfgNode.hashCode() : 0);
        hash = 71 * hash + (this.automatonID != null ?
                                    this.automatonID.hashCode() : 0);
        return hash;
    }

    // package-private section

    AutomatonStateContextItem(final ComposedAutomatonID automatonID) {
        this.cfgNode = null;
        this.automatonID = automatonID;
    }

    AutomatonStateContextItem(final CFGNode cfgNode,
                              final ComposedAutomatonID automatonID) {
        this.cfgNode = cfgNode;
        this.automatonID = automatonID;
    }

    AutomatonStateContextItem(final AutomatonStateContextItem source,
                              final ComposedAutomatonID automatonID) {
        this.cfgNode = source.getCFGNode();
        this.automatonID = automatonID;
    }

    CFGNode getCFGNode() {
        return cfgNode;
    }

    ComposedAutomatonID getAutomatonID() {
        return automatonID;
    }

    boolean isEqualWith(AutomatonStateContextItem other) {
        return getCFGNode() == other.getCFGNode() &&
               getAutomatonID().equals(other.getAutomatonID());
    }

    // private section

    private final CFGNode cfgNode;
    private final ComposedAutomatonID automatonID;
}
