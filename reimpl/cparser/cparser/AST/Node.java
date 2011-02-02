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
	final Vector children = new Vector();

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
