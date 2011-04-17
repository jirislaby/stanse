/*
 * Licensed under GPLv2
 */

package cparser.CFG;

import cparser.AST.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * The ControlFlowGraph of a function
 *
 * @author Jiri Slaby
 */
public class CFGBranchNode extends CFGNode {
	private final List<Node> labels = new ArrayList<Node>();

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
		return labels.get(edge);
	}

	/**
	 * Adds a labeled edge to the CFG from this node
	 *
	 * @param to which node to add the edge to
	 * @param label label for this edge
	 */
	public void addEdge(final CFGNode to, final Node label) {
		super.addEdge(to);
		labels.add(label);
	}

	@Override
	public void addEdge(CFGNode to) {
		throw new UnsupportedOperationException(
			"can't add edge without a label");
	}
}
