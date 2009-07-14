package cz.muni.stanse.utils;

import java.util.LinkedList;

import org.dom4j.Element;

public final class PassingSolver {

    // public section

    public static String makeArgument(final Element elem) {
        String result = parseElement(elem);
        for (Object obj : elem.elements())
            result += (' ' + makeArgument((Element)obj));
        return result;
    }

    public static String
    pass(final String argument, final LinkedList<Pair<String,String>>
                                                                  callMapping) {
        boolean wasTransformed = false;
        String result = argument;
        for (final Pair<String,String> map : callMapping) {
            final String paramTransformation = pass(result,map);
            if (paramTransformation != null) {
                wasTransformed = true;
                result = paramTransformation;
            }
        }
        return (wasTransformed) ? result : null;
    }

    public static String
    pass(final String argument, final Pair<String,String> callMapping) {
        if (argument.contains(callMapping.getFirst()))
            return simplify(argument.replace(callMapping.getFirst(),
                                             callMapping.getSecond()));
        if (callMapping.getFirst().contains(argument) &&
            callMapping.getFirst().charAt(0) == '&')
            return "* " + callMapping.getSecond();
        return null;
    }

    public static String simplify(final String argument) {
        return argument.replace("* & ","")
                       .replace("->",". *");
    }

    public static String parseRootVariableName(final String argument) {
        for (int i = 0; i < argument.length(); ++i)
            if (argument.charAt(i) == '_' ||
                    Character.isLetter(argument.charAt(i))) {
                int j = i+1;
                for ( ; j < argument.length() && argument.charAt(j) != ' '; )
                    ++j;
                return argument.substring(i,j);
            }
        return null;
    }

    // private section

    private static String parseElement(final Element elem) {
        if (elem.getName().equals("id")) return elem.getText();
        if (elem.getName().equals("member")) return elem.getText();
        if (elem.getName().equals("intConst")) return elem.getText();
        if (elem.getName().equals("addrExpression")) return "&";
        if (elem.getName().equals("derefExpression")) return "*";
        if (elem.getName().equals("dotExpression")) return ".";
        if (elem.getName().equals("arrowExpression")) return ". *";
        if (elem.getName().equals("arrayAccess")) return "[]";
        if (elem.getName().equals("functionCall")) return "(" +
                                                 (elem.elements().size()  - 1) +
                                                          ")";
        return "";
    }

    private PassingSolver() {
    }
}
