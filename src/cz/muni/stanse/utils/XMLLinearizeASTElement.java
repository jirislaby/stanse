package cz.muni.stanse.utils;

import java.util.List;
import java.util.Vector;

import org.dom4j.Element;

public final class XMLLinearizeASTElement {

    // public section

    @SuppressWarnings("unchecked")
    public static Vector<Element> functionCall(final Element elem) {
        Vector<Element> result = voidFunctionCall(elem);
        if (result != null)
            return result;
        result = assignFunctionCall(elem);
        return (result != null) ? tail(result) : null;
    }

    @SuppressWarnings("unchecked")
    public static Vector<Element> voidFunctionCall(final Element elem) {
        return elem.getName().equals("functionCall") ?
                new Vector<Element>((List<Element>)elem.elements()) : null;
    }

    @SuppressWarnings("unchecked")
    public static Vector<Element> assignFunctionCall(final Element elem) {
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
    public static Vector<Element> functionDeclaration(final Element elem) {
        final Element fnDecl = elem.getName().equals("declarator") ? elem :
                                (Element)elem.selectSingleNode(".//declarator");
        if (fnDecl == null)
            return null;
        final Vector<Element> result = new Vector<Element>();
        result.add((Element)fnDecl.selectSingleNode(".//id"));
        int argID = 0;
        for (final Element param : (List<Element>)((Element)
                       fnDecl.selectSingleNode(".//functionDecl")).elements())
            result.add(parseParameterName(param,argID++));
        return result;
    }

    // private section

    private static Element parseParameterName(final Element param,
                                              final int argID) {
        final Element paramElem = (Element)param.selectSingleNode(".//id");
        if (paramElem != null)
            return paramElem;
        if (param.selectSingleNode(".//varArgs") != null)
            return createElement("id","$ellipsis");
        return createElement("id","$arg" + argID);
    }

    private static Element createElement(final String type, final String data) {
        final Element elem =
            org.dom4j.DocumentFactory.getInstance().createElement(type);
        elem.add(org.dom4j.DocumentFactory.getInstance().createText(data));
        return elem;
    }

    private static <T> Vector<T> cons(final T v, final List<T> l) {
        final Vector<T> result = new Vector<T>(l);
        result.insertElementAt(v,0);
        return result;
    }

    private static <T> Vector<T> tail(final List<T> l) {
        return new Vector<T>(l.subList(1,l.size()));
    }

    private XMLLinearizeASTElement() {
    }
}
