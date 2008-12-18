/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import org.dom4j.Element;

/**
 * Represents a node in control-flow graph
 */
public class CFGBreakNode extends CFGNode {

    private CFGBreakNode() {
    }

    /**
     * Creates a new instance of CFGBreakNode
     */
    public CFGBreakNode(Element e) {
	super(e);
    }

    public void addEdge(CFGNode to) {
	/* nothing */
    }

    public void addBreakEdge(CFGNode to) {
	super.addEdge(to);
    }

    public void replaceEdge(CFGNode oldTo, CFGNode newTo) {
	int idx = indexOfSucc(oldTo);
	removeSucc(idx);
	addSucc(idx, newTo);
	newTo.addPred(this);
    }

    @Override
    public String toString() {
	return "X " + super.toString();
    }
}
