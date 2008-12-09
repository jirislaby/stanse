/**
 * @brief
 *
 */
package cz.muni.stanse.automatonchecker;

import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import cz.muni.stanse.parser.ControlFlowGraph;

/**
 * @brief
 *
 * @see
 */
public final class AutomatonChecker extends cz.muni.stanse.checker.Checker {

    // public section

    /**
     * @brief
     *
     * @param XMLdefinition XML representation of AST
     * @throws 
     * @see
     */
    public AutomatonChecker(final org.dom4j.Document XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        super();
        XMLAutomatonDefinition = new XMLAutomatonDefinition(
                                                XMLdefinition.getRootElement());
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return "Automaton checker [" +
               getXMLAutomatonDefinition().getAutomatonName() +
               "]";
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see cz.muni.stanse.checker.Checker#check(java.util.Set)
     */
    @Override
    public List<cz.muni.stanse.checker.CheckerError>
    check(final Set<cz.muni.stanse.parser.ControlFlowGraph> CFGs)
                                       throws XMLAutomatonSyntaxErrorException {
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

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return XMLAutomatonDefinition;
    }

    /**
     * @brief
     */
    private final XMLAutomatonDefinition XMLAutomatonDefinition;
}
