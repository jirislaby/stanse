/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class FunctionDefinition extends Node {
	private Id name;
	private int endLine = -1;

	public void setEndLine(int line) {
		endLine = line;
	}

	public int getEndLine() {
		return endLine;
	}

	@Override
	public void compute() {
		assert(children.size() >= 3); /* list of K&R declarations */
		final Node decl = getChild(1);
		Node id;
		int idx = 0;

		assert(decl instanceof Declarator);

		do {
			id = decl.getChild(idx++);
		} while (id instanceof Pointer);

		if (!(id instanceof Id)) {
			System.err.println("FunctionDef: no ID in child 1:");
			System.err.println(toXML());
			assert(false);
		}
		name = (Id)id;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (endLine <= 0)
			throw new RuntimeException("wrong endline value");
		sb.append(" el=\"").append(endLine).append('"');
	}

}
