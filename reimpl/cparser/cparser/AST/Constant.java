/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Constant extends Node {
	private String val;
	private Constant() { throw new UnsupportedOperationException(); }

	public Constant(final String val) {
		this.val = val;
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		sb.append(BinaryExpression.encodeXML(val));
	}
}
