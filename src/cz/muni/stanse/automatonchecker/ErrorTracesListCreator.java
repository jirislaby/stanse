/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

final class ErrorTracesListCreator extends cz.muni.stanse.utils.CFGPathVisitor {

    // public section

    @Override
    public boolean visit(final List<CFGNode> path,
                         final java.util.Stack<CFGNode> cfgContext) {
        if (getErrorTracesList().size() >= 20)
            return false;

        final CFGNode node = path.iterator().next();

        if (getStartNode().equals(node) && path.size() == 1)
            return true;

        final Pair<PatternLocation,PatternLocation> locationPair =
            getNodeLocationDictionary().get(node);

        if (locationPair == null)
            return true;

        final PatternLocation location = locationPair.getSecond();

        if (!getRule().checkForError(
                AutomatonStateCFGcontextAlgo.filterStatesByContext(
                            location.getDeliveredAutomataStates(), cfgContext)))
            return false;

        if (!getRule().checkForError(
                AutomatonStateCFGcontextAlgo.filterStatesByContext(
                           location.getProcessedAutomataStates(),cfgContext))) {
            getErrorTracesList().add(buildErrorTrace(
                    getRule().getErrorBeginMessage(),
                    getRule().getErrorPropagMessage(),
                    getRule().getErrorEndMessage(),
                    path));
            return false;
        }

        if (cfgContext.isEmpty() && location.isIsStartLocation()) {
            getErrorTracesList().add(buildErrorTrace(
                    getRule().getErrorEntryMessage(),
                    getRule().getErrorPropagMessage(),
                    getRule().getErrorEndMessage(),
                    path));
            return false;
        }

        return true;
    }

    @Override
    public boolean onCFGchange(CFGNode from, CFGNode to) {
        final SimpleAutomatonID transformedID =
            getTransferor().transfer(from,getRule().getAutomatonID(),to);
        if (transformedID == null)
            return false;
        updateRule(transformedID);
        return true;
    }

    // package-private section

    ErrorTracesListCreator(final ErrorRule rule,
                    final AutomatonStateTransferManager transferor,
                    final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary,
                    final CFGNode startNode,
                    final HashMap<CFGNode,CFG> nodeCFGdictionary) {
        super();
        this.rule = rule;
        this.transferor = transferor;
        this.nodeLocationDictionary = nodeLocationDictionary;
        this.startNode = startNode;
        this.nodeCFGdictionary = nodeCFGdictionary;
        errorTracesList = new LinkedList<ErrorTrace>();
    }

    LinkedList<ErrorTrace> getErrorTracesList() {
        return errorTracesList;
    }

    // private section

    private ErrorTrace buildErrorTrace(final String beginMsg,
                                       final String innerMsg,
                                       final String endMsg,
                                       final List<CFGNode> path) {
        final List< Triple<CFGNode,String,CFG> > trace =
                                new LinkedList< Triple<CFGNode,String,CFG> >();
        trace.add(new Triple<CFGNode,String,CFG>(path.get(0),beginMsg,
                                      getNodeCFGdictionary().get(path.get(0))));
        if (path.size() > 1)
            for (CFGNode item : path.subList(1,path.size() - 1))
                trace.add(new Triple<CFGNode,String,CFG>(item,innerMsg,
                                             getNodeCFGdictionary().get(item)));
        trace.add(new Triple<CFGNode,String,CFG>(path.get(path.size() - 1),
                 endMsg,getNodeCFGdictionary().get(path.get(path.size() - 1))));
        return new ErrorTrace(
                        "error-trace [" + (getErrorTracesList().size()+1) + "]",
                        // TODO: full description should contain little more
                        //       info then short one. :-)
                        "error-trace [" + (getErrorTracesList().size()+1) + "]",
                        trace);
    }

    private ErrorRule getRule() {
        return rule;
    }

    private void updateRule(final SimpleAutomatonID id) {
        rule = new ErrorRule(getRule(),id);
    }

    private HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
    getNodeLocationDictionary() {
        return nodeLocationDictionary;
    }

    private CFGNode getStartNode() {
        return startNode;
    }

    private AutomatonStateTransferManager getTransferor() {
        return transferor;
    }

    private HashMap<CFGNode,CFG> getNodeCFGdictionary() {
        return nodeCFGdictionary;
    }

    private ErrorRule rule;
    private final AutomatonStateTransferManager transferor;
    private final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary;
    private final CFGNode startNode;
    private final HashMap<CFGNode,CFG> nodeCFGdictionary;
    private final LinkedList<ErrorTrace> errorTracesList;
}
