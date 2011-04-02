/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;

/**
 * @author Jiri Slaby
 */
public class AssignExpression extends Expression {
	enum ASSIGN_OP {
		NONE,
		PLUS,
		MINUS,
		MUL,
		DIV,
		MOD,
		SHL,
		SHR,
		AND,
		OR,
		XOR,
		AOP_SIZE
	};

	private static final Table<ASSIGN_OP> ops;
	private ASSIGN_OP op = ASSIGN_OP.NONE;

	static {
		ops = new Table<ASSIGN_OP>(ASSIGN_OP.AOP_SIZE.ordinal());
		ops.fill("+", ASSIGN_OP.PLUS);
		ops.fill("-", ASSIGN_OP.MINUS);
		ops.fill("*", ASSIGN_OP.MUL);
		ops.fill("/", ASSIGN_OP.DIV);
		ops.fill("%", ASSIGN_OP.MOD);
		ops.fill("<<", ASSIGN_OP.SHL);
		ops.fill(">>", ASSIGN_OP.SHR);
		ops.fill("&", ASSIGN_OP.AND);
		ops.fill("|", ASSIGN_OP.OR);
		ops.fill("^", ASSIGN_OP.XOR);
	};

	@Override
	public void setAttrSS(final String name, final String value) {
		if (name.equals("op")) {
			op = ops.getVal(value);
			if (op == null)
				throw new RuntimeException(
					"assignOp: invalid op: " + value);
		} else
			throw new RuntimeException("assignOp: invalid attr: " +
				name);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (op != ASSIGN_OP.NONE)
			sb.append(" op=\"").append(BinaryExpression.encodeXML(
				ops.getStr(op))).append('"');
	}
}
