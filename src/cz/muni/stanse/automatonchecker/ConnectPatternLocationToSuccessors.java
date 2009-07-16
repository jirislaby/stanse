/**
 * @file ConnectPatternLocationToSuccessors.java
 * @brief Implements final class ConnectPatternLocationToSuccessors which is
 *        responsible for creating connections between PatternLocation instances
 *        with respect to control-flow of source code.  
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;

import java.util.HashMap;

/**
 * @brief Instances are responsible for creating connections between
 *        PatternLocation instances with respect to control-flow of source code.
 *
 * 
 *
 * @see cz.muni.stanse.utils.CFGvisitor
 */
final class ConnectPatternLocationToSuccessors extends
                                               cz.muni.stanse.codestructures.traversal.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {
        final Pair<PatternLocation,PatternLocation> nodeLocationsPair =
            getNodeLocationsDictionary().get(node);

        if (nodeLocationsPair == null ||
            nodeLocationsPair.getSecond() == getReferenceLocation())
            return true;

        assert(node == nodeLocationsPair.getFirst().getCFGreferenceNode());
        if (!getReferenceLocation().getSuccessorPatternLocations().
                contains(nodeLocationsPair.getFirst()))
            getReferenceLocation().getSuccessorPatternLocations().
                add(nodeLocationsPair.getFirst());

        return false;
    }

    // package-private section

    ConnectPatternLocationToSuccessors(final PatternLocation location,
                    final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                            dictionary) {
        super();
        referenceLocation = location;
        nodeLocationsDictionary = dictionary;
    }

    // private section

    private PatternLocation getReferenceLocation() {
        return referenceLocation; 
    }

    private HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
    getNodeLocationsDictionary() {
        return nodeLocationsDictionary; 
    }

    private PatternLocation referenceLocation;
    private HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
                nodeLocationsDictionary;
}
