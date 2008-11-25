package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Trinity;

import java.util.LinkedList;
import java.util.Vector;

final class XMLRuleStringParser {

    // package-private section

    static Trinity<String,Vector<String>,Character>
    parseOneSymbolRuleString(final String string) throws Exception {
        final LinkedList< Trinity<String,Vector<String>,Character> > result =
            parseRuleString(string);
        if (result.size() != 1)
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "XMLRuleStringParser.parseOneSymbolRuleString() :: error:" +
                    " XML rule syntax error -> '" + "attributeName" + "=\"" +
                    string + "\"' must containt exactly one generic" +
                    " symbol");
        return result.getFirst();
    }

    static LinkedList< Trinity<String,Vector<String>,Character> >
    parseRuleString(final String string) throws Exception {
        final LinkedList< Trinity<String,Vector<String>,Character> > result =
            new LinkedList< Trinity<String,Vector<String>,Character> >();
        for (String symbol : splitStringToGenericsSymbolsParts(string))
            result.add(parseGenericSymbol(symbol));
        return result;
    }

    static LinkedList<String>
    splitStringToGenericsSymbolsParts(final String string) throws Exception {
        final String[] splittedStringArray = string.replaceAll("[ \t]+","")
                                                   .replaceAll("\\]","] ")
                                                   .replaceAll("\\] \\[","][")
                                                   .replaceAll(" $","")
                                                   .split(" ");
        final LinkedList<String> result =
            new LinkedList<String>();
        for (int i = 0; i < splittedStringArray.length; ++i)
            result.add(splittedStringArray[i]);
        return result;
    }

    static Trinity<String,Vector<String>,Character>
    parseGenericSymbol(final String stateString) throws Exception {
        Pair<String,String> split = splitStateString(stateString,"\\[");
        String stateSymbol = (Character.isLetter(split.getFirst().charAt(0))) ?
                               split.getFirst() : split.getFirst().substring(1);
        Character mode = (Character.isLetter(split.getFirst().charAt(0))) ?
                               '+' : split.getFirst().charAt(0);
        if (mode != '+' && mode != '-')
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "RuleStringParser.parseGenericSymbol() :: " +
                    "Symbol mode can only be '+' or '-'.");
        final Vector<String> varsSymbols = new Vector<String>();
        while (!split.getSecond().isEmpty()) {
            split = splitStateString(split.getSecond(),"\\][.*^\\[]*\\[|\\]");
            varsSymbols.add(split.getFirst());
        }
        return new Trinity<String,Vector<String>,Character>
                                (stateSymbol,varsSymbols,mode);
    }

    // private section

    private static Pair<String,String> splitStateString(final String stateString,
                                          final String regex) throws Exception {
        if (stateString.length() < 1)
            return new Pair<String,String>("","");
        final String[] splittedStringArray = stateString.split(regex,2);
        if (splittedStringArray == null || splittedStringArray.length != 2)
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "RuleStringParser.splitStateString() :: " +
                    "Cannot read state symbol in state string '" +
                    stateString + "'.");
        if (splittedStringArray[0].length() < 1)
            throw new Exception("[stanse/AutomatonChecker] - " +
                    "RuleStringParser.splitStateString() :: " +
                    "State symbol of state string '" +
                    splittedStringArray[0] + "' is empty.");
        return new Pair<String,String>(splittedStringArray[0],
                                       splittedStringArray[1]);
    }

    private XMLRuleStringParser() {
    }
}
