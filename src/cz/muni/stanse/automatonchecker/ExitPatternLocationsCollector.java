package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.HashMap;
import java.util.LinkedList;

final class ExitPatternLocationsCollector extends
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {
        final PatternLocation nodeLocation =
            getNodeLocationsDictionary().get(node);

        if (nodeLocation == null)
            return true;

        getCollectedLocations().add(nodeLocation);

        return false;
    }

    // package-private section

    ExitPatternLocationsCollector(
                        final HashMap<CFGNode,PatternLocation> dictionary) {
        super();
        nodeLocationsDictionary = dictionary;
        collectedLocations = new LinkedList<PatternLocation>();
    }
    
    LinkedList<PatternLocation> getCollectedLocations() {
        return collectedLocations;
    }

    // private section

    private HashMap<CFGNode,PatternLocation> getNodeLocationsDictionary() {
        return nodeLocationsDictionary; 
    }

    private HashMap<CFGNode,PatternLocation> nodeLocationsDictionary;
    private final LinkedList<PatternLocation> collectedLocations;
}
