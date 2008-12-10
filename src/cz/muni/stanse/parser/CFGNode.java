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
public class CFGNode {
    private static int numberCounter;
    private int number;
    private Element element;
    private List<CFGNode> preds = new ArrayList<CFGNode>();
    private List<CFGNode> succs = new ArrayList<CFGNode>();

    /**
     * Creates a new instance of CFGNode
     */
    public CFGNode() {
	number = numberCounter++;
    }

    /**
     * Creates a new instance of CFGNode
     */
    public CFGNode(Element e) {
	number = numberCounter++;
	element = e;
    }

    /**
     * Get unique node number
     * @return unique node number
     */
    public int getNumber() {
	return number;
    }

    /**
     * Get element corresponding to this node
     * @return element
     */
    public Element getElement() {
	return element;
    }

    /**
     * Get all predecessors of node
     * @return (read only) set of this node's predecessors
     */
    public List<CFGNode> getPredecessors() {
	return Collections.unmodifiableList(preds);
    }

    /**
     * Get all successors of node
     * @return (read only) set of this node's successors
     */
    public List<CFGNode> getSuccessors() {
	return Collections.unmodifiableList(succs);
    }

    /**
     * Insert node to predecessors set
     * @param pred new predecesor
     */
    protected void addPred(CFGNode pred) {
	preds.add(pred);
    }

    /**
     * Insert node to successors set
     * @param succ new successor
     */
    protected void addSucc(CFGNode succ) {
	succs.add(succ);
    }

    /**
     * Adds an edge between two nodes
     * @param label label
     * @param from starting node
     * @param to ending node
     */
    public void addEdge(CFGNode to) {
	addSucc(to);
	to.addPred(this);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final CFGNode other = (CFGNode)obj;
	if (number != other.getNumber())
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 59 * hash + number;
	return hash;
    }

    @Override
    public String toString() {
	return new Integer(number).toString();
    }
}
