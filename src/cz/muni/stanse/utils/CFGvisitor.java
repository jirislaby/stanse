package cz.muni.stanse.utils;

public abstract class CFGvisitor {
    public abstract boolean visit(cz.muni.stanse.parser.CFGNode node,
                                  org.dom4j.Element element);
}
