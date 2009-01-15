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
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {
        final PatternLocation nodeLocation = 
            getNodeLocationsDictionary().get(node);

        if (nodeLocation == null || nodeLocation == getReferenceLocation())
            return true;

        assert(node == nodeLocation.getCFGreferenceNode());
        if (!getReferenceLocation().getSuccessorPatternLocations().
                contains(nodeLocation))
            getReferenceLocation().getSuccessorPatternLocations().
                add(nodeLocation);

        return false;
    }

    // package-private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    ConnectPatternLocationToSuccessors(
                        final PatternLocation location,
                        final HashMap<CFGNode,PatternLocation> dictionary) {
        super();
        referenceLocation = location;
        nodeLocationsDictionary = dictionary;
    }

    // private section

    private PatternLocation getReferenceLocation() {
        return referenceLocation; 
    }

    private HashMap<CFGNode,PatternLocation> getNodeLocationsDictionary() {
        return nodeLocationsDictionary; 
    }

    private PatternLocation referenceLocation;
    private HashMap<CFGNode,PatternLocation> nodeLocationsDictionary;
}
