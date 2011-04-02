/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Enumerator extends Node {
	private final String name;

	private Enumerator() { throw new UnsupportedOperationException(); }

	public Enumerator(final String name) {
		this.name = name;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" id=\"").append(name).append('"');
	}
}
