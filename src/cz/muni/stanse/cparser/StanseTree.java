/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.cparser;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.Token;

import org.dom4j.Element;

/**
 * Specific AST node
 *
 * StanseTree AST node is used for holding/handling also an XML element.
 */
public class StanseTree extends CommonTree {
    private Element e;

    public StanseTree() {
	super();
    }

    public StanseTree(Token t) {
	super(t);
    }

    public StanseTree(StanseTree node) {
	super(node);
    }

    @Override
    public Tree dupNode() {
	return new StanseTree(this);
    }

    /**
     * Method for setting Element to this node
     *
     * @param e element to set
     */
    public void setElement(Element e) {
	this.e = e;
    }

    /**
     * Method for getting Element assigned to this node
     *
     * @return assigned element
     */
    public Element getElement() {
	return e;
    }
}
