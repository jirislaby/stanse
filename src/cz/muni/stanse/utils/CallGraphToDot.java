package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFGHandle;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class CallGraphToDot {

    // public section

    public static String
            run(DefaultDirectedGraph<CFGHandle, DefaultEdge> graph) {
        StringBuilder result = new StringBuilder();

        result.append("digraph stronglyConnected {\n");
        for(final CFGHandle cfgh: graph.vertexSet())
            result.append('"').append(cfgh.getFunctionName()).append("\"\n");
        for(DefaultEdge edge: graph.edgeSet())
            result.append('"').append(graph.getEdgeSource(edge)).
		append("\" -> \"").append(graph.getEdgeTarget(edge)).
		append("\";\n");
        result.append('}');

        return result.toString();
    }

    // private section

    private CallGraphToDot() {
    }
}
