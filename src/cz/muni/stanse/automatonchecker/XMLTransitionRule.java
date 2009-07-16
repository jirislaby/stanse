/**
 * @file XMLTransitionRule.java
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

/**
 * @brief
 *
 * @see
 */
final class XMLTransitionRule {

    // package-private section

    XMLTransitionRule(final org.dom4j.Element XMLtransitionElement)
                                       throws XMLAutomatonSyntaxErrorException {
        final Triple<String,Vector<String>,Character> fromSymbol =
            XMLRuleStringParser.parseOneSymbolRuleString(
                XMLtransitionElement.attribute("from").getValue());
        checkMode(fromSymbol.getThird());
        checkVars(1,-1,fromSymbol.getSecond().size());
        inSymbol = fromSymbol.getFirst();

        final String toText = XMLtransitionElement.attribute("to").getValue();
        final Triple<String,Vector<String>,Character> toSymbol =
            (toText.isEmpty()) ?
                new Triple<String,Vector<String>,Character>
                                            (null,new Vector<String>(),'+') :
                XMLRuleStringParser.parseOneSymbolRuleString(toText);
        checkMode(toSymbol.getThird());
        if (!toText.isEmpty())
            checkVars(1,-1,toSymbol.getSecond().size());
        outSymbol = toSymbol.getFirst();

        final Triple<String,Vector<String>,Character> bySymbol =
            XMLRuleStringParser.parseOneSymbolRuleString(
                XMLtransitionElement.attribute("by").getValue());
        assert(bySymbol.getSecond().size() < 2);
        checkMode(bySymbol.getThird());
        checkVars(1,1,bySymbol.getSecond().size());
        matchFlags = buildMatchFlags(fromSymbol.getSecond(),
                                     bySymbol.getSecond().firstElement());
        matchOutIndices = buildMatchOutIndices(fromSymbol.getSecond(),
                                           toSymbol.getSecond(),
                                           bySymbol.getSecond().firstElement());
        patternName = bySymbol.getFirst();
    }

    String getPatternName() {
        return patternName;
    }

    Pair<Boolean,AutomatonState>
    transformAutomatonState(final AutomatonState state,
                            final SimpleAutomatonID simpleID) {
        if (state.getSymbol().equals(getInSymbol()) &&
                isThisRuleApplicable(state.getAutomatonID(),simpleID))
            return new Pair<Boolean,AutomatonState>(true,
                                buildResultState(state,simpleID));
        return new Pair<Boolean,AutomatonState>(false,null);
    }

    // private section

    private boolean isThisRuleApplicable(
                    final ComposedAutomatonID composedID,
                    final SimpleAutomatonID simpleID) {
        if (composedID.getSimpleAutomataIDs().size() != getMatchFlags().size())
            return false;
        for (int i = 0; i < composedID.getSimpleAutomataIDs().size(); ++i)
            if (( getMatchFlags().get(i) &&
                  !composedID.getSimpleAutomataIDs().get(i).equals(simpleID)) ||
                (!getMatchFlags().get(i) &&
                  composedID.getSimpleAutomataIDs().get(i).equals(simpleID))  )
                return false;
        return true;
    }

    private AutomatonState buildResultState(final AutomatonState state,
                                            final SimpleAutomatonID simpleID) {
        final ComposedAutomatonID inAutomatonID = state.getAutomatonID();
        if (getOutSymbol() == null)
            return null;
        final Vector<SimpleAutomatonID> outAutomatonID =
            new Vector<SimpleAutomatonID>(getMatchOutIndices().size());
        for (int i = 0; i < getMatchOutIndices().size(); ++i)
            outAutomatonID.add((getMatchOutIndices().get(i) == -1) ?
                  simpleID : inAutomatonID.getSimpleAutomataIDs()
                                          .get(getMatchOutIndices().get(i)));
        return new AutomatonState(getOutSymbol(),AutomatonStateContextAlgo
                                    .swop(state.getContext(),state.getCFGNode(),
                                      new ComposedAutomatonID(outAutomatonID)));
    }

    private static Vector<Boolean> buildMatchFlags(
                  final Vector<String> automataIDs, final String automatonID) {
        final Vector<Boolean> matchFlags =
            new Vector<Boolean>(automataIDs.size());
        for (int i = 0; i < automataIDs.size(); ++i)
            matchFlags.add(automataIDs.get(i).equals(automatonID));
        return matchFlags;
    }

    private static Vector<Integer> buildMatchOutIndices(
                                final Vector<String> fromAutomataIDs,
                                final Vector<String> toAutomataIDs,
                                final String automatonID)
                                       throws XMLAutomatonSyntaxErrorException {
        final Vector<Integer> matchIndices =
            new Vector<Integer>(toAutomataIDs.size());
        for (int i = 0; i < toAutomataIDs.size(); ++i)
            matchIndices.add((toAutomataIDs.get(i).equals(automatonID)) ? -1 :
                    findAutomatonIDindex(fromAutomataIDs,toAutomataIDs.get(i)));

        return matchIndices;
    }

    private static int findAutomatonIDindex(final Vector<String> automataIDs,
              final String searchedID) throws XMLAutomatonSyntaxErrorException {
        final int index = automataIDs.indexOf(searchedID);
        if (index != -1)
           return index;
        throw new XMLAutomatonSyntaxErrorException("Variable '" + searchedID +
                    "' in attribute 'to' cannot be found in 'from' attribute " +
                    "of the transition [possibilities are: " + automataIDs);
    }

    private static void checkMode(final char mode)
                                       throws XMLAutomatonSyntaxErrorException {
        if (mode != '+')
            throw new XMLAutomatonSyntaxErrorException(
                                "Invalid mode of symbol. " +
                                "Mode can only be nothing or symbol '+'.");
    }

    private static void checkVars(final int minVars, final int maxVars,
                    final int numVars) throws XMLAutomatonSyntaxErrorException {
        if ((minVars >= 0 && numVars < minVars) ||
            (maxVars >= 0 && numVars > maxVars) )
            throw new XMLAutomatonSyntaxErrorException(
                                                "Invalid number of variables.");
    }

    private String getInSymbol() {
        return inSymbol;
    }

    private String getOutSymbol() {
        return outSymbol;
    }

    private Vector<Boolean> getMatchFlags() {
        return matchFlags;
    }

    private Vector<Integer> getMatchOutIndices() {
        return matchOutIndices;
    }

    private final String patternName;
    private final String inSymbol;
    private final String outSymbol;
    private final Vector<Boolean> matchFlags;
    private final Vector<Integer> matchOutIndices;
}
