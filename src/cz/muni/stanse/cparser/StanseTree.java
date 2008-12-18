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

public class StanseTree extends CommonTree {
    private Element e;

    public StanseTree(Token t) {
	super(t);
    }

    public StanseTree(StanseTree node) {
	super(node);
    }

    public Tree dupNode() {
	return new StanseTree(this);
    }

    public void setElement(Element e) {
	this.e = e;
    }

    public Element getElement() {
	return e;
    }
}
