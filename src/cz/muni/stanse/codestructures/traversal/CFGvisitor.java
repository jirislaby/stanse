/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.codestructures.traversal;

import cz.muni.stanse.codestructures.CFGNode;

import org.dom4j.Element;

public abstract class CFGvisitor {
    public abstract boolean visit(CFGNode node, Element element);
}
