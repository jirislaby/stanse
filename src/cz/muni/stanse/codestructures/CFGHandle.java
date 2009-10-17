/*
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import cz.muni.stanse.Stanse;
import org.dom4j.Element;

import java.util.Set;

/**
 * Holds a handle to a CFG which might be unmapped
 */
public class CFGHandle {
    private Unit unit;
    private CFG cfg;
    private String functionName;

    public CFGHandle(Unit unit, CFG cfg) {
        this.unit = unit;
        this.cfg = cfg;
        functionName = cfg.getFunctionName();
    }

    public synchronized void drop() {
        /* what if somebody still needs it? */
        cfg.drop();
        cfg = null;
    }

    public Element getElement() {
        return getCFG().getElement();
    }

    public Set<CFGNode> getAllNodes() {
        return getCFG().getAllNodes();
    }

    public Set<CFGNode> getAllNodesReverse() {
        return getCFG().getAllNodesReverse();
    }

    public CFGNode getStartNode() {
        return getCFG().getStartNode();
    }

    public CFGNode getEndNode() {
        return getCFG().getEndNode();
    }

    public String toDot() {
        return getCFG().toDot();
    }

    @Override
    public String toString() {
        return getCFG().toString();
    }

    public boolean isSymbolLocal(String symbol) {
        return getCFG().isSymbolLocal(symbol);
    }

    protected Unit getUnit() {
        return unit;
    }

    public String getFunctionName() {
        return functionName;
    }

    private CFG getCFG() {
        Stanse.getUnitManager().touchUnit(unit);
        if (cfg == null)
            makeAvailable();
        return cfg;
    }

    private synchronized void makeAvailable() {
        if (cfg != null)
            return;
        cfg = unit.getCFG(functionName);
        assert(cfg != null);
    }
}
