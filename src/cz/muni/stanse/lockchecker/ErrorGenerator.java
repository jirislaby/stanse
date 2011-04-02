package cz.muni.stanse.lockchecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.UnitManager;
import cz.muni.stanse.lockchecker.Occurrences.PairComparator;
import cz.muni.stanse.utils.Pair;


/**
 * Class generating possible locking errors.
 *
 * @author Radim Cebis
 *
 */
class ErrorGenerator {
	/*
	 * var id, var state, node containing var, number of flows through that
	 * node
	 */
	private Map<String, Occurrences> occurrences =
		new HashMap<String, Occurrences>();
	private Map<CFGNode, CFGHandle> dictionary;
	private CheckerErrorFilter errReceiver;
	private CFGNode startNode;
	private State startState;
	/*
	 * do we want to count flows or just occurrences
	 * if set to false we count number of nodes, if set to true we count
	 * also how many times the node is visited
	 */
	private boolean countFlows;
	private boolean countPairs;
	private int threshold;
	private boolean generateMoreLocksErrors;

	/**
	 * This comparator will compare CheckerErrorTraceLocations according to
	 * their line number
	 *
	 * @author Radim Cebis
	 */
	private static class LocationsComparator
			implements Comparator<CheckerErrorTraceLocation>,
			Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(CheckerErrorTraceLocation first,
				CheckerErrorTraceLocation second) {
			return first.getLineNumber() - second.getLineNumber();
		}
	}

	/**
	 * Creates new error generator for the given summary and configuration
	 * information
	 *
	 * @param errReceiver where to store found errors
	 * @param summary from which errors should be generated
	 * @param countFlows should we count using flows?
	 * @param countPairs should we count using pairs?
	 * @param threshold minimal importance value
	 * @param generateMoreLocksErrors should we generate errors when more
	 * locks are held than in common state?
	 * @see Configuration
	 */
	public ErrorGenerator(final CheckerErrorFilter errReceiver,
			final FunctionStateSummary summary, boolean countFlows,
			boolean countPairs, int threshold,
			boolean generateMoreLocksErrors) {
		this.errReceiver = errReceiver;
		this.occurrences = summary.getVarOccurrences();
		this.dictionary = summary.getDictionary();
		this.startState = summary.getStartState();
		this.startNode = summary.getStartNode();
		this.countFlows = countFlows;
		this.countPairs = countPairs;
		this.threshold = threshold;
		this.generateMoreLocksErrors = generateMoreLocksErrors;
	}

	private void generateErrorsPaired(
			final Entry<String, Occurrences> entry) {
		final PairComparator pairComparator =
			new Occurrences.PairComparator();

		for (final Pair<Pair<State, Double>, Pair<State, Double>> pair :
				entry.getValue().getZStatsPairs(countFlows)) {
			Pair<State, Double> goodState;
			Pair<State, Double> wrongState;
			if (pairComparator.compare(pair.getFirst(),
					pair.getSecond()) < 0) {
				goodState = pair.getFirst();
				wrongState = pair.getSecond();
			} else {
				goodState = pair.getSecond();
				wrongState = pair.getFirst();
			}
			/*
			 * do not generate errors for variables which are
			 * commonly in unlocked state
			 */
			if (!goodState.getFirst().isUnlocked()) {
				final Set<Pair<State,Double>> set =
					new HashSet<Pair<State,Double>>();
				set.add(goodState);
				set.add(wrongState);
				final CheckerErrorHolder err = generateError(
					entry.getKey(), goodState, set);
				if (err != null)
					errReceiver.receive(err);
			}
		}
	}

	private void generateErrorsUnpaired(
			final Entry<String, Occurrences> entry) {
		// count pairs != true -> use set
		final SortedSet<Pair<State, Double>> zStatsSet =
			entry.getValue().getZStatsSet(countFlows);

		if (zStatsSet.size() <= 1)
			return;

		/*
		 * generate errors only for the most common state for this
		 * variable
		 */
		final Pair<State, Double> common = zStatsSet.first();
		/*
		 * do not generate errors for variables which are commonly in
		 * unlocked state
		 */
		if (!common.getFirst().isUnlocked()) {
			final CheckerErrorHolder err = generateError(
				entry.getKey(), common, zStatsSet);
			if (err != null)
				errReceiver.receive(err);
		}
	}

	/**
	 * Generate errors.
	 * @param errReceiver to put generated errors into
	 */
	public void generateErrors() {
		for (final Entry<String, Occurrences> entry :
				occurrences.entrySet()) {
			if (countPairs)
				generateErrorsPaired(entry);
			else
				generateErrorsUnpaired(entry);
		}
	}

	/**
	 * Generate error
	 *
	 * @param variable identifier
	 * @param pair common state,z-statistic pair
	 * @param zStatsSet all pairs
	 * @return error
	 */
	private CheckerErrorHolder generateError(final String variable,
			final Pair<State, Double> pair,
			final Set<Pair<State, Double>> zStatsSet) {
		final List<CheckerErrorTrace> errTraces =
			new ArrayList<CheckerErrorTrace>();

		/*
		 * the higher the z stat is the higher possibility of error
		 * (multiply to convert to int)
		 */
		int importance = (int)Math.round(pair.getSecond()*1000.0);
		// filter out errors under the threshold
		if (importance < threshold)
			return null;

		addTrace(variable, pair.getFirst(), errTraces, true);
		State wrongState = null;
		for (final Pair<State, Double> ent : zStatsSet) {
			if (ent.equals(pair))
				continue;
			/*
			 * when there are more locks in the wrong state
			 * than in the goodState it is not considered a
			 * problem
			 */
			if (generateMoreLocksErrors || !ent.getFirst().
					contains(pair.getFirst())) {
				addTrace(variable, ent.getFirst(), errTraces,
					false);
				wrongState = ent.getFirst();
			}
		}
		// if we did not find really a wrong state then return
		if (!generateMoreLocksErrors && wrongState == null)
			return null;

		final String prettyVar = VarTransformations.prettyPrint(variable);
		final StringBuilder desc = new StringBuilder("Variable \"");
		desc.append(prettyVar).append("\" is commonly in state: \"").
			append(pair.getFirst()).
			append("\" but sometimes it is in ");
		if (zStatsSet.size() < 3)
			desc.append("state: \"").append(wrongState).append('"');
		else
			desc.append("other states");
		desc.append(" when the function \"").
			append(dictionary.get(startNode).getFunctionName()).
			append("\" was entered in state \"").append(startState).
			append("\".");

		final String shortDesc = "Probable locking error on " +
			"variable \"" + prettyVar + "\" in function \"" +
			dictionary.get(startNode).getFunctionName() + "\".";

		final CheckerError error = new CheckerError(shortDesc,
			desc.toString(), importance, "LockChecker", errTraces);

		final CheckerErrorHolder res = new CheckerErrorHolder();
		res.setError(error);
		res.setVariable(variable);
		return res;
	}

	/**
	 * Add trace for given variable, state
	 *
	 * @param variable identifier
	 * @param state
	 * @param errTraces to save trace into
	 * @param isGoodState is this common or wrong state?
	 */
	private void addTrace(final String variable, final State state,
			final List<CheckerErrorTrace> errTraces,
			boolean isGoodState) {
		final Map<CFGNode, Counter> path =
			new HashMap<CFGNode, Counter>();
		path.putAll(occurrences.get(variable).get(state));
		final StringBuilder desc = new StringBuilder("Occurrences in ");
		desc.append(isGoodState ? "common" : "wrong").
			append(" state: \"").append(state).append("\".");
		addTrace(errTraces, path, desc.toString(), state);
	}

	/**
	 * Add trace. Sorts locations according to the line number
	 *
	 * @param errTraces to put trace into
	 * @param path locations
	 * @param tracename
	 * @param state
	 */
	private void addTrace(final List<CheckerErrorTrace> errTraces,
			final Map<CFGNode, Counter> path,
			final String tracename, final State state) {
		final List<CheckerErrorTraceLocation> trace =
			new ArrayList<CheckerErrorTraceLocation>();
		final CFGHandle handle = dictionary.get(path.keySet().
			iterator().next());
		final UnitManager um = Stanse.getUnitManager();

		for (final Entry<CFGNode, Counter> entry : path.entrySet()) {
			final CheckerErrorTraceLocation traceNode =
				new CheckerErrorTraceLocation(
					um.getUnitName(handle),
					entry.getKey().getLine(),
					entry.getKey().getColumn(),
					entry.getValue() +
					" flows through this node in state \"" +
					state.toString() + "\".");
			trace.add(traceNode);
		}
		Collections.sort(trace, new LocationsComparator());
		errTraces.add(new CheckerErrorTrace(trace, tracename));
	}
}
