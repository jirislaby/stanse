package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.HashMap;
import java.util.LinkedList;

final class StartPatternLocationsInitializer extends
                                               cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {
        final PatternLocation nodeLocation =
            getNodeLocationsDictionary().get(node);

        if (nodeLocation == null)
            return true;

        nodeLocation.setInitialAutomataStates(getInitStates());

        return false;
    }

    // package-private section

    StartPatternLocationsInitializer(
                        final HashMap<CFGNode,PatternLocation> dictionary,
                        final LinkedList<AutomatonState> initStates) {
        super();
        nodeLocationsDictionary = dictionary;
        this.initStates = initStates;
    }
    
    // private section

    private HashMap<CFGNode,PatternLocation> getNodeLocationsDictionary() {
        return nodeLocationsDictionary; 
    }

    private LinkedList<AutomatonState> getInitStates() {
        return initStates;
    }

    private HashMap<CFGNode,PatternLocation> nodeLocationsDictionary;
    final LinkedList<AutomatonState> initStates;
}
