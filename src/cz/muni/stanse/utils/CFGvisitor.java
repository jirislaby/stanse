package cz.muni.stanse.utils;

public abstract class CFGvisitor {
    public abstract boolean visit(final CFGEdge edge,
                                  final org.dom4j.Element element);
}
