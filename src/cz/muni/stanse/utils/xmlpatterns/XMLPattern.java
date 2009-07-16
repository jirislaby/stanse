/**
 * @file XMLPattern.java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.utils.xmlpatterns;

import cz.muni.stanse.utils.Pair;
import java.util.Iterator;

import org.dom4j.Element;

/**
 * @brief
 *
 * @see
 */
public final class XMLPattern {

    // public section

    public XMLPattern(final Element XMLelement) {
        patternXMLelement = XMLelement;
        name = patternXMLelement.attribute("name").getValue();
        final org.dom4j.Attribute constructiveAttr =
            patternXMLelement.attribute("constructive");
        constructive = (constructiveAttr == null) ? true :
                            (constructiveAttr.getText().equals("false") ?
                                    false : true);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((XMLPattern)obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.patternXMLelement != null ?
                                         this.patternXMLelement.hashCode() : 0);
        hash = 43 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 43 * hash + (this.constructive ? 1 : 0);
        return hash;
    }

    boolean isEqualWith(XMLPattern other) {
        return isSonstructive() == other.isSonstructive() &&
               getName().equals(other.getName()) &&
               getPatternXMLelement().equals(other.getPatternXMLelement());
    }

    public String getName() {
        return name;
    }

    public boolean isSonstructive() {
        return constructive;
    }

    public Pair<Boolean,XMLPatternVariablesAssignment>
    matchesXMLElement(final Element XMLelement) {
        final XMLPatternVariablesAssignment varsAssignment =
                new XMLPatternVariablesAssignment();
        return new Pair<Boolean,XMLPatternVariablesAssignment>(
                          matchingElements(getPatternXMLelement(),XMLelement,
                                           varsAssignment),
                          varsAssignment);
    }

    // private section

    private static boolean matchingElements(final Element XMLpivot,
                           final Element XMLelement,
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
        for ( ; i.hasNext() && j.hasNext(); ) {
            final Element pivotNext = (Element)i.next();
            if (pivotNext.getName().equals("any"))
                return true;
            if (!matchingElements(pivotNext,(Element)j.next(),varsAssignment))
                return false;
        }
        if (i.hasNext() || j.hasNext())
            return false;

        return true;
    }

    private static boolean onNested(final Element XMLpivot,
                           final Element XMLelement,
                           final XMLPatternVariablesAssignment varsAssignment) {
        if (matchingElements((Element)XMLpivot.elementIterator().next(),
                             XMLelement,varsAssignment))
            return true;
        
        for (final Iterator j = XMLelement.elementIterator() ; j.hasNext(); )
            if (matchingElements(XMLpivot,(Element)j.next(),varsAssignment))
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
