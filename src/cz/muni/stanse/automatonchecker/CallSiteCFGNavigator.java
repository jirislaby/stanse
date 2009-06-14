package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.CFGsNavigator;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.Set;
import java.util.HashSet;

final class CallSiteCFGNavigator implements CFGsNavigator {

    // public section

    @Override
    public boolean isCallNode(final CFGNode node) {
        return callDetector.isCallNode(node);
    }

    @Override
    public boolean isStartNode(final CFGNode node) {
        return navigator.isStartNode(node);
    }

    @Override
    public boolean isEndNode(final CFGNode node) {
        return navigator.isEndNode(node);
    }

    @Override
    public CFGNode getCalleeStart(final CFGNode node) {
        return navigator.getCalleeStart(node);
    }

    @Override
    public CFGNode getCalleeEnd(final CFGNode node) {
        return navigator.getCalleeEnd(node);
    }

    @Override
    public HashSet<CFGNode> getCallersFromStart(final CFGNode node) {
        return navigator.getCallersFromStart(node);
    }

    @Override
    public HashSet<CFGNode> getCallersFromEnd(final CFGNode node) {
        return navigator.getCallersFromEnd(node);
    }

    @Override
    public Set<CFGNode> callSites() {
        return navigator.callSites();
    }

    // package-private section

    CallSiteCFGNavigator(final CFGsNavigator navigator,
                         final CallSiteDetector callDetector) {
        this.navigator = navigator;
        this.callDetector = callDetector;
    }

    // private section

    private final CFGsNavigator navigator;
    private final CallSiteDetector callDetector;
}
