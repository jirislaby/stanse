package cz.muni.stanse.lockchecker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;



/**
 * Class holding all FunctionSummary instances for the check run
 *
 * @author Radim Cebis
 *
 */
class Summaries {

	private Map<CFGNode, FunctionSummary> map = new HashMap<CFGNode, FunctionSummary>();
	private Map<CFGNode, CFGHandle> dictionary;
	private StateRepository repos = new StateRepository();
	private Configuration conf;

	/**
	 * Get function state summary for given function and enter state
	 * @param startNode of the function
	 * @param startState enter state of the function
	 * @return appropriate function state summary
	 */
	public FunctionStateSummary get(CFGNode startNode, State startState) {
		FunctionSummary fs = map.get(startNode);
		if (fs!=null)
			return fs.get(startState);
		return null;
	}

	/**
	 * @return state repository
	 */
	public StateRepository getRepos() {
		return repos;
	}

	/**
	 * Constructs repository with given configuration
	 * @param dictionary to translate nodes to handles
	 * @param conf Configuration
	 */
	public Summaries(Map<CFGNode, CFGHandle> dictionary, Configuration conf) {
		super();
		this.dictionary = dictionary;
		this.conf = conf;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("/////////////////  SUMMARY  /////////////////\n");
		sb.append("/////////////////////////////////////////////\n");
		for (final Entry<CFGNode, FunctionSummary> ent : map.entrySet())
			sb.append(ent.getValue());
		return sb.toString();
	}

	/**
	 * Get function summary for the given function
	 * @param startNode of the function
	 * @return appropriate function summary
	 */
	public FunctionSummary get(CFGNode startNode) {
		FunctionSummary fs = map.get(startNode);
		if (fs == null) {
			fs = new FunctionSummary(repos, startNode, dictionary,
				conf);
			map.put(startNode, fs);
		}
		return fs;
	}

	/**
	 * @return all function state summaries hold in this instance
	 */
	public Collection<FunctionStateSummary> getAllFunctionStateSummaries() {
		Collection<FunctionStateSummary> res = new ArrayList<FunctionStateSummary>();
		for (final FunctionSummary fs : map.values())
			res.addAll(fs.getFunctionStateSummaries());
		return res;
	}
}
