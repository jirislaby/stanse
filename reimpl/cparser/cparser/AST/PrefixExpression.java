/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;

/**
 * @author Jiri Slaby
 */
public class PrefixExpression extends Node {
	public enum PREFIX_EXPR {
		PLUS,
		MINUS,
		PLUSPLUS,
		MINUSMINUS,
		NEG,
		NOT,
		LABREF,
		REAL,
		IMAG,
		PE_SIZE
	}
	private static final Table<PREFIX_EXPR> ops;
	private final PREFIX_EXPR op;

	static {
		ops = new Table<PREFIX_EXPR>(PREFIX_EXPR.PE_SIZE.ordinal());
		ops.fill("+", PREFIX_EXPR.PLUS);
		ops.fill("-", PREFIX_EXPR.MINUS);
		ops.fill("++", PREFIX_EXPR.PLUSPLUS);
		ops.fill("--", PREFIX_EXPR.MINUSMINUS);
		ops.fill("~", PREFIX_EXPR.NEG);
		ops.fill("!", PREFIX_EXPR.NOT);
		ops.fill("&&", PREFIX_EXPR.LABREF);
		ops.fill("__real", PREFIX_EXPR.REAL);
		ops.fill("__imag", PREFIX_EXPR.IMAG);
	}

	public PrefixExpression(final String op) {
		this.op = ops.getVal(op);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" op=\"").
			append(BinaryExpression.encodeXML(ops.getStr(op))).
			append('"');
	}
}
