/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.Table;

/**
 * @author Jiri Slaby
 */
public class BaseType extends Node {
	public enum BASE_TYPE {
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
		BT_SIZE
	};

	private static final Table<BASE_TYPE> types;
	private final BASE_TYPE type;

	static {
		types = new Table<BASE_TYPE>(BASE_TYPE.BT_SIZE.ordinal());
		types.fill("void", BASE_TYPE.VOID);
		types.fill("char", BASE_TYPE.CHAR);
		types.fill("short", BASE_TYPE.SHORT);
		types.fill("int", BASE_TYPE.INT);
		types.fill("long", BASE_TYPE.LONG);
		types.fill("float", BASE_TYPE.FLOAT);
		types.fill("double", BASE_TYPE.DOUBLE);
		types.fill("signed", BASE_TYPE.SIGNED);
		types.fill("unsigned", BASE_TYPE.UNSIGNED);
		types.fill("_Bool", BASE_TYPE.BOOL);
		types.fill("_Complex", BASE_TYPE.COMPLEX);
		types.fill("_Imaginary", BASE_TYPE.IMAGINARY);
	};

	private BaseType() { throw new UnsupportedOperationException(); }

	public BaseType(final String type) {
		this.type = types.getVal(type);
		if (this.type == null)
			throw new RuntimeException("Invalid type: " + type);
	}

	public BASE_TYPE getType() {
		return type;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + types.getStr(type) + ")";
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		sb.append(types.getStr(type));
	}
}
