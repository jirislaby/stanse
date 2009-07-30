package cz.muni.stanse.threadchecker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.threadchecker.graph.DependencyGraph;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Class represent thread with specified Function. ThreadInfo holds its
 * Function, id, and its purpose is shell from Function class.
 * @author Jan Kučera
 */
public class ThreadInfo {
    private static int actualId=0;
    private final int id;
    private boolean isFinished=false;
    private final String functionName;
    private Function function;
    private Set<DependencyGraph> dependencyGraphs;
    private final static Logger logger =
                                Logger.getLogger(ThreadChecker.class.getName());
    private static CheckerSettings checkerSettings
                                                = CheckerSettings.getInstance();

    @SuppressWarnings("static-access")
    public ThreadInfo(CFGHandle cfg) {
        if(cfg == null) {
            throw new NullPointerException("CFG is null");
        }
        this.id = this.actualId;
        this.actualId++;
        this.functionName = cfg.getFunctionName();
        this.buildInitialGraph(cfg);
    }

    public int getId() {
        return id;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public Function getFunction() {
        return function;
    }

    /**
     * Function return string representation of thread showing ID and Datalist.
     * @return String
     */
    @Override
    public String toString() {
        String result = "Thread "+id+": with "+function.getFunctionStates();
        return result;
    }

    public Set<DependencyGraph> getDependencyGraphs() {
        return this.dependencyGraphs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ThreadInfo other = (ThreadInfo) obj;
        return(id == other.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash *= id;
        return hash;
    }
    
    /**
     * Method builds Function from CFG and stores it to checkerSettings
     * @param cfg CFG
     */
    private void buildInitialGraph(CFGHandle cfg) {
        DependencyGraph graph;
        logger.info("===============");
        logger.info("Analyzing thread: "+this.getFunctionName());
        logger.info("===============");

        if(this.function == null) {
            this.function = checkerSettings.getFunction(cfg);
        }

        //Checker settings hasn't function already processed
        if(this.function == null) {
            this.function = CFGTransit.analyseCFG(cfg);
            checkerSettings.addFunction(function, cfg);
        }

        this.dependencyGraphs = new HashSet<DependencyGraph>();

        //Add to every rule thread where it created
        //Create temporary dependencyGraph for avoiding dependency deadlock
        for(FunctionState currentState : function.getFunctionStates()) {
            graph = currentState.generateGraph(this);
            //Insert only new FunctionState
            this.dependencyGraphs.add(graph);
        }
        this.reduceDependencyGraphs();
    }

    /**
     * Method erase same or subset graph in dependencyGraphs.
     */
    private void reduceDependencyGraphs() {
        if(dependencyGraphs.size()<2)
            return;

        Iterator<DependencyGraph> mainIterator
                                            = this.dependencyGraphs.iterator();
        Iterator<DependencyGraph> cycleIt;
        DependencyGraph selectedGraph;
        DependencyGraph otherGraph;
        Set<DependencyGraph> graphsToRemove = new HashSet<DependencyGraph>();
        while(mainIterator.hasNext()) {
            selectedGraph = mainIterator.next();
            if(graphsToRemove.contains(selectedGraph))
                continue;
            
            cycleIt = this.getDependencyGraphs().iterator();
            while(cycleIt.hasNext()) {
                if(selectedGraph == cycleIt.next())
                    break;
            }
            while(cycleIt.hasNext()) {
                otherGraph = cycleIt.next();
                if(selectedGraph.size()>otherGraph.size()){
                    if(selectedGraph.isSubset(otherGraph)) {
                        graphsToRemove.add(otherGraph);
                        continue;
                    }
                } else {
                    if(otherGraph.isSubset(selectedGraph)) {
                        graphsToRemove.add(selectedGraph);
                        break;
                    }
                }
            }
        }
        if(!graphsToRemove.isEmpty()) {
            dependencyGraphs.removeAll(graphsToRemove);
        }
    }
}
