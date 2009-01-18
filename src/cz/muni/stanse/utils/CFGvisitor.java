/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.utils;

public abstract class CFGvisitor {
    public abstract boolean visit(cz.muni.stanse.codestructures.CFGNode node,
                                  org.dom4j.Element element);
}
