package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.Set;
import java.util.HashSet;

public interface CFGsNavigator {
    public boolean isCallNode(final CFGNode node);
    public boolean isStartNode(final CFGNode node);
    public boolean isEndNode(final CFGNode node);
    public CFGNode getCalleeStart(final CFGNode node);
    public CFGNode getCalleeEnd(final CFGNode node);
    public HashSet<CFGNode> getCallersFromStart(final CFGNode node);
    public HashSet<CFGNode> getCallersFromEnd(final CFGNode node);
    public Set<CFGNode> callSites();
}
