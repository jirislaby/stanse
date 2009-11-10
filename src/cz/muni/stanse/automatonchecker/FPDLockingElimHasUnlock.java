package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;

import java.util.Map;
import java.util.Set;

final class FPDLockingElimHasUnlock extends FalsePositivesDetector {
    public FPDLockingElimHasUnlock(LazyInternalStructures internals,
            final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
	lis = internals;
    dict = nodeLocationDictionary;
    }

    @Override
    int getTraceImpotance(final java.util.List<CFGNode> path,
                            final java.util.Stack<CFGNode> cfgContext,
                            final ErrorRule rule) {
        final CFGNode endNode = path.get(path.size() - 1);
        final Pair<PatternLocation,PatternLocation> endLocPair =
                dict.get(endNode);
        if (endLocPair == null)
            return getBugImportance(0);
        if (isHULocation(endLocPair.getFirst().getDeliveredAutomataStates()))
            return getBugImportance(0);
        return getFalsePositiveImportance();
    }

    private static boolean isHULocation(final Set<AutomatonState> states) {
        for (final AutomatonState state : states)
            if ((state.getSymbol().equals("HU") &&
                    isSymbolLocation("L",states,state.getAutomatonID())) ||
                (state.getSymbol().equals("HL") &&
                    isSymbolLocation("IU", states,state.getAutomatonID()))    )
                return true;
        return false;
    }

    private static boolean isSymbolLocation(final String symbol,
                                            final Set<AutomatonState> states,
                                            final ComposedAutomatonID ID) {
        for (final AutomatonState state : states)
            if (state.getSymbol().equals(symbol) &&
                    ID.equals(state.getAutomatonID()))
                return true;
        return false;
    }

    private final LazyInternalStructures lis;
    private final Map<CFGNode,Pair<PatternLocation,PatternLocation>> dict;
}

final class FPDLockingElimHasUnlockCreator
        extends FalsePositivesDetectorCreator {

    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return definition.getAutomatonName().equals(
                "Linux kernel lock/unlock/islocked automaton checker");
    }

    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
        LazyInternalStructures internals,boolean isInterprocediral,
        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
        return new FPDLockingElimHasUnlock(internals,nodeLocationDictionary);
    }
}
