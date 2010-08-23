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
        for (final CFGNode caller : navigator.callSites()) {
            final CFGNode start = navigator.getCalleeStart(caller);
            buildPassingsForCallSite(caller,
                    nodeToCFGdict.get(start));
        }
    }

    private void
    buildPassingsForCallSite(final CFGNode caller,
                             final CFGHandle callee) {
        final LinkedList<Pair<String,String>> map =
            buildMappingFromCallSiteToCallee(caller, callee);
        getMapping().put(Pair.make(caller, callee.getStartNode()),map);
        getMapping().put(Pair.make(caller, callee.getEndNode()), map);

        final LinkedList<Pair<String,String>> mapTransposed =
            transposeCallSiteMapping(map);
        getMapping().put(Pair.make(callee.getStartNode(), caller),mapTransposed);
        getMapping().put(Pair.make(callee.getEndNode(), caller),mapTransposed);
    }

    private static LinkedList<Pair<String,String>>
    buildMappingFromCallSiteToCallee(final CFGNode caller,
                                     final CFGHandle callee) {
        final LinkedList<Pair<String,String>> result =
            new LinkedList<Pair<String,String>>();

        if (caller.getNodeType() != CFGNode.NodeType.none) {
            Iterator<CFGNode.Operand> opIter = caller.getOperands().iterator();
            opIter.next();
            Iterator<String> paramIter = callee.getParams().iterator();
            while (opIter.hasNext()) {
                assert paramIter.hasNext();
                result.add(Pair.make(paramIter.next(), PassingSolver.makeArgument(opIter.next())));
            }
            assert !paramIter.hasNext();
        } else {
            final Iterator<Element> callIter =
                XMLLinearizeASTElement.functionCall(caller.getElement()).iterator();
            final Iterator<Element> calleeIter =
                XMLLinearizeASTElement.functionDeclaration(callee.getElement()).iterator();
            for (callIter.next(), calleeIter.next(); callIter.hasNext(); )
                result.add(Pair.make(PassingSolver
                                                 .makeArgument(callIter.next()),
                                     PassingSolver
                                                 .makeArgument(calleeIter.next())));
        }
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
