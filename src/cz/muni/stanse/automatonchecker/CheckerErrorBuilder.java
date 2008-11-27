package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.utils.CFGTraversal;

import java.util.HashMap;
import java.util.LinkedList;

final class CheckerErrorBuilder {

    // package-private section

    static LinkedList<CheckerError>
    buildErrorList(final HashMap<cz.muni.stanse.xml2cfg.CFGEdge,PatternLocation>
                                      edgeLocationDictionary) throws Exception {
        final LinkedList<CheckerError> errorsList = new LinkedList<CheckerError>();
        for (PatternLocation location : edgeLocationDictionary.values())
            errorsList.addAll(buildErrorsInLocation(location,
                                                    edgeLocationDictionary));

        return errorsList;
    }

    // private section

    private static LinkedList<CheckerError> buildErrorsInLocation(
            final PatternLocation location,
            final HashMap<cz.muni.stanse.xml2cfg.CFGEdge,PatternLocation>
                                      edgeLocationDictionary) throws Exception {
        final LinkedList<CheckerError> errorsList =
            new LinkedList<CheckerError>();

        for (ErrorRule rule : location.getErrorRules())
            if (rule.checkForError(location.getProcessedAutomataStates()))
                errorsList.add(
                    new CheckerError(
                            getMsgPrefix(location.getCFG()) +
                                rule.getErrorDescription(),
                            getMsgPrefix(location.getCFG()) +
                                rule.getErrorDescription(),
                            rule.getErrorLevel(),
                            CFGTraversal.traverseCFGToDepthBackward(
                                location.getCFG(),
                                location.getCFGreferenceEdge().getFrom(),
                                (new ErrorTracesListCreator(
                                        rule,edgeLocationDictionary,
                                        location.getCFGreferenceEdge(),
                                        location.getCFG()))
                            ).GetTraceErrorsList())
                    );

        return errorsList;
    }

    private static String getMsgPrefix(
            final cz.muni.stanse.xml2cfg.ControlFlowGraph cfg) {
        return "In function: '" + cfg.getFunctionName() + "()' : ";
    }

    private CheckerErrorBuilder() {
    }
}
