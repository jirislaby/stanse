package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFG;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class CallGraphToDot {

    // public section

    public static String run(DefaultDirectedGraph<CFG,DefaultEdge> graph) {
        String result = "";

        result += "digraph stronglyConnected {\n";
        for(final CFG vertex : graph.vertexSet())
            result += "\"" + vertex + "\"\n";
        for(DefaultEdge edge : graph.edgeSet())
            result += "\"" + graph.getEdgeSource(edge) + "\" -> \"" +
                             graph.getEdgeTarget(edge) + "\";\n";
        result += "}";

        return result;
    }

    // private section

    private CallGraphToDot() {
    }
}
