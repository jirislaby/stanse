/**
 * @file XMLPattern.java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;

import java.util.Iterator;
import java.util.HashSet;

import org.dom4j.Element;

/**
 * @brief
 *
 * @see
 */
final class XMLPattern {

    // package-private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    XMLPattern(final Element XMLelement) {
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
    String getName() {
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
    boolean isSonstructive() {
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
    Pair<Boolean,PatternVariablesAssignment>
    matchesXMLElement(final Element XMLelement) {
        final PatternVariablesAssignment varsAssignment =
                new PatternVariablesAssignment();
        return new Pair<Boolean,PatternVariablesAssignment>(
                          matchingElements(getPatternXMLelement(),XMLelement,
                                           varsAssignment),
                          varsAssignment);
    }

    // private section

    private static boolean matchingElements(final Element XMLpivot,
                              final Element XMLelement,
                              final PatternVariablesAssignment varsAssignment) {
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
            if (!matchingElements((Element)i.next(), (Element)j.next(),
                                  varsAssignment))
                return false;
        if (i.hasNext() || j.hasNext())
            return false;

        return true;
    }

    private static boolean onNested(final Element XMLpivot,
                              final Element XMLelement,
                              final PatternVariablesAssignment varsAssignment) {
        if (matchingElements((Element)XMLpivot.elementIterator().next(),
                           XMLelement,varsAssignment))
            return true;
        
        for (final Iterator j = XMLelement.elementIterator() ; j.hasNext(); )
            if (matchingElements(XMLpivot, (Element)j.next(), varsAssignment))
                return true;

        return false;
    }

    private Element getPatternXMLelement() {
        return (Element)patternXMLelement.elementIterator().next();
    }
    
    private static String getAliasedName(final Element element) {
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
            
    private final Element patternXMLelement;
    private final String name;
    private final boolean constructive;
}
