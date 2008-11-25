package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Trinity;
import cz.muni.stanse.utils.XMLAlgo;

import java.util.HashMap;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;

final class XMLErrorRule {

    // package-private section

    XMLErrorRule(final org.dom4j.Element XMLelement,
             final HashMap<String,Integer> statesSymbolTable) throws Exception {
        description = XMLAlgo.readValueOfAttribute("desc",XMLelement).
                                    replaceAll("[ \t]+"," ");
        errorLevel = Integer.decode(XMLAlgo.readValueOfAttribute(
                                    "level",XMLelement));
        entryMessage = XMLAlgo.readValueOfAttribute("entry",XMLelement).
                                    replaceAll("[ \t]+"," ");
        beginMessage = XMLAlgo.readValueOfAttribute("begin",XMLelement).
                                    replaceAll("[ \t]+"," ");
        propagMessage = XMLAlgo.readValueOfAttribute("propag",XMLelement).
                                    replaceAll("[ \t]+"," ");
        endMessage = XMLAlgo.readValueOfAttribute("end",XMLelement).
                                    replaceAll("[ \t]+"," ");

        final String byString = XMLAlgo.readValueOfAttribute("by",XMLelement);
        final Trinity<String,Vector<String>,Character> bySymbol =
            (byString.isEmpty()) ?
                    new Trinity<String,Vector<String>,Character>
                                        ("",new Vector<String>(),'+') :
                    XMLRuleStringParser.parseOneSymbolRuleString(byString);
        if (!byString.isEmpty())
            checkVars(1,-1,bySymbol);

        final String locationVarName =
               (byString.isEmpty()) ? "" : bySymbol.getSecond().firstElement();

        patternName = bySymbol.getFirst();

        final LinkedList<Trinity<String,Vector<String>,Character> > fromList =
            XMLRuleStringParser.parseRuleString(
                XMLAlgo.readValueOfAttribute("from",XMLelement));
        checkList(fromList);
        checkVars(1,-1,fromList);

        excludedMatchFlags = buildMatchFlags(fromList,'-',statesSymbolTable,
                                             locationVarName);
        includedMatchFlags = buildMatchFlags(fromList,'+',statesSymbolTable,
                                             locationVarName);
    }

    boolean checkForError(final Collection<AutomatonState> statesCollection,
                                                        final int automatonID) {
        return checkExcludedStates(statesCollection,automatonID) &&
               checkIncludedStates(statesCollection,automatonID);
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

    private static void checkList(
               final LinkedList<Trinity<String,Vector<String>,Character> > list)
                                                              throws Exception {
        if (list.isEmpty())
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "XMLErrorRule.checkList() :: error: " +
                    "XML rule syntax error -> Invalid" +
                    " number of symbols in rule string. Minimum is 1.");
    }

    private static void checkVars(final int minVars, final int maxVars,
            final LinkedList<Trinity<String,Vector<String>,Character> > symbols)
                                                              throws Exception {
        for (Trinity<String,Vector<String>,Character> symbol : symbols)
            checkVars(minVars,maxVars,symbol);
    }

    private static void checkVars(final int minVars, final int maxVars,
                          final Trinity<String,Vector<String>,Character> symbol)
                                                              throws Exception {
        if ((minVars >= 0 && symbol.getSecond().size() < minVars) ||
            (maxVars >= 0 && symbol.getSecond().size() > maxVars) )
                throw new Exception("[stanse/AutomatonChecker] - " +
                            "XMLErrorRule.checkVars() :: error: " +
                            "XML rule syntax error -> Invalid" +
                            " number of variables.");
    }

    private Vector< Pair<Integer,Vector<Boolean> > > getExcludedMatchFlags() {
        return excludedMatchFlags;
    }

    private Vector< Pair<Integer,Vector<Boolean> > > getIncludedMatchFlags() {
        return includedMatchFlags;
    }

    private final String description;
    private final int errorLevel;
    private final String entryMessage;
    private final String beginMessage;
    private final String propagMessage;
    private final String endMessage;
    private final String patternName;
    private final Vector< Pair<Integer,Vector<Boolean> > > excludedMatchFlags;
    private final Vector< Pair<Integer,Vector<Boolean> > > includedMatchFlags;
}
