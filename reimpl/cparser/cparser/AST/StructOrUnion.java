/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class StructOrUnion extends Node {
	private String id = null;

	@Override
	public void setAttr(final String name, final String value) {
		if (name.equals("id"))
			this.id = value;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (id != null)
			sb.append(" id=\"").append(id).append('"');
	}
}
