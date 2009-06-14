package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public final class LazyInternalProgramStructuresCollection {

    public LazyInternalProgramStructuresCollection(final Collection<CFG> CFGs,
                                                final boolean interprocedural) {
        this.CFGs = CFGs;
        this.interprocedural = interprocedural;

        startFunctions = null;
        callGraph = null;
        navigator = null;
        argumentPassingManager = null;
        returnValuePassingManager = null;
        nodeToCFGdictionary = null;
        elementToCFGdictionary = null;
    }

    public Collection<CFG> getCFGs() {
        return CFGs;
    }

    public HashSet<CFG> getStartFunctions() {
        if (startFunctions == null)
            startFunctions = StartFunctionsSetBuilder.run(getCallGraph());
        return startFunctions;
    }

    public DefaultDirectedGraph<CFG, DefaultEdge> getCallGraph() {
        if (callGraph == null)
            callGraph = CallGraphBuilder.run(getCFGs(),getNavigator(),
                                             getNodeToCFGdictionary());
        return callGraph;
    }

    public ArgumentPassingManager getArgumentPassingManager() {
        if (argumentPassingManager == null)
            argumentPassingManager = new ArgumentPassingManager(getNavigator(),
                                                      getNodeToCFGdictionary());
        return argumentPassingManager;
    }

    public ReturnValuePassingManager getReturnValuePassingManager() {
        if (returnValuePassingManager == null)
            returnValuePassingManager =
                new ReturnValuePassingManager(getNavigator());
        return returnValuePassingManager;
    }

    public CFGsNavigator getNavigator() {
        if (navigator == null)
            navigator = (interprocedural) ?
                                    new InterproceduralCFGsNavigator(getCFGs(),
                                                  getElementToCFGdictionary()) :
                                    new IntraproceduralCFGsNavigator(getCFGs());
        return navigator;
    }

    public HashMap<CFGNode,CFG> getNodeToCFGdictionary() {
        if (nodeToCFGdictionary == null)
            nodeToCFGdictionary = NodeToCFGdictionaryBuilder.run(getCFGs());
        return nodeToCFGdictionary;
    }

    public ElementCFGdictionary getElementToCFGdictionary() {
        if (elementToCFGdictionary == null)
            elementToCFGdictionary = new ElementCFGdictionary(getCFGs());
        return elementToCFGdictionary;
    }

    public void clearStartFunctions() { startFunctions = null; }
    public void clearCallGraph() { callGraph = null; }
    public void clearArgumentPassingManager() { argumentPassingManager = null; }
    public void clearNavigator() { navigator = null; }
    public void clearNodeToCFGdictionary() { nodeToCFGdictionary = null; }
    public void clearElementToCFGdictionary() { elementToCFGdictionary = null; }

    public void clear() {
        clearStartFunctions();
        clearCallGraph();
        clearArgumentPassingManager();
        clearNavigator();
        clearNodeToCFGdictionary();
        clearElementToCFGdictionary();
    }

    // private section

    private final Collection<CFG> CFGs;
    private final boolean interprocedural;

    private HashSet<CFG> startFunctions;
    private DefaultDirectedGraph<CFG,DefaultEdge> callGraph;
    private CFGsNavigator navigator;
    private ArgumentPassingManager argumentPassingManager;
    private ReturnValuePassingManager returnValuePassingManager;
    private HashMap<CFGNode,CFG> nodeToCFGdictionary;
    private ElementCFGdictionary elementToCFGdictionary;
}
