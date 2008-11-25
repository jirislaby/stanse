package cz.muni.stanse.utils;

import cz.muni.stanse.xml2cfg.CFGEdge;

public abstract class CFGvisitor {
    public abstract boolean visit(final CFGEdge edge,
                           final org.dom4j.Element element) throws Exception;
}
