/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class LabelStatement extends Node {
	private String label;

	private LabelStatement() { throw new UnsupportedOperationException(); }

	public LabelStatement(final String label) {
		this.label = label;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" id=\"").append(label).append('"');
	}
}
