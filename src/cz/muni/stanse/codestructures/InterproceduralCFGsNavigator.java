package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.traversal.CFGvisitor;
import cz.muni.stanse.codestructures.traversal.CFGTraversal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.dom4j.Element;

public final class InterproceduralCFGsNavigator implements CFGsNavigator {

    // public section

    public InterproceduralCFGsNavigator(final Collection<CFGHandle> CFGs,
                                        final ElementCFGdictionary cfgDict) {
        callToStart = new HashMap<CFGNode,CFGNode>();
        callToEnd = new HashMap<CFGNode,CFGNode>();
        beginings = new HashMap<CFGNode,HashSet<CFGNode>>();
        endings = new HashMap<CFGNode,HashSet<CFGNode>>();
        build(CFGs,cfgDict);
    }

    public
    InterproceduralCFGsNavigator(final InterproceduralCFGsNavigator source) {
        callToStart = source.getCallToStart();
        callToEnd = source.getCallToEnd();
        beginings = source.getBeginings();
        endings = source.getEndings();
    }

    @Override
    public boolean isCallNode(final CFGNode node) {
        return getCallToStart().containsKey(node);
    }

    @Override
    public boolean isStartNode(final CFGNode node) {
        return getBeginings().containsKey(node);
    }

    @Override
    public boolean isEndNode(final CFGNode node) {
        return getEndings().containsKey(node);
    }

    @Override
    public CFGNode getCalleeStart(final CFGNode node) {
        return getCallToStart().get(node);
    }

    @Override
    public CFGNode getCalleeEnd(final CFGNode node) {
        return getCallToEnd().get(node);
    }

    @Override
    public HashSet<CFGNode> getCallersFromStart(final CFGNode node) {
        return getBeginings().get(node);
    }

    @Override
    public HashSet<CFGNode> getCallersFromEnd(final CFGNode node) {
        return getEndings().get(node);
    }

    @Override
    public Set<CFGNode> callSites() {
        return getCallToStart().keySet();
    }

    // private section

    private final void build(final Collection<CFGHandle> CFGs,
                             final ElementCFGdictionary cfgDict) {
        for (final CFGHandle cfgh : CFGs) {
            CFGTraversal.traverseCFGToBreadthForward(cfgh,cfgh.getStartNode(),
                new CFGvisitor() {
                    @Override
                    public boolean visit(final CFGNode node,
                                         final Element element) {
                        final CFGHandle calleeCFG = cfgDict.get(element);
                        if (calleeCFG != null)
                            fillDictionaries(node,calleeCFG);
                        return true;
                    }
                });
        }
    }

    private final void fillDictionaries(final CFGNode node,
            final CFGHandle cfg) {
        getCallToStart().put(node,cfg.getStartNode());

        getCallToEnd().put(node,cfg.getEndNode());

        if (!getBeginings().containsKey(cfg.getStartNode()))
            getBeginings().put(cfg.getStartNode(),new HashSet<CFGNode>());
        getBeginings().get(cfg.getStartNode()).add(node);

        if (!getEndings().containsKey(cfg.getEndNode()))
            getEndings().put(cfg.getEndNode(),new HashSet<CFGNode>());
        getEndings().get(cfg.getEndNode()).add(node);
    }

    private final HashMap<CFGNode,CFGNode> getCallToStart() {
        return callToStart;
    }

    private final HashMap<CFGNode,CFGNode> getCallToEnd() {
        return callToEnd;
    }

    private final HashMap<CFGNode,HashSet<CFGNode>> getBeginings() {
        return beginings;
    }

    private final HashMap<CFGNode,HashSet<CFGNode>> getEndings() {
        return endings;
    }

    private final HashMap<CFGNode,CFGNode> callToStart;
    private final HashMap<CFGNode,CFGNode> callToEnd;
    private final HashMap<CFGNode,HashSet<CFGNode>> beginings;
    private final HashMap<CFGNode,HashSet<CFGNode>> endings;
}
