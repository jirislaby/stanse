package cz.muni.stanse.codestructures.builders;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.HashMap;
import java.util.Collection;

public final class NodeToCFGdictionaryBuilder {

    // public section

    public static HashMap<CFGNode,CFG> run(final Unit unit) {
        return run(unit.getCFGs());
    }

    public static HashMap<CFGNode,CFG> run(final Collection<CFG> CFGs) {
        final HashMap<CFGNode,CFG> result = new HashMap<CFGNode,CFG>();
        for (final CFG cfg : CFGs)
            for (final CFGNode node : cfg.getAllNodes())
                result.put(node,cfg);
        return result;
    }

    // private section

    private NodeToCFGdictionaryBuilder() {
    }
}
