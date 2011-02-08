/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class PostfixExpression extends Node {
	final String op;

	public PostfixExpression(final String op) {
		this.op = op;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" op=\"").append(op).append('"');
	}
}
