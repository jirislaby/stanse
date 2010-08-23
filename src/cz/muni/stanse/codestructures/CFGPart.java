/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import org.dom4j.Element;

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
     * Returns start of the CFG
     * @return start node
     */
    public CFGNode getStartNode() {
	return startNode;
    }

    /**
     * Sets start of the CFG
     * @param n start node
     */
    public void setStartNode(CFGNode n) {
	startNode = n;
    }

    /**
     * Returns end of the CFG
     * @return end node
     */
    public CFGNode getEndNode() {
	return endNode;
    }

    /**
     * Sets end of the CFG
     * @param n end node
     */
    public void setEndNode(CFGNode n) {
	endNode = n;
    }

    public boolean isEmpty() {
	return getEndNode() == null;
    }

    /**
     * Appends one CFG to the another
     * @param g a CFG to append at the end of this instance
     */
    public void append(CFGPart g) {
	if (isEmpty()) {
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
	if (isEmpty()) {
	    setStartNode(n);
	    setEndNode(n);
	    return;
	}
	getEndNode().addEdge(n);
	setEndNode(n);
    }

    private Set<CFGNode> getAllNodes(boolean optimized) {
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
	    if (!optimized)
		continue;
	    for (CFGNode succ : node.getOptSuccessors())
		if (!nodesDone.contains(succ))
		    nodesToDo.add(succ);
	}

	/* endNode might be unavailable, add it unconditionally */
	nodesDone.add(getEndNode());

	return nodesDone;
    }

    /**
     * Returns all nodes in this CFG
     * @return list of all nodes
     */
    public Set<CFGNode> getAllNodes() {
	return getAllNodes(false);
    }

    /**
     * Returns all nodes in this CFG including optimized ones
     * @return list of all nodes
     */
    public Set<CFGNode> getAllNodesOpt() {
	return getAllNodes(true);
    }

    public Set<CFGNode> getAllNodesReverse() {
	Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
	Set<CFGNode> nodesDone = new LinkedHashSet<CFGNode>();

	nodesToDo.add(getEndNode());

	while (!nodesToDo.isEmpty()) {
	    CFGNode node = nodesToDo.iterator().next();
	    nodesToDo.remove(node);

	    nodesDone.add(node);

	    for (CFGNode pred: node.getPredecessors())
		if (!nodesDone.contains(pred))
		    nodesToDo.add(pred);
	}

	/* startNode might be unavailable, add it unconditionally */
	nodesDone.add(getStartNode());

	return nodesDone;
    }

    public void drop() {
	Set<CFGNode> nodes = getAllNodes();
	Set<CFGNode> nodesr = getAllNodesReverse();
	nodesr.removeAll(nodes);
	for (CFGNode n: nodes)
	    n.drop();
	for (CFGNode n: nodesr)
	    n.drop();
	setStartNode(null);
	setEndNode(null);
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

    /**
     * @brief Converts CFGPart to a string representation
     *
     * Useful for dumping to logs or console.
     *
     * @return string representation
     */
    public String toStringGraph() {
	StringBuilder sb = new StringBuilder();
	String eol = System.getProperty("line.separator");
	boolean shorten = false;

	for (CFGNode n: getAllNodes()) {
	    sb.append(n.toString());
	    Element e = n.getElement();
	    if (e != null) {
		sb.append(' ');
		sb.append(e.asXML());
	    }
	    CFGBranchNode bn = null;
	    if (n instanceof CFGBranchNode)
		bn = (CFGBranchNode)n;
	    int edge = 0;
	    for (CFGNode succ: n.getSuccessors()) {
		sb.append(eol);
		sb.append("  -");
		if (bn != null) {
		    sb.append(bn.getEdgeLabel(edge).asXML());
		    sb.append('-');
		    edge++;
		}
		sb.append("> ");
		sb.append(succ.toString());
	    }
	    sb.append(eol);
	    shorten = true;
	}

	if (shorten)
	    sb.setLength(sb.length() - 1);

	return sb.toString();
    }

    /**
     * @brief Converts CFGPart to a dot graph representation
     *
     * Useful for dumping cfgs to a file and generating e.g. PostScript from it.
     * See Graphviz software.
     *
     * @return dot representation stored in a string
     */
    public String toDot() {
	String eol = System.getProperty("line.separator");
	StringBuilder sb = new StringBuilder("digraph CFG {");
	Set<CFGNode> allNodes = getAllNodesOpt();

	sb.append(eol);
	sb.append("\tnode [shape=box];");
	sb.append(eol);

	for (CFGNode n: allNodes) {
	    sb.append('\t');
	    sb.append(n.getNumber());
	    Element e = n.getElement();
	    if (e != null) {
		sb.append(" [label=\"");
		sb.append(n.getNumber());
		sb.append(": ");
		String label = e.getName();
		if (e.getName().equals("functionCall")) {
		    Element funName = (Element)e.node(0);
		    if (funName != null)
			label = funName.getText();
		}
		sb.append(label);
		sb.append("\"];");
	    }
	    sb.append(eol);
	}

	for (CFGNode from: allNodes) {
	    buildArcs(sb, from, false, eol);
	    buildArcs(sb, from, true, eol);
	}

	sb.append("}");

	return sb.toString();
    }

    private void buildArcs(StringBuilder sb, CFGNode from, boolean optimized,
	    String eol) {
	int edge = 0;
	CFGBranchNode bn = null;
	if (from instanceof CFGBranchNode)
	    bn = (CFGBranchNode)from;
	for (CFGNode succ: optimized ? from.getOptSuccessors() :
		from.getSuccessors()) {
	    sb.append('\t').append(from.getNumber()).append(" -> ").
		    append(succ.getNumber());
	    if (bn != null) {
		sb.append(" [label=\"");
		Element label = bn.getEdgeLabel(edge);
		if (label.getName().equals("intConst"))
		    sb.append(label.getText());
		else
		    sb.append(label.getName());
		sb.append("\"]");
		edge++;
	    }
	    if (optimized)
		sb.append(" [style=dashed]");
	    sb.append(';').append(eol);
	}
    }
}
