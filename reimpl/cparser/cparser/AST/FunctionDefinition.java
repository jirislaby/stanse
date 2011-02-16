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
		assert(getChild(1) instanceof Declarator);
		name = (Id)getChild(1).getChild(0);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (endLine <= 0)
			throw new RuntimeException("wrong endline value");
		sb.append(" el=\"").append(endLine).append('"');
	}

}
