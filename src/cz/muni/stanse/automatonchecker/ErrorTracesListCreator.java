/**
 * @file .java
 * @brief 
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Triple;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

final class ErrorTracesListCreator extends cz.muni.stanse.utils.CFGPathVisitor {

    // public section

    @Override
    public boolean visit(final List<CFGNode> path) {
        final CFGNode node = path.iterator().next();

        if (getStartNode().equals(node))
            return true;

        final PatternLocation location = getNodeLocationDictionary().get(node);

        if (location == null)
            return true;

        if (!getRule().checkForError(location.getDeliveredAutomataStates()))
            return false;

        if (!getRule().checkForError(location.getProcessedAutomataStates())) {
            getErrorTracesList().add(buildErrorTrace(
                    getRule().getErrorBeginMessage(),
                    getRule().getErrorPropagMessage(),
                    getRule().getErrorEndMessage(),
                    path));
            return false;
        }

        if (node.equals(getCFG().getStartNode())) {
            getErrorTracesList().add(buildErrorTrace(
                    getRule().getErrorEntryMessage(),
                    getRule().getErrorPropagMessage(),
                    getRule().getErrorEndMessage(),
                    path));
            return false;
        }

        return true;
    }

    // package-private section

    ErrorTracesListCreator(final ErrorRule errorRule,
                           final HashMap<CFGNode,PatternLocation> dictionary,
                           final CFGNode node, final CFG cfg) {
        super();
        rule = errorRule;
        this.cfg = cfg;
        nodeLocationDictionary = dictionary;
        startNode = node;
        errorTracesList = new LinkedList<ErrorTrace>();
    }

    LinkedList<ErrorTrace> getErrorTracesList() {
        return errorTracesList;
    }

    // private section

    private ErrorTrace buildErrorTrace(final String beginMsg,
                                       final String innerMsg,
                                       final String endMsg,
                                       final List<CFGNode> path) {
        final List< Triple<CFGNode,String,CFG> > trace =
                                new LinkedList< Triple<CFGNode,String,CFG> >();
        trace.add(new Triple<CFGNode,String,CFG>(path.get(0),
                                                  beginMsg,getCFG()));
        if (path.size() > 1)
            for (CFGNode item : path.subList(1,path.size() - 1))
                trace.add(new Triple<CFGNode,String,CFG>
                                                      (item,innerMsg,getCFG()));
        trace.add(new Triple<CFGNode,String,CFG>(path.get(path.size() - 1),
                                                  endMsg,getCFG()));
        return new ErrorTrace(
                        "error-trace [" + (getErrorTracesList().size()+1) + "]",
                        // TODO: full description should contain little more
                        //       info then short one. :-)
                        "error-trace [" + (getErrorTracesList().size()+1) + "]",
                        trace);
    }

    private ErrorRule getRule() {
        return rule;
    }

    private HashMap<CFGNode,PatternLocation> getNodeLocationDictionary() {
        return nodeLocationDictionary;
    }

    private CFG getCFG() {
        return cfg;
    }

    private CFGNode getStartNode() {
        return startNode;
    }

    private final ErrorRule rule;
    private final HashMap<CFGNode,PatternLocation> nodeLocationDictionary;
    private final CFG cfg;
    private final CFGNode startNode;
    private final LinkedList<ErrorTrace> errorTracesList;
}
