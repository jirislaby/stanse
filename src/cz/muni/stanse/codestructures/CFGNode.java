/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

/**
 * Represents a node in control-flow graph (class ControlFlowGraph in this
 * package)
 */
public class CFGNode {
    private int number;
    private Element element;
    private List<CFGNode> preds = new ArrayList<CFGNode>();
    private List<CFGNode> succs = new ArrayList<CFGNode>();

    /**
     * Creates a new instance of CFGNode
     */
    public CFGNode() {
	number = CFGNodeNumber.getNext();
    }

    /**
     * Creates a new instance of CFGNode
     * @param e element to assign to this node
     */
    public CFGNode(Element e) {
	number = CFGNodeNumber.getNext();
	element = e;
    }

    /**
     * Creates a new instance of CFGNode
     * @param number node number
     * @param e element to assign to this node
     */
    public CFGNode(int number, Element e) {
	this.number = number;
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
     * Get all predecessors of the node
     * @return (read only) set of this node's predecessors
     */
    public List<CFGNode> getPredecessors() {
	return Collections.unmodifiableList(preds);
    }

    /**
     * Get all successors of the node
     * @return (read only) set of this node's successors
     */
    public List<CFGNode> getSuccessors() {
	return Collections.unmodifiableList(succs);
    }

    /**
     * Adds the node to the predecessors
     * @param pred new predecessor
     */
    protected void addPred(CFGNode pred) {
	preds.add(pred);
    }

    /**
     * Adds the node to the successors
     * @param succ new successor
     */
    protected void addSucc(CFGNode succ) {
	succs.add(succ);
    }

    /**
     * Inserts the node to the successors at specified position
     * @param index index at which the specified element is to be inserted
     * @param succ new successor
     */
    protected void addSucc(int index, CFGNode succ) {
	succs.add(index, succ);
    }

    /**
     * Removes the node from the successors
     * @param index the index of the element to be removed
     */
    protected void removeSucc(int index) {
	succs.remove(index);
    }

    /**
     * Returns index of the specified successor
     * @param succ successor to get index for
     * @return the index in the list
     */
    protected int indexOfSucc(CFGNode succ) {
	return succs.indexOf(succ);
    }

    /**
     * Adds an edge between two nodes
     * @param to which node to add the edge to
     */
    public void addEdge(CFGNode to) {
	addSucc(to);
	to.addPred(this);
    }

    /**
     * Replaces an edge between two nodes with a new edge
     * @param oldTo which node to replace
     * @param newTo which node use as a replacement
     */
    public void replaceEdge(CFGNode oldTo, CFGNode newTo) {
	int idx = indexOfSucc(oldTo);
	removeSucc(idx);
	addSucc(idx, newTo);
	newTo.addPred(this);
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
