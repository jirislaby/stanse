/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import org.dom4j.Element;

/**
 * Represents a node in control-flow graph
 */
public class CFGBreakNode extends CFGNode {

    private CFGBreakNode() {
    }

    /**
     * Creates an instance of the CFGBreakNode with assigned element
     *
     * @param e element to assign to this node
     */
    public CFGBreakNode(Element e) {
	super(e);
    }

    /**
     * Overriden addEdge which does (intentionally) nothing
     *
     * We want to ignore all added edges, since we are a node which breaks
     * code flow such as goto, break, return, etc.
     *
     * @param to ignored parameter
     */
    @Override
    public void addEdge(CFGNode to) {
	/* nothing */
    }

    /**
     * Real addEdge for CFGBreakNode
     *
     * Usually used when backpatching, when we know it's a break node and
     * should be handled specifically.
     *
     * @param to which node to add the edge to
     */
    public void addBreakEdge(CFGNode to) {
	super.addEdge(to);
    }

    @Override
    public String toString() {
	return "X " + super.toString();
    }
}
