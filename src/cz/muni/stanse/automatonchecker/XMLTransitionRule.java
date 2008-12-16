/**
 * @brief
 * 
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.HashMap;
import java.util.Vector;

/**
 * @brief
 *
 * @see
 */
final class XMLTransitionRule {

    // package-private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    XMLTransitionRule(final org.dom4j.Element XMLtransitionElement,
                      final HashMap<String,Integer> statesSymbolTable)
                                       throws XMLAutomatonSyntaxErrorException {
        final Triple<String,Vector<String>,Character> fromSymbol =
            XMLRuleStringParser.parseOneSymbolRuleString(
                XMLtransitionElement.attribute("from").getValue());
        checkMode(fromSymbol.getThird());
        checkVars(1,-1,fromSymbol.getSecond().size());
        inSymbolID = statesSymbolTable.get(fromSymbol.getFirst());

        final String toString = XMLtransitionElement.attribute("to").getValue();
        final Triple<String,Vector<String>,Character> toSymbol =
            (toString.isEmpty()) ?
                new Triple<String,Vector<String>,Character>
                                            ("",new Vector<String>(),'+') :
                XMLRuleStringParser.parseOneSymbolRuleString(toString);
        checkMode(toSymbol.getThird());
        if (!toString.isEmpty())
            checkVars(1,-1,toSymbol.getSecond().size());
        final Integer outSymbol = statesSymbolTable.get(toSymbol.getFirst());
        outSymbolID = (outSymbol == null) ? -1 : (int)outSymbol;

        final Triple<String,Vector<String>,Character> bySymbol =
            XMLRuleStringParser.parseOneSymbolRuleString(
                XMLtransitionElement.attribute("by").getValue());
        checkMode(bySymbol.getThird());
        checkVars(1,1,bySymbol.getSecond().size());
        matchFlags = buildMatchFlags(fromSymbol.getSecond(),
                                     bySymbol.getSecond().firstElement());
        matchOutIndices = buildMatchOutIndices(fromSymbol.getSecond(),
                                           toSymbol.getSecond(),
                                           bySymbol.getSecond().firstElement());
        patternName = bySymbol.getFirst();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    String getPatternName() {
        return patternName;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    Pair<Boolean,AutomatonState>
    transformAutomatonState(final AutomatonState state, final int automatonID) {
        if (state.getSymbolID() == getInSymbolID() &&
                matchIDsMatchesLocationID(state.getAutomatonIDs(),automatonID))
            return new Pair<Boolean,AutomatonState>(
                         true,buildResultState(state.getCFG(),
                                           state.getAutomatonIDs(),automatonID));
        return new Pair<Boolean,AutomatonState>(false,null);
    }

    // private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private boolean matchIDsMatchesLocationID(
                    final Vector<Integer> stateMatchIDs, final int locationID) {
        if (stateMatchIDs.size() != getMatchFlags().size())
            return false;
        for (int i = 0; i < stateMatchIDs.size(); ++i)
            if (( getMatchFlags().get(i) && stateMatchIDs.get(i) != locationID) ||
                (!getMatchFlags().get(i) && stateMatchIDs.get(i) == locationID) )
                return false;
        return true;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private AutomatonState buildResultState(
                        final cz.muni.stanse.parser.CFG cfg,
                        final Vector<Integer> matchIDs, final int locationID) {
        if (getOutSymbolID() == -1)
            return null;
        final Vector<Integer> outMatchIDs =
            new Vector<Integer>(getMatchOutIndices().size());
        for (int i = 0; i < getMatchOutIndices().size(); ++i) {
            assert(getMatchOutIndices().get(i) < matchIDs.size());
            outMatchIDs.add((getMatchOutIndices().get(i) == -1) ? locationID :
                                    matchIDs.get(getMatchOutIndices().get(i)));
        }
        return new AutomatonState(cfg,getOutSymbolID(),outMatchIDs);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static Vector<Boolean> buildMatchFlags(final Vector<String> matchIDs,
                                                 final String locationVarName) {
        final Vector<Boolean> matchFlags = new Vector<Boolean>(matchIDs.size());
        for (int i = 0; i < matchIDs.size(); ++i)
            matchFlags.add(matchIDs.get(i).equals(locationVarName));
        return matchFlags;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static Vector<Integer> buildMatchOutIndices(
                                final Vector<String> fromVarNames,
                                final Vector<String> toVarNames,
                                final String locationVarName)
                                       throws XMLAutomatonSyntaxErrorException {
        final Vector<Integer> matchIndices =
        new Vector<Integer>(toVarNames.size()); 
        for (int i = 0; i < toVarNames.size(); ++i)
            matchIndices.add((toVarNames.get(i).equals(locationVarName)) ?
                              -1 : findVarName(fromVarNames,toVarNames.get(i)));

        return matchIndices;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static int findVarName(final Vector<String> varNames,
            final String searchedName) throws XMLAutomatonSyntaxErrorException {
        final int index = varNames.indexOf(searchedName);
        if (index != -1)
           return index;
        throw new XMLAutomatonSyntaxErrorException("Variable '" +
                searchedName + "' in attribute 'to' cannot be found in 'from'" +
                " attribute of the transition [possibilities are: " + varNames);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static void checkMode(final char mode)
                                       throws XMLAutomatonSyntaxErrorException {
        if (mode != '+')
            throw new XMLAutomatonSyntaxErrorException(
                                "Invalid mode of symbol. " +
                                "Mode can only be nothing or symbol '+'.");
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @see
     */
    private static void checkVars(final int minVars, final int maxVars,
                    final int numVars) throws XMLAutomatonSyntaxErrorException {
        if ((minVars >= 0 && numVars < minVars) ||
            (maxVars >= 0 && numVars > maxVars) )
            throw new XMLAutomatonSyntaxErrorException(
                                                "Invalid number of variables.");
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private int getInSymbolID() {
        return inSymbolID;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private int getOutSymbolID() {
        return outSymbolID;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private Vector<Boolean> getMatchFlags() {
        return matchFlags;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private Vector<Integer> getMatchOutIndices() {
        return matchOutIndices;
    }

    /**
     * @brief
     */
    private final String patternName;
    /**
     * @brief
     */
    private final int inSymbolID;
    /**
     * @brief
     */
    private final int outSymbolID;
    /**
     * @brief
     */
    private final Vector<Boolean> matchFlags;
    /**
     * @brief
     */
    private final Vector<Integer> matchOutIndices;
}
