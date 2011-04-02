/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;
import cparser.CFG.CFGFiller;

/**
 * @author Jiri Slaby
 */
public class CompoundStatement extends Statement {
	public void fillCFG(final CFG cfg) {
		for (final Node n : children) {
			if (!(n instanceof CFGFiller))
				continue;
			final CFGFiller filler = (CFGFiller)n;
			filler.fillCFG(cfg);
		}
	}
}
