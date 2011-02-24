/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Declaration extends Node {

	@Override
	public void compute() {
		super.compute();
/*		System.out.println("decl: " + toXML());
		final DeclarationSpecifiers ds =
			(DeclarationSpecifiers)getChild(0);
		boolean tdef = false;
		if (ds.getModifiers().hasSC(Modifiers.SC.TYPEDEF))
			tdef = true;
		System.out.println("tdef=" + tdef);*/
	}

}
