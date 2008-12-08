package cz.muni.stanse.checker;

import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.utils.Pair;
import java.util.List;
import java.util.ListIterator;

public final class CheckerErrorAlgo {

    public static Pair<CFGNode,String> getErrorNode(
                                              final CheckerError checkerError) {
        final List< Pair<CFGNode,String> > firstTraceNodes =
            checkerError.getErrorTraces().get(0).getErrorTrace();
        return firstTraceNodes.get(firstTraceNodes.size() - 1);
    }
    
    public static ListIterator< Pair<CFGNode,String> >
                     findFirstNode(final ErrorTrace trace, final CFGNode node) {

        return findFirstNode(trace.getErrorTrace().listIterator(),node);
    }

    public static ListIterator< Pair<CFGNode,String> > findFirstEdge(
       final ErrorTrace trace, final CFGNode startNode, final CFGNode endNode) {

        return findFirstEdge(trace.getErrorTrace().listIterator(),
                             startNode,endNode);
    }

    public static ListIterator< Pair<CFGNode,String> > findFirstNode(
                               final ListIterator< Pair<CFGNode,String> > iter,
                               final CFGNode node) {
        for ( ; iter.hasNext(); )
            if (iter.next().getFirst().equals(node)) {
                iter.previous();
                return iter;
            }
        return null;
    }
    
    public static ListIterator< Pair<CFGNode,String> > findFirstEdge(
                               ListIterator< Pair<CFGNode,String> > iter,
                               final CFGNode startNode, final CFGNode endNode) {

        for (iter =  findFirstNode(iter,startNode); iter != null;
             iter =  findFirstNode(iter,startNode)) {
            iter.next();
            if (iter.hasNext() && iter.next().getFirst().equals(endNode)) {
                iter.previous();
                return iter;
            }
        }

        return null;
    }
    
}
