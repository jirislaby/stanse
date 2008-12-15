/**
 * @brief
 *
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Trinity;

import java.util.HashMap;
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
    XMLErrorRule(final org.dom4j.Element XMLelement,
                 final HashMap<String,Integer> statesSymbolTable)
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
        final Trinity<String,Vector<String>,Character> bySymbol =
            (byString.isEmpty()) ?
                    new Trinity<String,Vector<String>,Character>
                                        ("",new Vector<String>(),'+') :
                    XMLRuleStringParser.parseOneSymbolRuleString(byString);
        if (!byString.isEmpty())
            checkVars(1,-1,bySymbol);

        final String locationVarName =
               (byString.isEmpty()) ? "*" : bySymbol.getSecond().firstElement();

        patternName = bySymbol.getFirst();

        final LinkedList<Trinity<String,Vector<String>,Character> > fromList =
            XMLRuleStringParser.parseRuleString(
                XMLelement.attribute("from").getValue());
        checkList(fromList);
        checkVars(1,-1,fromList);

        excludedMatchFlags = buildMatchFlags(fromList,'-',statesSymbolTable,
                                             locationVarName);
        includedMatchFlags = buildMatchFlags(fromList,'+',statesSymbolTable,
                                             locationVarName);
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
                                                        final int automatonID) {
        return checkExcludedStates(statesCollection,automatonID) &&
               checkIncludedStates(statesCollection,automatonID);
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

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private boolean checkExcludedStates(
                            final Collection<AutomatonState> statesCollection,
                            final int automatonID) {
        for (final AutomatonState state : statesCollection)
            for (int i = 0; i < getExcludedMatchFlags().size(); ++i)
                if (stateMatch(state,
                               getExcludedMatchFlags().get(i).getFirst(),
                               getExcludedMatchFlags().get(i).getSecond(),
                               automatonID))
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
    private boolean checkIncludedStates(
                            final Collection<AutomatonState> statesCollection,
                            final int automatonID) {

        final int numIncluded = getIncludedMatchFlags().size();

        final Vector<Boolean> matchingFlags = new Vector<Boolean>(numIncluded);
        for (int i = 0; i < numIncluded; ++i) matchingFlags.add(false);

        for (AutomatonState state : statesCollection) {
            for (int i = 0; i < numIncluded; ++i)
                if (stateMatch(state,
                               getIncludedMatchFlags().get(i).getFirst(),
                               getIncludedMatchFlags().get(i).getSecond(),
                               automatonID)) {
                    matchingFlags.set(i,true);
                    break;
                }
        }
        
        return !matchingFlags.contains(false);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static boolean stateMatch(final AutomatonState state,
                                      final int symbolID,
                                      final Vector<Boolean> flags,
                                      final int automatonID) {
        if (state.getSymbolID() != symbolID ||
            state.getAutomatonIDs().size() != flags.size())
            return false;

        final Vector<Integer> stateAutomatonIDs = state.getAutomatonIDs();    
        for (int i = 0; i < flags.size(); ++i)
            if (( flags.get(i) && stateAutomatonIDs.get(i) != automatonID) ||
                (!flags.get(i) && stateAutomatonIDs.get(i) == automatonID) )
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
    private static Vector< Pair<Integer,Vector<Boolean> > >
    buildMatchFlags(
            final LinkedList<Trinity<String,Vector<String>,Character> > symbols,
            final char mode,
            final HashMap<String,Integer> statesSymbolTable,
            final String locationVarName) {
        final Vector< Pair<Integer,Vector<Boolean> > > result =
            new Vector< Pair<Integer,Vector<Boolean> > >();
        for (Trinity<String,Vector<String>,Character> symbol : symbols) {
            if (symbol.getThird().equals(mode)) {
                final Vector<Boolean> flags =
                    new Vector<Boolean>(symbol.getSecond().size());
                for (int i = 0; i < symbol.getSecond().size(); ++i)
                   flags.add(symbol.getSecond().get(i).equals(locationVarName));
                result.add(new Pair< Integer,Vector<Boolean> >
                              (statesSymbolTable.get(symbol.getFirst()),flags));
            }
        }
        return result;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static void checkList(
               final LinkedList<Trinity<String,Vector<String>,Character> > list)
                                       throws XMLAutomatonSyntaxErrorException {
        if (list.isEmpty())
            throw new XMLAutomatonSyntaxErrorException("Invalid number of " + 
                                       "symbols in rule string. Minimum is 1.");
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static void checkVars(final int minVars, final int maxVars,
            final LinkedList<Trinity<String,Vector<String>,Character> > symbols)
                                       throws XMLAutomatonSyntaxErrorException {
        for (Trinity<String,Vector<String>,Character> symbol : symbols)
            checkVars(minVars,maxVars,symbol);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private static void checkVars(final int minVars, final int maxVars,
                          final Trinity<String,Vector<String>,Character> symbol)
                                       throws XMLAutomatonSyntaxErrorException {
        if ((minVars >= 0 && symbol.getSecond().size() < minVars) ||
            (maxVars >= 0 && symbol.getSecond().size() > maxVars) )
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
    private Vector< Pair<Integer,Vector<Boolean> > > getExcludedMatchFlags() {
        return excludedMatchFlags;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private Vector< Pair<Integer,Vector<Boolean> > > getIncludedMatchFlags() {
        return includedMatchFlags;
    }

    /**
     * @brief
     */
    private final String description;
    /**
     * @brief
     */
    private final int errorLevel;
    /**
     * @brief
     */
    private final String entryMessage;
    /**
     * @brief
     */
    private final String beginMessage;
    /**
     * @brief
     */
    private final String propagMessage;
    /**
     * @brief
     */
    private final String endMessage;
    /**
     * @brief
     */
    private final String patternName;
    /**
     * @brief
     */
    private final Vector< Pair<Integer,Vector<Boolean> > > excludedMatchFlags;
    /**
     * @brief
     */
    private final Vector< Pair<Integer,Vector<Boolean> > > includedMatchFlags;
}
