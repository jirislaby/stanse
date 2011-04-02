/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;

/**
 * @author Jiri Slaby
 */
public class DefaultLabelStatement extends Statement {
	public Statement getStatement() {
		return (Statement)getChild(0);
	}

	public void fillCFG(final CFG cfg) {
		getStatement().fillCFG(cfg);
	}
}
