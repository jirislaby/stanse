/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;

/**
 * @author Jiri Slaby
 */
public class ExpressionStatement extends Statement {
	public Expression getExpression() {
		return (Expression)getChild(0);
	}

	public void fillCFG(final CFG cfg) {
		getExpression().fillCFG(cfg);
	}
}
