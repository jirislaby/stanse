/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.parser;

import org.dom4j.Element;

/**
 * Represents a node in control-flow graph
 */
public class CFGJoinNode extends CFGNode {

    public CFGJoinNode() {
	super();
    }

    @Override
    public void addEdge(CFGNode to) {
	for (CFGNode n: getPredecessors()) {
/*	    if (!(n instanceof CFGBreakNode)) {
		System.out.println("PROB: " + n.toString() + "->" + to.toString());
		cz.muni.stanse.utils.XMLAlgo.outputXML(n.getElement(), System.out);
		System.out.println("");
	    }*/
	    n.replaceEdge(this, to);
	}
    }

    @Override
    public String toString() {
	return "J " + super.toString();
    }
}
