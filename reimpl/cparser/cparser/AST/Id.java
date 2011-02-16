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

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + id + ")";
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		sb.append(id);
	}
}
