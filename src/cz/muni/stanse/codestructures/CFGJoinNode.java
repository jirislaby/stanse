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
public class CFGJoinNode extends CFGNode {

    public CFGJoinNode() {
	super();
    }

    /**
     * Overriden addEdge for CFGJoinNode
     *
     * @param to which node to add the edge to
     */
    public void addEdge(CFGNode to) {
	for (CFGNode n: getPredecessors())
	    n.replaceEdge(this, to);
    }

    @Override
    public String toString() {
	return "J " + super.toString();
    }
}
