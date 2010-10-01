/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    private List<Object> edgeLabels = new ArrayList<Object>();
    private List<CFGNode> optPreds = new ArrayList<CFGNode>();
    private List<CFGNode> optSuccs = new ArrayList<CFGNode>();

    public enum NodeType { none, call, exit, value, phi };
    public enum OperandType { none, operator, function, member, constant, varptr, varval, nodeval };

    public static class Operand {
	public Operand(OperandType type, Object id) {
	    this.type = type;
	    this.id = id;
	}

	public Operand(String type, Object id) {
	    if (type.equals("function"))
		this.type = OperandType.function;
	    else if (type.equals("member"))
		this.type = OperandType.member;
	    else if (type.equals("const"))
		this.type = OperandType.constant;
	    else if (type.equals("varptr"))
		this.type = OperandType.varptr;
	    else if (type.equals("varval"))
		this.type = OperandType.varval;
	    else if (type.equals("nodeval"))
		this.type = OperandType.nodeval;
	    else
		throw new IllegalArgumentException("Invalid operand type: " + type);
	    this.id = id;
	}

	public String toString() {
	    return type.toString() + " " + id.toString();
	}

	public OperandType type;
	public Object id;
    }

    NodeType nodeType = NodeType.none;
    List<Operand> operands = new ArrayList<Operand>();
    int line = -1;
    int column = 1;
    boolean visible = true;

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

    public void setLocation(int line, int column) {
	this.line = line;
	this.column = column;
    }

    public boolean hasLocation() {
	return line >= 0;
    }

    public final int getColumn() {
	return column;
    }

    public final int getLine() {
	return line;
    }

    public boolean isVisible() {
	return visible;
    }

    public void setVisible(boolean value) {
	visible = value;
    }

    public static Set<String> getDependentVars(Operand op) {
	Set<String> res = new java.util.HashSet<String>();
	if (op.type == OperandType.varptr || op.type == OperandType.varval)
	    res.add((String)op.id);
	else if (op.type == OperandType.nodeval)
	    res.addAll(((CFGNode)op.id).getDependentVars());
	return res;
    }

    public Set<String> getDependentVars() {
	Set<String> res = new java.util.HashSet<String>();
	for (Operand op : operands)
	    res.addAll(getDependentVars(op));
	return res;
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
     * Get all predecessors of the node
     * @return (read only) set of this node's predecessors
     */
    public List<CFGNode> getOptPredecessors() {
	return Collections.unmodifiableList(optPreds);
    }

    /**
     * Get all successors of the node
     * @return (read only) set of this node's successors
     */
    public List<CFGNode> getOptSuccessors() {
	return Collections.unmodifiableList(optSuccs);
    }

    public List<Operand> getOperands() {
	return Collections.unmodifiableList(operands);
    }

    public void setOperands(List<Operand> operands) {
	this.operands.clear();
	this.operands.addAll(operands);
    }

    public void addOperand(OperandType type, int id) {
	operands.add(new Operand(type, id));
    }

    public NodeType getNodeType() {
	return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
	this.nodeType = nodeType;
    }

    public void setNodeType(String nodeType) {
        if (nodeType.equals("none"))
            this.nodeType = NodeType.none;
        else if (nodeType.equals("call"))
	    this.nodeType = NodeType.call;
	else if (nodeType.equals("value"))
	    this.nodeType = NodeType.value;
	else if (nodeType.equals("exit"))
	    this.nodeType = NodeType.exit;
	else if (nodeType.equals("phi"))
	    this.nodeType = NodeType.phi;
	else
	    throw new IllegalArgumentException("Unknown node type: " + nodeType);
    }

    /**
     * Adds the node to the predecessors
     * @param pred new predecessor
     */
    protected void addPred(CFGNode pred) {
	preds.add(pred);
    }

    /**
     * Removes the node from the successors
     * @param index the index of the element to be removed
     */
    protected void removeSucc(int index) {
	succs.remove(index);
    }

    /**
     * Adds an edge between two nodes
     * @param to which node to add the edge to
     */
    public void addEdge(CFGNode to) {
	succs.add(to);
	edgeLabels.add(null);
	to.addPred(this);
    }

    /**
     * Adds an edge between two nodes
     * @param to which node to add the edge to
     */
    public void addEdge(CFGNode to, Object label) {
        succs.add(to);
        edgeLabels.add(label);
        to.addPred(this);
    }

    /**
     * Get label of a branch edge indexed from 0
     *
     * @param edge edge index
     * @return element which is the label
     */
    public Object getEdgeLabel(int edge) {
        return edgeLabels.get(edge);
    }

    /**
     * Adds an optimized edge between two nodes (is in code, not in CFG)
     * @param to which node to add the edge to
     */
    public void addOptEdge(CFGNode to) {
	optSuccs.add(to);
	to.addPred(this);
    }

    /**
     * Replaces an edge between two nodes with a new edge
     * @param oldTo which node to replace
     * @param newTo which node use as a replacement
     */
    public void replaceEdge(CFGNode oldTo, CFGNode newTo) {
	int idx = succs.indexOf(oldTo);
	removeSucc(idx);
	succs.add(idx, newTo);
	newTo.addPred(this);
    }

    /**
     * Replaces an edge between two nodes with a new edge
     * @param oldTo which node to replace
     * @param newTo which node use as a replacement
     */
    public void replaceOptEdge(CFGNode oldTo, CFGNode newTo) {
	int idx = optSuccs.indexOf(oldTo);
	optSuccs.remove(idx);
	optSuccs.add(idx, newTo);
	newTo.optPreds.add(this);
    }

    public void drop() {
	preds.clear();
	succs.clear();
	edgeLabels.clear();
	optPreds.clear();
	optSuccs.clear();
	element.clearContent();
	element = null;
    }

    @Override
    public boolean equals(Object obj) {
    if (this == obj)
		return true;
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
	String res = Integer.toString(number) + " " + nodeType.toString();
	for (Operand op : operands) {
	    res += "[" + op.toString() + "]";
	}
	return res;
    }
}
