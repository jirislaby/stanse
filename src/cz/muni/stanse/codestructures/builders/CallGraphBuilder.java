package cz.muni.stanse.codestructures.builders;

import cz.muni.stanse.codestructures.CFGsNavigator;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.Map;
import java.util.Collection;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public final class CallGraphBuilder {

    // public section

    public static DefaultDirectedGraph<CFGHandle,DefaultEdge>
    run(final Collection<CFGHandle> CFGs, final CFGsNavigator navigator,
            final Map<CFGNode,CFGHandle> nodeToCFGdict) {
        final DefaultDirectedGraph<CFGHandle,DefaultEdge> result =
            new DefaultDirectedGraph<CFGHandle,DefaultEdge>(DefaultEdge.class);
        for (final CFGHandle cfgh : CFGs)
            result.addVertex(cfgh);
        for (final CFGNode callSite : navigator.callSites())
            result.addEdge(nodeToCFGdict.get(callSite),
                         nodeToCFGdict.get(navigator.getCalleeStart(callSite)));
        return result;
    }

    // private section

    private CallGraphBuilder() {
    }
}
