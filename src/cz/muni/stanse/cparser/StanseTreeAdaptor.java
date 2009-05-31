/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.cparser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;

/**
 * ANTLR adaptor to create tree nodes
 *
 * Used for creating our StanseTree nodes.
 */
public class StanseTreeAdaptor extends CommonTreeAdaptor {
    @Override
    public Object create(Token payload) {
	return new StanseTree(payload);
    }

    @Override
    public Object errorNode(TokenStream input, Token start, Token stop,
	    RecognitionException e) {
	return new StanseErrorNode(input, start, stop, e);
    }
}
