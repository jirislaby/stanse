package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;

import java.util.Iterator;

final class XMLPattern {

    // package-private section

    XMLPattern(final org.dom4j.Element XMLelement) throws Exception {
        patternXMLelement = XMLelement;
        name = patternXMLelement.attribute("name").getValue();
    }

    String getName() {
        return name;
    }

    Pair<Boolean,PatternVariablesAssignment>
    matchesXMLElement(final org.dom4j.Element XMLelement) throws Exception {
        final PatternVariablesAssignment varsAssignment =
            new PatternVariablesAssignment();
        return new Pair<Boolean,PatternVariablesAssignment>(
                                matchingElements(getPatternXMLelement(),
                                                 XMLelement,varsAssignment),
                                varsAssignment);
    }

    // private section

    private static boolean matchingElements(
            final org.dom4j.Element XMLpivot, final org.dom4j.Element XMLelement,
            final PatternVariablesAssignment varsAssignment) throws Exception {
        if (XMLpivot.getName().equals("nested"))
            return onNested(XMLpivot,XMLelement,varsAssignment);
        if (XMLpivot.getName().equals("any"))
            return true;
        if (XMLpivot.getName().equals("ignore"))
            return true;
        if (XMLpivot.getName().equals("var")) {
            varsAssignment.put(XMLpivot.attribute("name").getValue(),
		    XMLelement);
            return true;
        }
        
        if (!XMLpivot.getName().equals(XMLelement.getName()))
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

    private static boolean onNested(
            final org.dom4j.Element XMLpivot, final org.dom4j.Element XMLelement,
            final PatternVariablesAssignment varsAssignment) throws Exception {
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

    private final org.dom4j.Element getPatternXMLelement() {
        return (org.dom4j.Element)patternXMLelement.elementIterator().next();
    }

    private final org.dom4j.Element patternXMLelement;
    private final String name;
}
