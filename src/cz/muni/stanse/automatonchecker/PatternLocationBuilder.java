package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.utils.CFGTraversal;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

final class PatternLocationBuilder {

    // package-private section

    static HashMap<CFGNode,PatternLocation>
    buildPatternLocations(final List<Unit> units,
                          final XMLAutomatonDefinition automatonDefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final HashMap<CFGNode,PatternLocation> nodeLocationDictionary =
            new HashMap<CFGNode,PatternLocation>();

        for (final Unit unit : units) {
        	for (final CFG cfg : unit.getCFGs()) {
        		final HashMap<CFGNode,PatternLocation> locationsForCurrentCFG =
        			buildPatternLocationsForOneCFG(cfg,automatonDefinition);
        		nodeLocationDictionary.putAll(locationsForCurrentCFG);
        	}
        }

        return nodeLocationDictionary;
    }

    // private section

    private PatternLocationBuilder() {
    }

    private static HashMap<CFGNode,PatternLocation>
    buildPatternLocationsForOneCFG(final CFG cfg,
                               final XMLAutomatonDefinition automatonDefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final PatternLocationCreator patternLocationCreator =
            new PatternLocationCreator(cfg,automatonDefinition);
        final HashMap<CFGNode,PatternLocation> nodeLocationsDictionary =
            CFGTraversal.traverseCFGToBreadthForward(cfg,cfg.getStartNode(),
                    patternLocationCreator).getCreatedPatternLocations();

        nodeLocationsDictionary.get(cfg.getStartNode()).
            setInitialAutomataStates(getInitialStates(
                            cfg,automatonDefinition.getStartSymbol(),
                            patternLocationCreator.getNumDistinctLocations()));
        nodeLocationsDictionary.get(cfg.getEndNode()).
            getErrorRules().addAll(getExitErrorRules(
                            automatonDefinition.getExitErrorRules(),
                            patternLocationCreator.getNumDistinctLocations()));

        for (final PatternLocation location : nodeLocationsDictionary.values())
            CFGTraversal.traverseCFGToBreadthForward(cfg,
                        location.getCFGreferenceNode(),
                        new ConnectPatternLocationToSuccessors(
                                location,nodeLocationsDictionary));

        return nodeLocationsDictionary;
    }

    private static LinkedList<AutomatonState> getInitialStates(final CFG cfg,
                        final int startSymbol, final int numDistinctLocations) {
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
