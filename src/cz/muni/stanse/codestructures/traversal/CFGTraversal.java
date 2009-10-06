/*
 * Licensed under GPLv2.
 */

package cz.muni.stanse.codestructures.traversal;

import cz.muni.stanse.codestructures.CFGsNavigator;
import cz.muni.stanse.utils.Pair;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Collections;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;
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

// Node followers Interprocedural ----------------------------------------------

abstract class CFGNodeFollowersInterprocedural {
    abstract List<CFGNode> get(final CFGNode node);
    abstract boolean isCallNode(final CFGNode node);
    abstract boolean isReturnNode(final CFGNode node);
    abstract CFGNode getCalleeNode(final CFGNode node);
}

final class ForwardCFGNodeFollowersInterprocedural extends
                                               CFGNodeFollowersInterprocedural {
    ForwardCFGNodeFollowersInterprocedural(final CFGsNavigator navigator) {
        super();
        this.navigator = navigator;
    }
    @Override
    List<CFGNode> get(final CFGNode node) { return node.getSuccessors(); }
    @Override
    boolean isCallNode(final CFGNode node) {
        return navigator.isCallNode(node);
    }
    @Override
    boolean isReturnNode(final CFGNode node) {
        return navigator.isEndNode(node);
    }
    @Override
    CFGNode getCalleeNode(final CFGNode node) {
        return navigator.getCalleeStart(node);
    }
    private final CFGsNavigator navigator;
}

final class BackwardCFGNodeFollowersInterprocedural  extends
                                               CFGNodeFollowersInterprocedural {
    BackwardCFGNodeFollowersInterprocedural(final CFGsNavigator navigator){
        super();
        this.navigator = navigator;
    }
    @Override
    List<CFGNode> get(final CFGNode node) { return node.getPredecessors(); }
    @Override
    boolean isCallNode(final CFGNode node) {
        return navigator.isCallNode(node);
    }
    @Override
    boolean isReturnNode(final CFGNode node) {
        return navigator.isStartNode(node);
    }
    @Override
    CFGNode getCalleeNode(final CFGNode node) {
        return navigator.getCalleeEnd(node);
    }
    private final CFGsNavigator navigator;
}

// Traversation container ------------------------------------------------------

abstract class CFGTraversationContainer<T> {
    abstract void insert(final T node);
    abstract T remove();
    abstract boolean isEmpty();
}

final class CFGTraversationQueue<T> extends CFGTraversationContainer<T> {
    CFGTraversationQueue() { super(); queue = new LinkedList<T>(); }

    @Override
    void insert(final T obj) { queue.add(obj); }
    @Override
    T remove() { return queue.remove(); }
    @Override
    boolean isEmpty() { return queue.isEmpty(); }

    private final LinkedList<T> queue;
}

final class CFGTraversationStack<T> extends CFGTraversationContainer<T> {
    CFGTraversationStack() { super(); stack = new Stack<T>(); }

    @Override
    void insert(final T obj) { stack.push(obj); }
    @Override
    T remove() { return stack.pop(); }
    @Override
    boolean isEmpty() { return stack.isEmpty(); }

    private final Stack<T> stack;
}

// CFG traversal itself --------------------------------------------------------

public final class CFGTraversal {
    
    // public section

    public static <T extends CFGvisitor>
    T traverseCFGToBreadthForward(final CFGHandle cfg,
                                  final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new ForwardCFGNodeFollowers(),
                    new CFGTraversationQueue<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToBreadthBackward(final CFGHandle cfg,
                                   final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new BackwardCFGNodeFollowers(),
                    new CFGTraversationQueue<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToDepthForward(final CFGHandle cfg,
                                final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new ForwardCFGNodeFollowers(),
                    new CFGTraversationStack<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGvisitor>
    T traverseCFGToDepthBackward(final CFGHandle cfg,
                                 final CFGNode startNode, final T visitor) {
        traverseCFG(cfg,startNode,
                    new BackwardCFGNodeFollowers(),
                    new CFGTraversationStack<CFGNode>(),
                    visitor);
        return visitor;        
    }

    public static <T extends CFGPathVisitor>
    T traverseFunctionForward(final CFG cfg, final T visitor) {
        final LinkedList<CFGNode> path = new LinkedList<CFGNode>();
        path.add(cfg.getStartNode());
        traverseCFGPaths(cfg,path,
                         new ForwardCFGNodeFollowers(),
                         visitor,new HashSet<Pair<CFGNode,CFGNode>>());
        return visitor;
    }

    public static <T extends CFGPathVisitor>
    T traverseFunctionBackward(final CFG cfg, final T visitor) {
        final LinkedList<CFGNode> path = new LinkedList<CFGNode>();
        path.add(cfg.getEndNode());
        traverseCFGPaths(cfg,path,
                         new BackwardCFGNodeFollowers(),
                         visitor,new HashSet<Pair<CFGNode,CFGNode>>());
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

    public static <T extends CFGPathVisitor>
    T traverseCFGPathsForwardInterprocedural(final CFGsNavigator navigator,
                                     final CFGNode startNode, final T visitor) {
        return traverseCFGPathsForwardInterprocedural(navigator,startNode,
                                                  visitor,new Stack<CFGNode>());
    }

    public static <T extends CFGPathVisitor>
    T traverseCFGPathsForwardInterprocedural(final CFGsNavigator navigator,
                                       final CFGNode startNode, final T visitor,
                                       final Stack<CFGNode> stack) {
        final LinkedList<CFGNode> path = new LinkedList<CFGNode>();
        path.add(startNode);
        traverseCFGPathsInterprocedural(path,
                          new ForwardCFGNodeFollowersInterprocedural(navigator),
                          visitor,new HashSet<Pair<CFGNode,CFGNode>>(),stack);
        return visitor;
    }

    public static <T extends CFGPathVisitor>
    T traverseCFGPathsBackwardInterprocedural(final CFGsNavigator navigator,
                                     final CFGNode startNode, final T visitor) {
        return traverseCFGPathsBackwardInterprocedural(navigator,startNode,
                                                  visitor,new Stack<CFGNode>());
    }

    public static <T extends CFGPathVisitor>
    T traverseCFGPathsBackwardInterprocedural(final CFGsNavigator navigator,
                                       final CFGNode startNode, final T visitor,
                                       final Stack<CFGNode> stack) {
        final LinkedList<CFGNode> path = new LinkedList<CFGNode>();
        path.add(startNode);
        traverseCFGPathsInterprocedural(path,
                         new BackwardCFGNodeFollowersInterprocedural(navigator),
                         visitor,new HashSet<Pair<CFGNode,CFGNode>>(),stack);
        return visitor;
    }

    // private section

    private static void traverseCFG(final CFGHandle cfg,
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
            if (!visitor.visitInternal(currentNode,currentNode.getElement()))
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
        if (!visitor.visitInternal(Collections.unmodifiableList(path),
                                   new Stack<CFGNode>()))
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

    private static void traverseCFGPathsInterprocedural(
                            final LinkedList<CFGNode> path,
                            final CFGNodeFollowersInterprocedural nodeFollowers,
                            final CFGPathVisitor visitor,
                            final HashSet<Pair<CFGNode,CFGNode>> visitedEdges,
                            final Stack<CFGNode> callStack) {
        if (nodeFollowers.isCallNode(path.get(0))) {
            if (path.size() < 2 || !nodeFollowers.isReturnNode(path.get(1))) {
                final Pair<CFGNode,CFGNode> edge = Pair.make(path.getFirst(),
                                  nodeFollowers.getCalleeNode(path.getFirst()));
                if (visitedEdges.contains(edge))
                    return;
                if (visitor.onCFGchange(path.getFirst(),edge.getSecond())) {
                    callStack.push(edge.getFirst());
                    traverseCFGPathsInterproceduralByEdge(edge,path,
                                  nodeFollowers,visitor,visitedEdges,callStack);
                    return;
                }
            }
        }
        else if (!visitor.visitInternal(Collections.unmodifiableList(path),
                                        callStack))
            return;
        if (nodeFollowers.isReturnNode(path.get(0)) && !callStack.isEmpty()) {
            final Pair<CFGNode,CFGNode> edge = Pair.make(path.getFirst(),
                                                         callStack.peek());
            if (visitedEdges.contains(edge))
                return;
            callStack.pop();
            visitor.onCFGchange(path.getFirst(),edge.getSecond());
            traverseCFGPathsInterproceduralByEdge(edge,path,nodeFollowers,
                                                visitor,visitedEdges,callStack);
            return;
        }
        for (CFGNode currentNodeFollower : nodeFollowers.get(path.getFirst())) {
            final Pair<CFGNode,CFGNode> edge = Pair.make(path.getFirst(),
                                                         currentNodeFollower);
            if (visitedEdges.contains(edge))
                continue;
            traverseCFGPathsInterproceduralByEdge(edge,path,nodeFollowers,
                                                visitor,visitedEdges,callStack);
        }
    }

    private static void traverseCFGPathsInterproceduralByEdge(
                            final Pair<CFGNode,CFGNode> edge,
                            final LinkedList<CFGNode> path,
                            final CFGNodeFollowersInterprocedural nodeFollowers,
                            final CFGPathVisitor visitor,
                            final HashSet<Pair<CFGNode,CFGNode>> visitedEdges,
                            final Stack<CFGNode> callStack) {
        path.addFirst(edge.getSecond());
        visitedEdges.add(edge);

        traverseCFGPathsInterprocedural(path,nodeFollowers,visitor,
                                        visitedEdges,callStack);
        visitedEdges.remove(edge);
        path.removeFirst();
    }

    private CFGTraversal() {
    }
}
