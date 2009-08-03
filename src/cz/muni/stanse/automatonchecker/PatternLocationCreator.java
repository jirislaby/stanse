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
import cz.muni.stanse.codestructures.CFGsNavigator;
import cz.muni.stanse.codestructures.traversal.CFGvisitor;
import cz.muni.stanse.utils.xmlpatterns.XMLPattern;
import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Make;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

final class PatternLocationCreator extends CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {
        final LinkedList<Pair<XMLPattern,SimpleAutomatonID>>
            matchings = new LinkedList<Pair<XMLPattern,SimpleAutomatonID>>();
        for (XMLPattern pattern : getXMLAutomatonDefinition().getXMLpatterns()){
            final Pair<Boolean,XMLPatternVariablesAssignment>
                matchResult = pattern.matchesXMLElement(element);
            if (matchResult.getFirst())
                matchings.add(Pair.make(pattern,
                                   new SimpleAutomatonID(matchResult
                                                         .getSecond(),false)));
        }
        assert(matchings.size() <= 1);

        if (!matchings.isEmpty()) {
            final PatternLocation newLocation =
                    createCommonPatternLocation(node,matchings);
            getNodeLocationDictionary().put(node,Pair.make(newLocation,
                                                           newLocation));
            if (matchings.getFirst().getFirst().isSonstructive())
                getAutomataIDs().add(matchings.getFirst().getSecond());
        }
        else if (getNavigator().isCallNode(node)) {
            final PatternLocation callLocation =
                    createRuleLessPatternLocation(node);
            final PatternLocation returnLocation =
                    createRuleLessPatternLocation(node);
            getNodeLocationDictionary().put(node,Pair.make(callLocation,
                                                           returnLocation));
            callLocation.setLocationForCallNotPassedStates(returnLocation);
        }

        return true;
    }

    // package-private section

    PatternLocationCreator(final CFG cfg,
                           final XMLAutomatonDefinition XMLdefinition,
                           final CFGsNavigator navigator) {
        super();
        automatonDefinition = XMLdefinition;
        nodeLocationDictionary = new HashMap<CFGNode,Pair<PatternLocation,
                                                          PatternLocation>>();
        automataIDs = new HashSet<SimpleAutomatonID>();
        this.navigator = navigator;

        createStartEndPatternLocations(cfg);
    }

    HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
    getNodeLocationDictionary() {
        return nodeLocationDictionary;
    }

    public HashSet<SimpleAutomatonID> getAutomataIDs() {
        return automataIDs;
    }

    // private section

    private PatternLocation createCommonPatternLocation(final CFGNode node,
               final LinkedList<Pair<XMLPattern,SimpleAutomatonID>> matchings) {
        return new PatternLocation(node,createTransitionRules(matchings),
                                   createErrorRules(matchings));
    }

    private LinkedList<TransitionRule> createTransitionRules(
               final LinkedList<Pair<XMLPattern,SimpleAutomatonID>> matchings) {
        final LinkedList<TransitionRule> transitionRules =
                new LinkedList<TransitionRule>();
        for (Pair<XMLPattern,SimpleAutomatonID> item : matchings)
            for (final XMLTransitionRule XMLtransitionRule :
                        getXMLAutomatonDefinition().
                               getXMLtransitionRulesForPattern(item.getFirst()))
                transitionRules.add(new TransitionRule(XMLtransitionRule,
                                                       item.getSecond()));
        return transitionRules;
    }
    
    private LinkedList<ErrorRule> createErrorRules(
               final LinkedList<Pair<XMLPattern,SimpleAutomatonID>> matchings) {
        final LinkedList<ErrorRule> errorRules = new LinkedList<ErrorRule>();
        for (Pair<XMLPattern,SimpleAutomatonID> item : matchings)
            for (final XMLErrorRule XMLerrorRule : getXMLAutomatonDefinition().
                                    getXMLerrorRulesForPattern(item.getFirst()))
                errorRules.add(new ErrorRule(XMLerrorRule,item.getSecond()));
        return errorRules;
    }

    private PatternLocation createRuleLessPatternLocation(final CFGNode node) {
        return new PatternLocation(node,Make.<TransitionRule>linkedList(),
                                   Make.<ErrorRule>linkedList());
    }

    private void createStartEndPatternLocations(final CFG cfg) {
        final PatternLocation startLocation =
                    createRuleLessPatternLocation(cfg.getStartNode());
        getNodeLocationDictionary().put(cfg.getStartNode(),
                                        Pair.make(startLocation,startLocation));

        final PatternLocation endLocation =
                    createRuleLessPatternLocation(cfg.getEndNode());
        getNodeLocationDictionary().put(cfg.getEndNode(),
                                        Pair.make(endLocation,endLocation));
    }

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return automatonDefinition;
    }

    private CFGsNavigator getNavigator() {
        return navigator;
    }

    private final XMLAutomatonDefinition automatonDefinition;
    private final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                            nodeLocationDictionary;
    private final HashSet<SimpleAutomatonID> automataIDs;
    private final CFGsNavigator navigator;
}
