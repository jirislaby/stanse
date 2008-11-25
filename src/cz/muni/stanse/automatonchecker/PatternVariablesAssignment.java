package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.XMLAlgo;

import java.util.HashMap;
import java.util.Iterator;

final class PatternVariablesAssignment {

    // public section

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        for (final Iterator<String> iter =
                getVarsAssignments().keySet().iterator(); iter.hasNext(); )
            result += PRIME * iter.next().hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return isEqualWith((PatternVariablesAssignment)obj);
    }

    // package-private section

    PatternVariablesAssignment() {
        varsAssignments = new HashMap<String,org.dom4j.Element>(); 
    }

    org.dom4j.Element put(final String varName,
                          final org.dom4j.Element XMLelement) {
        return getVarsAssignments().put(varName,XMLelement);
    }

    boolean isEqualWith(final PatternVariablesAssignment other) {
        if (!getVarsAssignments().keySet().equals(
                getVarsAssignments().keySet()))
            return false;
        for (final Iterator<String> iter =
                getVarsAssignments().keySet().iterator(); iter.hasNext(); ) {
            final String varName = iter.next();
            if (!XMLAlgo.equalElements(getVarsAssignments().get(varName),
                                       other.getVarsAssignments().get(varName)))
                return false;
        }
        return true;
    }

    // private section

    private HashMap<String,org.dom4j.Element> getVarsAssignments() {
        return varsAssignments;
    }

    private final HashMap<String,org.dom4j.Element> varsAssignments;
}
