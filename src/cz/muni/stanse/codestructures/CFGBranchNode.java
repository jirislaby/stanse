/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import java.util.ArrayList;
import java.util.List;

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
    public CFGBranchNode(final Element e) {
	super(e);
    }

    /**
     * Creates an instance of the CFGBranchNode with assigned element
     *
     * @param e element to assign to this node
     */
    public CFGBranchNode(final Element e, final String c) {
	super(e, c);
    }

    @Override
    public String toString() {
	return "B " + super.toString();
    }
}
