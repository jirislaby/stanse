/*
 * Licensed under GPLv2
 */

package cparser.AST;

/**
 * @author Jiri Slaby
 */
public class DeclarationSpecifiers extends Node {
	private boolean isTypedef = false;

	@Override
	public void setAttr(final String attr, final String value) {
		if (attr.equals("storageClass") && value.equals("typedef"))
			isTypedef = true;
//			throw new RuntimeException("invalid attribute");
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		if (isTypedef)
			sb.append(" storageClass=\"typedef\"");
	}
}
