package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.utils.CFGTraversal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Iterator;

final class CheckerErrorBuilder {

    // package-private section

    static LinkedList<CheckerError>
    buildErrorList(final Set<cz.muni.stanse.xml2cfg.ControlFlowGraph> CFGs,
                   final ErrorRule exitRule,
                   final HashMap<cz.muni.stanse.xml2cfg.CFGEdge,PatternLocation>
                                      edgeLocationDictionary) throws Exception {
        final LinkedList<CheckerError> errorsList = new LinkedList<CheckerError>();

        for (PatternLocation location : edgeLocationDictionary.values())
            errorsList.addAll(buildErrorsInLocation(location,
                                                    edgeLocationDictionary));

        for (cz.muni.stanse.xml2cfg.ControlFlowGraph cfg : CFGs) {
            final CheckerError exitError =
                buildErrorInExitLocationOfCFG(exitRule,cfg,edgeLocationDictionary); 
            if (exitError != null)
                errorsList.add(exitError);
        }

        {
        // TODO: toto je potreba dat pryc, jakmile se osetri propagace chyb
        //       az do entry nodu CFG.
        for (Iterator<CheckerError> it = errorsList.iterator(); it.hasNext(); )
            if (it.next().getErrorTraces().isEmpty())
                it.remove();
        }
        
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

    private static CheckerError buildErrorInExitLocationOfCFG(
            final ErrorRule rule,
            final cz.muni.stanse.xml2cfg.ControlFlowGraph cfg,
            final HashMap<cz.muni.stanse.xml2cfg.CFGEdge,PatternLocation>
                                      edgeLocationDictionary) throws Exception {
        /*
        // TODO: tento kod je zaremovany, protoze je jeste nejaky problem
        //       s propagaci chybovych stavu, takze to nefunguje zcela spravne.
        final LinkedList<PatternLocation> exitLocations =
            CFGTraversal.traverseCFGToBreadthBackward(cfg,cfg.getEndNode(),
                    new ExitPatternLocationsCollector(edgeLocationDictionary)).
                getCollectedLocations();

        for (PatternLocation location : exitLocations)
            if (rule.checkForError(location.getProcessedAutomataStates())) {
                return new CheckerError(
                        getMsgPrefix(cfg) + rule.getErrorDescription(),
                        getMsgPrefix(cfg) + rule.getErrorDescription() +
                        " [Contains all the errors traces related to the" +
                        " exit of the function.]",
                        rule.getErrorLevel(),
                        CFGTraversal.traverseCFGToDepthBackward(
                            location.getCFG(),
                            location.getCFG().getEndNode(),
                            (new ErrorTracesListCreator(
                                    rule,edgeLocationDictionary,
                                    location.getCFG().getEndNode(),
                                    location.getCFG()))
                        ).GetTraceErrorsList());
            }
        */
        return null;
    }

    private static String getMsgPrefix(
            final cz.muni.stanse.xml2cfg.ControlFlowGraph cfg) {
        return "In function: '" + cfg.getFunctionName() + "()' : ";
    }

    private CheckerErrorBuilder() {
    }
}
