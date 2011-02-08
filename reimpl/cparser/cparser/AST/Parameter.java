/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class Parameter extends Node {
	private boolean varArgs = false;

	@Override
	public void setAttr(final String attr, final String value) {
		if (!attr.equals("varArgs"))
			throw new RuntimeException(
				"parameter: invalid attribute: " + attr);
		varArgs = true;
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		super.XMLChildren(sb);
		if (varArgs)
			sb.append("<varArgs/>");
	}
}
