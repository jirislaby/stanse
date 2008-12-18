/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.cparser;

import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.Token;

public class StanseTreeAdaptor extends CommonTreeAdaptor {
    public Object create(Token payload) {
	return new StanseTree(payload);
    }
}
