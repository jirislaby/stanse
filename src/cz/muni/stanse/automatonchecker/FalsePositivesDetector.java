package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

abstract class FalsePositivesDetector {

    static int getFalsePositiveImportance() {
        return -1;
    }

    static int getBugImportance(int importance) {
        assert(importance >= 0);
        return importance;
    }

    static int getBugDefaultImportance() {
        return 0;
    }

    abstract int getTraceImpotance(final java.util.List<CFGNode> path,
                                   final java.util.Stack<CFGNode> cfgContext,
                                   final ErrorRule rule);

}
