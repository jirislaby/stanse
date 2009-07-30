package cz.muni.stanse.codestructures.builders;

import cz.muni.stanse.codestructures.CFG;
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
        for (final CFGHandle cfgh : CFGs) {
            CFG cfg = cfgh.getCFG();
            for (final CFGNode node: cfg.getAllNodes())
                result.put(node,cfgh);
        }
        return result;
    }

    // private section

    private NodeToCFGdictionaryBuilder() {
    }
}
