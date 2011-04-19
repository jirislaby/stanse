/**
 * @file AutomatonState.java
 * @brief Here are defined automata states for all the automata defined in XML
 *        automata definition file of automatonchecker package.
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.Stack;

/**
 * @brief This class represents automaton state for each automata class defined
 *        in XML automata definition file.
 *
 *  Automata states represents the data, which are delivered between
 *  PatternLocation instances, which is the main idea in whole automatonchecker
 *  package. So each automaton state presented in some PatternLocation tells as,
 *  that related automaton (for that state) already was in the location in that
 *  state.
 *
 * @see cz.muni.stanse.automatonchecker.PatternLocation
 */
final class AutomatonState {

    // public section

    @Override
    public boolean equals(final Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((AutomatonState)obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
        hash = 97 * hash + (this.context != null ? this.context.hashCode() : 0);
        return hash;
    }

    // package-private section

    AutomatonState(final String symbol, final SimpleAutomatonID id) {
        this(symbol,new ComposedAutomatonID(id));
    }

    AutomatonState(final String symbol, final ComposedAutomatonID id) {
        this.symbol = symbol;
        context = new Stack<AutomatonStateContextItem>();
        getContext().push(new AutomatonStateContextItem(id));
    }

    AutomatonState(final String symbol,
                   final Stack<AutomatonStateContextItem> context) {
        this.symbol = symbol;
        this.context = context;
    }

    String getSymbol() {
        return symbol;
    }

    ComposedAutomatonID getAutomatonID() {
        return getContext().peek().getAutomatonID();
    }

    CFGNode getCFGNode() {
        return getContext().peek().getCFGNode();
    }

    Stack<AutomatonStateContextItem> getContext() {
	return context;
    }

    boolean isEqualWith(AutomatonState other) {
        return getSymbol().equals(other.getSymbol()) &&
               getContext().equals(other.getContext());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(symbol);
        for (AutomatonStateContextItem ctxitem : context)
            sb.append("[").append(ctxitem.getAutomatonID()).append("]");
        return sb.toString();
    }

    // private section

    private final String symbol;
    private Stack<AutomatonStateContextItem> context;
}
