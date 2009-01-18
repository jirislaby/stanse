/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Collections;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;

// Node followers --------------------------------------------------------------

abstract class CFGNodeFollowers { 
    abstract List<CFGNode> get(final CFGNode node);
}

final class ForwardCFGNodeFollowers extends CFGNodeFollowers {
    ForwardCFGNodeFollowers() { super(); }
    @Override
    List<CFGNode> get(final CFGNode node) { return node.getSuccessors(); }
}

final class BackwardCFGNodeFollowers extends CFGNodeFollowers {
    BackwardCFGNodeFollowers() { super(); }
    @Override
    List<CFGNode> get(final CFGNode node) { return node.getPredecessors(); }
}

// Traversation container ------------------------------------------------------

abstract class CFGTraversationContainer<T> {
    abstract void insert(final T node);
    abstract T remove();
    abstract boolean isEmpty();
}

final class CFGTraversationQueue<T> extends CFGTraversationContainer<T> {
    CFGTraversationQueue() { super(); queue = new LinkedList<T>(); }

    void insert(final T obj) { queue.add(obj); }
    T remove() { return queue.remove(); }
    boolean isEmpty() { return queue.isEmpty(); }

    private final LinkedList<T> queue;
}

final class CFGTraversationStack<T> extends CFGTraversationContainer<T> {
    CFGTraversationStack() { super(); stack = new Stack<T>(); }

    void insert(final T obj) { stack.push(obj); }
    T remove() { return stack.pop(); }
    boolean isEmpty() { return stack.isEmpty(); }

    private final Stack<T> stack;
}

// CFG traversal itself --------------------------------------------------------

public final class CFGTraversal {
    
    // public section

    public static <T extends CFGvisitor>
    T traverseCFGToBreadthForward(final CFG cfg,
                                  final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new ForwardCFGNodeFollowers(),
                    new CFGTraversationQueue<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToBreadthBackward(final CFG cfg,
                                   final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new BackwardCFGNodeFollowers(),
                    new CFGTraversationQueue<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToDepthForward(final CFG cfg,
                                final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new ForwardCFGNodeFollowers(),
                    new CFGTraversationStack<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToDepthBackward(final CFG cfg,
                                 final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new BackwardCFGNodeFollowers(),
                    new CFGTraversationStack<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGPathVisitor>
    T traverseCFGPathsForward(final CFG cfg,
                              final CFGNode startNode,final T visitor) {
        final LinkedList<CFGNode> path = new LinkedList<CFGNode>();
        path.add(startNode);
        traverseCFGPaths(cfg,path,
                         new ForwardCFGNodeFollowers(),
                         visitor,new HashSet<Pair<CFGNode,CFGNode>>());
        return visitor;        
    }

    public static <T extends CFGPathVisitor>
    T traverseCFGPathsBackward(final CFG cfg,
                              final CFGNode startNode,final T visitor) {
        final LinkedList<CFGNode> path = new LinkedList<CFGNode>();
        path.add(startNode);
        traverseCFGPaths(cfg,path,
                         new BackwardCFGNodeFollowers(),
                         visitor,new HashSet<Pair<CFGNode,CFGNode>>());
        return visitor;        
    }

    // private section

    private static void traverseCFG(final CFG cfg,
                           final CFGNode startNode,
                           final CFGNodeFollowers nodeFollowers,
                           final CFGTraversationContainer<CFGNode> nodesToVisit,
                           final CFGvisitor visitor) {
        final HashSet<CFGNode> visitedNodes = new HashSet<CFGNode>();
        nodesToVisit.insert(startNode);
        do {
            final CFGNode currentNode = nodesToVisit.remove();
            if (visitedNodes.contains(currentNode))
                continue;
            visitedNodes.add(currentNode);
            if (!visitor.visit(currentNode,currentNode.getElement()))
                continue;
            for (CFGNode currentNodeFollower : nodeFollowers.get(currentNode))
                nodesToVisit.insert(currentNodeFollower);
        }
        while (!nodesToVisit.isEmpty());
    }

    private static void traverseCFGPaths(final CFG cfg,
                            final LinkedList<CFGNode> path,
                            final CFGNodeFollowers nodeFollowers,
                            final CFGPathVisitor visitor,
                            final HashSet<Pair<CFGNode,CFGNode>> visitedEdges) {
        if (!visitor.visit(Collections.unmodifiableList(path)))
            return;

        for (CFGNode currentNodeFollower : nodeFollowers.get(path.getFirst())) {
            final Pair<CFGNode,CFGNode> edge =
                new Pair<CFGNode,CFGNode>(path.getFirst(),currentNodeFollower);
            if (visitedEdges.contains(edge))
                continue;

            path.addFirst(currentNodeFollower);
            visitedEdges.add(edge);

            traverseCFGPaths(cfg,path,nodeFollowers,visitor,visitedEdges);

            visitedEdges.remove(edge);
            path.removeFirst();
        }
    }

    private CFGTraversal() {
    }
}
