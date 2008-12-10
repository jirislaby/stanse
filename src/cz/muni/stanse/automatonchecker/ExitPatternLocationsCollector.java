package cz.muni.stanse.automatonchecker;

import java.util.HashMap;
import java.util.LinkedList;

final class ExitPatternLocationsCollector extends
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGEdge edge, final org.dom4j.Element element) {
        final PatternLocation edgeLocation =
            getEdgeLocationsDictionary().get(edge);

        if (edgeLocation == null)
            return true;

        getCollectedLocations().add(edgeLocation);

        return false;
    }

    // package-private section

    ExitPatternLocationsCollector(
                        final HashMap<CFGEdge,PatternLocation> dictionary) {
        super();
        edgeLocationsDictionary = dictionary;
        collectedLocations = new LinkedList<PatternLocation>();
    }
    
    LinkedList<PatternLocation> getCollectedLocations() {
        return collectedLocations;
    }

    // private section

    private HashMap<CFGEdge,PatternLocation> getEdgeLocationsDictionary() {
        return edgeLocationsDictionary; 
    }

    private HashMap<CFGEdge,PatternLocation> edgeLocationsDictionary;
    private final LinkedList<PatternLocation> collectedLocations;
}
