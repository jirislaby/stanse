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

import org.dom4j.Element;

import java.util.regex.Pattern;

/**
 * @brief Static checker which is able to detect unrechable code.
 *
 * @see cz.muni.stanse.checker.Checker
 */
final class ReachabilityChecker extends cz.muni.stanse.checker.Checker {
    private static Pattern exprCompoundPattern = Pattern.compile(
	    "[eE]xpression.*\\bcompoundStatement\\b");
    private static Pattern retCompoundPattern = Pattern.compile(
	    "\\breturnStatement\\b.*\\bcompoundStatement\\b");

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
	    CFGNode end = cfg.getEndNode();
	    for (CFGNode node: cfg.getAllNodesReverse()) {
		if (node.equals(start) || node.equals(end))
		    continue;
		if (!node.getPredecessors().isEmpty())
		    continue;
		if (!node.getOptPredecessors().isEmpty())
		    continue;
		Element nodeElem = node.getElement();
		String elemPath = nodeElem.getPath();

		/* we don't do CFGs of statements inside expressions ({ .. }) */
		if (exprCompoundPattern.matcher(elemPath).find())
		    continue;
		if (retCompoundPattern.matcher(elemPath).find())
		    continue;

		String elemName = nodeElem.getName();
		if (elemName.equals("intConst") &&
			nodeElem.getText().equals("0"))
		    continue;
		int importance = 0;
		StringBuilder fullDesc = new StringBuilder("The code is " +
			"unreachable by any path.");
		monitor.write("An error found");
		if (elemName.equals("emptyStatement") ||
			elemName.equals("breakStatement") ||
			elemName.equals("returnStatement")) {
		    importance += 3;
		    fullDesc.append(" Superfluous semicolon, break or return " +
			    "statement.");
		}
		Element parentElem = nodeElem.getParent();
		if (parentElem != null &&
			parentElem.getName().equals("forStatement") &&
			nodeElem.equals(parentElem.elements().get(2))) {
		    importance += 5;
		    fullDesc.append(" The third 'for' expression is never " +
			    "used.");
		}
		errReceiver.receive(new CheckerError("Unreachable code",
			fullDesc.toString(), importance,
			ReachabilityCheckerCreator.getNameForCheckerFactory(),
			Make.<CFGNode>linkedList(node, node),
			"This node is unreachable", "",
			"This node is unreachable", internals));
	    }
	}
	monitor.write("Reachability Checker finished");
	return result;
    }
}
