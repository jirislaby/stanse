/*
 * Licensed under GPLv2
 */

package cparser.CFG;

import cparser.AST.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ControlFlowGraph of a function
 *
 * @author Jiri Slaby
 */
public class CFGNode {
	private List<CFGNode> preds = new ArrayList<CFGNode>();
	private List<CFGNode> succs = new ArrayList<CFGNode>();;
	private List<CFGNode> optPreds = new ArrayList<CFGNode>();;
	private List<CFGNode> optSuccs = new ArrayList<CFGNode>();;
	private final Node ast;
	private final int number;

	CFGNode() { throw new UnsupportedOperationException(); }

	public CFGNode(final Node ast) {
		number = CFGNodeNumber.getNext();
		this.ast = ast;
	}

	public int getNumber() {
		return number;
	}

	public Node getAST() {
		return ast;
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
	 * Adds an edge between two nodes
	 * @param to which node to add the edge to
	 */
	public void addEdge(final CFGNode to) {
		addSucc(to);
		to.addPred(this);
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

	@Override
	public String toString() {
		return Integer.toString(number);
	}
}
