/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class PrefixExpression extends Node {
	final String op;

	public PrefixExpression(final String op) {
		this.op = op;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" op=\"").append(op).append('"');
	}
}
