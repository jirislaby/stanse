/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Declarator extends Node {
	private Id id;

	@Override
	public void compute() {
		super.compute();
		Node child = getChild(0);
		if (child instanceof Pointer)
			child = getChild(1);
		if (child instanceof Declarator)
			id = ((Declarator)child).getId();
		else
			id = (Id)child;
	}

	public Id getId() {
		return id;
	}
}
