package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.XMLAlgo;

import java.util.Vector;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

final class XMLAutomatonDefinition {

    // package-private section

    XMLAutomatonDefinition(final org.dom4j.Element XMLdefinition)
                                                              throws Exception {
        automatonName =
               XMLAlgo.readValueOfAttribute("description","name",XMLdefinition);
        automatonDesc =
               XMLAlgo.readValueOfAttribute("description","desc",XMLdefinition);
        final String startSymbolName =
               XMLAlgo.readValueOfAttribute("start","state",XMLdefinition);

        final HashMap<String,Integer> statesSymbolTable =
            buildStatesDictionary(XMLdefinition);

        if (!statesSymbolTable.containsKey(startSymbolName))
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "XMLAutomatonDefinition.XMLAutomatonDefinition() :: " +
                    "XML document '" + XMLdefinition.getName() +
                    "' error: Automaton start symbol '" + startSymbolName +
                    "' is not member of set of all symbols: " +
                    statesSymbolTable.keySet());
        
        startSymbol = statesSymbolTable.get(startSymbolName);

        XMLpatterns = buildXMLPatterns(XMLdefinition);
        XMLtransitionRules = buildXMLTransitionRules(XMLdefinition,
                                                     statesSymbolTable);
        XMLerrorRules = buildXMLErrorRules(XMLdefinition,statesSymbolTable);

        patternTransitionRulesDictionary =
            buildPatternTransitionRulesDictionary(XMLpatterns,XMLtransitionRules);
        patternErrorRulesDictionary =
            buildPatternErrorRulesDictionary(XMLpatterns,XMLerrorRules);
    }

    String getAutomatonName() {
        return automatonName;
    }

    String getAutomatonDescription() {
        return automatonDesc;
    }

    int getStartSymbol() {
        return startSymbol;
    }

    Vector<XMLPattern> getXMLpatterns() {
        return XMLpatterns;
    }

    LinkedList<XMLTransitionRule> getXMLtransitionRulesForPattern(
                                                       final int patternIndex) {
    	return patternTransitionRulesDictionary.get(patternIndex);
    }

    LinkedList<XMLErrorRule> getXMLerrorRulesForPattern(
                                                       final int patternIndex) {
        return patternErrorRulesDictionary.get(patternIndex);
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
    buildXMLPatterns(final org.dom4j.Element XMLdefinition) throws Exception {
        final List patternNodes = XMLdefinition.selectNodes("//pattern");
        if (patternNodes.isEmpty())
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "XMLAutomatonDefinition.buildXMLPatterns() :: " +
                    "XML document '" + XMLdefinition.getName() +
                    "' error: No pattern XML node was found.");
        final Vector<XMLPattern> XMLpatterns =
            new Vector<XMLPattern>(patternNodes.size());
        for (final Iterator iter = patternNodes.iterator(); iter.hasNext(); )
            XMLpatterns.add(new XMLPattern((org.dom4j.Element)iter.next()));
        return XMLpatterns;
    }

    private static final HashMap<String,Integer>
    buildStatesDictionary(final org.dom4j.Element XMLdefinition)
                                                              throws Exception {
        final HashMap<String,Integer> statesSymbolTable =
            new HashMap<String,Integer>();

        int stateCounter = 0;
        final List stateAttributes = XMLdefinition.selectNodes(
                                       "//transition/@from | //transition/@to");
        for (final Iterator iter = stateAttributes.iterator(); iter.hasNext(); ) {
            final String stateString =
                                  ((org.dom4j.Attribute)iter.next()).getValue();
            if (stateString.length() > 0) {
                final String stateSymbol = XMLRuleStringParser.
                               parseOneSymbolRuleString(stateString).getFirst();
                if (!statesSymbolTable.containsKey(stateSymbol))
                    statesSymbolTable.put(stateSymbol,++stateCounter);
            }
        }

        return statesSymbolTable;
    }

    private static Vector<XMLTransitionRule>
    buildXMLTransitionRules(final org.dom4j.Element XMLdefinition,
                            final HashMap<String,Integer> statesSymbolTable)
                                                              throws Exception {
        final List patternNodes = XMLdefinition.selectNodes("//transition");
        if (patternNodes.isEmpty())
            throw new Exception("[stanse/AutomatonChecker] - " +
                "XMLAutomatonDefinition.buildXMLTransitionRules() :: " +
                "XML document '" + XMLdefinition.getName() +
                "' error: No transition rule XML node was found.");
        final Vector<XMLTransitionRule> XMLtransitionRules =
            new Vector<XMLTransitionRule>(patternNodes.size());
        for (final Iterator iter = patternNodes.iterator(); iter.hasNext(); )
            XMLtransitionRules.add(new XMLTransitionRule(
                                                (org.dom4j.Element)iter.next(),
                                                statesSymbolTable));
        return XMLtransitionRules;
    }

    private static Vector<XMLErrorRule>
    buildXMLErrorRules(final org.dom4j.Element XMLdefinition,
                       final HashMap<String,Integer> statesSymbolTable)
                                                              throws Exception {
        final List patternNodes = XMLdefinition.selectNodes("//error");
        if (patternNodes.isEmpty())
            throw new Exception("[stanse/AutomatonChecker] - " +
                "XMLAutomatonDefinition.buildXMLErrorRules() :: " +
                "XML document '" + XMLdefinition.getName() +
                "' error: No error rule XML node was found.");
        final Vector<XMLErrorRule> XMLerrorRules =
            new Vector<XMLErrorRule>(patternNodes.size());
        for (final Iterator iter = patternNodes.iterator(); iter.hasNext(); )
            XMLerrorRules.add(new XMLErrorRule((org.dom4j.Element)iter.next(),
                                               statesSymbolTable));
        return XMLerrorRules;
    }

    private static HashMap< Integer,LinkedList<XMLTransitionRule> >
    buildPatternTransitionRulesDictionary(final Vector<XMLPattern> XMLpatterns,
                           final Vector<XMLTransitionRule> XMLtransitionRules) {
        final HashMap< Integer,LinkedList<XMLTransitionRule> > dictionary =
            new HashMap< Integer,LinkedList<XMLTransitionRule> >();
        for (int i = 0; i < XMLpatterns.size(); ++i) {
            final LinkedList<XMLTransitionRule> patternTransitions =
                new LinkedList<XMLTransitionRule>();
            for (final XMLTransitionRule rule : XMLtransitionRules)
                if (rule.getPatternName().equals(XMLpatterns.get(i).getName()))
                    patternTransitions.add(rule);
            dictionary.put(i,patternTransitions);
        }
        return dictionary;
    }

    private static HashMap< Integer,LinkedList<XMLErrorRule> >
    buildPatternErrorRulesDictionary(final Vector<XMLPattern> XMLpatterns,
                                     final Vector<XMLErrorRule> XMLerrorRules) {
        final HashMap< Integer,LinkedList<XMLErrorRule> > dictionary =
            new HashMap< Integer,LinkedList<XMLErrorRule> >();
        for (int i = 0; i < XMLpatterns.size(); ++i) {
            final LinkedList<XMLErrorRule> patternErrors =
                new LinkedList<XMLErrorRule>();
            for (final XMLErrorRule rule : XMLerrorRules)
                if (rule.getPatternName().equals(XMLpatterns.get(i).getName()))
                    patternErrors.add(rule);
            dictionary.put(i,patternErrors);
        }
        return dictionary;
    }

    private final String automatonName;
    private final String automatonDesc;
    private final int startSymbol;
    private final Vector<XMLPattern> XMLpatterns;
    private final Vector<XMLTransitionRule> XMLtransitionRules;
    private final Vector<XMLErrorRule> XMLerrorRules;
    private final HashMap< Integer,LinkedList<XMLTransitionRule> >
    			patternTransitionRulesDictionary;
    private final HashMap< Integer,LinkedList<XMLErrorRule> >
                patternErrorRulesDictionary;
}
