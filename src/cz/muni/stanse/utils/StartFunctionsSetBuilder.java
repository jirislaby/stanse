package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFG;

import java.util.HashSet;
import java.util.LinkedList;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public final class StartFunctionsSetBuilder {

    // public section

    @SuppressWarnings("unchecked")
    public static HashSet<CFG>
    run(final DefaultDirectedGraph<CFG,DefaultEdge> callGraph) {
        return runDestructive((DefaultDirectedGraph<CFG,DefaultEdge>)
                              callGraph.clone());
    }

    public static HashSet<CFG>
    runDestructive(final DefaultDirectedGraph<CFG,DefaultEdge> callGraph) {
        final HashSet<CFG> result = new HashSet<CFG>();
        for (int inDegree = 0; !callGraph.vertexSet().isEmpty(); ++inDegree) {
            final HashSet<CFG> fnSet =
                getFunctionsWithInDegree(inDegree,callGraph);
            result.addAll(fnSet);
            removeComponentContaining(fnSet,callGraph);
        }
        assert(callGraph.vertexSet().isEmpty());
        return result;
    }

    // private section

    private static HashSet<CFG> getFunctionsWithInDegree(int inDegree,
                        final DefaultDirectedGraph<CFG,DefaultEdge> callGraph) {
        final HashSet<CFG> result = new HashSet<CFG>();
        for (final CFG cfg : callGraph.vertexSet())
            if (callGraph.inDegreeOf(cfg) == inDegree)
                result.add(cfg);
        return result;
    }

    private static void removeComponentContaining(final HashSet<CFG> fnSet,
                        final DefaultDirectedGraph<CFG,DefaultEdge> callGraph) {
        final LinkedList<CFG> progressQueue = new LinkedList<CFG>();
        progressQueue.addAll(fnSet);
        do {
            final CFG cfg = progressQueue.poll();
            if (!callGraph.containsVertex(cfg))
                continue;
            for (DefaultEdge e :callGraph.outgoingEdgesOf(cfg))
                progressQueue.add(callGraph.getEdgeTarget(e));
            callGraph.removeVertex(cfg);
        } while (!progressQueue.isEmpty());
    }

    private StartFunctionsSetBuilder() {
    }
}
