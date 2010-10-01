/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import org.dom4j.Element;

/**
 * Specific CFG node for holding info about if/switch and other branching
 */
public class CFGBranchNode extends CFGNode {

    /**
     * Creates an instance of the CFGBranchNode with assigned element
     *
     * @param e element to assign to this node
     */
    public CFGBranchNode(Element e) {
	super(e);
    }

    @Override
    public String toString() {
	return "B " + super.toString();
    }
}
