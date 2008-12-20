package cz.muni.stanse.utils;

import java.io.IOException;
import java.io.PrintStream;

import java.util.Iterator;

import org.dom4j.Element;
import org.dom4j.Node;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public final class XMLAlgo {

    /**
     * Check if two elements are (recursively) equal
     *
     * TODO: remove and use equality tester from dom4j pkg
     *
     * @param e1 first element
     * @param e2 second element
     */
    public static boolean equalElements(final Element e1, final Element e2) {
	if (!e1.getName().equals(e2.getName()))
	    return false;
	if (!e1.getText().equals(e2.getText()))
	    return false;

	final Iterator<Element> i = e1.elementIterator();
	final Iterator<Element> j = e2.elementIterator();
	while (i.hasNext() && j.hasNext())
	    if (!equalElements(i.next(), j.next()))
		return false;
	if (i.hasNext() || j.hasNext())
	    return false;

	return true;
    }

    /**
     * Pretty-print XML node to a stream
     *
     * @param n node to dump
     * @param o stream to dump to
     */
    public static void outputXML(Node n, PrintStream o) {
	OutputFormat format = OutputFormat.createPrettyPrint();
	try {
	    XMLWriter writer = new XMLWriter(o, format);
	    writer.write(n);
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    /**
     * Pretty-print XML node to the System.err
     *
     * @param n node to dump
     */
    public static void outputXML(Node n) {
	outputXML(n, System.err);
    }

    private XMLAlgo() {
    }
}
