/**
 * @file XMLErrorRule.java
 * @brief 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;

/**
 * @brief
 *
 * @see
 */
final class XMLErrorRule {

    // package-private section

    XMLErrorRule(final org.dom4j.Element XMLelement)
                                       throws XMLAutomatonSyntaxErrorException {
        description = XMLelement.attribute("desc").getValue().
                                    replaceAll("[ \t]+", " ");
        errorLevel = Integer.decode(XMLelement.attributeValue("level"));
        entryMessage = XMLelement.attributeValue("entry").
                                    replaceAll("[ \t]+"," ");
        beginMessage = XMLelement.attributeValue("begin").
                                    replaceAll("[ \t]+"," ");
        propagMessage = XMLelement.attributeValue("propag").
                                    replaceAll("[ \t]+"," ");
        endMessage = XMLelement.attributeValue("end").
                                    replaceAll("[ \t]+"," ");

        final String byString = XMLelement.attributeValue("by");
        final Triple<String,Vector<String>,Character> bySymbol =
            (byString.isEmpty()) ?
                    new Triple<String,Vector<String>,Character>
                                        ("",new Vector<String>(),'+') :
                    XMLRuleStringParser.parseOneSymbolRuleString(byString);
        if (!byString.isEmpty())
            checkVars(1,-1,bySymbol);

        final String locationVarName =
               (byString.isEmpty()) ? "*" : bySymbol.getSecond().firstElement();

        patternName = bySymbol.getFirst();

        final LinkedList<Triple<String,Vector<String>,Character> > fromList =
            XMLRuleStringParser.parseRuleString(
                XMLelement.attributeValue("from"));
        checkList(fromList);
        checkVars(1,-1,fromList);

        excludedMatchFlags = buildMatchFlags(fromList,'-',locationVarName);
        includedMatchFlags = buildMatchFlags(fromList,'+',locationVarName);
    }

    boolean checkForError(final Collection<AutomatonState> statesCollection,
                          final SimpleAutomatonID simpleID) {
        return checkExcludedStates(statesCollection,simpleID) &&
               checkIncludedStates(statesCollection,simpleID);
    }

    int getErrorLevel() { return errorLevel; }

    String getErrorDescription() { return description; }
    String getErrorEntryMessage() { return entryMessage; }
    String getErrorBeginMessage() { return beginMessage; }
    String getErrorPropagMessage() { return propagMessage; }
    String getErrorEndMessage() { return endMessage; }
    String getPatternName() { return patternName; }

    boolean isExitRule() {
        return getPatternName().isEmpty();
    }

    // private section

    private boolean checkExcludedStates(
                            final Collection<AutomatonState> statesCollection,
                            final SimpleAutomatonID simpleID) {
        for (final AutomatonState state : statesCollection)
            for (int i = 0; i < getExcludedMatchFlags().size(); ++i)
                if (stateMatch(state,
                               getExcludedMatchFlags().get(i).getFirst(),
                               getExcludedMatchFlags().get(i).getSecond(),
                               simpleID))
                    return false;
        return true;
    }

    private boolean checkIncludedStates(
                            final Collection<AutomatonState> statesCollection,
                            final SimpleAutomatonID simpleID) {

        final int numIncluded = getIncludedMatchFlags().size();

        final Vector<Boolean> matchingFlags = new Vector<Boolean>(numIncluded);
        for (int i = 0; i < numIncluded; ++i) matchingFlags.add(false);

        for (AutomatonState state : statesCollection) {
//            if (isExitRule() && state.getContext().size() > 1)
//                continue;
            for (int i = 0; i < numIncluded; ++i)
                if (stateMatch(state,
                               getIncludedMatchFlags().get(i).getFirst(),
                               getIncludedMatchFlags().get(i).getSecond(),
                               simpleID)) {
                    matchingFlags.set(i,true);
                    break;
                }
        }
        
        return !matchingFlags.contains(false);
    }

    private static boolean stateMatch(final AutomatonState state,
                               final String symbol, final Vector<Boolean> flags,
                               final SimpleAutomatonID simpleID) {
        final ComposedAutomatonID composedID = state.getAutomatonID();
        if (!state.getSymbol().equals(symbol) ||
             composedID.getSimpleAutomataIDs().size() != flags.size())
            return false;

        for (int i = 0; i < flags.size(); ++i) {
            final SimpleAutomatonID ithSimpleID =
                    composedID.getSimpleAutomataIDs().get(i);
            if (ithSimpleID.isGlobal() != simpleID.isGlobal())
                return false;
            final boolean ithFlag = flags.get(i);
            if (( ithFlag && !ithSimpleID.equals(simpleID)) ||
                (!ithFlag &&  ithSimpleID.equals(simpleID)) )
                return false;
        }

        return true;
    }

    private static Vector< Pair<String,Vector<Boolean> > >
    buildMatchFlags(
            final LinkedList<Triple<String,Vector<String>,Character> > symbols,
            final char mode, final String locationVarName) {
        final Vector< Pair<String,Vector<Boolean> > > result =
            new Vector< Pair<String,Vector<Boolean> > >();
        for (Triple<String,Vector<String>,Character> symbol : symbols) {
            if (symbol.getThird().equals(mode)) {
                final Vector<Boolean> flags =
                    new Vector<Boolean>(symbol.getSecond().size());
                for (int i = 0; i < symbol.getSecond().size(); ++i)
                   flags.add(symbol.getSecond().get(i).equals(locationVarName));
                result.add(new Pair< String,Vector<Boolean> >
                                                     (symbol.getFirst(),flags));
            }
        }
        return result;
    }

    private static void checkList(
               final LinkedList<Triple<String,Vector<String>,Character> > list)
                                       throws XMLAutomatonSyntaxErrorException {
        if (list.isEmpty())
            throw new XMLAutomatonSyntaxErrorException("Invalid number of " + 
                                       "symbols in rule string. Minimum is 1.");
    }

    private static void checkVars(final int minVars, final int maxVars,
            final LinkedList<Triple<String,Vector<String>,Character> > symbols)
                                       throws XMLAutomatonSyntaxErrorException {
        for (Triple<String,Vector<String>,Character> symbol : symbols)
            checkVars(minVars,maxVars,symbol);
    }

    private static void checkVars(final int minVars, final int maxVars,
                          final Triple<String,Vector<String>,Character> symbol)
                                       throws XMLAutomatonSyntaxErrorException {
        if ((minVars >= 0 && symbol.getSecond().size() < minVars) ||
            (maxVars >= 0 && symbol.getSecond().size() > maxVars) )
                throw new XMLAutomatonSyntaxErrorException(
                                                "Invalid number of variables.");
    }

    private Vector< Pair<String,Vector<Boolean> > > getExcludedMatchFlags() {
        return excludedMatchFlags;
    }

    private Vector< Pair<String,Vector<Boolean> > > getIncludedMatchFlags() {
        return includedMatchFlags;
    }

    private final String description;
    private final int errorLevel;
    private final String entryMessage;
    private final String beginMessage;
    private final String propagMessage;
    private final String endMessage;
    private final String patternName;
    private final Vector< Pair<String,Vector<Boolean> > > excludedMatchFlags;
    private final Vector< Pair<String,Vector<Boolean> > > includedMatchFlags;
}
