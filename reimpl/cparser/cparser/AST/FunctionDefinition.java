/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class FunctionDefinition extends Node {
	private int endLine = -1;

	public void setEndLine(int line) {
		endLine = line;
	}

	public int getEndLine() {
		return endLine;
	}

	@Override
	void XMLattributes(final StringBuilder sb) {
		if (endLine <= 0)
			throw new RuntimeException("wrong endline value");
		sb.append(" el=\"").append(endLine).append('"');
	}

}
