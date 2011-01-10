package cz.muni.stanse.threadchecker;

import cz.muni.stanse.threadchecker.debug.Utils;
import cz.muni.stanse.threadchecker.graph.DependencyCycleDetector;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingSuccess;
import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.threadchecker.graph.Cycle;
import cz.muni.stanse.threadchecker.graph.DependencyGraph;
import cz.muni.stanse.threadchecker.graph.RAG;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * Class provides static analysis specialized to finding deadlocks in multiple
 * threads.
 * @author Jan Kučera
 */
public class ThreadChecker extends Checker {
    private final static Logger logger =
                                Logger.getLogger(ThreadChecker.class.getName());
    private static CheckerSettings settings = CheckerSettings.getInstance();

    /**
     * Function pick choose starting CFG and build their dependency graphs, then
     * find possible cycles and generate RAG and create appropriate errors
     * or warnings.
     * @param units List<Unit> representing all files intended to check
     * @return List<PresentableError> representing all errors that checker found
     * @throws cz.muni.stanse.checker.CheckerException
     */
    @Override
    public CheckingResult check(
                        final LazyInternalStructures internals,
                        final CheckerErrorReceiver errReceiver,
                        final CheckerProgressMonitor monitor)
                                                       throws CheckerException {
        List<CheckerError> errors;
        CheckerError error;
        Set<DependencyGraph> graphs = null;
        List<String> startFunctions;

        settings.setInternals(internals);

        Collection<Unit> units = internals.getUnits();

        settings.clearData();
//        for(Unit unit : units) {
//            logger.info("===============");
//            logger.info("Analysing file: "+unit.getName());
//            logger.info("===============");
//            settings.addAllCFGs(unit);
//            Utils.showGraph(unit);
//        }
        
        settings.addAllCFGs();


        //Parser somehow creates empty unit - prevent throwing expcetion
        if (units.size()==1 && internals.getCFGHandles().isEmpty()) {
            return new CheckingSuccess();
        }

        startFunctions = settings.getStartFunctions();


        analyseFunctions(startFunctions);
        graphs = generateDependencyGraphs();
        errors = findErrors(graphs);

        if (errors.size() > 0) {
            logger.warn("\nError result in file:"+
                        units.iterator().next().getName()+"\n"+errors);
        }
        Collections.sort(errors);

        for (final CheckerError err : errors)
            errReceiver.receive(err);

        settings.setInternals(null);

        return new CheckingSuccess();
    }
    
    /**
     * Method picks startFunctions, creates ThreadInfo instance which executes
     * CFG analysis on every of those threads.
     * @param startFunctions List<String> of function names
    */
    private void analyseFunctions(final List<String> startFunctions) {
        CFGHandle cfg;
        ThreadInfo thread;

        logger.debug("Start functions are: " + startFunctions);

        for (final String functionName : startFunctions) {
            cfg = settings.getCFG(functionName);
            if (cfg == null) {
                logger.error("Can't find CFG with startName " + functionName);
                continue;
            }
            thread = new ThreadInfo(cfg);
            settings.addThread(thread);
        }
    }

    /**
     * Detects cycle in every dependency graph, creates RAG and
     * generate proper error.
     * @param graphs Set<DependencyGraph> of dependency graphs
     * @return List<CheckerErrors> founded errors
     */
    private static List<CheckerError> findErrors(Set<DependencyGraph> graphs){
        List<CheckerError> errors = new Vector<CheckerError>();
        CheckerError error;
        DependencyCycleDetector detector
                                        = DependencyCycleDetector.getInstance();
        Set<Cycle> cycles = new HashSet<Cycle>();
        RAG rag = new RAG();
        
        //No dependency graph was created - return with no error
        if(graphs == null)
            return errors;

        logger.info("Graph:\n"+graphs);
        Utils.showDependencyGraphs(graphs);
        for(DependencyGraph rules : graphs) {
            cycles.addAll(detector.detect(rules));
        }
        for(Cycle cycle : cycles) {
            logger.info("Cycle detected:"+cycle);
            error = rag.detectDeadlock(cycle);
            errors.add(error);
        }
            
        return errors;
    }

    /**
     * Method picks all threads created in file and creates dependency graphs.
     * @return Set<DependencyGraph> dependency graphs
     */
    private Set<DependencyGraph> generateDependencyGraphs() {
            ThreadInfo thread;
            Set<DependencyGraph> graphs;
            Iterator<ThreadInfo> it = settings.getThreads().iterator();
            if(!it.hasNext())
                return new HashSet<DependencyGraph>();

            graphs = it.next().getDependencyGraphs();
            while (it.hasNext()) {
                thread = it.next();
                graphs = joinGraphs(graphs, thread.getDependencyGraphs());
            }
        return graphs;
    }

    /**
     * Merge rules from graphs to one -> merge every graph from first with ever
     * y graph in second.
     * @param first Set<DependencyGraph>
     * @param second Set<DependencyGraph>
     * @return Set<DependencyGraph>
     */
    private static Set<DependencyGraph> joinGraphs(Set<DependencyGraph> first,
                                                Set<DependencyGraph> second) {
       Set<DependencyGraph> graphResult = new HashSet<DependencyGraph>();
       if(first.isEmpty())
           return second;

       for(DependencyGraph graphFromFirst : first) {
            if(second.isEmpty()) {
                graphResult.add(graphFromFirst);
                continue;
            }
                
            for(DependencyGraph graphFromSecond : second) {
                graphResult.add(graphFromFirst.merge(graphFromSecond));
            }
       }
       return graphResult;
    }

    @Override
    public String getName() {
        return "Pthread Checker for finding deadlocks in multiple threads";
    }
}
