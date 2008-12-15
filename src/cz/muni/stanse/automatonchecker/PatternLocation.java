package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

final class PatternLocation {

    // package-private section

    PatternLocation(final cz.muni.stanse.parser.CFG cfg,
                    final cz.muni.stanse.parser.CFGNode referenceNode,
                    final Collection<TransitionRule> transitionRules,
                    final Collection<ErrorRule> errorRules) {
        this.cfg = cfg;
        CFGreferenceNode = referenceNode;
        processedAutomataStates = new HashSet<AutomatonState>();
        unprocessedAutomataStates = new LinkedList<AutomatonState>();
        deliveredAutomataStates = new HashSet<AutomatonState>();
        successorPatternLocations = new LinkedList<PatternLocation>();
        this.transitionRules = new LinkedList<TransitionRule>(transitionRules);
        this.errorRules = new LinkedList<ErrorRule>(errorRules);
    }

    LinkedList<PatternLocation> getSuccessorPatternLocations() {
        return successorPatternLocations;
    }

    cz.muni.stanse.parser.CFGNode getCFGreferenceNode() {
        return CFGreferenceNode;
    }

    void setInitialAutomataStates(
                         final LinkedList<AutomatonState> initStates) {
        assert(getUnprocessedAutomataStates().isEmpty());
        getUnprocessedAutomataStates().addAll(initStates);
    }

    boolean hasUnprocessedAutomataStates() {
        return !getUnprocessedAutomataStates().isEmpty();
    }

    HashSet<AutomatonState> getProcessedAutomataStates() {
        return processedAutomataStates;
    }

    HashSet<AutomatonState> getDeliveredAutomataStates() {
        return deliveredAutomataStates;
    }

    public cz.muni.stanse.parser.CFG getCFG() {
        return cfg;
    }

    LinkedList<ErrorRule> getErrorRules() {
        return errorRules;
    }

    boolean processUnprocessedAutomataStates() {
        assert(getSuccessorPatternLocations().size() < 2);
        assert(!getUnprocessedAutomataStates().isEmpty());
        boolean successorsWereAffected = false;
        do {
            final AutomatonState currentState =
                getUnprocessedAutomataStates().remove();
            if (!getProcessedAutomataStates().contains(currentState)) {
                getProcessedAutomataStates().add(currentState);
                transformAutomataStateToSuccessors(currentState);
                successorsWereAffected = true;
            }
        } while (!getUnprocessedAutomataStates().isEmpty());
        return successorsWereAffected;
    }

    // private section

    private void transformAutomataStateToSuccessors(final AutomatonState state) {
        final LinkedList<AutomatonState> transformedStates =
            transformAutomatonState(state);
        for (final PatternLocation location : getSuccessorPatternLocations())
            location.getUnprocessedAutomataStates().addAll(transformedStates);
        getDeliveredAutomataStates().addAll(transformedStates);
    }

    private LinkedList<AutomatonState>
    transformAutomatonState(final AutomatonState state) {
        final LinkedList<AutomatonState> transformedStates =
            new LinkedList<AutomatonState>();
        boolean stateWasTransformedByAtLeastOneRule = false;
        for (final TransitionRule rule : getTransitionRules()) {
            final Pair<Boolean,AutomatonState> transformationResult =
                rule.transformAutomatonState(state);
            if (transformationResult.getFirst()) {
                stateWasTransformedByAtLeastOneRule = true;
                if (transformationResult.getSecond() != null)
                    transformedStates.add(transformationResult.getSecond());
            }
        }
        if (!stateWasTransformedByAtLeastOneRule)
            transformedStates.add(state);
        return transformedStates;
    }

    private LinkedList<AutomatonState> getUnprocessedAutomataStates() {
        return unprocessedAutomataStates;
    }

    private LinkedList<TransitionRule> getTransitionRules() {
        return transitionRules;
    }

    private final cz.muni.stanse.parser.CFG cfg;
    private final cz.muni.stanse.parser.CFGNode CFGreferenceNode;
    private final HashSet<AutomatonState> processedAutomataStates;
    private final LinkedList<AutomatonState> unprocessedAutomataStates;
    private final HashSet<AutomatonState> deliveredAutomataStates;
    private final LinkedList<PatternLocation> successorPatternLocations;
    private final LinkedList<TransitionRule> transitionRules;
    private final LinkedList<ErrorRule> errorRules;
}
