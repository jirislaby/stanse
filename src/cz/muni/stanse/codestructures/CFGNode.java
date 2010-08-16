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
    private List<CFGNode> optPreds = new ArrayList<CFGNode>();
    private List<CFGNode> optSuccs = new ArrayList<CFGNode>();

    public enum NodeType { none, call, value };
    public enum OperandType { none, operator, function, member, constant, varptr, varval, nodeval };
    
    public static class Operand {
	public Operand(OperandType type, int id) {
	    this.type = type;
	    this.id = id;
	}
	
	public Operand(String type, int id) {
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
	
	public OperandType type;
	public int id;
    }

    NodeType nodeType;
    List<Operand> operands = new ArrayList<Operand>();

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

    public final int getColumn() {
	if (getElement() == null || getElement().attribute("bc") == null)
	    return 1;
        return Integer.parseInt(getElement().attributeValue("bc"));
    }

    public final int getLine() {
	if (getElement() == null || getElement().attribute("bl") == null)
	    return 1;
        return Integer.parseInt(getElement().attributeValue("bl"));
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
	if (nodeType.equals("call"))
	    this.nodeType = NodeType.call;
	else if (nodeType.equals("value"))
	    this.nodeType = NodeType.value;
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
     * Adds the node to the predecessors
     * @param pred new predecessor
     */
    protected void addOptPred(CFGNode pred) {
	optPreds.add(pred);
    }

    /**
     * Adds the node to the successors
     * @param succ new successor
     */
    protected void addOptSucc(CFGNode succ) {
	optSuccs.add(succ);
    }

    /**
     * Inserts the node to the successors at specified position
     * @param index index at which the specified element is to be inserted
     * @param succ new successor
     */
    protected void addOptSucc(int index, CFGNode succ) {
	optSuccs.add(index, succ);
    }

    /**
     * Removes the node from the successors
     * @param index the index of the element to be removed
     */
    protected void removeOptSucc(int index) {
	optSuccs.remove(index);
    }

    /**
     * Returns index of the specified successor
     * @param succ successor to get index for
     * @return the index in the list
     */
    protected int indexOfOptSucc(CFGNode succ) {
	return optSuccs.indexOf(succ);
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
     * Adds an optimized edge between two nodes (is in code, not in CFG)
     * @param to which node to add the edge to
     */
    public void addOptEdge(CFGNode to) {
	addOptSucc(to);
	to.addOptPred(this);
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

    /**
     * Replaces an edge between two nodes with a new edge
     * @param oldTo which node to replace
     * @param newTo which node use as a replacement
     */
    public void replaceOptEdge(CFGNode oldTo, CFGNode newTo) {
	int idx = indexOfOptSucc(oldTo);
	removeOptSucc(idx);
	addOptSucc(idx, newTo);
	newTo.addOptPred(this);
    }

    public void drop() {
	preds.clear();
	succs.clear();
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
	return Integer.toString(number);
    }
}
