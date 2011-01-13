package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;

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

    SimpleAutomatonID(final XMLPatternVariablesAssignment assignment,
                      final boolean isGlobal) {
        this.varsAssignment = buildVarsCollection(assignment);
        this.isGlobal = isGlobal;
    }

    SimpleAutomatonID(final List<String> varsAssignment,final boolean isGlobal){
        this.varsAssignment = new Vector<String>(varsAssignment);
        this.isGlobal = isGlobal;
    }

    Vector<String> getVarsAssignment() {
        return varsAssignment;
    }

    boolean isGlobal() {
        return isGlobal;
    }

    boolean isEqualWith(final SimpleAutomatonID other) {
        return getVarsAssignment().equals(other.getVarsAssignment());
    }

    // private section

    private static Vector<String>
    buildVarsCollection(final XMLPatternVariablesAssignment varsAssignment) {
        return new Vector<String>(new TreeMap<String, String>(varsAssignment.makeArgumentMap()).values());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isGlobal)
            sb.append("g");
        for (String ass : varsAssignment) {
            sb.append("(").append(ass).append(")");
        }
        return sb.toString();
    }

    private final Vector<String> varsAssignment;
    private final boolean isGlobal;
}
