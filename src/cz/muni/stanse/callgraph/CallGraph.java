/*
 * CallGraph.java
 *
 * Licensed under GPLv2.
 */

package cz.muni.stanse.callgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DirectedSubgraph;


/**
 * Represents a call graph of the given set of function definitions
 * @author xstastn
 */
public class CallGraph {
    
    /** 
     * Map representing the call graph. Procedures are specified by the String 
     * representation of their name.
     */
    private Map<String, List<String>> callingNames = new HashMap<String, List<String>>();
    
    private static Logger logger = Logger.getLogger(CallGraph.class);
    
    /**
     * Multigraph is generated, if true.
     */
    private boolean isMultiGraph = false;
    
    /**
     * Creates a new instance of CallGraph
     * 
     * @param functionDefinitions The function of which the CG will be made
     */
    public CallGraph(List<Element> functionDefinitions, boolean isMultigraph) {
        this.isMultiGraph = isMultigraph;
        for(int i=0; i<functionDefinitions.size(); i++) {
            String functionName = ((Element)functionDefinitions.get(i).selectSingleNode("declarator/descendant-or-self::id")).getText();
            Set<Element> children = new HashSet<Element>();
            Collection list = functionDefinitions.get(i).selectNodes(".//functionCall");
            children.addAll(list);
        
            List<String> childrenNames = new ArrayList<String>();
            for(Element oneNode : children) {
                if(isMultiGraph || !childrenNames.contains(oneNode.node(0).getText())) {
                    childrenNames.add(oneNode.node(0).getText());
                }
            }
            if(callingNames.containsKey(functionName)) {
                childrenNames.addAll((Collection<String>) callingNames.get(functionName));
            }
            
            callingNames.put(functionName, childrenNames);
            
        }
        
        
        
    }
    
    public CallGraph(List<Element> functionDefinitions) {
        this(functionDefinitions, false);
    }
    /**
     * Get the DirectedGraph representation for the actual call graph
     * @return directed graph of the actual call graph
     */
    public DirectedGraph generateDirectedGraph() {
        DirectedGraph<String, DefaultEdge> graph;
        if(isMultiGraph) {
            graph = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
        } else {
            graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        }
        for(String functionDefinition : callingNames.keySet()) {
            graph.addVertex(functionDefinition);
        }
        for(String functionDefinition : callingNames.keySet()) {
        
            for(String called : callingNames.get(functionDefinition)) {
                if(!graph.vertexSet().contains(called)) {
                    graph.addVertex(called);
                }
                DefaultEdge addEdge = graph.addEdge(functionDefinition, called);
            }
        }
        return graph;
    }
    
    /**
     * Get list of strongly connected subgaphs for the actual call graph
     * @return list of the strongly connected directed subgraphs
     */
    public List<DirectedSubgraph<String, DefaultEdge>> stronglyConnected() {
        StrongConnectivityInspector inspector = new StrongConnectivityInspector(generateDirectedGraph());
        return inspector.stronglyConnectedSubgraphs();
    }
    
    /**
     * Generate dot source code for single directed graph
     * @param graph Directed graph to be transformed to dot
     * @return Standalone dot source
     */
    public static String directedGraphToDot(DirectedGraph<String, DefaultEdge> graph) {
        String retString = "digraph stronglyConnected { \n";
        
        retString += directedGraphToDotBody(graph);
        
        return retString + "\n}";
    }
    
    
    /**
     * Generate body of the dot source code (vertices and egdes) for 
     * the specified directed graph
     *
     * @param graph Directed graph to be transformed to dot
     * @return Part of the dot source
     */
    private static String directedGraphToDotBody(DirectedGraph<String, DefaultEdge> graph) {
        String retString = "";
        
        for(String vertex : graph.vertexSet()) {
            retString += "\""+vertex+"\"" + "\n";
        }
        for(DefaultEdge edge : graph.edgeSet()) {
            retString += "\""+graph.getEdgeSource(edge) + "\" -> \"" + graph.getEdgeTarget(edge) + "\";\n";
        }
        return retString;
    }
    
    
    /**
     * Represent directed subgraphs of a graph in dot format. Subgraphs are
     * boxed in clusters.
     * @param parentGraph Parent graph of the subgraphs
     * @param subgraphs List of the subgraphs of the parent graph
     * @return String representing the dot source code
     */
    public static String directedSubgraphsToDot(DirectedGraph<String, DefaultEdge> parentGraph, 
        List<DirectedSubgraph<String, DefaultEdge>> subgraphs) {
        String retString = "digraph stronglyConnected { \n ";
        
        retString += directedGraphToDotBody(parentGraph);
        
        int i=1;
        for(DirectedSubgraph<String, DefaultEdge> subgraph : subgraphs) {
            retString += "subgraph \"cluster_subset"+ i + "\" { label=\"Component "+ i + "\" \n";
            for(String vertex : subgraph.vertexSet()) {
                retString += " \"" + vertex + "\"; "; 
            }
            retString += " } \n";
            i++;
        }
        retString += " } \n";
        logger.debug(retString);
        return retString; 
    }

    

    
    public String toString() {
        String result = new String();
        for(String function : callingNames.keySet()) {
            result += "==================\n" +function + "\n";
            
            result += "Calling these: \n";
            
            for(String called : callingNames.get(function)) {
                result += " > "+ called + "\n";
            }
        }
        
        return result;
    }
    
    
    
    public String toDot() {
        String result = "digraph CallGraph { \n";
        for(String function : callingNames.keySet()) {
            
            for(String called : callingNames.get(function)) {
                result += "\""+function + "\" -> \""+ called + "\"\n";
            }
        }
        result += "} \n";
        logger.debug("CallGraph dot source code: "+ result);
        return result;
    }
    
}
