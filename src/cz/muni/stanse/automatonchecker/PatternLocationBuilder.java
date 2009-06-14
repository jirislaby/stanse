/**
 * @file .java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.CFGTraversal;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.CFGsNavigator;
import cz.muni.stanse.utils.ArgumentPassingManager;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;

final class PatternLocationBuilder {

    // package-private section

    static HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
    buildPatternLocations(final Collection<CFG> cfgs,
                          final XMLAutomatonDefinition automatonDefinition,
                          final ArgumentPassingManager passingManager,
                          final CFGsNavigator navigator,
                          final Set<CFG> startFunctions)
                                       throws XMLAutomatonSyntaxErrorException {
        final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
            nodeLocationDictionary=new HashMap<CFGNode,Pair<PatternLocation,
                                                            PatternLocation>>();

        for (final CFG cfg : cfgs) {
    		final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                locationsForCurrentCFG = buildPatternLocationsForOneCFG(cfg,
                                                 automatonDefinition,navigator,
                                                 startFunctions.contains(cfg));
    		nodeLocationDictionary.putAll(locationsForCurrentCFG);
        }

        final CallSiteDetector callDetector =
                new CallSiteDetector(navigator,nodeLocationDictionary);

        createInterproceduralConnectionsBetweenPatternLocations(
            nodeLocationDictionary,navigator,callDetector);

        setStateTransferorToLocations(nodeLocationDictionary.values(),
                new AutomatonStateTransferManager(passingManager,callDetector));

        return nodeLocationDictionary;
    }

    // private section

    private PatternLocationBuilder() {
    }

    private static HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
    buildPatternLocationsForOneCFG(final CFG cfg,
                               final XMLAutomatonDefinition automatonDefinition,
                               final CFGsNavigator navigator,
                               final boolean generateAutomamtaStates)
                                       throws XMLAutomatonSyntaxErrorException {
        final PatternLocationCreator patternLocationCreator =
            new PatternLocationCreator(cfg,automatonDefinition,navigator);

        final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
            nodeLocationsDictionary = CFGTraversal.traverseCFGToBreadthForward(
                                                        cfg,cfg.getStartNode(),
                                                        patternLocationCreator)
                                                  .getNodeLocationDictionary();

        createIntraproceduralConnectionsBetweenPatternLocations(
            nodeLocationsDictionary,cfg);

        final HashSet<SimpleAutomatonID> automataIDs =
            (generateAutomamtaStates) ?
                patternLocationCreator.getAutomataIDs() :
                getParameterIndependentAutomataIDs(cfg,patternLocationCreator.
                                                              getAutomataIDs());
        if (!automataIDs.isEmpty())
            setupAutomatonStatesForCFG(cfg,automatonDefinition,
                                       nodeLocationsDictionary,
                                       patternLocationCreator.getAutomataIDs());

        return nodeLocationsDictionary;
    }

    private static void setStateTransferorToLocations(
              final Collection<Pair<PatternLocation,PatternLocation>> locations,
              final AutomatonStateTransferManager transferor) {
        for (final Pair<PatternLocation,PatternLocation> item : locations) {
            item.getFirst().setTransferor(transferor);
            item.getSecond().setTransferor(transferor);
        }
    }

    private static void createIntraproceduralConnectionsBetweenPatternLocations(
                    final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary,
                    final CFG cfg) {
        for (final Pair<PatternLocation,PatternLocation> locationsPair :
                                                nodeLocationDictionary.values())
            CFGTraversal.traverseCFGToBreadthForward(
                            cfg,locationsPair.getSecond().getCFGreferenceNode(),
                            new ConnectPatternLocationToSuccessors(
                                                      locationsPair.getSecond(),
                                                       nodeLocationDictionary));

    }

    private static void createInterproceduralConnectionsBetweenPatternLocations(
                    final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary,
                    final CFGsNavigator navigator,
                    final CallSiteDetector callDetector) {
        for (final CFGNode callNode : callDetector.callSites()) {
            final Pair<PatternLocation,PatternLocation> locationsPair =
                nodeLocationDictionary.get(callNode);
            connectCallerCallee(locationsPair.getFirst(),
                                locationsPair.getSecond(),
                                nodeLocationDictionary.get(
                                        navigator.getCalleeStart(callNode))
                                    .getSecond(),
                                nodeLocationDictionary.get(
                                        navigator.getCalleeEnd(callNode))
                                    .getFirst());
        }
    }

    private static void connectCallerCallee(final PatternLocation callLocation,
                                    final PatternLocation returnLocation,
                                    final PatternLocation calleeStartLocation,
                                    final PatternLocation calleeEndLocation) {
        callLocation.getSuccessorPatternLocations()
                    .add(calleeStartLocation);
        calleeEndLocation.getSuccessorPatternLocations()
                         .add(returnLocation);
    }

    private static HashSet<SimpleAutomatonID>
    getParameterIndependentAutomataIDs(final CFG cfg,
                                 final HashSet<SimpleAutomatonID> automataIDs) {
        final HashSet<SimpleAutomatonID> result =
            new HashSet<SimpleAutomatonID>();
        for (final SimpleAutomatonID id : automataIDs) {
            final java.util.Iterator<org.dom4j.Element> paramIter =
                cz.muni.stanse.utils.XMLLinearizeASTElement
                    .functionDeclaration(cfg.getElement()).iterator();
            boolean paramDependent = false;
            for (paramIter.next(); paramIter.hasNext(); )
                if (isParameterDependentID(id,paramIter.next().getText())) {
                    paramDependent = true;
                    break;
                }
            if (!paramDependent)
                result.add(id);
        }
        return result;
    }

    private static boolean isParameterDependentID(final SimpleAutomatonID id,
                                                  final String paramName) {
        for (final String varsAssign : id.getVarsAssignment())
            if (varsAssign.contains(paramName))
                return true;
        return false;
    }

    private static void setupAutomatonStatesForCFG(final CFG cfg,
                    final XMLAutomatonDefinition automatonDefinition,
                    final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                        nodeLocationsDictionary,
                    final HashSet<SimpleAutomatonID> automataIDs) {
        nodeLocationsDictionary.get(cfg.getStartNode()).getSecond().
            setInitialAutomataStates(
                getAutomataStates(automatonDefinition.getStartSymbol(),
                                  automataIDs));
        nodeLocationsDictionary.get(cfg.getEndNode()).getFirst().
            getErrorRules().addAll(getExitErrorRules(
                                  automatonDefinition.getExitErrorRules(),
                                  automataIDs));
        nodeLocationsDictionary.get(cfg.getStartNode()).getSecond().
            setIsStartLocation(true);
    }

    private static LinkedList<AutomatonState>
    getAutomataStates(final String startSymbol,
                      final HashSet<SimpleAutomatonID> automataIDs){
        final LinkedList<AutomatonState> states =
            new LinkedList<AutomatonState>();
        for (final SimpleAutomatonID id : automataIDs)
            states.add(new AutomatonState(startSymbol,id));
        return states;
    }

    private static LinkedList<ErrorRule>
    getExitErrorRules(final LinkedList<XMLErrorRule> XMLrules,
                      final HashSet<SimpleAutomatonID> automataIDs){
        final LinkedList<ErrorRule> errorRules = new LinkedList<ErrorRule>();
        for (XMLErrorRule rule : XMLrules)
            for (final SimpleAutomatonID id : automataIDs)
                errorRules.add(new ErrorRule(rule,id));
        return errorRules;
    }
}
