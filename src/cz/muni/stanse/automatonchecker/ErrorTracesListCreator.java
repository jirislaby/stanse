package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.parser.CFGEdge;
import cz.muni.stanse.parser.ControlFlowGraph;
import cz.muni.stanse.utils.Pair;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

final class ErrorTraceNode {

    // public section

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((message == null) ? 0 : message.hashCode());
        result = PRIME * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                    false : isEqualWith((ErrorTraceNode)obj);
    }

    // package-private section

    ErrorTraceNode(final CFGNode node, final String message,
                   final ErrorTraceNode predecessor) {
        this.node = node;
        this.message = message;
        this.predecessors = new HashSet<ErrorTraceNode>();
        this.predecessors.add(predecessor);
    }

    ErrorTraceNode(final CFGNode node, final String message) {
        this.node = node;
        this.message = message;
        this.predecessors = new HashSet<ErrorTraceNode>();
    }

    boolean isEqualWith(final ErrorTraceNode other) {
        return node.equals(other.node);
    }

    CFGNode getCFGNode() {
        return node;
    }

    String getMessage() {
        return message;
    }

    HashSet<ErrorTraceNode> getPredecessors() {
        return predecessors;
    }

    // private section

    private final CFGNode node;
    private final String message;
    private final HashSet<ErrorTraceNode> predecessors;
}

final class ErrorTracesListCreator extends cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGEdge edge, final org.dom4j.Element element)
                                                              throws Exception {
        if (getStartEdge().equals(edge))
            return false;

        final PatternLocation location = getEdgeLocationDictionary().get(edge);

        if (location == null) {
            addTracedErrorEdge(edge,getRule().getErrorPropagMessage());
            return true;
        }

        if (!getRule().checkForError(location.getDeliveredAutomataStates()))
            return false;

        if (!getRule().checkForError(location.getProcessedAutomataStates())) {
            getTracedErrorLeafNodes().add(
                          addTracedErrorEdge(edge,rule.getErrorBeginMessage()));
            return false;
        }

        if (edge.equals(getCFG().getEntryEdge())) {
            getTracedErrorLeafNodes().add(
                          addTracedErrorEdge(edge,rule.getErrorEntryMessage()));
            return false;
        }

        addTracedErrorEdge(edge,getRule().getErrorPropagMessage());
        return true;
    }

    // package-private section

    ErrorTracesListCreator(final ErrorRule errorRule,
                           final HashMap<CFGEdge,PatternLocation> dictionary,
                           final CFGEdge edge, final ControlFlowGraph cfg) {
        super();
        rule = errorRule;
        CFG = cfg;
        edgeLocationDictionary = dictionary;
        startEdge = edge;
        tracedErrorLeafNodes = new HashSet<ErrorTraceNode>();
        tracedErrorNodes = new HashMap<CFGNode,ErrorTraceNode>();
        addInitialTracedErrorEdge(edge);
    }

    LinkedList<ErrorTrace> GetTraceErrorsList() {
        final LinkedList<ErrorTrace> result = new LinkedList<ErrorTrace>();

        final LinkedList< LinkedList< Pair<CFGNode,String> > > errorTraceNodes =
                buildErrorTraceNodes();
        int traceCounter = 0;
        final int numTraces = errorTraceNodes.size();
        for (LinkedList< Pair<CFGNode,String> > trace : errorTraceNodes) {
            ++traceCounter;
            result.add(new ErrorTrace(
                        "error-trace [" + traceCounter + "/" + numTraces + "]",
                        // TODO: full description should contain little more
                        //       info then short one. :-)
                        "error-trace [" + traceCounter + "/" + numTraces + "]",
                        trace));
        }

        return result;
    }

    // private section

    private void addInitialTracedErrorEdge(final CFGEdge edge) {
        final ErrorTraceNode fromTraceNode = new ErrorTraceNode(edge.getTo(),
                                  "Here we are in error state.");
        final ErrorTraceNode toTraceNode = new ErrorTraceNode(edge.getFrom(),
                                  getRule().getErrorEndMessage(),fromTraceNode);
        getTracedErrorNodes().put(edge.getTo(),fromTraceNode);
        getTracedErrorNodes().put(edge.getFrom(),toTraceNode);
    }

    private ErrorTraceNode addTracedErrorEdge(final CFGEdge edge,
                                              final String msg) {
        final ErrorTraceNode fromTraceNode =
                                     getTracedErrorNodes().get(edge.getTo());
        assert(fromTraceNode != null);

        ErrorTraceNode toTraceNode = getTracedErrorNodes().get(edge.getFrom());
        if (toTraceNode != null)
            toTraceNode.getPredecessors().add(fromTraceNode);
        else
            getTracedErrorNodes().put(edge.getFrom(),toTraceNode =
                          new ErrorTraceNode(edge.getFrom(),msg,fromTraceNode));

        return toTraceNode;
    }

    private LinkedList< LinkedList< Pair<CFGNode,String> > >
    buildErrorTraceNodes() {
        final LinkedList< LinkedList< Pair<CFGNode,String> > > result =
            new LinkedList< LinkedList< Pair<CFGNode,String> > >();

        for (ErrorTraceNode traceNode : getTracedErrorLeafNodes())
            result.addAll(buildErrorTraceNodesFromNode(traceNode,
                                                new HashSet<ErrorTraceNode>()));
        return result;
    }

    private static LinkedList< LinkedList< Pair<CFGNode,String> > >
    buildErrorTraceNodesFromNode(final ErrorTraceNode node,
                             final HashSet<ErrorTraceNode> crossedNodes) {
        if (crossedNodes.contains(node))
            return null;
        
        final LinkedList< LinkedList< Pair<CFGNode,String> > > result =
            new LinkedList< LinkedList< Pair<CFGNode,String> > >();

        if (node.getPredecessors().isEmpty()) {
            final LinkedList< Pair<CFGNode,String> > newTrace =
                new LinkedList< Pair<CFGNode,String> >();
            newTrace.add(new Pair<CFGNode,String>(node.getCFGNode(),
                                                  node.getMessage()));
            result.add(newTrace);
            return result;
        }

        crossedNodes.add(node);
        for (ErrorTraceNode predNode : node.getPredecessors()) {
            final LinkedList< LinkedList< Pair<CFGNode,String> > > temp =
                buildErrorTraceNodesFromNode(predNode,
                                     new HashSet<ErrorTraceNode>(crossedNodes));
            if (temp != null)
                result.addAll(temp);
        }

        final Pair<CFGNode,String> item =
                new Pair<CFGNode,String>(node.getCFGNode(),
                                         node.getMessage());
        for (LinkedList< Pair<CFGNode,String> > trace : result)
            trace.addFirst(item);

        return result;
    }

    private ErrorRule getRule() {
        return rule;
    }

    private HashMap<CFGEdge,PatternLocation> getEdgeLocationDictionary() {
        return edgeLocationDictionary;
    }

    private ControlFlowGraph getCFG() {
        return CFG;
    }

    private CFGEdge getStartEdge() {
        return startEdge;
    }

    private HashSet<ErrorTraceNode> getTracedErrorLeafNodes() {
        return tracedErrorLeafNodes;
    }

    private HashMap<CFGNode,ErrorTraceNode> getTracedErrorNodes() {
        return tracedErrorNodes;
    }

    private final ErrorRule rule;
    private final HashMap<CFGEdge,PatternLocation> edgeLocationDictionary;
    private final ControlFlowGraph CFG;
    private final CFGEdge startEdge;
    private final HashSet<ErrorTraceNode> tracedErrorLeafNodes;
    private final HashMap<CFGNode,ErrorTraceNode> tracedErrorNodes;
}
