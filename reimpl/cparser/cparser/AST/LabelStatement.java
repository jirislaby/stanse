/*
 * Licensed under GPLv2
 */

package cparser.AST;

import cparser.CFG.CFG;

/**
 * @author Jiri Slaby
 */
public class LabelStatement extends Statement {
	private final String label;

	private LabelStatement() { throw new UnsupportedOperationException(); }

	public LabelStatement(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public Statement getStatement() {
		return (Statement)getChild(0);
	}

	public void fillCFG(final CFG cfg) {
		getStatement().fillCFG(cfg);
	}

	@Override
	void XMLAttributes(final StringBuilder sb) {
		sb.append(" id=\"").append(label).append('"');
	}
}
