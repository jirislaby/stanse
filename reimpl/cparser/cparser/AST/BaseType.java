/*
 * Licensed under GPLv2
 */

package cparser.AST;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiri Slaby
 */
public class BaseType extends Node {

	enum BASE_TYPE {
		VOID,
		CHAR,
		SHORT,
		INT,
		LONG,
		FLOAT,
		DOUBLE,
		SIGNED,
		UNSIGNED,
		BOOL,
		COMPLEX,
		IMAGINARY,
	};
	final BASE_TYPE type;

	private static final Map<String,BASE_TYPE> strType;
	private static final Map<BASE_TYPE,String> typeStr;

	static {
		strType = new HashMap<String,BASE_TYPE>();
		typeStr = new HashMap<BASE_TYPE,String>();
		fill("void", BASE_TYPE.VOID);
		fill("char", BASE_TYPE.CHAR);
		fill("short", BASE_TYPE.SHORT);
		fill("int", BASE_TYPE.INT);
		fill("long", BASE_TYPE.LONG);
		fill("float", BASE_TYPE.FLOAT);
		fill("double", BASE_TYPE.DOUBLE);
		fill("signed", BASE_TYPE.SIGNED);
		fill("unsigned", BASE_TYPE.UNSIGNED);
		fill("_Bool", BASE_TYPE.BOOL);
		fill("_Complex", BASE_TYPE.COMPLEX);
		fill("_Imaginary", BASE_TYPE.IMAGINARY);
	};

	private static void fill(final String op, BASE_TYPE binop) {
		strType.put(op, binop);
		typeStr.put(binop, op);
	}

	private static BASE_TYPE strToType(final String op) {
		final BASE_TYPE ret = strType.get(op);
		if (ret == null)
			throw new RuntimeException("Invalid type: " + op);
		return ret;
	}

	private BaseType() { throw new UnsupportedOperationException(); }

	public BaseType(final String type) {
		this.type = strToType(type);
	}

	@Override
	public String toString() {
		return super.toString() + " (" + typeStr.get(type) + ")";
	}

	@Override
	void XMLattributes(final StringBuilder sb) {
		sb.append(" type=\"").append(typeStr.get(type)).append('"');
	}
}
