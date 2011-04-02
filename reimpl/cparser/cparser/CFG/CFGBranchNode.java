/*
 * Licensed under GPLv2
 */

package cparser.CFG;

import cparser.AST.Node;

/**
 * The ControlFlowGraph of a function
 *
 * @author Jiri Slaby
 */
public class CFGBranchNode extends CFGNode {
	CFGBranchNode() {
		super();
	}

	public CFGBranchNode(final Node ast) {
		super(ast);
	}

	/**
	 * Get label of a branch edge indexed from 0
	 *
	 * @param edge edge index
	 * @return element which is the label
	 */
	public Node getEdgeLabel(int edge) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
