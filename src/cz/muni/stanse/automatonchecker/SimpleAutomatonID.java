package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.PassingSolver;
import cz.muni.stanse.utils.XMLPatternVariablesAssignment;

import java.util.List;
import java.util.Vector;
import java.util.TreeMap;

final class SimpleAutomatonID {

    // public section

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((SimpleAutomatonID)obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.varsAssignment != null ?
                                            this.varsAssignment.hashCode() : 0);
        return hash;
    }

    // package-private section

    SimpleAutomatonID(final XMLPatternVariablesAssignment assignment) {
        this.varsAssignment = buildVarsCollection(assignment);
    }

    SimpleAutomatonID(final List<String> varsAssignment) {
        this.varsAssignment = new Vector<String>(varsAssignment);
    }

    Vector<String> getVarsAssignment() {
        return varsAssignment;
    }

    boolean isEqualWith(final SimpleAutomatonID other) {
        return getVarsAssignment().equals(other.getVarsAssignment());
    }

    // private section

    private static Vector<String>
    buildVarsCollection(final XMLPatternVariablesAssignment varsAssignment) {
        final Vector<String> result = new Vector<String>();
        for (final org.dom4j.Element elem : sortToVector(varsAssignment))
            result.add(PassingSolver.makeArgument(elem));
        return result;
    }

    private static Vector<org.dom4j.Element>
    sortToVector(final XMLPatternVariablesAssignment varsAssignment) {
        return new Vector<org.dom4j.Element>(
                    new TreeMap<String,org.dom4j.Element>(
                        varsAssignment.getVarsMap())
                    .values());
    }

    private final Vector<String> varsAssignment;
}
