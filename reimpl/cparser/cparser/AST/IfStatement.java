/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;
import cparser.CFG.CFGBranchNode;

/**
 * @author Jiri Slaby
 */
public class IfStatement extends Statement {
	private boolean hasElse = false;

	public Expression getTest() {
		return (Expression)getChild(0);
	}

	public Statement getThen() {
		return (Statement)getChild(1);
	}

	public boolean hasElse() {
		return hasElse;
	}

	public Statement getElse() {
		return hasElse ? (Statement)getChild(2) : null;
	}

	@Override
	public void compute() {
		super.compute();
		hasElse = children.size() == 3;
	}

	public void fillCFG(final CFG cfg) {
		final CFGBranchNode branch = new CFGBranchNode(getTest());
		branch.addEdge(branch, new IntConst("0"));
		getThen().fillCFG(cfg);
		if (hasElse)
			getElse().fillCFG(cfg);

	}
}
