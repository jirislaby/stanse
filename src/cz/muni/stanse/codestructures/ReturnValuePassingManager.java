package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.builders.XMLLinearizeASTElement;
import cz.muni.stanse.utils.Pair;

import java.util.HashMap;

import org.dom4j.Element;

public final class ReturnValuePassingManager {

    // public section

    public ReturnValuePassingManager(final CFGsNavigator navigator) {
        mapping = new HashMap<Pair<CFGNode,CFGNode>,Pair<String,String>>();
        build(navigator);
    }

    public boolean isIdentityPass(final CFGNode from, final CFGNode to) {
        return getMapping().get(Pair.make(from,to)) == null;
    }

    public String
    pass(final CFGNode from, final String argument, final CFGNode to) {
        return PassingSolver.pass(argument,
                                          getMapping().get(Pair.make(from,to)));
    }

    // private section

    private void build(final CFGsNavigator navigator) {
        for (final CFGNode call : navigator.callSites()) {
            final java.util.Vector<Element> callElem = XMLLinearizeASTElement.
                assignFunctionCall(call.getElement());
            if (callElem != null)
                for (final CFGNode endPredecessor :
                               navigator.getCalleeEnd(call).getPredecessors()) {
                    final Element retElem = XMLLinearizeASTElement.
                        functionRet(endPredecessor.getElement());
                    if (retElem != null)
                        build(call,callElem.firstElement(),endPredecessor,
                              retElem);
                }
        }
    }

    private void build(final CFGNode call, Element callElem,
                       final CFGNode ret,  Element retElem) {
        final Pair<String,String> map =
            Pair.make(PassingSolver.makeArgument(callElem),
                      PassingSolver.makeArgument(retElem));
        getMapping().put(Pair.make(call,ret),map);
        getMapping().put(Pair.make(ret,call),Pair.make(map.getSecond(),
                                                       map.getFirst()));
    }

    private final HashMap<Pair<CFGNode,CFGNode>,Pair<String, String>>
    getMapping() {
        return mapping;
    }

    private final HashMap<Pair<CFGNode,CFGNode>,
                          Pair<String,String>> mapping;
}
