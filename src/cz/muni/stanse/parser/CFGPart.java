/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.parser;

import org.dom4j.Element;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Represents a control-flow graph of a procedure
 */
public class CFGPart {
    private CFGNode startNode;
    private CFGNode endNode;

    /**
     * Returns start of the control-flow graph
     * @return start node
     */
    public CFGNode getStartNode() {
	return startNode;
    }

    /**
     * Sets start of the control-flow graph
     * @param n start node
     */
    public void setStartNode(CFGNode n) {
	startNode = n;
    }

    /**
     * Returns end of control-flow graph
     * @return end node
     */
    public CFGNode getEndNode() {
	return endNode;
    }

    /**
     * Sets end of the control-flow graph
     * @param n end node
     */
    public void setEndNode(CFGNode n) {
	endNode = n;
    }

    /**
     * Appends one CFG to the another
     * @param g a CFG to append at the end of this instance
     */
    public void append(CFGPart g) {
	if (getEndNode() == null) {
	    setStartNode(g.getStartNode());
	    setEndNode(g.getEndNode());
	    return;
	}
	getEndNode().addEdge(g.getStartNode());
	setEndNode(g.getEndNode());
    }

    /**
     * Appends one CFGNode to the CFG
     * @param n a CFGNode to append at the end of this CFG
     */
    public void append(CFGNode n) {
	if (getEndNode() == null) {
	    setStartNode(n);
	    setEndNode(n);
	    return;
	}
	getEndNode().addEdge(n);
	setEndNode(n);
    }

    public Set<CFGNode> getAllNodes() {
	Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
	Set<CFGNode> nodesDone = new LinkedHashSet<CFGNode>();

	nodesToDo.add(getStartNode());

	while (!nodesToDo.isEmpty()) {
	    CFGNode node = nodesToDo.iterator().next();
	    nodesToDo.remove(node);

	    nodesDone.add(node);

	    for (CFGNode succ : node.getSuccessors())
		if (!nodesDone.contains(succ))
		    nodesToDo.add(succ);
	}

	return Collections.unmodifiableSet(nodesDone);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = super.hashCode();
	hash = 5 * hash + getStartNode().getNumber();
	hash = 7 * hash + getEndNode().getNumber();
	return hash;
    }

    @Override
    public String toString() {
	return Integer.toString(getStartNode().getNumber()) + "->...->" +
	    Integer.toString(getEndNode().getNumber());
    }

    public String toStringGraph() {
	StringBuilder sb = new StringBuilder();
	boolean shorten = false;

	for (CFGNode n: getAllNodes()) {
	    sb.append(n.toString());
	    Element e = n.getElement();
	    if (e != null) {
		sb.append(' ');
		sb.append(e.toString());
	    }
	    CFGBranchNode bn = null;
	    if (n instanceof CFGBranchNode)
		bn = (CFGBranchNode)n;
	    int edge = 0;
	    for (CFGNode succ: n.getSuccessors()) {
		sb.append("\n  -");
		if (bn != null) {
		    sb.append(bn.getEdgeLabel(edge));
		    sb.append('-');
		    edge++;
		}
		sb.append("> ");
		sb.append(succ.toString());
	    }
	    sb.append("\n");
	    shorten = true;
	}

	if (shorten)
	    sb.setLength(sb.length() - 1);

	return sb.toString();
    }
}
