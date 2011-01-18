package cz.muni.stanse.lockchecker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;

/**
 * Class holding all the FunctionStateSummary objects of the given function.
 *
 * @author Radim Cebis
 *
 */
class FunctionSummary {

	// entering state, functionStateSummary
	private Map<State, FunctionStateSummary> stateSummary = new HashMap<State, FunctionStateSummary>();
	private StateRepository repos;
	private CFGNode startNode;
	private Map<CFGNode, CFGHandle> dictionary;
	private Configuration conf;



	/**
	 * Constructs function summary for given function
	 *
	 * @param repos state repository
	 * @param startNode this functionsummary's start node
	 * @param dictionary
	 * @param conf Configuration
	 */
	public FunctionSummary(StateRepository repos, CFGNode startNode,
			Map<CFGNode, CFGHandle> dictionary, Configuration conf) {
		super();
		this.repos = repos;
		this.startNode = startNode;
		this.dictionary = dictionary;
		this.conf = conf;
	}

	/**
	 * Returns FunctionStateSummary for a given entered state
	 * If FSS is not found, it creates a new one
	 *
	 * @param state entered state
	 * @return FunctionStateSummary for the give entered state
	 */
	public FunctionStateSummary get(State state) {
		FunctionStateSummary fss = stateSummary.get(state);
		if(fss == null) {
			state = repos.get(state);
			fss = new FunctionStateSummary(dictionary, repos, startNode, state, conf);
			stateSummary.put(state, fss);
		}
		return fss;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Entry<State, FunctionStateSummary> ent: stateSummary.entrySet()) {
			builder.append(ent.getValue());
		}
		return builder.toString();
	}

	/**
	 * Returns FunctionStateSummary for a given entered state
	 * If FSS for the given state does not exists null is returned
	 * @param state entered state
	 * @return FunctionStateSummary for the give entered state or null if not existing
	 */
	public FunctionStateSummary getFromCache(State state) {
		return stateSummary.get(state);
	}


	/**
	 * @return all FunctionStateSummary held for this FunctionSummary
	 */
	public Collection<FunctionStateSummary> getFunctionStateSummaries() {
		return stateSummary.values();
	}
}
