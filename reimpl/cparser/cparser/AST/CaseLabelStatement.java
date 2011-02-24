/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;

/**
 * @author Jiri Slaby
 */
public class CaseLabelStatement extends Statement {
	public Expression getLabel() {
		return (Expression)getChild(0);
	}

	public Statement getStatement() {
		return (Statement)getChild(1);
	}

	public void fillCFG(final CFG cfg) {
		getStatement().fillCFG(cfg);
	}
}
