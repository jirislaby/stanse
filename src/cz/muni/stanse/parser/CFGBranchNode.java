/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

/**
 * Represents a node in control-flow graph (class ControlFlowGraph in this
 * package)
 */
public class CFGBranchNode extends CFGNode {
    private List<Element> labels = new ArrayList<Element>();

    private CFGBranchNode() {
    }

    /**
     * Creates a new instance of CFGNode
     */
    public CFGBranchNode(Element e) {
	super(e);
    }

    public Element getEdgeLabel(int edge) {
	return labels.get(edge);
    }

    public void addEdge(CFGNode to) {
	addEdge(to, null);
    }

    public void addEdge(CFGNode to, Element label) {
	super.addEdge(to);
	labels.add(label);
    }

    @Override
    public String toString() {
	return "B " + super.toString();
    }
}
