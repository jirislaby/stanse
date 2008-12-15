package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.parser.CFGNode;
import java.util.HashMap;

final class ConnectPatternLocationToSuccessors extends
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

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
