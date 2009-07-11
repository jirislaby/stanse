package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.Map;
import java.util.Collection;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public final class CallGraphBuilder {

    // public section

    public static DefaultDirectedGraph<CFG,DefaultEdge>
    run(final Unit unit, final CFGsNavigator navigator,
                         final Map<CFGNode,CFG> nodeToCFGdict) {
        return run(unit.getCFGs(),navigator,nodeToCFGdict);
    }

    public static DefaultDirectedGraph<CFG,DefaultEdge>
    run(final Collection<CFG> CFGs, final CFGsNavigator navigator,
                                    final Map<CFGNode,CFG> nodeToCFGdict) {
        final DefaultDirectedGraph<CFG,DefaultEdge> result =
            new DefaultDirectedGraph<CFG,DefaultEdge>(DefaultEdge.class);
        for (final CFG cfg : CFGs)
            result.addVertex(cfg);
        for (final CFGNode callSite : navigator.callSites())
            result.addEdge(nodeToCFGdict.get(callSite),
                         nodeToCFGdict.get(navigator.getCalleeStart(callSite)));
        return result;
    }

    // private section

    private CallGraphBuilder() {
    }
}
