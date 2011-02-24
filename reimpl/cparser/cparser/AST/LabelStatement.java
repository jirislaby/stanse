/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;

/**
 * @author Jiri Slaby
 */
public class LabelStatement extends Statement {
	private String label;

	private LabelStatement() { throw new UnsupportedOperationException(); }

	public LabelStatement(final String label) {
		this.label = label;
	}

	public void fillCFG(final CFG cfg) {
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" id=\"").append(label).append('"');
	}
}
