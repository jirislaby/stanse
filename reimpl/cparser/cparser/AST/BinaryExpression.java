/*
 * Licensed under GPLv2
 */

package cparser.AST;

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
	};
	final BIN_EXP op;

	private static final Map<String, BIN_EXP> strOps;
	private static final Map<BIN_EXP, String> opsStr;

	static {
		strOps = new HashMap<String, BIN_EXP>();
		opsStr = new HashMap<BIN_EXP, String>();
		fill("==", BIN_EXP.EQ);
		fill("!=", BIN_EXP.NE);
		fill("<=", BIN_EXP.LEQ);
		fill(">=", BIN_EXP.GEQ);
		fill("<", BIN_EXP.LT);
		fill(">", BIN_EXP.GT);
		fill("+", BIN_EXP.PLUS);
		fill("-", BIN_EXP.MINUS);
		fill("*", BIN_EXP.MUL);
		fill("/", BIN_EXP.DIV);
		fill("%", BIN_EXP.MOD);
		fill("<<", BIN_EXP.SHL);
		fill(">>", BIN_EXP.SHR);
		fill("^", BIN_EXP.XOR);
		fill("&", BIN_EXP.BAND);
		fill("|", BIN_EXP.BOR);
		fill("&&", BIN_EXP.AND);
		fill("||", BIN_EXP.OR);
	};

	private static void fill(final String op, BIN_EXP binop) {
		strOps.put(op, binop);
		opsStr.put(binop, op);
	}

	private static BIN_EXP strToOp(final String op) {
		final BIN_EXP ret = strOps.get(op);
		if (ret == null)
			throw new RuntimeException("Invalid OP: " + op);
		return ret;
	}

	private BinaryExpression() { throw new UnsupportedOperationException(); }

	public BinaryExpression(final String op) {
		this.op = strToOp(op);
	}

	@Override
	public String toString() {
		return super.toString() + " (" + opsStr.get(op) + ")";
	}

	@Override
	void XMLattributes(final StringBuilder sb) {
		sb.append(" op=\"").append(opsStr.get(op)).append('"');
	}
}
