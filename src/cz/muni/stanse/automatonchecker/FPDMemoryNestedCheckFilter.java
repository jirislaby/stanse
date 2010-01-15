package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;

import java.util.Map;

/**
 * Removes cases such as:
 * void abc(void *xyz) {if (!xyz) return;}
 * void def(void *xyz) {if (xyz) abc(xyz);}
 * @author xslaby
 */
final class FPDMemoryNestedCheckFilter extends FalsePositivesDetector {
    private LazyInternalStructures lis;

    public FPDMemoryNestedCheckFilter(LazyInternalStructures internals) {
	lis = internals;
    }

    @Override
    int getTraceImportance(final java.util.List<CFGNode> path,
                            final java.util.Stack<CFGNode> cfgContext,
                            final ErrorRule rule) {
        String desc = rule.getErrorDescription();
        if (!desc.equals("unnecessary check (checking for NULL)") &&
                !desc.equals("unnecessary check (checking for not NULL)"))
            return getBugImportance(0);
        CFGNode start = path.get(0);
        CFGNode end = path.get(path.size()-1);
	Map<CFGNode, CFGHandle> dict = lis.getNodeToCFGdictionary();
	if (!dict.get(start).getFunctionName().equals(
		dict.get(end).getFunctionName()))
        return getFalsePositiveImportance();
        return getBugImportance(0);
    }
}
final class FPDMemoryNestedCheckFilterCreator
        extends FalsePositivesDetectorCreator {

    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return definition.getAutomatonName().equals(
                "Linux kernel pointer analysis automaton checker");
    }

    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
        LazyInternalStructures internals,boolean isInterprocediral,
        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
        return new FPDMemoryNestedCheckFilter(internals);
    }
}
