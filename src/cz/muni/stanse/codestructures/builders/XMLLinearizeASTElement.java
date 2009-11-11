package cz.muni.stanse.codestructures.builders;

import java.util.List;
import java.util.Vector;

import org.dom4j.Element;
import org.dom4j.DocumentFactory;

public final class XMLLinearizeASTElement {

    // public section

    @SuppressWarnings("unchecked")
    public static Vector<Element> functionCall(final Element elem) {
        Vector<Element> result = voidFunctionCall(elem);
        if (result != null)
            return result;
        result = assignFunctionCall(elem);
	if (result == null)
	    return null;
        return tail(result);
    }

    @SuppressWarnings("unchecked")
    public static Vector<Element> voidFunctionCall(final Element elem) {
        return elem.getName().equals("functionCall") ?
                new Vector<Element>((List<Element>)elem.elements()) : null;
    }

    @SuppressWarnings("unchecked")
    public static Vector<Element> assignFunctionCall(final Element elem) {
	if (!elem.getName().equals("assignExpression"))
	    return null;
	List<Element> children = elem.elements();
        if (!children.get(1).getName().equals("functionCall"))
	    return null;

	return cons(children.get(0), children.get(1).elements());
    }

    public static Element functionRet(final Element elem) {
        return (elem.getName().equals("returnStatement") &&
                elem.elements().size() == 1) ?
                    (Element)elem.elements().get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public static Vector<Element> functionDeclaration(final Element elem) {
        Element fnDecl = (Element)elem.selectSingleNode(
		"./declarator[1]");
        if (fnDecl == null)
            return null;
	Element id;
	while ((id = (Element)fnDecl.selectSingleNode("./id")) == null) {
	    fnDecl = (Element)fnDecl.selectSingleNode("./declarator");
	    if (fnDecl == null)
		throw new NullPointerException("wrong functionDeclaration");
	}
        final Vector<Element> result = new Vector<Element>();
        result.add(id);
        int argID = 0;
        for (final Element param:
		    (List<Element>)fnDecl.selectNodes("./functionDecl/*"))
            result.add(parseParameterName(param, argID++));
        return result;
    }

    // private section

    private static Element parseParameterName(final Element param,
                                              final int argID) {
	/* K&R style */
	if (param.getName().equals("id"))
	    return param;
        final Element paramElem =
		(Element)param.selectSingleNode(".//id[last()]");
        if (paramElem != null)
            return paramElem;
        if (param.selectSingleNode("./varArgs") != null)
            return createElement("id", "$ellipsis");
        return createElement("id", "$arg" + Integer.toString(argID));
    }

    private static Element createElement(final String type, final String data) {
        final Element elem = DocumentFactory.getInstance().createElement(type);
        return elem.addText(data);
    }

    private static <T> Vector<T> cons(final T v, final List<T> l) {
        final Vector<T> result = new Vector<T>(l);
        result.insertElementAt(v,0);
        return result;
    }

    private static <T> Vector<T> tail(final List<T> l) {
        return new Vector<T>(l.subList(1, l.size()));
    }

    private XMLLinearizeASTElement() {
    }
}
