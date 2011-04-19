/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.codestructures.traversal;

import cz.muni.stanse.codestructures.CFGNode;

import org.dom4j.Element;

public abstract class CFGvisitor {
    public abstract boolean visit(CFGNode node, Element element);

    protected boolean forceEnd() {
        return !(terminate = true);
    }

    boolean visitInternal(final CFGNode node, Element element) {
        return terminate ? false : visit(node, element);
    }

    private boolean terminate = false;
}
