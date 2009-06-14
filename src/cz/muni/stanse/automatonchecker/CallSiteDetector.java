package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.CFGsNavigator;
import cz.muni.stanse.utils.Pair;

import java.util.HashSet;
import java.util.HashMap;

final class CallSiteDetector {

    // package-private section

    CallSiteDetector(final CFGsNavigator navigator,
                     final HashMap<CFGNode,Pair<PatternLocation,
                                                PatternLocation>>
                                                       nodeLocationDictionary) {
        this.nodeLocationDictionary = nodeLocationDictionary;
        this.navigator = navigator;
    }

    boolean isCallNode(final CFGNode node) {
        if (!getNavigator().isCallNode(node))
            return false;
        final PatternLocation location = getNodeLocationDictionary().get(node)
                                                                    .getFirst();
//        return location != null && location.getTransitionRules().isEmpty()
//                                && location.getErrorRules().isEmpty();
        return location != null;
    }

    HashSet<CFGNode> callSites() {
        final HashSet<CFGNode> result = new HashSet<CFGNode>();
        for (final CFGNode node : getNavigator().callSites())
            if (isCallNode(node))
                result.add(node);
        return result;
    }

    // private section

    private CFGsNavigator getNavigator() {
        return navigator;
    }

    private HashMap<CFGNode, Pair<PatternLocation, PatternLocation>>
    getNodeLocationDictionary() {
        return nodeLocationDictionary;
    }

    private final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                         nodeLocationDictionary;
    private final CFGsNavigator navigator;
}
