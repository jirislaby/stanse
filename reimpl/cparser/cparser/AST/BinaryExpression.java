/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiri Slaby
 */
public class BinaryExpression extends Expression {
	enum BIN_EXP {
		EQ,
		NE,
		LEQ,
		GEQ,
		LT,
		GT,
		PLUS,
		MINUS,
		MUL,
		DIV,
		MOD,
		SHL,
		SHR,
		XOR,
		BAND,
		BOR,
		AND,
		OR,
		BE_SIZE
	};
	final BIN_EXP op;

	private static final Table<BIN_EXP> ops;

	static {
		ops = new Table<BIN_EXP>(BIN_EXP.BE_SIZE.ordinal());
		ops.fill("==", BIN_EXP.EQ);
		ops.fill("!=", BIN_EXP.NE);
		ops.fill("<=", BIN_EXP.LEQ);
		ops.fill(">=", BIN_EXP.GEQ);
		ops.fill("<", BIN_EXP.LT);
		ops.fill(">", BIN_EXP.GT);
		ops.fill("+", BIN_EXP.PLUS);
		ops.fill("-", BIN_EXP.MINUS);
		ops.fill("*", BIN_EXP.MUL);
		ops.fill("/", BIN_EXP.DIV);
		ops.fill("%", BIN_EXP.MOD);
		ops.fill("<<", BIN_EXP.SHL);
		ops.fill(">>", BIN_EXP.SHR);
		ops.fill("^", BIN_EXP.XOR);
		ops.fill("&", BIN_EXP.BAND);
		ops.fill("|", BIN_EXP.BOR);
		ops.fill("&&", BIN_EXP.AND);
		ops.fill("||", BIN_EXP.OR);
	};

	private BinaryExpression() { throw new UnsupportedOperationException(); }

	public BinaryExpression(final String op) {
		this.op = ops.getVal(op);
		if (this.op == null)
			throw new RuntimeException("binExp: invalid OP: " + op);
	}

	@Override
	public String toString() {
		return super.toString() + " (" + ops.getStr(op) + ")";
	}

	public static String encodeXML(String input){
		return input.replace("&", "&amp;").
			replace("<", "&lt;").
			replace(">", "&gt;");
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" op=\"").append(encodeXML(ops.getStr(op))).
			append('"');
	}
}
