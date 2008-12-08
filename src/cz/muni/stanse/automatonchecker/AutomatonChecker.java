package cz.muni.stanse.automatonchecker;

import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import cz.muni.stanse.parser.ControlFlowGraph;

public final class AutomatonChecker extends cz.muni.stanse.checker.Checker {

    // public section

    public AutomatonChecker(final org.dom4j.Document XMLdefinition)
                                                              throws Exception {
        super();
        XMLAutomatonDefinition = new XMLAutomatonDefinition(
                                                XMLdefinition.getRootElement());
    }

    @Override
    public String getName() {
        return "Automaton checker [" +
               getXMLAutomatonDefinition().getAutomatonName() +
               "]";
    }

    @Override
    public List<cz.muni.stanse.checker.CheckerError>
    check(final Set<cz.muni.stanse.parser.ControlFlowGraph> CFGs)
                                                              throws Exception {
        final HashMap<cz.muni.stanse.parser.CFGEdge,PatternLocation>
            edgeLocationDictionary = PatternLocationBuilder.
                   buildPatternLocations(CFGs,getXMLAutomatonDefinition());

        final LinkedList<PatternLocation> progressQueue =
                new LinkedList<PatternLocation>();
        for (final ControlFlowGraph cfg : CFGs)
            progressQueue.add(edgeLocationDictionary.get(cfg.getEntryEdge()));

        while (!progressQueue.isEmpty()) {
            final PatternLocation currentLocation = progressQueue.remove();
            if (!currentLocation.hasUnprocessedAutomataStates())
                continue;
            final boolean successorsWereAffected =
                currentLocation.processUnprocessedAutomataStates();
            if (successorsWereAffected)
                progressQueue.addAll(
                        currentLocation.getSuccessorPatternLocations());
        }
        return CheckerErrorBuilder.buildErrorList(edgeLocationDictionary);
    }

    // private section

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return XMLAutomatonDefinition;
    }

    private final XMLAutomatonDefinition XMLAutomatonDefinition;
}
