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
        return (!argument.contains(callMapping.getFirst())) ?
                     null : simplify(argument.replace(callMapping.getFirst(),
                                                      callMapping.getSecond()));
    }

    public static String simplify(final String argument) {
        return argument.replace("* &","")
                       .replace("->",". *");
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
