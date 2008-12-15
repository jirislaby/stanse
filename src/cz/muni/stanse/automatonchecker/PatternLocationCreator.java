package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.utils.Pair;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Vector;

final class PatternLocationCreator extends cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGNode node, final org.dom4j.Element element) {

// TODO: remove this check, when ALL the CFG nodes will contain related XML
//       element. 
{if (element == null) return true;}

        final Vector<XMLPattern> XMLpatterns =
            getXMLAutomatonDefinition().getXMLpatterns();
        for (int i = 0; i < XMLpatterns.size(); ++i) {
            final Pair<Boolean,PatternVariablesAssignment>
                matchResult = XMLpatterns.get(i).matchesXMLElement(element);
            if (matchResult.getFirst()) {
                Integer automatonID = getAutomataIDs().get(
                                                matchResult.getSecond());
                if (automatonID == null)
                    getAutomataIDs().put(matchResult.getSecond(),
                                         automatonID = getUniqueAutomatonID());
                getCreatedPatternLocations().put(node,
                        createPatternLocation(i,node,automatonID) );
                break;
            }
        }
        return true;
    }

    // package-private section

    PatternLocationCreator(
            final cz.muni.stanse.parser.CFG cfg,
            final XMLAutomatonDefinition XMLdefinition) {
        super();
        this.cfg = cfg;
        automatonDefinition = XMLdefinition;
        createdPatternLocations = new HashMap<CFGNode,PatternLocation>();

        automataIDs = new HashMap<PatternVariablesAssignment,Integer>();

        patternAutomataCounter = 0;
        
        createEntryLocation();
        createExitLocation();
    }

    HashMap<CFGNode,PatternLocation> getCreatedPatternLocations() {
        return createdPatternLocations; 
    }

    int getNumDistinctLocations() {
        return getAutomataIDs().size();
    }

    // private section

    private void createEntryLocation() {
        final CFGNode node = getCFG().getStartNode();
        getCreatedPatternLocations().put(node,
            new PatternLocation(getCFG(),node,new LinkedList<TransitionRule>(),
                                new LinkedList<ErrorRule>()));
    }

    private void createExitLocation() {
        final CFGNode node = getCFG().getEndNode();
        getCreatedPatternLocations().put(node,
            new PatternLocation(getCFG(),node,new LinkedList<TransitionRule>(),
                                new LinkedList<ErrorRule>()));
    }

    private PatternLocation createPatternLocation(final int patternIndex,
                                    final CFGNode node, final int automatonID) {
        final LinkedList<TransitionRule> transitionRules =
                new LinkedList<TransitionRule>();
        for (final XMLTransitionRule XMLtransitionRule :
             getXMLAutomatonDefinition().
                                  getXMLtransitionRulesForPattern(patternIndex))
            transitionRules.add(new TransitionRule(XMLtransitionRule,
                                                   automatonID));

        final LinkedList<ErrorRule> errorRules = new LinkedList<ErrorRule>();
        for (final XMLErrorRule XMLerrorRule : getXMLAutomatonDefinition().
                                       getXMLerrorRulesForPattern(patternIndex))
            errorRules.add(new ErrorRule(XMLerrorRule,automatonID));

        return new PatternLocation(getCFG(),node,transitionRules,errorRules);
    }

    private cz.muni.stanse.parser.CFG getCFG() {
        return cfg;
    }

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return automatonDefinition;
    }

    private HashMap<PatternVariablesAssignment,Integer>
    getAutomataIDs() {
        return automataIDs;
    }

    private int getUniqueAutomatonID() {
        return patternAutomataCounter++;
    }

    private final cz.muni.stanse.parser.CFG cfg;
    private final XMLAutomatonDefinition automatonDefinition;
    private final HashMap<CFGNode,PatternLocation> createdPatternLocations;
    private final HashMap<PatternVariablesAssignment,Integer> automataIDs;
    private int patternAutomataCounter; 
}
