/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.codestructures.traversal.CFGPathVisitor;
import cz.muni.stanse.utils.Pair;

import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.Stack;

final class ErrorTracesListCreator extends CFGPathVisitor {

    // public section

    @Override
    public boolean visit(final List<CFGNode> path,
                         final Stack<CFGNode> cfgContext) {
        if (getErrorTracesList().size() >= 20 || path.size() > 100
                || isLimitOfRejectedMeasureExceeded())
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
                          location.getDeliveredAutomataStates(), cfgContext))) {
            incrementNumRejectedMeasure(path.size());
            return false;
        }

        if (!getRule().checkForError(
                AutomatonStateCFGcontextAlgo.filterStatesByContext(
                           location.getProcessedAutomataStates(),cfgContext))) {
            final int importance = getTraceImportance(path,cfgContext);
            if (importance !=
                    FalsePositivesDetector.getFalsePositiveImportance()) {
                getErrorTracesList().add(buildErrorTrace(
                        getRule().getErrorBeginMessage(),
                        getRule().getErrorPropagMessage(),
                        getRule().getErrorEndMessage(),
                        path,cfgContext));
                updateTotalImportance(importance);
                resetNumRejectedMeasure();
            }
            else
                incrementNumRejectedMeasure(path.size());
            return false;
        }

        if (cfgContext.isEmpty() &&
                getInternals().getNavigator().isStartNode(node)) {
            final int importance = getTraceImportance(path,cfgContext);
            if (importance !=
                    FalsePositivesDetector.getFalsePositiveImportance()) {
                getErrorTracesList().add(buildErrorTrace(
                        getRule().getErrorEntryMessage(),
                        getRule().getErrorPropagMessage(),
                        getRule().getErrorEndMessage(),
                        path,cfgContext));
                updateTotalImportance(importance);
                resetNumRejectedMeasure();
            }
            else
                incrementNumRejectedMeasure(path.size());
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
                    final LazyInternalStructures internals,
                    final java.util.List<FalsePositivesDetector> detectors,
                    final AutomatonCheckerLogger monitor) {
        super();
        this.rule = rule;
        this.transferor = transferor;
        this.nodeLocationDictionary = nodeLocationDictionary;
        this.startNode = startNode;
        this.internals = internals;
        this.detectors = detectors;
        this.monitor = monitor;
    }

    Vector<CheckerErrorTrace> getErrorTracesList() {
        return errorTracesList;
    }

    int getTotalImportance() {
        return totalImportance;
    }

    String getFailMessage() {
        return failMsg;
    }

    // private section

    private int getTraceImportance(final List<CFGNode> path,
                                   final Stack<CFGNode> cfgContext) {
        int importance = FalsePositivesDetector.getBugDefaultImportance();
        for (final FalsePositivesDetector detector : detectors) {
            final int currImportance =
                    detector.getTraceImportance(path,cfgContext,getRule());
            if (currImportance ==
                    FalsePositivesDetector.getFalsePositiveImportance())
                return currImportance;
            importance = importance > currImportance ?
                            importance : currImportance;
        }
        return importance;
    }

    private CheckerErrorTrace buildErrorTrace(final String beginMsg,
                                       final String innerMsg,
                                       final String endMsg,
                                       final List<CFGNode> path,
                                       final Stack<CFGNode> context) {
        final Vector<CheckerErrorTraceLocation> trace =
                                        new Vector<CheckerErrorTraceLocation>();
        for (final CFGNode node : context)
            trace.add(new CheckerErrorTraceLocation(getNodeUnitName(node),
                          node.getLine(),node.getColumn(),
                          "<context>When called from here."));
        trace.add(new CheckerErrorTraceLocation(getNodeUnitName(path.get(0)),
                                  path.get(0).getLine(),path.get(0).getColumn(),
                                  beginMsg));
        if (path.size() > 1)
            for (CFGNode item : path.subList(1,path.size() - 1))
                trace.add(new CheckerErrorTraceLocation(getNodeUnitName(item),
                                                item.getLine(),item.getColumn(),
                                                innerMsg));
        trace.add(new CheckerErrorTraceLocation(
                        getNodeUnitName(path.get(path.size() - 1)),
                        path.get(path.size() - 1).getLine(),
                        path.get(path.size() - 1).getColumn(),
                        endMsg + getRule().getAutomatonID().getVarsAssignment()
                                          .toString()));
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

    private LazyInternalStructures getInternals() {
        return internals;
    }

    private String getNodeUnitName(final CFGNode node) {
        return Stanse.getUnitManager().getUnitName(getInternals().
                getNodeToCFGdictionary().get(node));
    }

    private void updateTotalImportance(final int traceImportance) {
        assert(traceImportance !=
                    FalsePositivesDetector.getBugDefaultImportance());
        totalImportance = totalImportance > traceImportance ?
                                totalImportance : traceImportance ;
    }

    private boolean isLimitOfRejectedMeasureExceeded() {
        return numRejectedMeasure > 1000;
    }

    private void incrementNumRejectedMeasure(final int pathLen) {
        numRejectedMeasure += pathLen;
        if (isLimitOfRejectedMeasureExceeded()) {
            setFailMessage("*** FAILED: budget limit for error traces " +
                           "search exceeded. Search was early terminated!");
            getMonitor().note(getFailMessage());
        }
    }

    private void resetNumRejectedMeasure() {
        numRejectedMeasure = 0;
    }

    public AutomatonCheckerLogger getMonitor() {
        return monitor;
    }

    private void setFailMessage(final String msg) {
        failMsg = msg;
    }

	private ErrorRule rule;
	private final AutomatonStateTransferManager transferor;
	private final Map<CFGNode, Pair<PatternLocation, PatternLocation>>
		nodeLocationDictionary;
	private final CFGNode startNode;
	private final LazyInternalStructures internals;
	private final Vector<CheckerErrorTrace> errorTracesList =
		new Vector<CheckerErrorTrace>();
	private final java.util.List<FalsePositivesDetector> detectors;
	private final AutomatonCheckerLogger monitor;
	private int totalImportance =
		FalsePositivesDetector.getBugDefaultImportance();
	private String failMsg = null;
	private int numRejectedMeasure = 0;
}
