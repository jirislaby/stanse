/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Enum extends Node {
	private String name;

	@Override
	public void setAttr(final String name, final String value) {
		if (!name.equals("id"))
			throw new RuntimeException("invalid attr");
		this.name = value;
	}

	void XMLAttributes(final StringBuilder sb) {
		sb.append(" id=\"").append(name).append('"');
	}
}
