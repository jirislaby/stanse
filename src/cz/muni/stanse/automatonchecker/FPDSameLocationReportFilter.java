package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;

import java.util.HashSet;
import java.util.Map;

final class FPDSameLocationReportFilter extends FalsePositivesDetector {

    FPDSameLocationReportFilter() {
        errLocNodes = new HashSet<Pair<CFGNode,String>>();
        errStartLocNodes = new HashSet<CFGNode>();
    }

    @Override
    int getTraceImpotance(final java.util.List<CFGNode> path,
                          final java.util.Stack<CFGNode> cfgContext,
                          final ErrorRule rule) {
        assert(!path.isEmpty());
        final Pair<CFGNode,String> errLocID =
                Pair.make(path.get(path.size() - 1), rule.getErrorEndMessage());
        if (errLocNodes.contains(errLocID) ||
                errStartLocNodes.contains(path.get(0)))
            return getFalsePositiveImportance();
        errLocNodes.add(errLocID);
        errStartLocNodes.add(path.get(0));
        return getBugImportance(0);
    }

    private final HashSet<Pair<CFGNode,String>> errLocNodes;
    private final HashSet<CFGNode> errStartLocNodes;
}

final class FPDSameLocationReportFilterCreator
        extends FalsePositivesDetectorCreator {

    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return definition.getAutomatonName().startsWith("Linux kernel");
    }

    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
        LazyInternalStructures internals,boolean isInterprocediral,
        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
        return new FPDSameLocationReportFilter();
    }
}
