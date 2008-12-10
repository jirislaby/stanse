package cz.muni.stanse.automatonchecker;

import java.util.HashMap;

final class ConnectPatternLocationToSuccessors extends
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGEdge edge, final org.dom4j.Element element) {
        final PatternLocation edgeLocation = 
            getEdgeLocationsDictionary().get(edge);

        if (edgeLocation == null)
            return true;

        assert(edge == edgeLocation.getCFGreferenceEdge());
        if (!getReferenceLocation().getSuccessorPatternLocations().
                contains(edgeLocation))
            getReferenceLocation().getSuccessorPatternLocations().
                add(edgeLocation);

        return false;
    }

    // package-private section

    ConnectPatternLocationToSuccessors(
                        final PatternLocation location,
                        final HashMap<CFGEdge,PatternLocation> dictionary) {
        super();
        referenceLocation = location;
        edgeLocationsDictionary = dictionary;
    }

    // private section

    private PatternLocation getReferenceLocation() {
        return referenceLocation; 
    }

    private HashMap<CFGEdge,PatternLocation> getEdgeLocationsDictionary() {
        return edgeLocationsDictionary; 
    }

    private PatternLocation referenceLocation;
    private HashMap<CFGEdge,PatternLocation> edgeLocationsDictionary;
}
