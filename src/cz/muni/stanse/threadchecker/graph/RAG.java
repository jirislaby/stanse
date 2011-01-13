
package cz.muni.stanse.threadchecker.graph;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.threadchecker.ThreadInfo;
import cz.muni.stanse.threadchecker.exceptions.CycleException;
import cz.muni.stanse.threadchecker.locks.Lock;
import cz.muni.stanse.threadchecker.exceptions.RAGException;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * Class represents RAG graph which determines, whether deadlock can happen
 * @author Jan Kucera
 */
public class RAG {
    private DirectedMultigraph<Vertex, Edge> graph;
    private CycleDetector<Vertex, Edge> detector;
    private Map<String,Vertex> vertexes = new HashMap<String,Vertex>();
    private final static Logger logger = Logger.getLogger(RAG.class);

    public RAG() {
        this.graph = new DirectedMultigraph<Vertex, Edge>(
                new ClassBasedEdgeFactory<Vertex, Edge>(Edge.class));
        this.detector = new CycleDetector<Vertex, Edge>(graph);
    }

    /**
     * Analyses Cycle and tells, whether a deadlock may happen.
     * @param cycle Cycle representing assignment and requirement edges
     * @return CheckerError
     */
    public CheckerError detectDeadlock(Cycle cycle) {
        DependencyRule ruleToProcess;
        List<DependencyRule> processedRules = new Vector<DependencyRule>();
        CheckerError error;

        this.clearGraph();

        ruleToProcess = this.chooseStartRule(cycle);

        //traverse all rules by their dependecy
        while(cycle.getRules().size()>=processedRules.size()) {
            try {
                this.addRule(ruleToProcess);
            } catch (RAGException ex) {
                error =  this.generateWarning(cycle,"Cycle occured between "
                                                +cycle.getResourceNames()
                                                    +" but "+ex.getMessage());
                return error;
            }

            if(this.detector.detectCycles()) {
                try {
                     error = this.generateError(cycle);
                     return error;
                } catch (CycleException ex) {
                    logger.error(ex.getMessage());
                }
            }

            processedRules.add(ruleToProcess);
            //pick one which targetNode is the same as sourceNode of actual rule
            ruleToProcess = (DependencyRule) cycle.getRulesByTargetNode().get(
                                            ruleToProcess.getSource());
        }
        error = this.generateWarning(cycle, "Cycle occured between "
                                                +cycle.getResourceNames()
                                        +" but RAG couldn't generate cycle");
        return error;
    }
    
    /**
     * Method choose DependencyRule which start cycle analysis.
     * @param cycle Cycle
     * @return DependencyRule
     */
    private DependencyRule chooseStartRule(Cycle cycle) {
        DependencyRule startRule = null;
        int numberOfRules=Integer.MAX_VALUE;
        ThreadInfo smallestThread = null;
        //pick start rule from thread with only one rule
        //or pick thread with least rules.
        for(ThreadInfo key : cycle.getRulesByThread().keySet()) {
            if(cycle.getRulesByThread().get(key).size()<2) {
                startRule = cycle.getRulesByThread().get(key).get(0);
                break;
            }
            if(numberOfRules > cycle.getRulesByThread().get(key).size()) {
                numberOfRules = cycle.getRulesByThread().get(key).size();
                smallestThread = key;
            }
        }

        //choose startRule if it isn't decided yet
        if(startRule == null) {
           boolean loop=true;
           startRule = cycle.getRulesByThread().get(smallestThread).get(0);
           DependencyRule initialRule = startRule;

           //This section is vulnerable to infite cycle when rule cycle
           //has small cycles inside
           while(loop) {
               loop=false;
               DependencyRule rule = (DependencyRule)
                       cycle.getRulesBySourceNode().get(startRule.getTarget());
               if(rule.getThread().equals(smallestThread)) {
                   startRule = rule;
                   loop=true;
                   break;
               }
               //Break cycle in one thread
               if(initialRule.equals(startRule)) {
                   break;
               }
           }
        }
        return startRule;
    }

    /**
     * Method deletes all vertexes containing process and add all requests and
     * assignment, which is represented by rule.
     * @param rule DependencyRule
     * @throws RAGException when rule cannot added to graph => no deadlock
     * can happen
     */
    public void addRule(DependencyRule rule) throws RAGException {
        final ThreadInfo thread = rule.getThread();
        final String name = String.valueOf(thread.getId());
        Vertex process = this.vertexes.get(name);
        Vertex resource;
        Request request;

        //Process isn't already in graph - create one
        if(process == null) {
            process = new ProcessVertex(name);
            this.vertexes.put(name, process);
            this.graph.addVertex(process);
        } else {
            //Process exists - clear all assigns and requests which process has
            Set<Edge> relatedToProcess = this.graph.edgesOf(process);
            this.graph.removeAllEdges(relatedToProcess);
        }

        //Generate assignment which state has at this moment
        this.addAssignmentEdges(rule, process);

        resource = this.vertexes.get(rule.getSource().getName());
        if(resource == null) {
            resource = rule.getSource();
            this.vertexes.put(resource.getName(), resource);
            this.graph.addVertex(resource);
        }
        request = new Request(process, resource);
        this.addEdge(request);
    }

    /**
     * Method creates all assignment edges inserted in DependencyRule
     * @param rule
     * @param process
     * @throws RAGException RAG can't be build
     */
    private void addAssignmentEdges(DependencyRule rule, Vertex process)
                                                           throws RAGException {
        Vertex resource;
        Assignment assignment;

        for(Lock lock : rule.getLockStack().getLocks()) {
            resource = vertexes.get(lock.getName());
            if(resource == null) { //Resource doesn't exist - create one
                resource = new ResourceVertex(lock.getName(),
                                                        lock.getNodeNumber());
                vertexes.put(lock.getName(), resource);
                graph.addVertex(resource);
            }

            assignment = new Assignment(resource,process);
            this.addEdge(assignment);
        }
    }

	/**
	 * Function tries to insert an edge to the graph and also respects RAG rules
	 * @param edge
	 * @throws RAGException if RAG can't be built - resource would be
	 * held by two processes.
	 */
	private void addEdge(final Edge edge) throws RAGException {
		final Vertex resource = edge.getResource();
		final Vertex process = edge.getProcess();
		String reason;

		if (edge instanceof Assignment) {
			if (graph.outgoingEdgesOf(resource).size() > 0) {
				/*
				 * Can't add edge because resource is already held by other
				 * process -> deadlock can't happen
				 * Edge is created only for showing, where algorithm stopped
				 */
				reason = "deadlock can't happen - thread " + process.getName()
					+ " requires " + resource.getName()
					+ " which is already held by "
					+ graph.outgoingEdgesOf(resource).iterator().next().getProcess().getName();
				graph.addEdge(resource, process, edge);
				throw new RAGException(reason);
			}
			graph.addEdge(resource, process, edge);
		} else {
			graph.addEdge(process, resource, edge);
		}
	}

	/**
	 * Returns string representation of graph in toDot format
	 * @return graph in toDot format
	 */
	public String toDot() {
		final StringBuilder result = new StringBuilder("digraph CFG {");
		for (final Vertex vertex : vertexes.values())
			result.append(vertex.toDot()).append('\n');
		for (final Edge edge : graph.edgeSet())
			result.append(edge.toDot()).append('\n');
		result.append('}');
		return result.toString();
	}

    /**
     * Clear this graph - erase vertexes, create new grap and detector
     */
    private void clearGraph() {
        this.graph = new DirectedMultigraph<Vertex, Edge>(
                new ClassBasedEdgeFactory<Vertex, Edge>(Edge.class));
        this.detector = new CycleDetector<Vertex, Edge>(graph);
        this.vertexes.clear();
    }

    /**
     * Function creates CheckError describing what type of error program found.
     * @param cycle Cycle
     * @return CheckError describing error occurence
     * @throws CycleException
     */
    private CheckerError generateError(Cycle cycle) throws CycleException {
        LinkedList<CheckerErrorTrace> errorTrace
                                     = new LinkedList<CheckerErrorTrace>();
        CheckerErrorTraceLocation traceNode;
        BackTrack backTrackNode;
        Iterator<BackTrack> it;
        String description = "";
        DependencyRule rule;
        LinkedList<BackTrack> backTrack;
        Edge requestEdge;
        Integer nodeId;
        ThreadInfo thread;

        //For every thread create description and backtrace
        for(Vertex vertex : this.graph.vertexSet()) {
            if(vertex instanceof ResourceVertex)
                continue;
            description += this.createThreadDescription(vertex);
            requestEdge = this.graph.outgoingEdgesOf(vertex).iterator().next();
            //Get rule which simulates request edge for this thread
            rule = (DependencyRule) cycle.getRulesBySourceNode().get(
                                                    requestEdge.getResource());
            backTrack = rule.getBackTrack();
            thread = rule.getThread();
            nodeId = rule.getSource().getNodeID();

            final LinkedList<CheckerErrorTraceLocation> trace
                    = new LinkedList<CheckerErrorTraceLocation>();
            for(it = backTrack.iterator(); it.hasNext(); ) {
                backTrackNode = it.next();
                traceNode = new CheckerErrorTraceLocation(
                        backTrackNode.getUnitFilename(),backTrackNode.getLine(),
                        backTrackNode.getColumn(),backTrackNode.getDescription());
                trace.add(traceNode);
                if(backTrackNode.getCFGNodeID().equals(nodeId)) {
                    //Is't last lock from this thread - end backTrack
                    break;
                }
            }
            CheckerErrorTrace error = new CheckerErrorTrace(trace,
                        "Thread "+thread.getId()+" ("+thread.getFunctionName()+
                        ") lock rules:" +
                        "{"+cycle.getRulesByThread().get(thread).toString()+"}"
                        );
            errorTrace.add(error);

        }

        //Level count := Number of locks*2 + number of threads *2
        int level = cycle.getResources().size()*2
                +cycle.getRulesByThread().keySet().size()*2;
        return new CheckerError("Deadlock found",description,level,
                                "ThreadChecker",errorTrace);
    }

    /**
     * Function creates CheckError describing what type of warning was found.
     * @param cycle Cycle
     * @param message String which should be inserted to CheckerError
     * @return CheckerError
     */
    private CheckerError generateWarning(Cycle cycle, String message) {
        Set<Integer> traceNodes = new HashSet<Integer>();
        LinkedList<CheckerErrorTraceLocation> trace
                                  = new LinkedList<CheckerErrorTraceLocation>();
        LinkedList<CheckerErrorTrace> errorTraces
                                    = new LinkedList<CheckerErrorTrace>();
        CheckerErrorTraceLocation traceNode;
        BackTrack backTrackNode;
        Iterator<BackTrack> it;
        LinkedList<BackTrack> backTrack;
        
        for(ThreadInfo thread : cycle.getRulesByThread().keySet()) {
            traceNodes.clear();
            for(DependencyRule rule : cycle.getRulesByThread().get(thread)) {
                traceNodes.add(rule.getSource().getNodeID());
            }
            backTrack = cycle.getRulesByThread().get(
                                                  thread).get(0).getBackTrack();
            for(it = backTrack.iterator(); it.hasNext(); ) {
                backTrackNode = it.next();
                traceNode = new CheckerErrorTraceLocation(
                        backTrackNode.getUnitFilename(),backTrackNode.getLine(),
                        backTrackNode.getColumn(),
                        backTrackNode.getDescription());
                
                if(traceNodes.contains(backTrackNode.getLine())) {
                    trace.add(traceNode);
                    traceNodes.remove(backTrackNode.getLine());
                    if(traceNodes.size()<1) {
                        break;
                    }
                } else {
                    trace.add(traceNode);
                }
            }
            CheckerErrorTrace errorTrace = new CheckerErrorTrace(trace,
                        "Thread "+thread.getId()+" ("+thread.getFunctionName()+
                        ") lock rules:"+
                        "{"+cycle.getRulesByThread().get(thread).toString()+"}"
                        );
            errorTraces.add(errorTrace);
        }

        int level = (cycle.getResources().size()*2
                +cycle.getRulesByThread().keySet().size()*2)*10;
        CheckerError error = new CheckerError("Cycle warning",message,
                                             level,"ThreadChecker",errorTraces);
        return error;
    }

    /**
     * Method generated string description for process.
     * @param process Vertex node in RAG
     * @return String desription for thread represented by Vertex process
     * @throws cz.muni.stanse.threadchecker.exceptions.CycleException
     */
    private String createThreadDescription(Vertex process)
	    throws CycleException {
	StringBuilder description = new StringBuilder();
	Set<Edge> outgoingEdges;
	Edge requestEdge;

	description.append(" Thread ").append(process.getName()).
		append(" holds [");
	//Collect all incomming edges (assignment)
	//--> mark all locks which thread helds
	for (Edge incomingEdge: graph.incomingEdgesOf(process))
	    description.append(incomingEdge.getResource().getName()).
		append(' ');

	description.append("] and requires [");

	outgoingEdges = graph.outgoingEdgesOf(process);
	if(outgoingEdges.size() > 1) {
	    throw new CycleException("RAG has more outgoing edges");
	}
	requestEdge = outgoingEdges.iterator().next();
	description.append(requestEdge.getResource().getName()).
		append("]\n");
	return description.toString();
    }
}
