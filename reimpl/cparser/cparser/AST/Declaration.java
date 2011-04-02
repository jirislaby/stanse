/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;
import cparser.CFG.CFGFiller;
import cparser.CFG.CFGNode;

/**
 * @author Jiri Slaby
 */
public class Declaration extends Node implements CFGFiller {
	private boolean isTypedef = false;

	@Override
	public void compute() {
		super.compute();
		final DeclarationSpecifiers ds =
			(DeclarationSpecifiers)getChild(0);
		if (ds.getModifiers().hasSC(Modifiers.SC.TYPEDEF))
			isTypedef = true;
		if (isTypedef)
			return;
	}

	public void fillCFG(CFG cfg) {
		int a;

		if (isTypedef)
			return;

		for (a = 1; a < children.size(); a++) {
			final InitDeclarator id = (InitDeclarator)getChild(a);
			if (id.hasInitializer())
				cfg.append(new CFGNode(id));
		}
	}

}
