/*
 * Licensed under GPLv2
 */

package cparser.CFG;

/**
 *
 * @author Jiri Slaby
 */
public class CFGNodeNumber {
	private static int number;

	public synchronized static int getNext() {
		return number++;
	}
}
