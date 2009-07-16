/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.codestructures.traversal;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.List;
import java.util.Stack;

public abstract class CFGPathVisitor {
    public abstract boolean visit(List<CFGNode> path,Stack<CFGNode> cfgContext);
    public abstract boolean onCFGchange(CFGNode from, CFGNode to);
}
