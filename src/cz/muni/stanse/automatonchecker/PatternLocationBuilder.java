/**
 * @file .java
 * @brief
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.AliasResolver;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.traversal.CFGTraversal;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;
import cz.muni.stanse.codestructures.CFGsNavigator;
import cz.muni.stanse.codestructures.ArgumentPassingManager;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

final class PatternLocationBuilder {

    // package-private section

    static HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
    buildPatternLocations(final Collection<CFGHandle> cfgs,
                          final AliasResolver aliasResolver,
                          final XMLAutomatonDefinition automatonDefinition,
                          final ArgumentPassingManager passingManager,
                          final CFGsNavigator navigator,
                          final Set<CFGHandle> startFunctions)
                                       throws XMLAutomatonSyntaxErrorException {
        final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
            nodeLocationDictionary=new HashMap<CFGNode,Pair<PatternLocation,
                                                            PatternLocation>>();
        final HashSet<SimpleAutomatonID> globalAutomataIDs =
            new HashSet<SimpleAutomatonID>();

        for (final CFGHandle cfg: cfgs) {
    		final Pair<HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>,
                       HashSet<SimpleAutomatonID>> locationsAndStates =
                buildPatternLocationsAndStatesForOneCFG(cfg,
                        aliasResolver,
                        automatonDefinition, navigator,
                        startFunctions.contains(cfg));
    		nodeLocationDictionary.putAll(locationsAndStates.getFirst());
            globalAutomataIDs.addAll(locationsAndStates.getSecond());
        }

        final CallSiteDetector callDetector =
                new CallSiteDetector(navigator,nodeLocationDictionary);

        createInterproceduralConnectionsBetweenPatternLocations(
            nodeLocationDictionary,navigator,callDetector);

        setStateTransferorToLocations(nodeLocationDictionary.values(),
                new AutomatonStateTransferManager(passingManager,callDetector));

        if (!globalAutomataIDs.isEmpty())
            for (final CFGHandle cfg: startFunctions) {
                addInitialAutomatonStatesForCFGLocations(
                    nodeLocationDictionary.get(cfg.getStartNode()).getSecond(),
                    nodeLocationDictionary.get(cfg.getEndNode()).getFirst(),
                    automatonDefinition,globalAutomataIDs,true);
            }

        return nodeLocationDictionary;
    }

    // private section

    private PatternLocationBuilder() {
    }

    private static Pair<HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>,
                        HashSet<SimpleAutomatonID>>
    buildPatternLocationsAndStatesForOneCFG(final CFGHandle cfg,
                               final AliasResolver aliasResolver,
                               final XMLAutomatonDefinition automatonDefinition,
                               final CFGsNavigator navigator,
                               final boolean isStartFunction)
                                       throws XMLAutomatonSyntaxErrorException {
        final PatternLocationCreator patternLocationCreator =
            new PatternLocationCreator(cfg, automatonDefinition,navigator, aliasResolver);

        final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
            nodeLocationsDictionary = CFGTraversal.traverseCFGToBreadthForward(
                                                        cfg,cfg.getStartNode(),
                                                        patternLocationCreator)
                                                  .getNodeLocationDictionary();

        createIntraproceduralConnectionsBetweenPatternLocations(
            nodeLocationsDictionary,cfg);

        final Triple<HashSet<SimpleAutomatonID>,
                     HashSet<SimpleAutomatonID>,
                     HashSet<SimpleAutomatonID>> automataIDs =
            splitAutomataIDsIntoGlobalLocalAndFloation(patternLocationCreator.
                                                          getAutomataIDs(),cfg);
        if (!automataIDs.getSecond().isEmpty())
            addInitialAutomatonStatesForCFGLocations(
                    nodeLocationsDictionary.get(cfg.getStartNode()).getSecond(),
                    nodeLocationsDictionary.get(cfg.getEndNode()).getFirst(),
                    automatonDefinition,automataIDs.getSecond(),false);
        if (isStartFunction && !automataIDs.getThird().isEmpty())
            addInitialAutomatonStatesForCFGLocations(
                    nodeLocationsDictionary.get(cfg.getStartNode()).getSecond(),
                    nodeLocationsDictionary.get(cfg.getEndNode()).getFirst(),
                    automatonDefinition,automataIDs.getThird(),false);
        return Pair.make(nodeLocationsDictionary,automataIDs.getFirst());
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
                    final CFGHandle cfg) {
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
                                    .getFirst(),
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

    private static Triple<HashSet<SimpleAutomatonID>,HashSet<SimpleAutomatonID>,
                          HashSet<SimpleAutomatonID>>
    splitAutomataIDsIntoGlobalLocalAndFloation(
                          final HashSet<SimpleAutomatonID> IDs,
                          final CFGHandle cfg) {
        final Triple<HashSet<SimpleAutomatonID>,
                     HashSet<SimpleAutomatonID>,
                     HashSet<SimpleAutomatonID>>
            result = Triple.make(new HashSet<SimpleAutomatonID>(),
                                 new HashSet<SimpleAutomatonID>(),
                                 new HashSet<SimpleAutomatonID>());
        for (final SimpleAutomatonID id : IDs)
           if (isParameterDependentID(id, cfg.getParams().iterator()))
                result.getThird().add(id);
            else if (isOfLocallyDeclaredVariable(id,cfg)) {
                if (isInReturnExpression(id,cfg))
                    result.getThird().add(id);
                else
                    result.getSecond().add(id);
            }
            else
                result.getFirst().add(
                        new SimpleAutomatonID(id.getVarsAssignment(),true));
        return result;
    }

    private static boolean
    isParameterDependentID(final SimpleAutomatonID automatonID,
                        final java.util.Iterator<String> params) {
        while (params.hasNext())
            if (isParameterDependentID(automatonID,params.next()))
                return true;
        return false;
    }

    private static boolean isParameterDependentID(final SimpleAutomatonID id,
                                                  final String paramName) {
        for (final String varsAssign : id.getVarsAssignment())
            if (varsAssign.contains(paramName))
                return true;
        return false;
    }

    private static boolean
    isOfLocallyDeclaredVariable(final SimpleAutomatonID id, final CFGHandle cfg) {
        for (final String varsAssign : id.getVarsAssignment())
            if (!cfg.isSymbolLocal(cz.muni.stanse.codestructures.PassingSolver.
                        parseRootVariableName(varsAssign)))
                return false;
        return true;
    }

    private static boolean isInReturnExpression(final SimpleAutomatonID id,
            final CFGHandle cfg) {
        for (final CFGNode node : cfg.getEndNode().getPredecessors()) {
            Set<String> dependentVars = null;
            if (node.getNodeType() == CFGNode.NodeType.call
                    && ((String)node.getOperands().get(0).id).equals("=")
                    && ((String)node.getOperands().get(1).id).equals(cfg.getRetVar())) {
                CFGNode.Operand retop = node.getOperands().get(2);
                dependentVars = CFGNode.getDependentVars(retop);
            } else if (node.getElement() != null && node.getElement().getName().equals("returnStatement")) {
                dependentVars = new HashSet<String>();
                for (Object idElem : node.getElement().selectNodes("id"))
                    dependentVars.add(((org.dom4j.Element)idElem).getText());
            }
            if (dependentVars != null && isInReturnExpression(id,dependentVars))
                return true;
        }
        return false;
    }

    private static boolean isInReturnExpression(final SimpleAutomatonID id,
                                                final Set<String> dependentVars) {
        for (final String varsAssign : id.getVarsAssignment()) {
            final String varName = cz.muni.stanse.codestructures.PassingSolver.
                                              parseRootVariableName(varsAssign);
            for (String depVar : dependentVars)
                if (varName.equals(depVar))
                    return true;
        }
        return false;
    }

    private static void addInitialAutomatonStatesForCFGLocations(
                    final PatternLocation startLoc,final PatternLocation endLoc,
                    final XMLAutomatonDefinition automatonDefinition,
                    final HashSet<SimpleAutomatonID> automataIDs,
                    final boolean asGlobal) {
        startLoc.setInitialAutomataStates(
                    getAutomataStates(automatonDefinition.getStartSymbol(),
                                      automataIDs,asGlobal));
        endLoc.getErrorRules()
              .addAll(getExitErrorRules(automatonDefinition.getExitErrorRules(),
                                        automataIDs,asGlobal));
    }

    private static LinkedList<AutomatonState>
    getAutomataStates(final String startSymbol,
                      final HashSet<SimpleAutomatonID> automataIDs,
                      final boolean asGlobal){
        final LinkedList<AutomatonState> states =
            new LinkedList<AutomatonState>();
        for (final SimpleAutomatonID id : automataIDs)
            states.add(new AutomatonState(startSymbol,new SimpleAutomatonID(
                                          id.getVarsAssignment(),asGlobal)));
        return states;
    }

    private static LinkedList<ErrorRule>
    getExitErrorRules(final LinkedList<XMLErrorRule> XMLrules,
                      final HashSet<SimpleAutomatonID> automataIDs,
                      final boolean asGlobal){
        final LinkedList<ErrorRule> errorRules = new LinkedList<ErrorRule>();
        for (XMLErrorRule rule : XMLrules)
            for (final SimpleAutomatonID id : automataIDs)
                errorRules.add(new ErrorRule(rule,new SimpleAutomatonID(
                                             id.getVarsAssignment(),asGlobal)));
        return errorRules;
    }
}
