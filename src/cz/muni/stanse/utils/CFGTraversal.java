package cz.muni.stanse.utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import cz.muni.stanse.parser.CFGEdge;
import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.parser.ControlFlowGraph;

// Node followers --------------------------------------------------------------

abstract class CFGEdgeFollowers { 
    abstract LinkedList<CFGEdge> get(final CFGEdge edge);
    abstract LinkedList<CFGEdge> get(final CFGNode node);
}

final class ForwardCFGEdgeFollowers extends CFGEdgeFollowers {
    ForwardCFGEdgeFollowers() { super(); }
    @Override
    LinkedList<CFGEdge> get(final CFGEdge edge) { return get(edge.getTo()); }
    LinkedList<CFGEdge> get(final CFGNode node) {
        final LinkedList<CFGEdge> edges = new LinkedList<CFGEdge>();
        for (CFGNode nod : node.getSuccessors())
            edges.add(CFGEdge.getInstance(node,nod));
        return edges;
    }
}

final class BackwardCFGEdgeFollowers extends CFGEdgeFollowers {
    BackwardCFGEdgeFollowers() { super(); }
    @Override
    LinkedList<CFGEdge> get(final CFGEdge edge) { return get(edge.getFrom()); }
    LinkedList<CFGEdge> get(final CFGNode node) {
        final LinkedList<CFGEdge> edges = new LinkedList<CFGEdge>();
        for (CFGNode nod : node.getPredecessors())
            edges.add(CFGEdge.getInstance(nod,node));
        return edges;
    }
}

// Traversation container ------------------------------------------------------

abstract class CFGTraversationContainer {
    abstract void insert(final CFGEdge edge);
    abstract CFGEdge remove();
    abstract boolean isEmpty();
}

final class CFGTraversationQueue extends CFGTraversationContainer {
    CFGTraversationQueue() { super(); queue = new LinkedList<CFGEdge>(); }

    void insert(final CFGEdge edge) { queue.add(edge); }
    CFGEdge remove() { return queue.remove(); }
    boolean isEmpty() { return queue.isEmpty(); }

    private final LinkedList<CFGEdge> queue;
}

final class CFGTraversationStack extends CFGTraversationContainer {
    CFGTraversationStack() { super(); stack = new Stack<CFGEdge>(); }

    void insert(final CFGEdge edge) { stack.push(edge); }
    CFGEdge remove() { return stack.pop(); }
    boolean isEmpty() { return stack.isEmpty(); }

    private final Stack<CFGEdge> stack;
}

// CFG traversal itself --------------------------------------------------------

public final class CFGTraversal {
    
    // public section

    public static <T extends CFGvisitor>
    T traverseCFGToBreadthForward(final ControlFlowGraph cfg,
                                  final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new ForwardCFGEdgeFollowers(),
                    new CFGTraversationQueue(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToBreadthBackward(final ControlFlowGraph cfg,
                                   final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new BackwardCFGEdgeFollowers(),
                    new CFGTraversationQueue(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToDepthForward(final ControlFlowGraph cfg,
                                final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new ForwardCFGEdgeFollowers(),
                    new CFGTraversationStack(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToDepthBackward(final ControlFlowGraph cfg,
                                 final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new BackwardCFGEdgeFollowers(),
                    new CFGTraversationStack(),
                    visitor);
        return visitor;        
    }

    // private section
    
    private static void traverseCFG(final ControlFlowGraph cfg,
                                    final CFGNode startNode,
                                    final CFGEdgeFollowers nodeFollowers,
                                    final CFGTraversationContainer nodesToVisit,
                                    final CFGvisitor visitor) {
        final HashSet<CFGEdge> visitedNodes = new HashSet<CFGEdge>();
        for (CFGEdge currentEdgeFollower : nodeFollowers.get(startNode))
            nodesToVisit.insert(currentEdgeFollower);
        while (!nodesToVisit.isEmpty()) {
            final CFGEdge currentEdge = nodesToVisit.remove();
            if (visitedNodes.contains(currentEdge))
                continue;
            visitedNodes.add(currentEdge);
            if (!visitor.visit(currentEdge,cfg.getEdgeElement(
                                    currentEdge.getFrom(),currentEdge.getTo())))
                continue;
            for (CFGEdge currentEdgeFollower : nodeFollowers.get(currentEdge))
                nodesToVisit.insert(currentEdgeFollower);
        }
    }

    private CFGTraversal() {
    }
}
