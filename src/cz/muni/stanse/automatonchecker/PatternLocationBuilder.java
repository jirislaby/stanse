package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.parser.ControlFlowGraph;
import cz.muni.stanse.utils.CFGTraversal;

import java.util.LinkedList;
import java.util.Set;
import java.util.HashMap;

final class PatternLocationBuilder {

    // package-private section

    static HashMap<CFGEdge,PatternLocation>
    buildPatternLocations(final Set<ControlFlowGraph> setOfAllCFGs,
                          final XMLAutomatonDefinition automatonDefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final HashMap<CFGEdge,PatternLocation> edgeLocationDictionary =
            new HashMap<CFGEdge,PatternLocation>();

        for (final ControlFlowGraph cfg : setOfAllCFGs) {
            final HashMap<CFGEdge,PatternLocation> locationsForCurrentCFG =
                        buildPatternLocationsForOneCFG(cfg,automatonDefinition);
            edgeLocationDictionary.putAll(locationsForCurrentCFG);
        }

        return edgeLocationDictionary;
    }

    // private section

    private PatternLocationBuilder() {
    }

    private static HashMap<CFGEdge,PatternLocation>
    buildPatternLocationsForOneCFG(final ControlFlowGraph cfg,
                               final XMLAutomatonDefinition automatonDefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final PatternLocationCreator patternLocationCreator =
            new PatternLocationCreator(cfg,automatonDefinition);
        final HashMap<CFGEdge,PatternLocation> edgeLocationsDictionary =
            CFGTraversal.traverseCFGToBreadthForward(cfg,cfg.getStartNode(),
                    patternLocationCreator).getCreatedPatternLocations();

        edgeLocationsDictionary.get(cfg.getEntryEdge()).
            setInitialAutomataStates(getInitialStates(
                            cfg,automatonDefinition.getStartSymbol(),
                            patternLocationCreator.getNumDistinctLocations()));
        edgeLocationsDictionary.get(cfg.getExitEdge()).
            getErrorRules().addAll(getExitErrorRules(
                            automatonDefinition.getExitErrorRules(),
                            patternLocationCreator.getNumDistinctLocations()));

        for (final PatternLocation location : edgeLocationsDictionary.values())
            CFGTraversal.traverseCFGToBreadthForward(cfg,
                location.getCFGreferenceEdge().getTo(),
                new ConnectPatternLocationToSuccessors(
                        location,edgeLocationsDictionary));

        return edgeLocationsDictionary;
    }

    private static LinkedList<AutomatonState> getInitialStates(
                          final ControlFlowGraph cfg, final int startSymbol,
                          final int numDistinctLocations) {
        final LinkedList<AutomatonState> initStates =
            new LinkedList<AutomatonState>();

        for (int i = 0; i < numDistinctLocations; ++i)
            initStates.add(new AutomatonState(cfg,startSymbol,i));

        return initStates;
    }

    private static LinkedList<ErrorRule> getExitErrorRules(
                                        final LinkedList<XMLErrorRule> XMLrules,
                                        final int numDistinctLocations) {
        final LinkedList<ErrorRule> errorRules = new LinkedList<ErrorRule>();

        for (XMLErrorRule rule : XMLrules)
            for (int i = 0; i < numDistinctLocations; ++i)
                errorRules.add(new ErrorRule(rule,i));

        return errorRules;
    }
}
