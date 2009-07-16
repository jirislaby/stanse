
package cz.muni.stanse.threadchecker.graph;

import cz.muni.stanse.PresentableError;
import cz.muni.stanse.PresentableErrorTrace;
import cz.muni.stanse.threadchecker.ThreadInfo;
import cz.muni.stanse.threadchecker.exceptions.CycleException;
import cz.muni.stanse.threadchecker.locks.Lock;
import cz.muni.stanse.utils.Triple;
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
 * @author Jan Kučera
 */
public class RAG {
    private DirectedMultigraph<Vertex, Edge> graph;
    private CycleDetector<Vertex, Edge> detector;
    private Map<String,Vertex> vertexes = new HashMap<String,Vertex>();
    private final static Logger logger = Logger.getLogger(RAG.class);

    public RAG() {
        this. graph = new DirectedMultigraph<Vertex, Edge>(
                new ClassBasedEdgeFactory<Vertex, Edge>(Edge.class));
        this.detector = new CycleDetector<Vertex, Edge>(graph);
    }

    /**
     * Function analyse Cycle and tell, whether deadlock can happen.
     * @param cycle Cycle representing assignment and requirement edges
     * @return CheckerError
     */
    public PresentableError detectDeadlock(Cycle cycle) {
        DependencyRule ruleToProcess;
        List<DependencyRule> processedRules = new Vector<DependencyRule>();
        PresentableError error;

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
        ThreadInfo thread = rule.getThread();
        String name = String.valueOf(thread.getId());
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
     * Function tries to insert edge to graph and also respects RAG rules
     * @param edge
     * @throws RAGException whether RAG can't be build - resource would be
     * held by two processes.
     */
    private void addEdge(Edge edge) throws RAGException {
        Vertex resource;
        Vertex process;
        String reason;

        resource = edge.getResource();
        process = edge.getProcess();
        if(edge instanceof Assignment) {
            if(graph.outgoingEdgesOf(resource).size()>0) {
                //Can't add edge because resource is already held by other
                //process -> deadlock can't happen
                //Edge is created only for showing, where algorithm stopped
                reason = "deadlock can't happen - thread "+process.getName()+
                            " requires "+resource.getName()
                            +" which is already held by "
                            +graph.outgoingEdgesOf(resource).iterator().next()
                                            .getProcess().getName();
                graph.addEdge(resource, process, edge);
                throw new RAGException(reason);
            }
            graph.addEdge(resource, process, edge);
            return;
        } else {
            graph.addEdge(process, resource, edge);
            return;
        }
    }

    /**
     * Returns string representation of graph in toDot format
     * @return
     */
    public String toDot() {
        String result = "digraph CFG {";
        for(Vertex vertex : vertexes.values()) {
            result += vertex.toDot()+"\n";
        }
        for(Edge edge : graph.edgeSet()) {
            result += edge.toDot()+"\n";
        }
        result += "}";
        return result;
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
    private PresentableError generateError(Cycle cycle) throws CycleException {
        LinkedList<PresentableErrorTrace> errorTrace
                                     = new LinkedList<PresentableErrorTrace>();
        List<Triple<Integer,String,String>> trace;
        Triple<Integer,String,String> traceNode;
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
            trace = new Vector<Triple<Integer,String,String>>();

            for(it = backTrack.iterator(); it.hasNext(); ) {
                backTrackNode = it.next();
                traceNode = new Triple<Integer,String,String>(null, null, null);
                traceNode.setFirst(backTrackNode.getLine());
                traceNode.setSecond(backTrackNode.getDescription());
                traceNode.setThird(backTrackNode.getUnitFilename());
                trace.add(traceNode);
                if(backTrackNode.getCFGNodeID().equals(nodeId)) {
                    //Is't last lock from this thread - end backTrack
                    break;
                }
            }
            PresentableErrorTrace error = new PresentableErrorTrace(
                                    "Thread "+thread.getId()+" ("
                                    +thread.getFunctionName()+") lock rules:",
                                cycle.getRulesByThread().get(thread).toString(),
                                    trace);
            errorTrace.add(error);

        }

        //Level count := Number of locks*2 + number of threads *2
        int level = cycle.getResources().size()*2
                +cycle.getRulesByThread().keySet().size()*2;
        return new PresentableError("Deadlock found",description,level,
                                                                    errorTrace);
    }

    /**
     * Function creates CheckError describing what type of warning was found.
     * @param cycle Cycle
     * @param message String which should be inserted to CheckerError
     * @return CheckerError
     */
    private PresentableError generateWarning(Cycle cycle, String message) {
        Set<Integer> traceNodes = new HashSet<Integer>();
        List<Triple<Integer,String,String>> trace;
        LinkedList<PresentableErrorTrace> errorTraces
                                    = new LinkedList<PresentableErrorTrace>();
        Triple<Integer,String,String> traceNode;
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
            trace = new Vector<Triple<Integer,String,String>>();
            for(it = backTrack.iterator(); it.hasNext(); ) {
                backTrackNode = it.next();
                traceNode = new Triple<Integer, String, String>(null,null,null);
                traceNode.setFirst(backTrackNode.getLine());
                traceNode.setSecond(backTrackNode.getDescription());
                traceNode.setThird(backTrackNode.getUnitFilename());
                
                if(traceNodes.contains(traceNode.getFirst())) {
                    trace.add(traceNode);
                    traceNodes.remove(traceNode.getFirst());
                    if(traceNodes.size()<1) {
                        break;
                    }
                } else {
                    trace.add(traceNode);
                }
            }
            PresentableErrorTrace errorTrace = new PresentableErrorTrace(
                                        "Thread "+thread.getId()+" ("
                                    +thread.getFunctionName()+") lock rules:",
                                cycle.getRulesByThread().get(thread).toString(),
                                        trace);
            errorTraces.add(errorTrace);
        }

        int level = (cycle.getResources().size()*2
                +cycle.getRulesByThread().keySet().size()*2)*10;
        PresentableError error = new PresentableError("Cycle warning",message,
                                                            level, errorTraces);
        return error;
    }

    /**
     * Method generated string description for process.
     * @param process Vertex node in RAG
     * @return String desription for thread represented by Vertex process
     * @throws cz.muni.stanse.threadchecker.exceptions.CycleException
     */
    private String createThreadDescription(Vertex process)
                                                        throws CycleException{
            String description;
            Set<Edge> outgoingEdges;
            Edge requestEdge;

            description =" Thread "+process.getName()+" holds [";
            //Collect all incomming edges (assignment)
            //--> mark all locks which thread helds
            for(Edge incomingEdge : this.graph.incomingEdgesOf(process)) {
                description += incomingEdge.getResource().getName()+" ";
            }
            description += "] and requires [";

            outgoingEdges = this.graph.outgoingEdgesOf(process);
            if(outgoingEdges.size() > 1) {
                throw new CycleException("RAG has more outgoing edges");
            }
            requestEdge = outgoingEdges.iterator().next();
            description += requestEdge.getResource().getName()+"]\n";
            return description;
    }
}
