/*
 * Licensed under GPLv2
 */

package cparser.CFG;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The ControlFlowGraph of a function
 *
 * @author Jiri Slaby
 */
public class CFG {
	private CFGNode startNode;
	private CFGNode endNode;
	private String name;

	private CFG() {}

	public CFG(final String name) {
		this.name = name;
	}

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
	 * Appends one CFGNode to the CFG
	 * @param n a CFGNode to append at the end of this CFG
	 */
	public void append(final CFGNode n) {
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

			for (CFGNode succ : node.getSuccessors()) {
				if (!nodesDone.contains(succ)) {
					nodesToDo.add(succ);
				}
			}
			if (!optimized) {
				continue;
			}
			for (CFGNode succ : node.getOptSuccessors()) {
				if (!nodesDone.contains(succ)) {
					nodesToDo.add(succ);
				}
			}
		}

		/* endNode might be unavailable, add it unconditionally */
		nodesDone.add(getEndNode());

		return nodesDone;
	}

	/**
	 * Returns all nodes in this CFG
	 * @return list of all nodes
	 */
	protected Set<CFGNode> getAllNodes() {
		return getAllNodes(false);
	}

	/**
	 * Returns all nodes in this CFG including optimized ones
	 * @return list of all nodes
	 */
	protected Set<CFGNode> getAllNodesOpt() {
		return getAllNodes(true);
	}
}
