/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Enum extends Node {
	private String name = null;

	@Override
	public void setAttrSS(final String name, final String value) {
		if (!name.equals("id"))
			throw new RuntimeException("invalid attr");
		this.name = value;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (name != null)
			sb.append(" id=\"").append(name).append('"');
	}
}
