/**
 * @file XMLPattern.java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.utils;

import java.util.Iterator;

/**
 * @brief
 *
 * @see
 */
public final class XMLPattern {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public XMLPattern(final org.dom4j.Element XMLelement) {
        patternXMLelement = XMLelement;
        name = patternXMLelement.attribute("name").getValue();
        constructive = XMLelement.selectNodes(
                                   ".//var[@constructive=\"false\"]").isEmpty();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public String getName() {
        return name;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public boolean isSonstructive() {
        return constructive;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public Pair<Boolean,XMLPatternVariablesAssignment>
    matchesXMLElement(final org.dom4j.Element XMLelement) {
        final XMLPatternVariablesAssignment varsAssignment =
                new XMLPatternVariablesAssignment();
        return new Pair<Boolean,XMLPatternVariablesAssignment>(
                          matchingElements(getPatternXMLelement(),XMLelement,
                                           varsAssignment),
                          varsAssignment);
    }

    // private section

    private static boolean matchingElements(final org.dom4j.Element XMLpivot,
                              final org.dom4j.Element XMLelement,
                              final XMLPatternVariablesAssignment varsAssignment) {
        if (XMLpivot.getName().equals("nested"))
        {
            final String elementAliasedName = getAliasedName(XMLelement);
            for (final Iterator j = XMLpivot.attributeIterator(); j.hasNext(); )
                if (elementAliasedName.equals(
                        ((org.dom4j.Attribute)j.next()).getValue()))
                    return false;
            return onNested(XMLpivot,XMLelement,varsAssignment);
        }
        if (XMLpivot.getName().equals("any"))
            return true;
        if (XMLpivot.getName().equals("ignore"))
            return true;
        if (XMLpivot.getName().equals("var")) {
            varsAssignment.put(XMLpivot.attribute("name").getValue(),
                               XMLelement);
            return true;
        }
        
        //if (!XMLpivot.getName().equals(XMLelement.getName()))
        if (!XMLpivot.getName().equals(getAliasedName(XMLelement)))
            return false;
        if (XMLpivot.isTextOnly() != XMLelement.isTextOnly())
            return false;
        if (XMLpivot.isTextOnly() &&
            !XMLpivot.getText().equals(XMLelement.getText()))
            return false;

        final Iterator i = XMLpivot.elementIterator();
        final Iterator j = XMLelement.elementIterator();
        for ( ; i.hasNext() && j.hasNext(); )
            if (!matchingElements((org.dom4j.Element)i.next(),
                                  (org.dom4j.Element)j.next(),
                                  varsAssignment))
                return false;
        if (i.hasNext() || j.hasNext())
            return false;

        return true;
    }

    private static boolean onNested(final org.dom4j.Element XMLpivot,
                              final org.dom4j.Element XMLelement,
                              final XMLPatternVariablesAssignment varsAssignment) {
        if (matchingElements(
                           (org.dom4j.Element)XMLpivot.elementIterator().next(),
                           XMLelement,varsAssignment))
            return true;
        
        for (final Iterator j = XMLelement.elementIterator() ; j.hasNext(); )
            if (matchingElements(XMLpivot,(org.dom4j.Element)j.next(),
                                 varsAssignment))
                return true;

        return false;
    }

    private org.dom4j.Element getPatternXMLelement() {
        return (org.dom4j.Element)patternXMLelement.elementIterator().next();
    }
    
    private static String getAliasedName(final org.dom4j.Element element) {
        final String elemName = element.getName();

        if (elemName.equals("prefixExpression") &&
            element.attribute("op").getText().equals("!"))
            return "prefixExpressionLogicalNot";  
        if (elemName.equals("binaryExpression") &&
            element.attribute("op").getText().equals("=="))
            return "binaryExpressionEquality";
        if (elemName.equals("binaryExpression") &&
            element.attribute("op").getText().equals("!="))
            return "binaryExpressionNonEquality";

        return elemName;
    }
            
    private final org.dom4j.Element patternXMLelement;
    private final String name;
    private final boolean constructive;
}
