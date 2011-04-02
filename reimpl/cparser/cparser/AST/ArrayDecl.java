/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class ArrayDecl extends Node {
	private Modifiers mods = new Modifiers();
	private boolean isStatic = false;
	private boolean isAsterisk = false;

	@Override
	public void setAttrSS(final String attr, final String value) {
		if (mods.setTQ(attr))
			return;
		if (attr.equals("static"))
			isStatic = true;
		else if (attr.equals("asterisk"))
			isAsterisk = true;
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		for (final String tq : mods.getTQList())
			sb.append(' ').append(tq).append("=\"1\"");
		if (isStatic)
			sb.append(" static=\"1\"");
		if (isAsterisk)
			sb.append(" asterisk=\"1\"");
	}
}
