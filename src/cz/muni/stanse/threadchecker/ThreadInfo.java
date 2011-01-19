package cz.muni.stanse.threadchecker;

import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.checker.CheckerProgressMonitor;
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
	private static int actualId = 0;
	private CheckerProgressMonitor monitor;
	private final int id;
	private final String functionName;
	private Function function;
	private Set<DependencyGraph> dependencyGraphs;
	private final static Logger logger =
		Logger.getLogger(ThreadChecker.class.getName());
	private static CheckerSettings checkerSettings =
		CheckerSettings.getInstance();

	@SuppressWarnings("static-access")
	public ThreadInfo(CFGHandle cfg, final CheckerProgressMonitor mon) {
		if (cfg == null)
			throw new NullPointerException("CFG is null");

		this.monitor = mon;
		this.id = this.actualId;
		this.actualId++;
		this.functionName = cfg.getFunctionName();
		this.buildInitialGraph(cfg);
	}

	public int getId() {
		return id;
	}

	public String getFunctionName() {
		return functionName;
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
		String result = "Thread " + id + ": with " + function.getFunctionStates();
		return result;
	}

	public Set<DependencyGraph> getDependencyGraphs() {
		return dependencyGraphs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ThreadInfo other = (ThreadInfo) obj;
		return id == other.getId();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash *= id;
		return hash;
	}
    
	/**
	 * Builds a Function from CFG and stores it into checkerSettings
	 * @param cfg CFG to build a Function from
	 */
	private void buildInitialGraph(CFGHandle cfg) {
		DependencyGraph graph;
		logger.debug("===============");
		logger.debug("Analysing thread: " + getFunctionName());
		logger.debug("===============");

		if (function == null)
			function = checkerSettings.getFunction(cfg);

		dependencyGraphs = new HashSet<DependencyGraph>();

		//Checker settings hasn't function already processed
		if (function == null) {
			try {
				function = CFGTransit.analyseCFG(cfg, monitor);
			} catch (CheckerException e) {
				monitor.write(cfg.getFunctionName() + ": " +
					e.getLocalizedMessage());
				return;
			}
			checkerSettings.addFunction(function, cfg);
		}

		/*
		 * Add to every rule a thread where it was created
		 * Create a temporary dependencyGraph to avoid dependency deadlock
		 */
		for (FunctionState currentState : function.getFunctionStates()) {
			graph = currentState.generateGraph(this);
			// insert only a new FunctionState
			dependencyGraphs.add(graph);
		}
		reduceDependencyGraphs();
	}

	/**
	 * Erases same or subset graph in dependencyGraphs.
	 */
	private void reduceDependencyGraphs() {
		if (dependencyGraphs.size() < 2)
			return;

		Iterator<DependencyGraph> cycleIt;
		Set<DependencyGraph> graphsToRemove = new HashSet<DependencyGraph>();
		for (final DependencyGraph selectedGraph : dependencyGraphs) {
			if (graphsToRemove.contains(selectedGraph))
				continue;

			cycleIt = this.getDependencyGraphs().iterator();
			while (cycleIt.hasNext())
				if (selectedGraph == cycleIt.next())
					break;

			while (cycleIt.hasNext()) {
				final DependencyGraph otherGraph = cycleIt.next();
				if (selectedGraph.size() > otherGraph.size()) {
					if (selectedGraph.isSubset(otherGraph)) {
						graphsToRemove.add(otherGraph);
						continue;
					}
				} else {
					if (otherGraph.isSubset(selectedGraph)) {
						graphsToRemove.add(selectedGraph);
						break;
					}
				}
			}
		}
		if (!graphsToRemove.isEmpty())
			dependencyGraphs.removeAll(graphsToRemove);
	}
}