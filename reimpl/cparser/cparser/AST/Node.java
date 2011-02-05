/*
 * Licensed under GPLv2
 */

package cparser.AST;

import java.util.Vector;

/**
 * The basic member of AST tree
 *
 * @author Jiri Slaby
 */
public abstract class Node {
	final Vector<Node> children = new Vector<Node>();
	int line = 0, column = 0;

	public void setLine(int line) {
		this.line = line;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setAttr(final String attr, final String value) {
		throw new UnsupportedOperationException();
	}

	public void addChild(final Node child) {
		children.add(child);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * To be overriden by nested classes if something should appear in the
	 * XML attributes for this node
	 * @param sb where to put the attributes
	 */
	void XMLattributes(final StringBuilder sb) {
	}

	public String toXML(){
		final StringBuilder sb = new StringBuilder("<");
		final String name = getClass().getSimpleName();
		final String XMLname = Character.toLowerCase(name.charAt(0)) +
			name.substring(1);
		sb.append(XMLname);
		XMLattributes(sb);
		if (line > 0)
			sb.append(" bl=\"").append(line).append('"');
		else
			sb.append(" nobl=\"1\"");
		if (column > 0)
			sb.append(" bc=\"").append(column).append('"');
		else
			sb.append(" nobc=\"1\"");
		sb.append('>');
		boolean terminate = children.size() > 1;
		for (final Node n : children) {
			sb.append(n.toXML());
			if (terminate)
				sb.append('\n');
		}
		sb.append("</").append(XMLname).append('>');
		return sb.toString();
	}
}
