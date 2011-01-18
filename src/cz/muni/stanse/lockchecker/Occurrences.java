package cz.muni.stanse.lockchecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;

/**
 * Class representing variable's occurrences
 * @author Radim Cebis
 */
class Occurrences {
	private Map<State, Map<CFGNode, Counter>> occurrences =
		new HashMap<State, Map<CFGNode,Counter>>();
	private StateRepository repos;

	/**
	 * Construct occurrences
	 * @param repos state repostiory
	 */
	public Occurrences(StateRepository repos) {
		super();
		this.repos = repos;
	}

	/**
	 * Returns map (node, counter) of occurrences in given state
	 * @param state
	 * @return map (node, counter) of occurrences in given state
	 */
	public Map<CFGNode, Counter> get(State state) {
		return occurrences.get(state);
	}

	/**
	 * Add occurrence in the given state and node
	 *
	 * @param state of the occurrence
	 * @param node of the occurrence
	 * @param increment how much increment the occurrence - should be 1 or -1
	 */
	public void addOccurrence(State state, CFGNode node, int increment) {
		Map<CFGNode, Counter> map = occurrences.get(state);
		if(map == null) {
			map = new HashMap<CFGNode, Counter>();
			occurrences.put(repos.get(state), map);
		}
		Counter counter = map.get(node);
		if(counter == null) {
			counter = new Counter(increment);
			map.put(node, counter);
		} else counter.add(increment);
		if(counter.get() < 1) {
			map.remove(node);
			if(map.size() == 0) occurrences.remove(repos.get(state));
		}
	}

	/**
	 * @return states for all occurrences
	 */
	public Collection<State> getAllStates() {
		return occurrences.keySet();
	}

	/**
	 * Joins this occurrences with occurrencesToAdd while transforming
	 * identifiers
	 *
	 * @param occurrencesToAdd occurrences to be added to this occurrences
	 * @param varTransformations variable identifiers transformations
	 * between caller and callee
	 */
	public void addOcurrences(Occurrences occurrencesToAdd,
			VarTransformations varTransformations) {
		for(Entry<State, Map<CFGNode, Counter>> entry :
				occurrencesToAdd.occurrences.entrySet()) {
			final State state = State.getRenamedCFGState(
				entry.getKey(), varTransformations, false);
			addVarOccurrences(state, entry.getValue());
		}
	}

	/**
	 * Adds occurrences to the given state from the input
	 *
	 * @param state to add occurrences to
	 * @param input map containing new occurrences
	 */
	private void addVarOccurrences(State state,
			Map<CFGNode, Counter> input) {
		Map<CFGNode, Counter> map = occurrences.get(state);
		if (map == null) {
			map = new HashMap<CFGNode, Counter>();
			occurrences.put(repos.get(state), map);
		}
		for (Entry<CFGNode, Counter> node : input.entrySet()) {
			Counter counter = map.get(node.getKey());
			if(counter == null) {
				counter = new Counter(node.getValue().get());
				map.put(node.getKey(), counter);
			} else counter.add(node.getValue().get());
		}
	}

	/**
	 * Counts z-statistic using pairs.
	 * Only two pairs are used to count z-statistic and to be compared.
	 *
	 *
	 * @param countZStatisticUsingFlows should we count z-statistic using
	 * flows?
	 * @return collection of pairs of state,z-stat pairs whith relevant
	 * z-statistic
	 */
	public Collection<Pair<Pair<State,Double>, Pair<State,Double>>>
			getZStatsPairs(boolean countZStatisticUsingFlows) {
		List<Pair<Pair<State,Double>, Pair<State,Double>>> result =
			new ArrayList<Pair<Pair<State,Double>, Pair<State,Double>>>();
		if (occurrences.entrySet().size() < 2)
			return result;
		/*
		 * this is a little bit ugly but iterators do not have to
		 * return results in same order
		 */
		Set<State> processedStates = new HashSet<State>();
		// count zStatistic all state pairs
		for (final Entry<State, Map<CFGNode, Counter>> firstEntry :
				occurrences.entrySet()) {
			int firstChecks = 0;
			if (countZStatisticUsingFlows) {
				for(Counter i : firstEntry.getValue().values())
					firstChecks += i.get();
			} else
				firstChecks = firstEntry.getValue().keySet().
					size();

			if (firstChecks != 0) {
				for (Entry<State, Map<CFGNode, Counter>> secondEntry :
						occurrences.entrySet()) {
					if (!secondEntry.getKey().equals(firstEntry.getKey()) &&
							!processedStates.contains(secondEntry.getKey())) {
						int secondChecks = 0;
						if (countZStatisticUsingFlows) {
							for(Counter i : secondEntry.getValue().values())
								secondChecks += i.get();
						} else
							secondChecks = secondEntry.getValue().keySet().size();

						if (secondChecks!=0) {
							int allChecks = firstChecks + secondChecks;
							Pair<Pair<State,Double>, Pair<State,Double>> entry =
								new Pair<Pair<State,Double>, Pair<State,Double>>(
								new Pair<State,Double>(firstEntry.getKey(), Util.zStat(firstChecks, allChecks)),
								new Pair<State,Double>(secondEntry.getKey(), Util.zStat(secondChecks, allChecks)));
							result.add(entry);
						}
					}
				}
			}
			processedStates.add(firstEntry.getKey());
		}
		return result;
	}

	/**
	 * This comparator will compare Pair<State,Double> according to their z stats
	 *
	 * @author Radim Cebis
	 */
	public static class PairComparator
			implements Comparator<Pair<State,Double>>,
			Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(Pair<State,Double> first,
				Pair<State,Double> second) {
			return -first.getSecond().compareTo(second.getSecond());
		}
	}

	/**
	 * Normally count z-statistics using all the states in which a variable
	 * occurs.
	 *
	 * @param countZStatisticUsingFlows should we count z-statistic using
	 * flows?
	 * @return sorted set containing pairs (state,z-stat), highest z-stat
	 * first
	 */
	public SortedSet<Pair<State,Double>>
			getZStatsSet(boolean countZStatisticUsingFlows) {
		SortedSet<Pair<State,Double>> result =
			new TreeSet<Pair<State,Double>>(new PairComparator());
		if (occurrences.entrySet().size() < 2)
			return result;
		int allChecks = 0;
		for (final Entry<State, Map<CFGNode, Counter>> firstEntry :
				occurrences.entrySet()) {
			if (countZStatisticUsingFlows) {
				for(Counter i : firstEntry.getValue().values())
					allChecks += i.get();
			} else
				allChecks += firstEntry.getValue().keySet().
					size();
		}

		// count zStatistic all state pairs
		for (final Entry<State, Map<CFGNode, Counter>> firstEntry :
				occurrences.entrySet()) {
			int firstChecks = 0;
			if (countZStatisticUsingFlows) {
				for (Counter i : firstEntry.getValue().values())
					firstChecks += i.get();
			} else
				firstChecks = firstEntry.getValue().keySet().size();
			result.add(new Pair<State,Double>(firstEntry.getKey(),
				Util.zStat(firstChecks, allChecks)));
		}
		return result;
	}
}
