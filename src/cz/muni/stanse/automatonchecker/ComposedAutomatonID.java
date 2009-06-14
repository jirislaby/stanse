package cz.muni.stanse.automatonchecker;

import java.util.List;
import java.util.Vector;

final class ComposedAutomatonID {

    // public section

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((ComposedAutomatonID)obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.automataIDs != null ?
                                               this.automataIDs.hashCode() : 0);
        return hash;
    }

    // package-private section

    ComposedAutomatonID(SimpleAutomatonID id) {
        automataIDs = new Vector<SimpleAutomatonID>();
        getSimpleAutomataIDs().add(id);
    }

    ComposedAutomatonID(final List<SimpleAutomatonID> ids) {
        automataIDs = new Vector<SimpleAutomatonID>(ids);
    }

    Vector<SimpleAutomatonID> getSimpleAutomataIDs() {
        return automataIDs;
    }

    boolean isEqualWith(final ComposedAutomatonID other) {
        return getSimpleAutomataIDs().equals(other.getSimpleAutomataIDs());
    }

    // private section

    private final Vector<SimpleAutomatonID> automataIDs;
}
