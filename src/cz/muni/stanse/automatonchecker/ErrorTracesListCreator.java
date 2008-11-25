package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.xml2cfg.CFGNode;
import cz.muni.stanse.xml2cfg.CFGEdge;
import cz.muni.stanse.xml2cfg.ControlFlowGraph;

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
        this.predecessors = new LinkedList<ErrorTraceNode>();
        this.predecessors.add(predecessor);
    }

    ErrorTraceNode(final CFGNode node, final String message) {
        this.node = node;
        this.message = message;
        this.predecessors = new LinkedList<ErrorTraceNode>();
    }

    boolean isEqualWith(final ErrorTraceNode other) {
        return node.equals(other.node) &&
               message.equals(other.message) &&
               predecessors == other.predecessors;
    }

    CFGNode getCFGNode() {
        return node;
    }

    String getMessage() {
        return message;
    }

    LinkedList<ErrorTraceNode> getPredecessors() {
        return predecessors;
    }

    // private section

    private final CFGNode node;
    private final String message;
    private final LinkedList<ErrorTraceNode> predecessors;
}

final class ErrorTracesListCreator extends cz.muni.stanse.utils.CFGvisitor {

    // public section

    @Override
    public boolean visit(final CFGEdge edge, final org.dom4j.Element element)
                                                              throws Exception {
        final PatternLocation location = getEdgeLocationDictionary().get(edge);

        if (location == null) {
            /*
            // TODO: tady se pridava entry nod funkce, zatim je to zakomentovane
            //       protoze to neni zcela spravne. Poresit!
            if (edge.getFrom().equals(getCFG().getStartNode())) {
                addLeafTracedErrorNode(edge,getRule().getErrorEntryMessage());
                return false;
            }
            */
            addTracedErrorNode(edge,getRule().getErrorPropagMessage());
            return true;
        }

        if (!getRule().checkForError(location.getDeliveredAutomataStates()))
            return false;
        
        if (!getRule().checkForError(location.getProcessedAutomataStates())) {
           addLeafTracedErrorNode(edge,rule.getErrorBeginMessage());
           return false;
        }

        addTracedErrorNode(edge,getRule().getErrorPropagMessage());

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
        tracedErrorLeafNodes = new LinkedList<ErrorTraceNode>();
        tracedErrorNodes = new HashMap<CFGNode,ErrorTraceNode>();
        addInitialTracedErrorNode(edge,"Here we are in error state.");
        addTracedErrorNode(edge,rule.getErrorEndMessage());
    }

    ErrorTracesListCreator(final ErrorRule errorRule,
                           final HashMap<CFGEdge,PatternLocation> dictionary,
                           final CFGNode node, final ControlFlowGraph cfg) {
        super();
        rule = errorRule;
        CFG = cfg;
        edgeLocationDictionary = dictionary;
        tracedErrorLeafNodes = new LinkedList<ErrorTraceNode>();
        tracedErrorNodes = new HashMap<CFGNode,ErrorTraceNode>();
        addInitialTracedErrorNode(node,rule.getErrorEndMessage());
    }

    LinkedList<ErrorTrace> GetTraceErrorsList() {
        final LinkedList< LinkedList<ErrorTraceNode> > errorTraces =
            new LinkedList< LinkedList<ErrorTraceNode> >();
        for (ErrorTraceNode traceNode : getTracedErrorLeafNodes())
            errorTraces.addAll(buildErrorTracesFromNode(traceNode,
                                                new HashSet<ErrorTraceNode>()));
        final int numTraces = errorTraces.size();
        int traceCounter = 0;
        final LinkedList<ErrorTrace> result =
            new LinkedList<ErrorTrace>();
        for (LinkedList<ErrorTraceNode> trace : errorTraces) {
            ++traceCounter;
            result.add(
                new ErrorTrace(
                        "error-trace [" + traceCounter + "/" + numTraces + "]",
                        // TODO: full description should contain little more
                        //       info then short one. :-)
                        "error-trace [" + traceCounter + "/" + numTraces + "]",
                        buildTraceErrorNodesListTrace(trace)));
        }

        return result;
    }

    // private section

    private boolean addInitialTracedErrorNode(final CFGNode node,
                                              final String msg) {
        getTracedErrorNodes().put(node,
                lastTraceNode = new ErrorTraceNode(node,msg));
        return true;
    }

    private boolean addInitialTracedErrorNode(final CFGEdge edge,
                                              final String msg) {
        return addInitialTracedErrorNode(edge.getTo(),msg);
    }

    private boolean addTracedErrorNode(final CFGEdge edge, final String msg) {
        if (getLastTraceNode() == null)
            lastTraceNode = getTracedErrorNodes().get(edge.getTo());
        assert(getLastTraceNode() != null);
        final ErrorTraceNode tracedNode =
                    getTracedErrorNodes().get(edge.getFrom());
        if (tracedNode != null) {
            tracedNode.getPredecessors().add(getLastTraceNode());
            lastTraceNode = tracedNode;
            return false;
        }
        else
            getTracedErrorNodes().put(edge.getFrom(),
                  lastTraceNode = new ErrorTraceNode(edge.getFrom(),
                                                     msg,getLastTraceNode()));
        return true;
    }

    private void addLeafTracedErrorNode(final CFGEdge edge, final String msg) {
        addTracedErrorNode(edge,msg);
        getTracedErrorLeafNodes().add(getLastTraceNode());
        lastTraceNode = null;
    }

    private static LinkedList< LinkedList<ErrorTraceNode> >
    buildErrorTracesFromNode(final ErrorTraceNode node,
                        final HashSet<ErrorTraceNode> crossedNodes) {
        if (crossedNodes.contains(node))
            return null;
        
        final LinkedList< LinkedList<ErrorTraceNode> > result =
            new LinkedList< LinkedList<ErrorTraceNode> >();
        
        if (node.getPredecessors().isEmpty()) {
            final LinkedList<ErrorTraceNode> newTrace =
                new LinkedList<ErrorTraceNode>();
            newTrace.add(node);
            result.add(newTrace);
            return result;
        }

        crossedNodes.add(node);
        for (ErrorTraceNode predNode : node.getPredecessors()) {
            final LinkedList< LinkedList<ErrorTraceNode> > temp =
                buildErrorTracesFromNode(predNode,
                                     new HashSet<ErrorTraceNode>(crossedNodes));
            if (temp != null)
                result.addAll(temp);
        }
        for (LinkedList<ErrorTraceNode> trace : result)
            trace.addFirst(node);
        
        return result;
    }

    private static LinkedList< Pair<CFGNode,String> >
    buildTraceErrorNodesListTrace(final LinkedList<ErrorTraceNode> trace) {
        final LinkedList< Pair<CFGNode,String> > result =
            new LinkedList< Pair<CFGNode,String> >();

        for (ErrorTraceNode node : trace)
            result.add(new Pair<CFGNode,String>(node.getCFGNode(),
                                                node.getMessage()));

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

    private LinkedList<ErrorTraceNode> getTracedErrorLeafNodes() {
        return tracedErrorLeafNodes;
    }

    private HashMap<CFGNode,ErrorTraceNode> getTracedErrorNodes() {
        return tracedErrorNodes;
    }

    private ErrorTraceNode getLastTraceNode() {
        return lastTraceNode;
    }

    private final ErrorRule rule;
    private final HashMap<CFGEdge,PatternLocation> edgeLocationDictionary;
    private final ControlFlowGraph CFG;
    private final LinkedList<ErrorTraceNode> tracedErrorLeafNodes;
    private final HashMap<CFGNode,ErrorTraceNode> tracedErrorNodes;
    private ErrorTraceNode lastTraceNode;
}
