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

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.utils.CFGTraversal;

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
    buildErrorList(final HashMap<cz.muni.stanse.codestructures.CFGNode,
                                 PatternLocation> edgeLocationDictionary) {
        final LinkedList<CheckerError> errorsList =
                                                 new LinkedList<CheckerError>();
        for (PatternLocation location : edgeLocationDictionary.values())
            errorsList.addAll(buildErrorsInLocation(location,
                                                    edgeLocationDictionary));

        return errorsList;
    }

    // private section

    private static LinkedList<CheckerError> buildErrorsInLocation(
            final PatternLocation location,
            final HashMap<cz.muni.stanse.codestructures.CFGNode,PatternLocation>
                                                       edgeLocationDictionary) {
        final LinkedList<CheckerError> errorsList =
            new LinkedList<CheckerError>();

        for (ErrorRule rule : location.getErrorRules())
            if (rule.checkForError(location.getProcessedAutomataStates())) {
                final LinkedList<ErrorTrace> traces =
                    CFGTraversal.traverseCFGPathsBackward(
                        location.getCFG(),location.getCFGreferenceNode(),
                        (new ErrorTracesListCreator(rule,edgeLocationDictionary,
                                      location.getCFGreferenceNode(),
                                      location.getCFG()))).getErrorTracesList();
                final String shortDesc = rule.getErrorDescription();
                final String fullDesc = "in function '" +
                                        location.getCFG().getFunctionName() +
                                        "' " + shortDesc + " [traces: " +
                                        traces.size() + "]";
                errorsList.add(new CheckerError(shortDesc,fullDesc,
                                                rule.getErrorLevel(),traces));
            }

        return errorsList;
    }

    private CheckerErrorBuilder() {
    }
}
