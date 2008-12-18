package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.utils.CFGTraversal;

import java.util.HashMap;
import java.util.LinkedList;

final class CheckerErrorBuilder {

    // package-private section

    static LinkedList<CheckerError>
    buildErrorList(final HashMap<cz.muni.stanse.codestructures.CFGNode,PatternLocation>
                                                       edgeLocationDictionary) {
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
                final String shortDesc = getMsgPrefix(location.getCFG()) +
                                         rule.getErrorDescription() +
                                         getMsgPostfix(traces.size());
                // TODO: full description should contains more info then short
                //       one ... :-)
                final String fullDesc = shortDesc;
                errorsList.add(new CheckerError(shortDesc,fullDesc,
                                                rule.getErrorLevel(),traces));
            }

        return errorsList;
    }

    private static String getMsgPrefix(
            final cz.muni.stanse.codestructures.CFG cfg) {
        return "In function: '" + cfg.getFunctionName() + "()' : ";
    }

    private static String getMsgPostfix(final int numTraces) {
        return " [num traces: " + numTraces + "]";
    }

    private CheckerErrorBuilder() {
    }
}
