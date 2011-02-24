/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;

/**
 * @author Jiri Slaby
 */
public class PostfixExpression extends Expression {
	public enum POSTFIX_EXPR {
		PLUSPLUS,
		MINUSMINUS,
		PE_SIZE
	}
	private static final Table<POSTFIX_EXPR> ops;
	private final POSTFIX_EXPR op;

	static {
		ops = new Table<POSTFIX_EXPR>(POSTFIX_EXPR.PE_SIZE.ordinal());
		ops.fill("++", POSTFIX_EXPR.PLUSPLUS);
		ops.fill("--", POSTFIX_EXPR.MINUSMINUS);
	}

	public PostfixExpression(final String op) {
		this.op = ops.getVal(op);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" op=\"").append(ops.getStr(op)).append('"');
	}
}
