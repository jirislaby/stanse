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
import cz.muni.stanse.codestructures.CFGsNavigator;
import cz.muni.stanse.codestructures.PassingSolver;
import cz.muni.stanse.codestructures.builders.XMLLinearizeASTElement;
import cz.muni.stanse.codestructures.traversal.CFGvisitor;
import cz.muni.stanse.utils.xmlpatterns.XMLPattern;
import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Make;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Element;

final class PatternLocationCreator extends CFGvisitor {
	private void checkMatchingConflict(final Element element,
			final LinkedList<Pair<XMLPattern, SimpleAutomatonID>> matchings) {
		if (!logger.isDebugEnabled())
			return;

		boolean same = true;
		final Set<String> names = new HashSet<String>();
		names.add(matchings.getFirst().getFirst().getName());
		for (final Pair<XMLPattern,SimpleAutomatonID> match : matchings)
			if (names.add(match.getFirst().getName())) {
				same = false;
				break;
			}
		if (!same) {
			logger.debug("This code:");
			logger.debug(element.asXML());
			logger.debug("can be matched by more than one " +
				"distinct rule:");
			for (final Pair<XMLPattern,SimpleAutomatonID> match :
					matchings) {
				final XMLPattern pat = match.getFirst();
				logger.debug(pat.getName() + ": " +
					pat.getPatternXMLelement().asXML());
			}
		}
	}

    @Override
    public boolean visit(final CFGNode node, final Element element) {
        final LinkedList<Pair<XMLPattern,SimpleAutomatonID>>
            matchings = new LinkedList<Pair<XMLPattern,SimpleAutomatonID>>();
        for (XMLPattern pattern : getXMLAutomatonDefinition().getXMLpatterns()){
            final Pair<Boolean,XMLPatternVariablesAssignment>
                matchResult = pattern.matchesNode(node, aliasResolver);
            if (matchResult.getFirst()) {
                final XMLPatternVariablesAssignment assign =
                        matchResult.getSecond();
                final boolean isGlobal = isGlobalAssignement(assign);
                matchings.add(Pair.make(pattern,
                                       new SimpleAutomatonID(assign,isGlobal)));
            }
        }

	if (matchings.size() > 1)
		checkMatchingConflict(element, matchings);

        if (!matchings.isEmpty()) {
            final PatternLocation newLocation =
                    createCommonPatternLocation(node,matchings);
            getNodeLocationDictionary().put(node,Pair.make(newLocation,
                                                           newLocation));
            if (matchings.getFirst().getFirst().isConstructive())
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

    PatternLocationCreator(final CFGHandle cfg,
                           final XMLAutomatonDefinition XMLdefinition,
                           final CFGsNavigator navigator,
                           final AliasResolver aliasResolver) {
        super();
        automatonDefinition = XMLdefinition;
        nodeLocationDictionary = new HashMap<CFGNode,Pair<PatternLocation,
                                                          PatternLocation>>();
        automataIDs = new HashSet<SimpleAutomatonID>();
        this.navigator = navigator;
        this.cfg = cfg;
        this.aliasResolver = aliasResolver;

        createStartEndPatternLocations();
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

    private void createStartEndPatternLocations() {
        final PatternLocation startLocation =
                    createRuleLessPatternLocation(getCfg().getStartNode());
        getNodeLocationDictionary().put(getCfg().getStartNode(),
                                        Pair.make(startLocation,startLocation));

        final PatternLocation endLocation =
                    createRuleLessPatternLocation(getCfg().getEndNode());
        getNodeLocationDictionary().put(getCfg().getEndNode(),
                                        Pair.make(endLocation,endLocation));
    }

    private boolean
    isGlobalAssignement(final XMLPatternVariablesAssignment assignment) {
        final SimpleAutomatonID id = new SimpleAutomatonID(assignment,false);

        for (String paramName : getCfg().getParams()){
            for (final String var : id.getVarsAssignment())
                if (var.contains(paramName))
                    return false;
        }
        for (final String var : id.getVarsAssignment()) {
            final String varName = PassingSolver.parseRootVariableName(var);
            if (!getCfg().isSymbolLocal(varName))
                return true;
        }
        return false;
    }

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return automatonDefinition;
    }

    private CFGsNavigator getNavigator() {
        return navigator;
    }

    private CFGHandle getCfg() {
        return cfg;
    }

    private final XMLAutomatonDefinition automatonDefinition;
    private final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                            nodeLocationDictionary;
    private final HashSet<SimpleAutomatonID> automataIDs;
    private final CFGsNavigator navigator;
    private final CFGHandle cfg;
    private final AliasResolver aliasResolver;
    private final static Logger logger =
	    Logger.getLogger(PatternLocationCreator.class);
}
