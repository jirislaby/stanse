/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import cz.muni.stanse.codestructures.CFGNode;

final class PatternLocation {

    // package-private section

    PatternLocation(final CFGNode referenceNode,
                    final Collection<TransitionRule> transitionRules,
                    final Collection<ErrorRule> errorRules) {
        this(referenceNode,transitionRules,errorRules,null);
    }

    PatternLocation(final CFGNode referenceNode,
                    final Collection<TransitionRule> transitionRules,
                    final Collection<ErrorRule> errorRules,
                    final AutomatonStateTransferManager transferor) {
        CFGreferenceNode = referenceNode;
        processedAutomataStates = new HashSet<AutomatonState>();
        unprocessedAutomataStates = new LinkedList<AutomatonState>();
        deliveredAutomataStates = new HashSet<AutomatonState>();
        successorPatternLocations = new LinkedList<PatternLocation>();
        this.transitionRules = new LinkedList<TransitionRule>(transitionRules);
        this.errorRules = new LinkedList<ErrorRule>(errorRules);

        this.transferor = transferor;
        locationForCallNotPassedStates = null;
    }

    LinkedList<PatternLocation> getSuccessorPatternLocations() {
        return successorPatternLocations;
    }

    CFGNode getCFGreferenceNode() {
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

    LinkedList<TransitionRule> getTransitionRules() {
        return transitionRules;
    }

    LinkedList<ErrorRule> getErrorRules() {
        return errorRules;
    }

    AutomatonStateTransferManager getTransferor() {
        return transferor;
    }

    void setTransferor(final AutomatonStateTransferManager transferor) {
        this.transferor = transferor;
    }

    PatternLocation getLocationForCallNotPassedStates() {
        return locationForCallNotPassedStates;
    }

    void setLocationForCallNotPassedStates(final PatternLocation location) {
        locationForCallNotPassedStates = location;
    }

    void fireLocalAutomataStates() {
        
    }

    boolean processUnprocessedAutomataStates() {
        assert(!getUnprocessedAutomataStates().isEmpty());
        boolean successorsWereAffected = false;
        do {
            final AutomatonState currentState =
                getUnprocessedAutomataStates().remove();
            //if (!getProcessedAutomataStates().contains(currentState)) {
            if (!wasProcessed(currentState)) {
                getProcessedAutomataStates().add(currentState);
                transformAutomataStateToSuccessors(currentState);
                successorsWereAffected = true;
            }
        } while (!getUnprocessedAutomataStates().isEmpty());
        return successorsWereAffected;
    }

    void reduceStateSets() {
        reduceStateSet(getProcessedAutomataStates());
        reduceStateSet(getDeliveredAutomataStates());
    }

    // private section

    private boolean wasProcessed(final AutomatonState state) {
        if (getProcessedAutomataStates().contains(state))
            return true;
        final java.util.Stack<CFGNode> stateContext =
                AutomatonStateCFGcontextAlgo.getContext(state);
        for (final AutomatonState processedOne : getProcessedAutomataStates())
            if (processedOne.getSymbol().equals(state.getSymbol())) {
                final java.util.Stack<CFGNode> processedOneContext =
                    AutomatonStateCFGcontextAlgo.getContext(
                        processedOne.getContext());
                AutomatonStateCFGcontextAlgo.firstIsSubcontextOfSecond(
                        stateContext,processedOneContext);
                return true;
            }
        return false;
    }

    private void transformAutomataStateToSuccessors(final AutomatonState state){
        final LinkedList<AutomatonState> transformedStates =
            transformAutomatonState(state);
        for (final PatternLocation location : getSuccessorPatternLocations())
            for (final AutomatonState transformedState : transformedStates)
                propagateStateToLocation(transformedState,location);
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

    private void propagateStateToLocation(final AutomatonState state,
                                          final PatternLocation location) {
        assert(getTransferor() != null);
        final AutomatonState propagatedState =
            getTransferor().transfer(getCFGreferenceNode(),state,
                                     location.getCFGreferenceNode());
        if (propagatedState != null)
            location.getUnprocessedAutomataStates().add(propagatedState);
        else if (getLocationForCallNotPassedStates() != null)
            getLocationForCallNotPassedStates().getUnprocessedAutomataStates()
                                               .add(state);
    }

    private static void reduceStateSet(final HashSet<AutomatonState> set) {
        final java.util.Vector<AutomatonState> stateVec =
            new java.util.Vector<AutomatonState>();
        final java.util.Vector<java.util.Stack<CFGNode>> contextVec =
            new java.util.Vector<java.util.Stack<CFGNode>>();
        for (final AutomatonState state : set) {
            stateVec.add(state);
            contextVec.add(AutomatonStateCFGcontextAlgo
                                .getContext(state.getContext()));
        }
        assert(stateVec.size() == contextVec.size());

        for (int i = 0, n = stateVec.size(); i < n; ++i) {
            final AutomatonState stateI = stateVec.get(i);
            for (int j = i+1; j < n; ++j) {
                final AutomatonState stateJ = stateVec.get(j);
                if (!stateI.getSymbol().equals(stateJ.getSymbol()))
                    continue;
                if (AutomatonStateCFGcontextAlgo.firstIsSubcontextOfSecond(
                        contextVec.get(i),contextVec.get(j))) {
                    set.remove(stateI);
                    break;
                }
            }
        }
    }

    private LinkedList<AutomatonState> getUnprocessedAutomataStates() {
        return unprocessedAutomataStates;
    }

    private final CFGNode CFGreferenceNode;
    private final HashSet<AutomatonState> processedAutomataStates;
    private final LinkedList<AutomatonState> unprocessedAutomataStates;
    private final HashSet<AutomatonState> deliveredAutomataStates;
    private final LinkedList<PatternLocation> successorPatternLocations;
    private final LinkedList<TransitionRule> transitionRules;
    private final LinkedList<ErrorRule> errorRules;

    private AutomatonStateTransferManager transferor;
    private PatternLocation locationForCallNotPassedStates;
}
