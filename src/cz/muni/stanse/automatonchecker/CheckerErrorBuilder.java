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

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.utils.LazyInternalProgramStructuresCollection;
import cz.muni.stanse.utils.CFGTraversal;
import cz.muni.stanse.utils.Pair;

import java.util.Map;
import java.util.List;

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
    static void
    buildErrorList(final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       edgeLocationDictionary,
                   final LazyInternalProgramStructuresCollection internals,
                   final CheckerErrorReceiver errReciver,
                   final AutomatonCheckerLogger monitor) {
        int numErrors = 0;
        for (Pair<PatternLocation,PatternLocation> locationsPair :
                                                edgeLocationDictionary.values())
            if (locationsPair.getFirst() != null)
                numErrors += buildErrorsInLocation(locationsPair.getFirst(),
                                      edgeLocationDictionary,internals,
                                      errReciver,monitor);
        if (numErrors > 0)
            monitor.note("*** " + numErrors + " error(s) found");
    }

    // private section

    private static int buildErrorsInLocation(final PatternLocation location,
            final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       edgeLocationDictionary,
            final LazyInternalProgramStructuresCollection internals,
            final CheckerErrorReceiver errReciver,
            final AutomatonCheckerLogger monitor) {
        final CallSiteDetector callDetector =
            new CallSiteDetector(internals.getNavigator(),
                                 edgeLocationDictionary);
        final AutomatonStateTransferManager transferor =
            new AutomatonStateTransferManager(
                            internals.getArgumentPassingManager(),callDetector);
        final CallSiteCFGNavigator callNavigator =
            new CallSiteCFGNavigator(internals.getNavigator(),callDetector);

        int numErrors = 0;
        for (final ErrorRule rule : location.getErrorRules())
            for (final java.util.Stack<CFGNode> cfgContext :
                                AutomatonStateCFGcontextAlgo.getContexts(
                                        location.getProcessedAutomataStates()))
                if (rule.checkForError(AutomatonStateCFGcontextAlgo.
                            filterStatesByContext(
                                          location.getProcessedAutomataStates(),
                                          cfgContext))) {
                    final List<CheckerErrorTrace> traces =
                        CFGTraversal.traverseCFGPathsBackwardInterprocedural(
                            callNavigator,location.getCFGreferenceNode(),
                            (new ErrorTracesListCreator(rule,transferor,
                                            edgeLocationDictionary,
                                            location.getCFGreferenceNode(),
                                            internals)),
                            cfgContext).getErrorTracesList();

                    // Next condition eliminates cyclic dependances of two
                    // error locations (diferent). These locations have same
                    // error rule and theirs methods checkForError() returns
                    // true (so  they are both error locations). But their
                    // cyclic dependancy disables to find starting nodes of
                    // theirs error traces -> both error traces returned will
                    // be empty.
                    if (traces.isEmpty())
                        continue;

                    final String shortDesc = rule.getErrorDescription();
                    final String fullDesc
                            = "{"
                            + AutomatonCheckerCreator.getNameForCheckerFactory()
                            + "} in function '"
                            + internals.getNodeToCFGdictionary()
                                       .get(location.getCFGreferenceNode())
                                       .getFunctionName()
                            + "' "
                            + shortDesc
                            + " [traces: " + traces.size() + "]";
                    errReciver.receive(
                       new CheckerError(shortDesc,fullDesc,rule.getErrorLevel(),
                                        AutomatonCheckerCreator.
                                            getNameForCheckerFactory(),
                                        traces));
                    ++numErrors;
                    monitor.note("*** error found: " + shortDesc);
                }

        return numErrors;
    }

    private CheckerErrorBuilder() {
    }
}
