package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

abstract class FalsePositivesDetector {

    abstract boolean isFalsePositive(final java.util.List<CFGNode> path,
                                     final java.util.Stack<CFGNode> cfgContext,
                                     final ErrorRule rule);

}
