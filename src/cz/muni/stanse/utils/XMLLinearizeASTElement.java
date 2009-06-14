package cz.muni.stanse.utils;

import java.util.List;
import java.util.LinkedList;

import org.dom4j.Element;

public final class XMLLinearizeASTElement {

    // public section

    @SuppressWarnings("unchecked")
    public static LinkedList<Element> functionCall(final Element elem) {
        LinkedList<Element> result = voidFunctionCall(elem);
        if (result != null)
            return result;
        result = assignFunctionCall(elem);
        return (result != null) ? tail(result) : null;
    }

    @SuppressWarnings("unchecked")
    public static LinkedList<Element> voidFunctionCall(final Element elem) {
        return elem.getName().equals("functionCall") ?
                new LinkedList<Element>((List<Element>)elem.elements()) : null;
    }

    @SuppressWarnings("unchecked")
    public static LinkedList<Element> assignFunctionCall(final Element elem) {
        return (elem.getName().equals("assignExpression") &&
                elem.elements().size() == 2 &&
                ((Element)elem.elements().get(1)).getName().
                                                equals("functionCall")) ?
                    cons((Element)elem.elements().get(0),
                        ((Element)elem.elements().get(1)).elements()) :
                    null;
    }

    public static Element functionRet(final Element elem) {
        return (elem.getName().equals("returnStatement") &&
                elem.elements().size() == 1) ?
                    (Element)elem.elements().get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public static LinkedList<Element> functionDeclaration(final Element elem) {
        final Element fnDecl = elem.getName().equals("declarator") ? elem :
                                (Element)elem.selectSingleNode(".//declarator");
        if (fnDecl == null)
            return null;
        final LinkedList<Element> result = new LinkedList<Element>();
        result.add((Element)fnDecl.selectSingleNode(".//id"));
        for (final Element param : (List<Element>)((Element)
                         fnDecl.selectSingleNode(".//functionDecl")).elements())
            result.add((Element)param.selectSingleNode(".//id"));
        return result;
    }

    // private section

    private static <T> LinkedList<T> cons(final T v, final List<T> l) {
        final LinkedList<T> result = new LinkedList<T>(l);
        result.add(v);
        return result;
    }

    private static <T> LinkedList<T> tail(final List<T> l) {
        return new LinkedList<T>(l.subList(1,l.size()));
    }

    private XMLLinearizeASTElement() {
    }
}
