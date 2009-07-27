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
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Attribute;

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
            final String elementName = XMLelement.getName();
            for (final Iterator j = XMLpivot.attributeIterator(); j.hasNext(); )
                if (elementName.equals(
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
        
        if (!XMLpivot.getName().equals(XMLelement.getName()))
            return false;
        if (!matchingAttributes(XMLpivot.attributes(),XMLelement))
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
    
    private static boolean matchingAttributes(final List pivotATTRs,
                                              final Element XMLelement) {
        for (final Object obj : pivotATTRs) {
            assert(obj instanceof Attribute);
            final Attribute pivotAttr = (Attribute)obj;
            final Attribute elemAttr=XMLelement.attribute(pivotAttr.getName());
            if (elemAttr == null || !matchingAttribute(pivotAttr.getValue(),
                                                       elemAttr.getValue()))
                return false;
        }
        return true;
    }

    private static boolean matchingAttribute(String pivotAttr,
                                             final String elemAttr) {
        pivotAttr = pivotAttr.replace(" ","")
                             .replace("\t","");
        if (pivotAttr.charAt(0) != '-' && pivotAttr.charAt(0) != '{')
            return pivotAttr.equals(elemAttr);

        final boolean negated =  (pivotAttr.charAt(0) == '-') ? true : false;
String zzz = pivotAttr.substring(pivotAttr.indexOf('{')+1,
                                 pivotAttr.lastIndexOf('}'));
String[] xxx = zzz.split("\\}\\{");

        for (final String attr : pivotAttr.substring(pivotAttr.indexOf('{') + 1,
                                                     pivotAttr.lastIndexOf('}'))
                                          .split("\\}\\{"))
            if (elemAttr.equals(attr))
                return !negated;
        return negated;
    }

    private final Element patternXMLelement;
    private final String name;
    private final boolean constructive;
}
