package cz.muni.stanse.codestructures;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class IntraproceduralCFGsNavigator implements CFGsNavigator {
    // public section

    public IntraproceduralCFGsNavigator(final Collection<CFGHandle> CFGs) {
        emptySet = new HashSet<CFGNode>();
        beginings = new HashSet<CFGNode>();
        endings = new HashSet<CFGNode>();
        buildBeginingsAndEndings(CFGs);
    }

    public
    IntraproceduralCFGsNavigator(final IntraproceduralCFGsNavigator source) {
        emptySet = source.getEmptySet();
        beginings = source.getBeginings();
        endings = source.getEndings();
    }

    @Override
    public boolean isCallNode(final CFGNode node) {
        return false;
    }

    @Override
    public boolean isStartNode(final CFGNode node) {
        return getBeginings().contains(node);
    }

    @Override
    public boolean isEndNode(final CFGNode node) {
        return getEndings().contains(node);
    }

    @Override
    public CFGNode getCalleeStart(final CFGNode node) {
        return null;
    }

    @Override
    public CFGNode getCalleeEnd(final CFGNode node) {
        return null;
    }

    @Override
    public HashSet<CFGNode> getCallersFromStart(final CFGNode node) {
        return getEmptySet();
    }

    @Override
    public HashSet<CFGNode> getCallersFromEnd(final CFGNode node) {
        return getEmptySet();
    }

    @Override
    public Set<CFGNode> callSites() {
        return getEmptySet();
    }

    // private section

    private void buildBeginingsAndEndings(final Collection<CFGHandle> CFGs) {
        for (final CFGHandle cfgh: CFGs) {
            CFG cfg = cfgh.getCFG();
            getBeginings().add(cfg.getStartNode());
            getEndings().add(cfg.getEndNode());
        }
    }

    private HashSet<CFGNode> getBeginings() {
        return beginings;
    }

    private HashSet<CFGNode> getEndings() {
        return endings;
    }

    private HashSet<CFGNode> getEmptySet() {
        return emptySet;
    }

    private final HashSet<CFGNode> emptySet;
    private final HashSet<CFGNode> beginings;
    private final HashSet<CFGNode> endings;
}
