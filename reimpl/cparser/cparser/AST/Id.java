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
	public void setAttrSS(final String attr, final String val) {
		if (!attr.equals("oldId"))
			throw new RuntimeException(
					"Id: invalid attr: " + attr);
		oldId = val;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + id + ")";
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		sb.append(id);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (oldId != null)
			sb.append(" oldId=\"").append(oldId).append('"');
	}
}
