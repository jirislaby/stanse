/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

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
    @Override
    public void addEdge(CFGNode to) {
	for (CFGNode n: getPredecessors())
	    n.replaceEdge(this, to);
	for (CFGNode n: getOptPredecessors())
	    n.replaceOptEdge(this, to);
    }

    /**
     * Overriden addEdge for CFGJoinNode
     *
     * @param to which node to add the edge to
     */
    @Override
    public void addOptEdge(CFGNode to) {
	throw new UnsupportedOperationException("can't add opt edge from a " +
		"join node");
    }

    @Override
    public String toString() {
	return "J " + super.toString();
    }
}
