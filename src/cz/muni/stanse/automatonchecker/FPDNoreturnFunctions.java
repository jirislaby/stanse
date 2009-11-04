package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;

import java.util.Map;

import org.dom4j.Element;

final class FPDNoreturnFunctions extends FalsePositivesDetector {
    /* this should be configurable from the outside */
    static final String[] noreturns = { "__st_BUG_st__", "panic" };
    @Override
    int getTraceImpotance(final java.util.List<CFGNode> path,
                          final java.util.Stack<CFGNode> cfgContext,
                          final ErrorRule rule) {
        for (CFGNode node: path)
            for (Object fno: node.getElement().
		    selectNodes("..//functionCall")) {
		Element nameEl = (Element)((Element)fno).elements().get(0);
		for (String nr: noreturns)
		    if (nameEl.getText().equals(nr))
			return getFalsePositiveImportance();
	    }
        return getBugImportance(0);
    }
}
final class FPDNoreturnFunctionsCreator
        extends FalsePositivesDetectorCreator {

    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return definition.getAutomatonName().startsWith("Linux kernel ");
    }

    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
        LazyInternalStructures internals,boolean isInterprocediral,
        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
        return new FPDNoreturnFunctions();
    }
}
