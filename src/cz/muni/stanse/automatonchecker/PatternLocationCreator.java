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
import cz.muni.stanse.utils.CFGvisitor;
import cz.muni.stanse.utils.Pair;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

final class PatternLocationCreator extends CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {
        HashSet<Pair<Integer,Integer>> nodeAutomataIDs =
                                                 getNodeAutomataIDs().get(node); 
        final Vector<XMLPattern> XMLpatterns =
            getXMLAutomatonDefinition().getXMLpatterns();
        for (int i = 0; i < XMLpatterns.size(); ++i) {
            final Pair<Boolean,PatternVariablesAssignment>
                matchResult = XMLpatterns.get(i).matchesXMLElement(element);
            if (matchResult.getFirst()) {
                Integer automatonID = getAutomataIDs().get(
                                                       matchResult.getSecond());
                if (automatonID == null) {
                    getAutomataIDs().put(matchResult.getSecond(),
                                          automatonID = getUniqueAutomatonID());
                    getAutomataIDsUsage().put(automatonID,
                                          XMLpatterns.get(i).isSonstructive());
                } else
                    getAutomataIDsUsage().put(automatonID,
                                       getAutomataIDsUsage().get(automatonID) ||
                                       XMLpatterns.get(i).isSonstructive());
                if (nodeAutomataIDs == null) {
                    nodeAutomataIDs = new HashSet<Pair<Integer,Integer>>();
                    getNodeAutomataIDs().put(node,nodeAutomataIDs);
                }
                nodeAutomataIDs.add(new Pair<Integer,Integer>(i,automatonID));
            }
        }
        return true;
    }

    // package-private section

    PatternLocationCreator(final CFG cfg,
            final XMLAutomatonDefinition XMLdefinition) {
        super();
        this.cfg = cfg;
        automatonDefinition = XMLdefinition;
        automataIDs = new HashMap<PatternVariablesAssignment,Integer>();
        automataIDsUsage = new HashMap<Integer,Boolean>();
        nodeAutomataIDs = new HashMap<CFGNode,HashSet<Pair<Integer,Integer>>>();
        patternAutomataCounter = 0;
    }

    HashMap<CFGNode,PatternLocation> getCreatedPatternLocations() {
        final HashMap<CFGNode,PatternLocation> nodeLocationDictionary =
            new HashMap<CFGNode,PatternLocation>();

        nodeLocationDictionary.put(getCFG().getStartNode(),
                                   new PatternLocation(getCFG(),
                                               getCFG().getStartNode(),
                                               new LinkedList<TransitionRule>(),
                                               new LinkedList<ErrorRule>()));

        for (CFGNode node : getNodeAutomataIDs().keySet()) {
            final LinkedList<Pair<Integer,Integer>> mapping =
                filterAutomataIdMapping(getNodeAutomataIDs().get(node));
            if (!mapping.isEmpty())
                nodeLocationDictionary.put(node,createPatternLocation(node,
                                                                      mapping));
        }

        nodeLocationDictionary.put(getCFG().getEndNode(),
                                   new PatternLocation(getCFG(),
                                               getCFG().getEndNode(),
                                               new LinkedList<TransitionRule>(),
                                               new LinkedList<ErrorRule>()));
        
        return nodeLocationDictionary;
    }

    LinkedList<Integer> getValidAutomataIDs() {
        final LinkedList<Integer> result = new LinkedList<Integer>();
        for (final Integer ID : getAutomataIDs().values())
            if (getAutomataIDsUsage().get(ID))
                result.add(ID);
        return result;
    }

    // private section

    LinkedList<Pair<Integer,Integer>> filterAutomataIdMapping(
                                 final HashSet<Pair<Integer,Integer>> mapping) {
        final LinkedList<Pair<Integer,Integer>> result =
            new LinkedList<Pair<Integer,Integer>>();
        for (final Pair<Integer,Integer> item : mapping)
            if (getAutomataIDsUsage().get(item.getSecond()))
                result.add(item);
        return result;
    }

    private PatternLocation createPatternLocation(final CFGNode node,
                              final LinkedList<Pair<Integer,Integer>> mapping) {
        final LinkedList<TransitionRule> transitionRules =
                new LinkedList<TransitionRule>();
        for (Pair<Integer,Integer> item : mapping)
            for (final XMLTransitionRule XMLtransitionRule :
                        getXMLAutomatonDefinition().
                               getXMLtransitionRulesForPattern(item.getFirst()))
                transitionRules.add(new TransitionRule(XMLtransitionRule,
                                                       item.getSecond()));

        final LinkedList<ErrorRule> errorRules = new LinkedList<ErrorRule>();
        for (Pair<Integer,Integer> item : mapping)
            for (final XMLErrorRule XMLerrorRule : getXMLAutomatonDefinition().
                                    getXMLerrorRulesForPattern(item.getFirst()))
                errorRules.add(new ErrorRule(XMLerrorRule,item.getSecond()));

        return new PatternLocation(getCFG(),node,transitionRules,errorRules);
    }
    
    private CFG getCFG() {
        return cfg;
    }

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return automatonDefinition;
    }

    private HashMap<PatternVariablesAssignment,Integer> getAutomataIDs() {
        return automataIDs;
    }

    private HashMap<Integer,Boolean> getAutomataIDsUsage() {
        return automataIDsUsage;
    }

    private HashMap<CFGNode,HashSet<Pair<Integer,Integer>>>
    getNodeAutomataIDs() {
        return nodeAutomataIDs;
    }

    private int getUniqueAutomatonID() {
        return patternAutomataCounter++;
    }

    private final CFG cfg;
    private final XMLAutomatonDefinition automatonDefinition;
    private final HashMap<PatternVariablesAssignment,Integer> automataIDs;
    private final HashMap<Integer,Boolean> automataIDsUsage;
    private final HashMap<CFGNode,HashSet<Pair<Integer,Integer>>>
                                                                nodeAutomataIDs;
    private int patternAutomataCounter; 
}
