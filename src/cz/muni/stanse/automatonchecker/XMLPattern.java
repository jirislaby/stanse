package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.XMLAlgo;

import java.util.Iterator;

final class XMLPattern {

    // package-private section

    XMLPattern(final org.dom4j.Element XMLelement) throws Exception {
        patternXMLelement = XMLelement;
        name = XMLAlgo.readValueOfAttribute("name",patternXMLelement);
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
        if (!XMLpivot.getName().equals(XMLelement.getName()))
            return false;
        if (XMLpivot.isTextOnly() != XMLelement.isTextOnly())
            return false;
        if (XMLpivot.isTextOnly() &&
            !XMLpivot.getText().equals(XMLelement.getText()))
            return false;

        final Iterator i = XMLpivot.elementIterator();
        final Iterator j = XMLelement.elementIterator();
        for ( ; i.hasNext() && j.hasNext(); ) {
            final org.dom4j.Element child_i = (org.dom4j.Element)i.next();
            final String name_i = child_i.getName(); 
            if (name_i.equals("any"))
                return true;
            if (name_i.equals("ignore")) {
                j.next();
                continue;
            }
            if (name_i.equals("var")) {
                varsAssignment.put(XMLAlgo.readValueOfAttribute("name",child_i),
                                   (org.dom4j.Element)j.next());
                continue;
            }
            if (!matchingElements(child_i,(org.dom4j.Element)j.next(),
                                  varsAssignment))
                return false;
        }
        if (i.hasNext() || j.hasNext())
            return false;

        return true;
    }

    private final org.dom4j.Element getPatternXMLelement() {
        return (org.dom4j.Element)patternXMLelement.elementIterator().next();
    }

    private final org.dom4j.Element patternXMLelement;
    private final String name;
}
