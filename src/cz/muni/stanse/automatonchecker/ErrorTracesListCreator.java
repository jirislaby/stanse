/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.LazyInternalProgramStructuresCollection;
import cz.muni.stanse.utils.Pair;

import java.util.List;
import java.util.Vector;
import java.util.Map;

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
                    path,cfgContext));
            return false;
        }

        if (cfgContext.isEmpty() &&
                getInternals().getNavigator().isStartNode(node)) {
            getErrorTracesList().add(buildErrorTrace(
                    getRule().getErrorEntryMessage(),
                    getRule().getErrorPropagMessage(),
                    getRule().getErrorEndMessage(),
                    path,cfgContext));
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
                    final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary,
                    final CFGNode startNode,
                    final LazyInternalProgramStructuresCollection internals) {
        super();
        this.rule = rule;
        this.transferor = transferor;
        this.nodeLocationDictionary = nodeLocationDictionary;
        this.startNode = startNode;
        this.internals = internals;
        errorTracesList = new Vector<CheckerErrorTrace>();
    }

    Vector<CheckerErrorTrace> getErrorTracesList() {
        return errorTracesList;
    }

    // private section

    private CheckerErrorTrace buildErrorTrace(final String beginMsg,
                                       final String innerMsg,
                                       final String endMsg,
                                       final List<CFGNode> path,
                                       final java.util.Stack<CFGNode> context) {
        final Vector<CheckerErrorTraceLocation> trace =
                                        new Vector<CheckerErrorTraceLocation>();
        for (final CFGNode node : context)
            trace.add(new CheckerErrorTraceLocation(getNodeUnitName(node),
                          getNodeLine(node),"<context>When called from here."));
        trace.add(new CheckerErrorTraceLocation(getNodeUnitName(path.get(0)),
                                            getNodeLine(path.get(0)),beginMsg));
        if (path.size() > 1)
            for (CFGNode item : path.subList(1,path.size() - 1))
                trace.add(new CheckerErrorTraceLocation(getNodeUnitName(item),
                                                   getNodeLine(item),innerMsg));
        trace.add(new CheckerErrorTraceLocation(
                        getNodeUnitName(path.get(path.size() - 1)),
                        getNodeLine(path.get(path.size() - 1)),endMsg));
        return new CheckerErrorTrace(trace,
                       "error-trace [" + (getErrorTracesList().size()+1) + "]");
    }

    private ErrorRule getRule() {
        return rule;
    }

    private void updateRule(final SimpleAutomatonID id) {
        rule = new ErrorRule(getRule(),id);
    }

    private Map<CFGNode,Pair<PatternLocation,PatternLocation>>
    getNodeLocationDictionary() {
        return nodeLocationDictionary;
    }

    private CFGNode getStartNode() {
        return startNode;
    }

    private AutomatonStateTransferManager getTransferor() {
        return transferor;
    }

    private LazyInternalProgramStructuresCollection getInternals() {
        return internals;
    }

    private String getNodeUnitName(final CFGNode node) {
        return getInternals().getCFGtoUnitDictionary()
                             .get(getInternals().getNodeToCFGdictionary()
                                                .get(node))
                             .getName();
    }

    private int getNodeLine(final CFGNode node) {
        return new Integer(
                       // TODO: following two lines of code can be removed, when
                       //       there are no CFGNodes without XML element and
                       //       each XML element has 'bl' attribute .
                       (node.getElement() == null ||
                        node.getElement().attribute("bl") == null) ? "1" :

                        node.getElement().attribute("bl").getValue());
    }

    private ErrorRule rule;
    private final AutomatonStateTransferManager transferor;
    private final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary;
    private final CFGNode startNode;
    private final LazyInternalProgramStructuresCollection internals;
    private final Vector<CheckerErrorTrace> errorTracesList;
}
