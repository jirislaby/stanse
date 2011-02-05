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

	enum BIN_OP {
		EQ,
		PLUS,
		MINUS,
		MUL,
		DIV,
	};
	final BIN_OP op;

	private static final Map<String, BIN_OP> strOps;
	private static final Map<BIN_OP, String> opsStr;

	static {
		strOps = new HashMap<String, BIN_OP>();
		opsStr = new HashMap<BIN_OP, String>();
		fill("==", BIN_OP.EQ);
		fill("+", BIN_OP.PLUS);
		fill("-", BIN_OP.MINUS);
		fill("*", BIN_OP.MUL);
		fill("/", BIN_OP.DIV);
	};

	private static void fill(final String op, BIN_OP binop) {
		strOps.put(op, binop);
		opsStr.put(binop, op);
	}

	private static BIN_OP strToOp(final String op) {
		final BIN_OP ret = strOps.get(op);
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
