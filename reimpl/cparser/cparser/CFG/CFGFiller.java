/*
 * Licensed under GPLv2
 */

package cparser.CFG;

/**
 *
 * @author Jiri Slaby
 */
public interface CFGFiller {
	/**
	 * Fill the @cfg by actual nodes
	 *
	 * @param cfg CFG to fill
	 */
	public void fillCFG(final CFG cfg);
}
