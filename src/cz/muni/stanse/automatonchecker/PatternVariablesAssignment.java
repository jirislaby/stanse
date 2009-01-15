/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.XMLAlgo;

import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;

/**
 * @brief
 *
 * @see
 */
final class PatternVariablesAssignment {

    // public section
    
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        for (final Iterator<String> iter =
                getVarsAssignments().keySet().iterator(); iter.hasNext(); )
            result += PRIME * iter.next().hashCode();
        return result;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return isEqualWith((PatternVariablesAssignment)obj);
    }

    // package-private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    PatternVariablesAssignment() {
        varsAssignments = new HashMap<String,Element>();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    Element put(final String varName,
                          final Element XMLelement) {
        return getVarsAssignments().put(varName,XMLelement);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
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

    private HashMap<String,Element> getVarsAssignments() {
        return varsAssignments;
    }

    private final HashMap<String,Element> varsAssignments;
}
