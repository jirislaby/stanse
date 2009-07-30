package cz.muni.stanse.codestructures.builders;

import cz.muni.stanse.codestructures.CFGHandle;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public final class StartFunctionsSetBuilder {

    // public section

    @SuppressWarnings("unchecked")
    public static HashSet<CFGHandle>
    run(final DefaultDirectedGraph<CFGHandle,DefaultEdge> callGraph) {
        return runDestructive((DefaultDirectedGraph<CFGHandle,DefaultEdge>)
                              callGraph.clone());
    }

    public static HashSet<CFGHandle>
    runDestructive(final DefaultDirectedGraph<CFGHandle,DefaultEdge> callGraph) {
        final HashSet<CFGHandle> result = new HashSet<CFGHandle>();
        for (int inDegree = 0; !callGraph.vertexSet().isEmpty(); ++inDegree) {
            final HashSet<CFGHandle> fnSet =
                getFunctionsWithInDegree(inDegree,callGraph);
            result.addAll(fnSet);
            removeComponentContaining(fnSet,callGraph);
        }
        assert(callGraph.vertexSet().isEmpty());
        return result;
    }

    // private section

    private static HashSet<CFGHandle> getFunctionsWithInDegree(int inDegree,
            final DefaultDirectedGraph<CFGHandle,DefaultEdge> callGraph) {
        final HashSet<CFGHandle> result = new HashSet<CFGHandle>();
        for (final CFGHandle cfg : callGraph.vertexSet())
            if (callGraph.inDegreeOf(cfg) == inDegree)
                result.add(cfg);
        return result;
    }

    private static void removeComponentContaining(final Set<CFGHandle> fnSet,
            final DefaultDirectedGraph<CFGHandle,DefaultEdge> callGraph) {
        final LinkedList<CFGHandle> progressQueue = new LinkedList<CFGHandle>();
        progressQueue.addAll(fnSet);
        do {
            final CFGHandle cfg = progressQueue.poll();
            if (!callGraph.containsVertex(cfg))
                continue;
            for (DefaultEdge e: callGraph.outgoingEdgesOf(cfg))
                progressQueue.add(callGraph.getEdgeTarget(e));
            callGraph.removeVertex(cfg);
        } while (!progressQueue.isEmpty());
    }

    private StartFunctionsSetBuilder() {
    }
}
