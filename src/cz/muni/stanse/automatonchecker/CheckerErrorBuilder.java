/**
 * @file CheckerErrorBuilder.java
 * @brief Implements final class CheckerErrorBuilder which is responsible to
 *        compute all checker errors which can be translated from automata
 *        states at PaternLocations. 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.codestructures.traversal.CFGTraversal;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingSuccess;
import cz.muni.stanse.checker.CheckingFailed;
import cz.muni.stanse.utils.Make;
import cz.muni.stanse.utils.Pair;

import java.util.Map;
import java.util.List;
import java.util.Vector;

/**
 * @brief Provides static method buildErrorList which compute the checker-errors
 *        from automata states at PattenLocations assigned to matching source
 *        code locacions by use of error transition rules defined in XML
 *        automata definition file.
 *
 * Class is not intended to be instantiated.
 *
 * @see cz.muni.stanse.checker.CheckerError
 * @see cz.muni.stanse.automatonchecker.AutomatonChecker
 */
final class CheckerErrorBuilder {

    // package-private section

    /**
     * @brief Computes a list of all checker-errors, which can be recognized by
     *        error transition rules (defined in XML automata definition file)
     *        from automata states at PattenLocations assigned to matching
     *        source code locacions.
     *
     * @param edgeLocationDictionary Dictionary, which provides mapping from
     *                                   CFGNodes (i.e. reference to souce code
     *                                   locations) to related PatternLocations.
     * @return List of checker-errors recognized from automata states at
     *         PatternLocations.
     */
    static CheckingResult
    buildErrorList(final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       edgeLocationDictionary,
                   final LazyInternalStructures internals,
                   final java.util.List<FalsePositivesDetector> detectors,
                   final CheckerErrorReceiver errReciver,
                   final AutomatonCheckerLogger monitor,
                   final String automatonName) {
        CheckingResult result = new CheckingSuccess();
        int numErrors = 0;
        final long startTracingTime = System.currentTimeMillis();
        for (Pair<PatternLocation,PatternLocation> locationsPair :
                                               edgeLocationDictionary.values()){
            final long tracingTime =
                System.currentTimeMillis() - startTracingTime;
            if (tracingTime > 60000)
                return result;
            if (locationsPair.getFirst() != null) {
                final Pair<Integer,CheckingResult> locBuildResult =
                    buildErrorsInLocation(locationsPair.getFirst(),
                                          edgeLocationDictionary,internals,
                                          detectors,errReciver,monitor,
                                          automatonName);
                numErrors += locBuildResult.getFirst();
                if (result instanceof CheckingSuccess)
                    result = locBuildResult.getSecond();
            }
        }
        if (numErrors > 0)
            monitor.note("*** " + numErrors + " error(s) found");

        return result;
    }

    // private section

    private static Pair<Integer,CheckingResult>
    buildErrorsInLocation(final PatternLocation location,
            final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       edgeLocationDictionary,
            final LazyInternalStructures internals,
            final java.util.List<FalsePositivesDetector> detectors,
            final CheckerErrorReceiver errReciver,
            final AutomatonCheckerLogger monitor,
            final String automatonName) {
        final CallSiteDetector callDetector =
            new CallSiteDetector(internals.getNavigator(),
                                 edgeLocationDictionary);
        final AutomatonStateTransferManager transferor =
            new AutomatonStateTransferManager(
                            internals.getArgumentPassingManager(),callDetector);
        final CallSiteCFGNavigator callNavigator =
            new CallSiteCFGNavigator(internals.getNavigator(),callDetector);

        if (location.getProcessedAutomataStates().size() > 100 ||
                location.getDeliveredAutomataStates().size() > 100)
            location.reduceStateSets();

        CheckingResult result = new CheckingSuccess();
        int numErrors = 0;
        final long startLocationTracingTime = System.currentTimeMillis();
        for (final ErrorRule rule : location.getErrorRules())
            for (final java.util.Stack<CFGNode> cfgContext :
                                AutomatonStateCFGcontextAlgo.getContexts(
                                        location.getProcessedAutomataStates())){
                final long locationTracingTime =
                    System.currentTimeMillis() - startLocationTracingTime;
                if (locationTracingTime > 10000)
                    return Pair.make(numErrors,result);
                if (rule.checkForError(AutomatonStateCFGcontextAlgo.
                            filterStatesByContext(
                                          location.getProcessedAutomataStates(),
                                          cfgContext))) {
                    final ErrorTracesListCreator creator =
                        new ErrorTracesListCreator(rule,transferor,
                                            edgeLocationDictionary,
                                            location.getCFGreferenceNode(),
                                            internals,detectors,monitor);
                    final List<CheckerErrorTrace> traces =
                        CFGTraversal.traverseCFGPathsBackwardInterprocedural(
                            callNavigator,location.getCFGreferenceNode(),
                            creator,cfgContext).getErrorTracesList();

                    if (result instanceof CheckingSuccess &&
                        creator.getFailMessage() != null)
                        result = new CheckingFailed(creator.getFailMessage(),
                                getLocationUnitName(location,internals));

                    // Next condition eliminates cyclic dependances of two
                    // error locations (diferent). These locations have same
                    // error rule and theirs methods checkForError() returns
                    // true (so  they are both error locations). But their
                    // cyclic dependancy disables to find starting nodes of
                    // theirs error traces -> both error traces returned will
                    // be empty.
                    if (traces.isEmpty())
                        continue;

		    int min_size = Integer.MAX_VALUE;
		    CheckerErrorTrace min_trace = null;
		    for (final CheckerErrorTrace trace : traces) {
			    if (trace.getLocations().size() >= min_size)
				    continue;
			    min_size = trace.getLocations().size();
			    min_trace = trace;
		    }

                    final String shortDesc = rule.getErrorDescription();
                    final String fullDesc
                            = "{" + automatonName + "} in function '"
                            + internals.getNodeToCFGdictionary()
                                       .get(location.getCFGreferenceNode())
                                       .getFunctionName()
                            + "' "
                            + shortDesc
                            + " [traces: " + traces.size() + "]";
                    errReciver.receive(
                       new CheckerError(shortDesc,fullDesc,
                                        rule.getErrorLevel() +
                                            creator.getTotalImportance(),
                                        automatonName, Make.linkedList(min_trace)));
                    ++numErrors;
                    monitor.note("*** error found: " + shortDesc);
                }
            }
        return Pair.make(numErrors,result);
    }

    @Deprecated
    private static CheckerErrorTrace
    builOneLocationTrace(final PatternLocation location, final ErrorRule rule,
                         final LazyInternalStructures internals) {
        final Vector<CheckerErrorTraceLocation> locs =
            new Vector<CheckerErrorTraceLocation>();
        final String unitName = getLocationUnitName(location,internals);
        final int line = location.getCFGreferenceNode().getLine();
        final int column = location.getCFGreferenceNode().getColumn();
        final String desc =rule.getErrorDescription() +
                           rule.getAutomatonID().getVarsAssignment().toString();
        locs.add(new CheckerErrorTraceLocation(unitName,line,column,desc));
        return new CheckerErrorTrace(locs,desc);
    }

    private static String getNodeUnitName(final CFGNode node,
                                       final LazyInternalStructures internals) {
        return Stanse.getUnitManager().getUnitName(
                    internals.getNodeToCFGdictionary().get(node));
    }

    private static String getLocationUnitName(final PatternLocation location,
                                       final LazyInternalStructures internals) {
        return getNodeUnitName(location.getCFGreferenceNode(),internals);
    }


    private CheckerErrorBuilder() {
    }
}
