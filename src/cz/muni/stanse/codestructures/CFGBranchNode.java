/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * Specific CFG node for holding info about if/switch and other branching
 */
public class CFGBranchNode extends CFGNode {
    private List<Element> labels = new ArrayList<Element>();

    private CFGBranchNode() {
    }

    /**
     * Creates an instance of the CFGBranchNode with assigned element
     *
     * @param e element to assign to this node
     */
    public CFGBranchNode(final Element e) {
	super(e);
    }

    /**
     * Creates an instance of the CFGBranchNode with assigned element
     *
     * @param e element to assign to this node
     */
    public CFGBranchNode(final Element e, final String c) {
	super(e, c);
    }

    /**
     * Get label of a branch edge indexed from 0
     *
     * @param edge edge index
     * @return element which is the label
     */
    public Element getEdgeLabel(int edge) {
	return labels.get(edge);
    }

    /**
     * Adds a labeled edge to the CFG from this node
     *
     * @param to which node to add the edge to
     * @param label label for this edge
     */
    public void addEdge(CFGNode to, Element label) {
	super.addEdge(to);
	labels.add(label);
    }

    @Override
    public void addEdge(CFGNode to) {
	throw new UnsupportedOperationException("can't add edge without a " +
		"label");
    }

    @Override
    public void addOptEdge(CFGNode to) {
	throw new UnsupportedOperationException("can't add edge without a " +
		"label");
    }

    @Override
    public void drop() {
	labels.clear();
	super.drop();
    }

    @Override
    public String toString() {
	return "B " + super.toString();
    }
}
