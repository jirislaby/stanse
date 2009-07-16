package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.builders.CallGraphBuilder;
import cz.muni.stanse.codestructures.builders.NodeToCFGdictionaryBuilder;
import cz.muni.stanse.codestructures.builders.StartFunctionsSetBuilder;

import java.util.Collections;
import java.util.Collection;
import java.util.Set;
import java.util.Map;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public final class LazyInternalProgramStructuresCollectionImpl
                            implements LazyInternalProgramStructuresCollection {

    public LazyInternalProgramStructuresCollectionImpl(
                                      final Collection<Unit> units,
                                      final Map<CFG,Unit> cfgToUnitDictionary) {
        this.units = units;
        this.cfgToUnitDictionary =
                Collections.unmodifiableMap(cfgToUnitDictionary);

        startFunctions = null;
        callGraph = null;
        navigator = null;
        argumentPassingManager = null;
        returnValuePassingManager = null;
        nodeToCFGdictionary = null;
        elementToCFGdictionary = null;
    }

    @Override
    public Collection<Unit> getUnits() {
        return units;
    }

    @Override
    public Collection<CFG> getCFGs() {
        return Collections.unmodifiableSet(getCFGtoUnitDictionary().keySet());
    }

    @Override
    public Set<CFG> getStartFunctions() {
        if (startFunctions == null)
            setStartFunctions();
        return startFunctions;
    }

    @Override
    public DefaultDirectedGraph<CFG, DefaultEdge> getCallGraph() {
        if (callGraph == null)
            setCallGraph();
        return callGraph;
    }

    @Override
    public ArgumentPassingManager getArgumentPassingManager() {
        if (argumentPassingManager == null)
            setArgumentPassingManager();
        return argumentPassingManager;
    }

    @Override
    public ReturnValuePassingManager getReturnValuePassingManager() {
        if (returnValuePassingManager == null)
            setReturnValuePassingManager();
        return returnValuePassingManager;
    }

    @Override
    public CFGsNavigator getNavigator() {
        if (navigator == null)
            setNavigator();
        return navigator;
    }

    @Override
    public Map<CFGNode,CFG> getNodeToCFGdictionary() {
        if (nodeToCFGdictionary == null)
            setNodeToCFGdictionary();
        return nodeToCFGdictionary;
    }

    @Override
    public Map<CFG,Unit> getCFGtoUnitDictionary() {
        return cfgToUnitDictionary;
    }

    @Override
    public ElementCFGdictionary getElementToCFGdictionary() {
        if (elementToCFGdictionary == null)
            setElementToCFGdictionary();
        return elementToCFGdictionary;
    }

//    @Override
//    public synchronized void clearStartFunctions()
//    { startFunctions = null; }
//    @Override
//    public synchronized void clearCallGraph()
//    { callGraph = null; }
//    @Override
//    public synchronized void clearArgumentPassingManager()
//    { argumentPassingManager = null; }
//    @Override
//    public synchronized void clearNavigator()
//    { navigator = null; }
//    @Override
//    public synchronized void clearNodeToCFGdictionary()
//    { nodeToCFGdictionary = null; }
//    @Override
//    public synchronized void clearCFGtoUnitDictionary()
//    { cfgToUnitDictionary = null; }
//    @Override
//    public synchronized void clearElementToCFGdictionary()
//    { elementToCFGdictionary = null; }
//
//    @Override
//    public synchronized void clear() {
//        clearStartFunctions();
//        clearCallGraph();
//        clearArgumentPassingManager();
//        clearNavigator();
//        clearNodeToCFGdictionary();
//        clearCFGtoUnitDictionary();
//        clearElementToCFGdictionary();
//    }

    // private section

    private synchronized void setStartFunctions() {
        if (startFunctions == null)
            startFunctions = Collections.unmodifiableSet(
                                  StartFunctionsSetBuilder.run(getCallGraph()));
    }

    private synchronized void setCallGraph() {
        if (callGraph == null)
            callGraph = CallGraphBuilder.run(getCFGs(),getNavigator(),
                                             getNodeToCFGdictionary());
    }

    private synchronized void setArgumentPassingManager() {
        if (argumentPassingManager == null)
            argumentPassingManager = new ArgumentPassingManager(getNavigator(),
                                                      getNodeToCFGdictionary());
    }

    private synchronized void setReturnValuePassingManager() {
        if (returnValuePassingManager == null)
            returnValuePassingManager =
                new ReturnValuePassingManager(getNavigator());
    }

    private synchronized void setNavigator() {
        if (navigator == null)
            navigator = new InterproceduralCFGsNavigator(getCFGs(),
                                                   getElementToCFGdictionary());
    }

    private synchronized void setNodeToCFGdictionary() {
        if (nodeToCFGdictionary == null)
            nodeToCFGdictionary = Collections.unmodifiableMap(
                                     NodeToCFGdictionaryBuilder.run(getCFGs()));
    }

    private synchronized void setElementToCFGdictionary() {
        if (elementToCFGdictionary == null)
            elementToCFGdictionary = new ElementCFGdictionary(getCFGs());
    }

    private final Collection<Unit> units;
    private final Map<CFG,Unit> cfgToUnitDictionary;

    private Set<CFG> startFunctions;
    private DefaultDirectedGraph<CFG,DefaultEdge> callGraph;
    private CFGsNavigator navigator;
    private ArgumentPassingManager argumentPassingManager;
    private ReturnValuePassingManager returnValuePassingManager;
    private Map<CFGNode,CFG> nodeToCFGdictionary;
    private ElementCFGdictionary elementToCFGdictionary;
}
