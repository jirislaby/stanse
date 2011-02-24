/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class InitDeclarator extends Node {
	private boolean hasInitializer = false;

	@Override
	public void compute() {
		super.compute();
		if (children.size() > 1)
			hasInitializer = true;
	}

	public Declarator getDeclarator() {
		return (Declarator)getChild(0);
	}

	public boolean hasInitializer() {
		return hasInitializer;
	}

	public Initializer getInitializer() {
		return hasInitializer ? (Initializer)getChild(1) : null;
	}
}
