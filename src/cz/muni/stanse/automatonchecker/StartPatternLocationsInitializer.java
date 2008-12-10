package cz.muni.stanse.automatonchecker;

import java.util.HashMap;
import java.util.LinkedList;

final class StartPatternLocationsInitializer extends
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGEdge edge, final org.dom4j.Element element) {
        final PatternLocation edgeLocation =
            getEdgeLocationsDictionary().get(edge);

        if (edgeLocation == null)
            return true;

        edgeLocation.setInitialAutomataStates(getInitStates());

        return false;
    }

    // package-private section

    StartPatternLocationsInitializer(
                        final HashMap<CFGEdge,PatternLocation> dictionary,
                        final LinkedList<AutomatonState> initStates) {
        super();
        edgeLocationsDictionary = dictionary;
        this.initStates = initStates;
    }
    
    // private section

    private HashMap<CFGEdge,PatternLocation> getEdgeLocationsDictionary() {
        return edgeLocationsDictionary; 
    }

    private LinkedList<AutomatonState> getInitStates() {
        return initStates;
    }

    private HashMap<CFGEdge,PatternLocation> edgeLocationsDictionary;
    final LinkedList<AutomatonState> initStates;
}
