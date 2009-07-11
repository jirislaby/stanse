package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collection;
import java.util.Set;
import java.util.Map;

public interface LazyInternalProgramStructuresCollection {
    public Collection<Unit> getUnits();
    public Collection<CFG> getCFGs();

    public Set<CFG> getStartFunctions();
    public DefaultDirectedGraph<CFG, DefaultEdge> getCallGraph();
    public ArgumentPassingManager getArgumentPassingManager();
    public ReturnValuePassingManager getReturnValuePassingManager();
    public CFGsNavigator getNavigator();
    public Map<CFGNode,CFG> getNodeToCFGdictionary();
    public Map<CFG,Unit> getCFGtoUnitDictionary();
    public ElementCFGdictionary getElementToCFGdictionary();

//    public void clearStartFunctions();
//    public void clearCallGraph();
//    public void clearArgumentPassingManager();
//    public void clearNavigator();
//    public void clearNodeToCFGdictionary();
//    public void clearCFGtoUnitDictionary();
//    public void clearElementToCFGdictionary();
//    public void clear();
}
