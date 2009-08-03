package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.builders.XMLLinearizeASTElement;
import cz.muni.stanse.utils.Pair;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

import org.dom4j.Element;

public final class ArgumentPassingManager {

    // public section

    public ArgumentPassingManager(final CFGsNavigator navigator,
                                  final Map<CFGNode,CFGHandle> nodeToCFGdict) {
        mapping = new HashMap<Pair<CFGNode,CFGNode>,
                              LinkedList<Pair<String,String>>>();
        build(navigator,nodeToCFGdict);
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

    private void build(final CFGsNavigator navigator,
                       final Map<CFGNode,CFGHandle> nodeToCFGdict) {
        for (final CFGNode call : navigator.callSites()) {
            final CFGNode start = navigator.getCalleeStart(call);
            buildPassingsForCallSite(call,call.getElement(),start,
                    nodeToCFGdict.get(start).getElement(),
                    navigator.getCalleeEnd(call));
        }
    }

    private void
    buildPassingsForCallSite(final CFGNode call, Element callElem,
                             final CFGNode start, Element calleeElem,
                             final CFGNode end) {
        final LinkedList<Pair<String,String>> map =
            buildMappingFromCallSiteToCallee(callElem,calleeElem);
        getMapping().put(Pair.make(call,start),map);
        getMapping().put(Pair.make(call,end),map);

        final LinkedList<Pair<String,String>> mapTransposed =
            transposeCallSiteMapping(map);
        getMapping().put(Pair.make(start,call),mapTransposed);
        getMapping().put(Pair.make(end,call),mapTransposed);
    }

    private static LinkedList<Pair<String,String>>
    buildMappingFromCallSiteToCallee(final Element callElem,
                                     final Element calleeElem) {
        final LinkedList<Pair<String,String>> result =
            new LinkedList<Pair<String,String>>();

        final Iterator<Element> callIter =
            XMLLinearizeASTElement.functionCall(callElem).iterator();
        final Iterator<Element> calleeIter =
            XMLLinearizeASTElement.functionDeclaration(calleeElem).iterator();
        for (callIter.next(), calleeIter.next(); callIter.hasNext(); )
            result.add(Pair.make(PassingSolver
                                             .makeArgument(callIter.next()),
                                 PassingSolver
                                             .makeArgument(calleeIter.next())));
        return result;
    }

    private static LinkedList<Pair<String,String>>
    transposeCallSiteMapping(final LinkedList<Pair<String, String>> map) {
        final LinkedList<Pair<String, String>> result =
                                         new LinkedList<Pair<String, String>>();
        for (final Pair<String,String> item : map)
            result.add(Pair.make(item.getSecond(),item.getFirst()));
        return result;
    }

    private HashMap<Pair<CFGNode,CFGNode>,LinkedList<Pair<String, String>>>
    getMapping() {
        return mapping;
    }

    private final HashMap<Pair<CFGNode,CFGNode>,
                          LinkedList<Pair<String,String>>> mapping;
}
