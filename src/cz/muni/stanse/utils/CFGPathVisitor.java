/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.List;

public abstract class CFGPathVisitor {
  public abstract boolean visit(final List<CFGNode> path);
}
