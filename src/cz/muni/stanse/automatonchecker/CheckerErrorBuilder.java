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

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.utils.CFGTraversal;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.CFGsNavigator;
import cz.muni.stanse.utils.ArgumentPassingManager;

import java.util.HashMap;
import java.util.LinkedList;

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
    static LinkedList<CheckerError>
    buildErrorList(final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       edgeLocationDictionary,
                   final ArgumentPassingManager passingManager,
                   final CFGsNavigator navigator,
                   final HashMap<CFGNode,CFG> nodeCFGdictionary) {
        final LinkedList<CheckerError> errorsList =
                new LinkedList<CheckerError>();

        for (Pair<PatternLocation,PatternLocation> locationsPair :
                                                edgeLocationDictionary.values())
            if (locationsPair.getFirst() != null)
                errorsList.addAll(buildErrorsInLocation(
                                                 locationsPair.getFirst(),
                                                 edgeLocationDictionary,
                                                 passingManager,navigator,
                                                 nodeCFGdictionary));

        return errorsList;
    }

    // private section

    private static LinkedList<CheckerError> buildErrorsInLocation(
            final PatternLocation location,
            final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       edgeLocationDictionary,
            final ArgumentPassingManager passingManager,
            final CFGsNavigator navigator,
            final HashMap<CFGNode,CFG> nodeCFGdictionary) {
        final LinkedList<CheckerError> errorsList =
            new LinkedList<CheckerError>();
        final CallSiteDetector callDetector =
            new CallSiteDetector(navigator,edgeLocationDictionary);
        final AutomatonStateTransferManager transferor =
            new AutomatonStateTransferManager(passingManager,callDetector);
        final CallSiteCFGNavigator callNavigator =
            new CallSiteCFGNavigator(navigator,callDetector);

        for (final ErrorRule rule : location.getErrorRules())
            for (final java.util.Stack<CFGNode> cfgContext :
                                AutomatonStateCFGcontextAlgo.getContexts(
                                        location.getProcessedAutomataStates()))
                if (rule.checkForError(AutomatonStateCFGcontextAlgo.
                            filterStatesByContext(
                                          location.getProcessedAutomataStates(),
                                          cfgContext))) {
                    final LinkedList<ErrorTrace> traces =
                        CFGTraversal.traverseCFGPathsBackwardInterprocedural(
                            callNavigator,location.getCFGreferenceNode(),
                            (new ErrorTracesListCreator(rule,transferor,
                                            edgeLocationDictionary,
                                            location.getCFGreferenceNode(),
                                            nodeCFGdictionary)),
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
                    final String fullDesc = "in function '" +
                        nodeCFGdictionary.get(location.getCFGreferenceNode())
                                         .getFunctionName() +
                        "' " + shortDesc + " [traces: " + traces.size() + "]";
                    errorsList.add(new CheckerError(shortDesc,fullDesc,
                                                  rule.getErrorLevel(),traces));
                }

        return errorsList;
    }

    private CheckerErrorBuilder() {
    }
}
