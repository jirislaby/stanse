/*
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.cparser;

import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

/**
 * Specific Error node
 *
 * StanseErrorNode is used for handling exception while using StanseTree.
 */
public class StanseErrorNode extends StanseTree {
    private CommonErrorNode c;

    public StanseErrorNode(TokenStream input, Token start, Token stop,
	    RecognitionException e) {
	c = new CommonErrorNode(input, start, stop, e);
    }

    public  boolean isNil() {
	return c.isNil();
    }

    public int getType() {
	return c.getType();
    }

    public String getText() {
	return c.getText();
    }

    public String toString() {
	return c.toString();
    }
}
