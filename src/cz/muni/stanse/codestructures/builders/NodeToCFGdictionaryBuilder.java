package cz.muni.stanse.codestructures.builders;

import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.HashMap;
import java.util.Collection;

public final class NodeToCFGdictionaryBuilder {

    // public section

    public static HashMap<CFGNode,CFGHandle>
            run(final Collection<CFGHandle> CFGs) {
        final HashMap<CFGNode,CFGHandle> result =
                new HashMap<CFGNode,CFGHandle>();
        for (final CFGHandle cfg: CFGs) {
            for (final CFGNode node: cfg.getAllNodes())
                result.put(node, cfg);
        }
        return result;
    }

    // private section

    private NodeToCFGdictionaryBuilder() {
    }
}
