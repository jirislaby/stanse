package cz.muni.stanse.utils;

import java.util.Iterator;

public final class XMLAlgo {

    // public section

    public static String readValueOfAttribute(final String nodeName,
            final String attributeName,final org.dom4j.Element XMLdefinition)
                                                              throws Exception {
        return readValueOfAttribute(XMLdefinition.selectSingleNode(nodeName),
                                    attributeName);
    }

    public static String readValueOfAttribute(final String attributeName,
                          final org.dom4j.Element XMLelement) throws Exception {
        return readValueOfAttribute(XMLelement,attributeName);
    }

    public static String readValueOfAttribute(final org.dom4j.Node XMLnode,
                                  final String attributeName) throws Exception {
        if (XMLnode == null)
            throw new Exception("[stanse/AutomatonChecker] - " +
                "XMLAlgo.readValueOfAttribute() :: " +
                "XML document '" + XMLnode.getDocument().getName() +
                "' error: Cannot read value of attribute '" + attributeName +
                "' of node '" + XMLnode.getName() + "'.");
        return XMLnode.valueOf("@" + attributeName);
    }

    public static boolean equalElements(final org.dom4j.Element XMLelement1,
                                        final org.dom4j.Element XMLelement2) {
        if (!XMLelement1.getName().equals(XMLelement2.getName()))
            return false;
        if (!XMLelement1.getText().equals(XMLelement2.getText()))
            return false;

        final Iterator i = XMLelement1.elementIterator();
        final Iterator j = XMLelement2.elementIterator();
        for ( ; i.hasNext() && j.hasNext(); )
            if (!equalElements((org.dom4j.Element) i.next(),
                               (org.dom4j.Element) j.next()))
                return false;
        if (i.hasNext() || j.hasNext())
            return false;

        return true;
    }

    // private section

    public XMLAlgo() {
    }
}
