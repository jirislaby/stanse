/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.utils.xmlpatterns;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;

/**
 * @brief
 *
 * @see
 */
public final class XMLPatternVariablesAssignment {

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
        return isEqualWith((XMLPatternVariablesAssignment)obj);
    }

    public XMLPatternVariablesAssignment() {
        varsAssignments = new HashMap<String,Element>();
    }

    public Element put(final String varName, final Element XMLelement) {
        return getVarsAssignments().put(varName, XMLelement);
    }

    public boolean isEqualWith(final XMLPatternVariablesAssignment other) {
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

    public Map<String,Element> getVarsMap() {
        return Collections.unmodifiableMap(getVarsAssignments());
    }

    // private section

    private HashMap<String,Element> getVarsAssignments() {
        return varsAssignments;
    }

    private final HashMap<String,Element> varsAssignments;
}
