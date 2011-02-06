/*
 * Licensed under GPLv2
 */

package cparser.AST;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Jiri Slaby
 */
public class TypeName extends Node {
	private static final Map<String,Integer> strFlag;
	private static final Map<Integer,String> flagStr;
	private int flags = 0;

	static {
		strFlag = new HashMap<String,Integer>();
		flagStr = new HashMap<Integer,String>();
		fill("const", 1);
		fill("restrict", 2);
		fill("volatile", 4);
	}

	private static void fill(final String op, int binop) {
		strFlag.put(op, binop);
		flagStr.put(binop, op);
	}

	private static int strToFlag(final String flag) {
		final Integer ret = strFlag.get(flag);
		if (ret == null)
			throw new RuntimeException("Invalid flag: " + flag);
		return ret;
	}

	@Override
	public void setAttr(final String attr, final String value) {
		flags |= strToFlag(attr);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		for (Entry<Integer,String> e : flagStr.entrySet())
			if ((flags & e.getKey()) != 0)
				sb.append(' ').append(e.getValue()).
					append("=\"1\"");
	}
}
