/**
 * @brief ReachabilityChecker implementation
 *
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.reachabilitychecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingSuccess;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.utils.Make;

/**
 * @brief Static checker which is able to detect unrechable code.
 *
 * @see cz.muni.stanse.checker.Checker
 */
final class ReachabilityChecker extends cz.muni.stanse.checker.Checker {

    // public section

    public ReachabilityChecker() {
        super();
    }

    /**
     * @brief Uniquely identifies the checker by the string identifier.
     *
     * @return String which uniquely identifies the checker.
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return ReachabilityCheckerCreator.getNameForCheckerFactory();
    }

    /**
     * @brief Does the source code checking itself.
     *
     * Method searches through source code to find nodes (except start node)
     * with no predecessors.
     *
     * @return List of errors found in the source code.
     * @see cz.muni.stanse.checker.Checker#check(java.util.List)
     */
    @Override
    public CheckingResult check(final LazyInternalStructures internals,
                                final CheckerErrorReceiver errReceiver,
                                final CheckerProgressMonitor monitor) {
	CheckingResult result = new CheckingSuccess();
	monitor.write("Starting Reachability Checker");
	for (CFGHandle cfg: internals.getCFGHandles()) {
	    CFGNode start = cfg.getStartNode();
	    for (CFGNode node: cfg.getAllNodesReverse()) {
		if (node.equals(start))
		    continue;
		if (!node.getPredecessors().isEmpty())
		    continue;
		monitor.write("An error found");
		errReceiver.receive(new CheckerError("Unreachable code",
			"The code is unreachable by any path.", 0,
			ReachabilityCheckerCreator.getNameForCheckerFactory(),
			Make.<CFGNode>linkedList(node, cfg.getEndNode()),
			"This node is unreachable", "",
			"This function leaves and some code was unreachable",
			internals));
	    }
	}
	monitor.write("Reachability Checker finished");
	return result;
    }
}
