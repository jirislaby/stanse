/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Id extends Node {
	private String id;
	private String oldId = null;

	private Id() { throw new UnsupportedOperationException(); }

	public Id(final String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + id + ")";
	}

	@Override
	void XMLattributes(final StringBuilder sb) {
		sb.append(" id=\"").append(id).append('"');
	}
}
