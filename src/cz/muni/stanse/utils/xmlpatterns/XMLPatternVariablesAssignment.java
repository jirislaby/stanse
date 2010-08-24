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

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.CFGNode.Operand;
import cz.muni.stanse.codestructures.PassingSolver;

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
        for (String key : varToElement.keySet())
            result += PRIME * key.hashCode();
        for (String key : varToNode.keySet())
            result += PRIME * key.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        return isEqualWith((XMLPatternVariablesAssignment)obj);
    }

    public XMLPatternVariablesAssignment() {
        varToElement = new HashMap<String,Element>();
        varToNode = new HashMap<String, CFGNode.Operand>();
    }

    public Element put(final String varName, final Element XMLelement) {
        return varToElement.put(varName, XMLelement);
    }

    public CFGNode.Operand put(final String varName, final CFGNode.Operand op) {
        return varToNode.put(varName, op);
    }

    public boolean isEqualWith(final XMLPatternVariablesAssignment other) {
	if (varToElement.size() != other.varToElement.size()
		|| varToNode.size() != other.varToNode.size())
	    return false;

        for (String key : varToElement.keySet()) {
            if (!varToElement.get(key).equals(other.varToElement.get(key)))
        	return false;
        }
        for (String key : varToNode.keySet()) {
            if (!varToNode.get(key).equals(other.varToNode.get(key)))
        	return false;
        }

        return true;
    }

    public Map<String, String> makeArgumentMap() {
	Map<String, String> res = new HashMap<String, String>();
	for (String key : varToElement.keySet())
	    res.put(key, PassingSolver.makeArgument(varToElement.get(key)));
	for (String key : varToNode.keySet())
	    res.put(key, PassingSolver.makeArgument(varToNode.get(key)));
	return res;
    }

    public void merge(XMLPatternVariablesAssignment other) {
	for (String key : other.varToElement.keySet())
	    varToElement.put(key, other.varToElement.get(key));
	for (String key : other.varToNode.keySet())
	    varToNode.put(key, other.varToNode.get(key));
    }

    @Deprecated
    public Map<String,Element> getVarsMap() {
        return Collections.unmodifiableMap(varToElement);
    }

    // private section

    private final HashMap<String, Element> varToElement;
    private final HashMap<String, CFGNode.Operand> varToNode;
}
