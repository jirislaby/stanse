/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.tools.ASTTypeSimplifier;

/**
 * @author Jiri Slaby
 */
public class DeclarationSpecifiers extends Node {
	private Modifiers mods = new Modifiers();
	private ComplexType ctype = null;

	@Override
	public void compute() {
		super.compute();
		ctype = ASTTypeSimplifier.simplifyTypes(children);
	}

	/**
	 * We get rid of typeSpec midchild here. We always handle typeSpecs as
	 * children. See also XMLChildren.
	 *
	 * @param child child to add
	 */
	@Override
	public void addChild(final Node child) {
		if (child.getChildren().size() != 1) {
			System.err.println("Children: " + child.toXML());
			assert(false);
		}
		children.add(child.getChild(0));
	}

	@Override
	public void setAttrSS(final String attr, final String value) {
		if (attr.equals("storageClass")) {
			if (!mods.setSC(value))
				throw new RuntimeException(
					"DeclSpec: invalid StorageClass: " +
					value);
		} else if (attr.equals("function")) {
			if (!mods.setFS(value))
				throw new RuntimeException(
					"DeclSpec: invalid Function: " +
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
		if (mods.hasFS())
			sb.append(" function=\"").
				append(mods.getFSString()).append('"');
	}

	@Override
	void XMLChildren(final StringBuilder sb) {
		if (ctype != null) {
			for (final String t: ctype.getTypeStr().split(" ")) {
				sb.append("<typeSpecifier><baseType>").
					append(t).
					append("</baseType></typeSpecifier>");
			}
			return;
		}
		for (final Node ch: children)
			sb.append("<typeSpecifier>").append(ch.toXML()).
				append("</typeSpecifier>");
	}
}
