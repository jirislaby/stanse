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

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    XMLErrorRule(final org.dom4j.Element XMLelement)
                                       throws XMLAutomatonSyntaxErrorException {
        description = XMLelement.attribute("desc").getValue().
                                    replaceAll("[ \t]+", " ");
        errorLevel = Integer.decode(XMLelement.attribute("level").getValue());
        entryMessage = XMLelement.attribute("entry").getValue().
                                    replaceAll("[ \t]+"," ");
        beginMessage = XMLelement.attribute("begin").getValue().
                                    replaceAll("[ \t]+"," ");
        propagMessage = XMLelement.attribute("propag").getValue().
                                    replaceAll("[ \t]+"," ");
        endMessage = XMLelement.attribute("end").getValue().
                                    replaceAll("[ \t]+"," ");

        final String byString = XMLelement.attribute("by").getValue();
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
                XMLelement.attribute("from").getValue());
        checkList(fromList);
        checkVars(1,-1,fromList);

        excludedMatchFlags = buildMatchFlags(fromList,'-',locationVarName);
        includedMatchFlags = buildMatchFlags(fromList,'+',locationVarName);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    boolean checkForError(final Collection<AutomatonState> statesCollection,
                          final SimpleAutomatonID simpleID) {
        return checkExcludedStates(statesCollection,simpleID) &&
               checkIncludedStates(statesCollection,simpleID);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    int getErrorLevel() { return errorLevel; }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorDescription() { return description; }
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorEntryMessage() { return entryMessage; }
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorBeginMessage() { return beginMessage; }
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorPropagMessage() { return propagMessage; }
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getErrorEndMessage() { return endMessage; }
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getPatternName() { return patternName; }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
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

        for (int i = 0; i < flags.size(); ++i)
            if (( flags.get(i) &&
                !composedID.getSimpleAutomataIDs().get(i).equals(simpleID))||
                (!flags.get(i) &&
                composedID.getSimpleAutomataIDs().get(i).equals(simpleID))  )
                return false;

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
