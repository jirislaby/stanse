package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.xml2cfg.ControlFlowGraph;
import cz.muni.stanse.xml2cfg.CFGEdge;
import cz.muni.stanse.utils.CFGTraversal;

import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

final class PatternLocationBuilder {

    // package-private section

    static HashMap<CFGEdge,PatternLocation>
    buildPatternLocations(final Set<ControlFlowGraph> setOfAllCFGs,
            final XMLAutomatonDefinition automatonDefinition) throws Exception {
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
            final XMLAutomatonDefinition automatonDefinition) throws Exception {
        final PatternLocationCreator patternLocationCreator =
            new PatternLocationCreator(cfg,automatonDefinition);
        final HashSet<PatternLocation> allLocations =
            CFGTraversal.traverseCFGToBreadthForward(cfg,cfg.getStartNode(),
                    patternLocationCreator).getCreatedPatternLocations();
        
        final HashMap<CFGEdge,PatternLocation> edgeLocationsDictionary =
            new HashMap<CFGEdge,PatternLocation>();
        for (final PatternLocation location : allLocations)
            edgeLocationsDictionary.put(location.getCFGreferenceEdge(),location);
        
        for (final PatternLocation location : allLocations)
            CFGTraversal.traverseCFGToBreadthForward(cfg,
                location.getCFGreferenceEdge().getTo(),
                new ConnectPatternLocationToSuccessors(
                        location,edgeLocationsDictionary));

        CFGTraversal.traverseCFGToBreadthForward(cfg,cfg.getStartNode(),
            new StartPatternLocationsInitializer(edgeLocationsDictionary,
                    getInitialStates(cfg,automatonDefinition.getStartSymbol(),
                            patternLocationCreator.getNumDistinctLocations())));

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
}
