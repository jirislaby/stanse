/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;

/**
 * @author Jiri Slaby
 */
public class EmptyStatement extends Statement {
	public void fillCFG(CFG cfg) {
		/* nothing to do here, really :-) */
	}
}
