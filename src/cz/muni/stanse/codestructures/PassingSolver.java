package cz.muni.stanse.codestructures;

import cz.muni.stanse.utils.Pair;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;

public final class PassingSolver {

    // public section

    public static String makeArgument(final CFGNode.Operand op) {
	StringBuilder result = new StringBuilder();
        switch (op.type)
        {
        case varptr:
            result.append("& ");

        case function:
        case member:
        case constant:
        case varval:
            result.append((String)op.id);
            break;

        case nodeval:
            result.append(makeArgument((CFGNode)op.id));
            break;
        }
        return result.toString();
    }

    public static String makeArgument(final CFGNode node) {
        if (node.getNodeType() == null)
            return makeArgument(node.getElement());

        assert node.getNodeType().equals("call");

        StringBuilder result = new StringBuilder();
        for (CFGNode.Operand op : node.getOperands())
        {
            if (result.length() != 0)
        	result.append(' ');
            result.append(makeArgument(op));
        }
        return result.toString();
    }

    public static String makeArgument(final Element elem) {
        assert elem != null;

        StringBuilder result = new StringBuilder(parseElement(elem));
        for (Element e: (List<Element>)elem.elements())
            result.append(' ').append(makeArgument(e));
        return result.toString();
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
        if (argument.isEmpty() || callMapping.getFirst().isEmpty())
            return null;
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
        if (elem.getName().equals("stringConst")) return '"'+elem.getText()+'"';
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
