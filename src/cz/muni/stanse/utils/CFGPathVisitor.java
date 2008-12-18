package cz.muni.stanse.utils;

import java.util.List;

public abstract class CFGPathVisitor {
  public abstract boolean visit(final List<cz.muni.stanse.codestructures.CFGNode> path);
}
