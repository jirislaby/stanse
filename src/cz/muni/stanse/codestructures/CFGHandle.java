/*
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

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

    public CFG getCFG() {
        if (cfg == null)
            makeAvailable();
        return cfg;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getFunctionName() {
        return functionName;
    }

    private synchronized void makeAvailable() {
        if (cfg != null)
            return;
        for (CFG c: unit.getCFGs())
            if (c.getFunctionName().equals(functionName)) {
                cfg = c;
                break;
            }
        assert(cfg != null);
    }
}
