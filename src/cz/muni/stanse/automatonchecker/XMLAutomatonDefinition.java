/**
 * @file XMLAutomatonDefinition.java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.XMLPattern;
import java.util.Vector;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;

final class XMLAutomatonDefinition {

    // package-private section

    XMLAutomatonDefinition(final Element XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        Element desc = (Element)XMLdefinition.selectSingleNode("description");
        automatonName = desc.attribute("name").getValue();
        automatonDesc = desc.attribute("desc").getValue();

        Element start = (Element)XMLdefinition.selectSingleNode("start");
        startSymbol = start.attribute("state").getValue();

        XMLpatterns = buildXMLPatterns(XMLdefinition);
        XMLtransitionRules = buildXMLTransitionRules(XMLdefinition);
        XMLerrorRules = buildXMLErrorRules(XMLdefinition);

        patternTransitionRulesDictionary=buildPatternTransitionRulesDictionary(
                                                XMLpatterns,XMLtransitionRules);
        patternErrorRulesDictionary =
            buildPatternErrorRulesDictionary(XMLpatterns,XMLerrorRules);
    }

    String getAutomatonName() {
        return automatonName;
    }

    String getAutomatonDescription() {
        return automatonDesc;
    }

    String getStartSymbol() {
        return startSymbol;
    }

    Vector<XMLPattern> getXMLpatterns() {
        return XMLpatterns;
    }

    LinkedList<XMLTransitionRule> getXMLtransitionRulesForPattern(
                                                     final XMLPattern pattern) {
    	return patternTransitionRulesDictionary.get(pattern);
    }

    LinkedList<XMLErrorRule> getXMLerrorRulesForPattern(
                                                     final XMLPattern pattern) {
        return patternErrorRulesDictionary.get(pattern);
    }

    LinkedList<XMLErrorRule> getExitErrorRules() {
        final LinkedList<XMLErrorRule> result = new LinkedList<XMLErrorRule>();
        for (XMLErrorRule rule : XMLerrorRules)
            if (rule.isExitRule())
                result.add(rule);
        return result;
    }

    // private section

    private static Vector<XMLPattern>
    buildXMLPatterns(final org.dom4j.Element XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final List patternNodes = XMLdefinition.selectNodes("//pattern");
        if (patternNodes.isEmpty())
            throw new XMLAutomatonSyntaxErrorException(
                            "XML document '" + XMLdefinition.getName() +
                            "' error: No pattern XML node was found.");
        final Vector<XMLPattern> XMLpatterns =
            new Vector<XMLPattern>(patternNodes.size());
        for (final Iterator iter = patternNodes.iterator(); iter.hasNext(); )
            XMLpatterns.add(new XMLPattern((org.dom4j.Element)iter.next()));
        return XMLpatterns;
    }

    private static Vector<XMLTransitionRule>
    buildXMLTransitionRules(final org.dom4j.Element XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final List patternNodes = XMLdefinition.selectNodes("//transition");
        if (patternNodes.isEmpty())
            throw new XMLAutomatonSyntaxErrorException(
                            "XML document '" + XMLdefinition.getName() +
                            "' error: No transition rule XML node was found.");
        final Vector<XMLTransitionRule> XMLtransitionRules =
            new Vector<XMLTransitionRule>(patternNodes.size());
        for (final Iterator iter = patternNodes.iterator(); iter.hasNext(); )
            XMLtransitionRules.add(new XMLTransitionRule(
                                               (org.dom4j.Element)iter.next()));
        return XMLtransitionRules;
    }

    private static Vector<XMLErrorRule>
    buildXMLErrorRules(final org.dom4j.Element XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        final List patternNodes = XMLdefinition.selectNodes("//error");
        if (patternNodes.isEmpty())
            throw new XMLAutomatonSyntaxErrorException(
                        "XML document '" + XMLdefinition.getName() +
                        "' error: No error rule XML node was found.");
        final Vector<XMLErrorRule> XMLerrorRules =
            new Vector<XMLErrorRule>(patternNodes.size());
        for (final Iterator iter = patternNodes.iterator(); iter.hasNext(); )
            XMLerrorRules.add(new XMLErrorRule((org.dom4j.Element)iter.next()));
        return XMLerrorRules;
    }

    private static HashMap< XMLPattern,LinkedList<XMLTransitionRule> >
    buildPatternTransitionRulesDictionary(final Vector<XMLPattern> XMLpatterns,
                           final Vector<XMLTransitionRule> XMLtransitionRules) {
        final HashMap< XMLPattern,LinkedList<XMLTransitionRule> > dictionary =
            new HashMap< XMLPattern,LinkedList<XMLTransitionRule> >();
        for (int i = 0; i < XMLpatterns.size(); ++i) {
            final LinkedList<XMLTransitionRule> patternTransitions =
                new LinkedList<XMLTransitionRule>();
            for (final XMLTransitionRule rule : XMLtransitionRules)
                if (rule.getPatternName().equals(XMLpatterns.get(i).getName()))
                    patternTransitions.add(rule);
            dictionary.put(XMLpatterns.get(i),patternTransitions);
        }
        return dictionary;
    }

    private static HashMap<XMLPattern,LinkedList<XMLErrorRule> >
    buildPatternErrorRulesDictionary(final Vector<XMLPattern> XMLpatterns,
                                     final Vector<XMLErrorRule> XMLerrorRules) {
        final HashMap<XMLPattern,LinkedList<XMLErrorRule> > dictionary =
            new HashMap<XMLPattern,LinkedList<XMLErrorRule> >();
        for (int i = 0; i < XMLpatterns.size(); ++i) {
            final LinkedList<XMLErrorRule> patternErrors =
                new LinkedList<XMLErrorRule>();
            for (final XMLErrorRule rule : XMLerrorRules)
                if (rule.getPatternName().equals(XMLpatterns.get(i).getName()))
                    patternErrors.add(rule);
            dictionary.put(XMLpatterns.get(i),patternErrors);
        }
        return dictionary;
    }

    private final String automatonName;
    private final String automatonDesc;
    private final String startSymbol;
    private final Vector<XMLPattern> XMLpatterns;
    private final Vector<XMLTransitionRule> XMLtransitionRules;
    private final Vector<XMLErrorRule> XMLerrorRules;
    private final HashMap<XMLPattern,LinkedList<XMLTransitionRule> >
    			patternTransitionRulesDictionary;
    private final HashMap<XMLPattern,LinkedList<XMLErrorRule> >
                patternErrorRulesDictionary;
}
