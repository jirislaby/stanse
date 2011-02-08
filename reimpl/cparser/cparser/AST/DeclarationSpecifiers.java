/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class DeclarationSpecifiers extends Node {
	private Modifiers mods = new Modifiers();

	@Override
	public void setAttr(final String attr, final String value) {
		if (attr.equals("storageClass")) {
			if (!mods.setSC(value))
				throw new RuntimeException(
					"DeclSpec: invalid StorageClass: " +
					value);
		} else if (!mods.setTQ(attr))
			throw new RuntimeException(
				"DeclSpec: invalid attribute: " + attr);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		for (final String tq : mods.getTQList())
			sb.append(' ').append(tq).append("=\"1\"");
		if (mods.hasSC())
			sb.append(" storageClass=\"").
				append(mods.getSCString()).append('"');
	}
}
