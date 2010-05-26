package cz.muni.stanse.lockchecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import cz.muni.stanse.lockchecker.Occurrences.PairComparator;
import cz.muni.stanse.utils.Pair;


/**
 * Class generating possible locking errors.
 * 
 * @author Radim Cebis
 *
 */
class ErrorGenerator {

	// var id, var state, node containing var, number of flows through that node
	private Map<String, Occurrences> occurrences = new HashMap<String, Occurrences>();
	private Map<CFGNode, CFGHandle> dictionary;
	private CFGNode startNode;
	private State outputState;
	private State startState;
	// do we want to count flows or just occurrecnes
	// if set to false we count number of nodes, if set to true we count also how many times the node is visited
	private boolean countFlows;
	private boolean countPairs;
	private int threshold;
	private boolean generateMoreLocksErrors;
	
	
	/**
	 * This comparator will compare CheckerErrorTraceLocations according to their line number
	 * 
	 * @author Radim Cebis
	 */
	private static class LocationsComparator implements Comparator<CheckerErrorTraceLocation>, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(CheckerErrorTraceLocation first,
				CheckerErrorTraceLocation second) {
			return first.getLineNumber() - second.getLineNumber();		
		}
	}
	
		
	/**
	 * Creates new error generator for the given summary and configuration information
	 * 
	 * @param summary from which errors should be generated 
	 * @param countFlows should we count using flows?
	 * @param countPairs should we count using pairs?
	 * @param threshold minimal importance value
	 * @param generateMoreLocksErrors should we generate errors when more locks are held than in common state?
	 * @see Configuration
	 */
	public ErrorGenerator(FunctionStateSummary summary, boolean countFlows, boolean countPairs, int threshold, boolean generateMoreLocksErrors) {		
		this.occurrences = summary.getVarOccurrences();
		this.dictionary = summary.getDictionary();
		this.startState = summary.getStartState();
		this.outputState = summary.getOutputState();
		this.startNode = summary.getStartNode();
		this.countFlows = countFlows;
		this.countPairs = countPairs;
		this.threshold = threshold;
		this.generateMoreLocksErrors = generateMoreLocksErrors;
	}

	
	/**
	 * Generate errors.
	 * @param errReciver to put generated errors into
	 */
	public void generateErrors(CheckerErrorFilter errReciver) {
		for(Entry<String, Occurrences> entry: occurrences.entrySet()) {
			
			if(countPairs) {			
				Collection<Pair<Pair<State, Double>, Pair<State, Double>>> zStats = entry.getValue().getZStatsPairs(countFlows);
				PairComparator pairComparator = new Occurrences.PairComparator();
				
				for(Pair<Pair<State, Double>, Pair<State, Double>> pair: zStats) {
					Pair<State, Double> goodState;
					Pair<State, Double> wrongState;
					if(pairComparator.compare(pair.getFirst(), pair.getSecond()) < 0) {
						goodState = pair.getFirst();
						wrongState = pair.getSecond();						
					} else {
						goodState = pair.getSecond();
						wrongState = pair.getFirst();	
					}
					// do not generate errors for variables which are commonly in unlocked state
					if(!goodState.getFirst().isUnlocked()) {	
						Set<Pair<State,Double>> set = new HashSet<Pair<State,Double>>();
						set.add(goodState);
						set.add(wrongState);
						CheckerErrorHolder generatedError = generateError(entry.getKey(), goodState, set);
						if(generatedError!=null)
							errReciver.receive(generatedError);
					}
				}
			} else {
				// count pairs != true -> use set
				SortedSet<Pair<State, Double>> zStatsSet = entry.getValue().getZStatsSet(countFlows);
				
				if(zStatsSet.size()>1) {
					// generate errors only for the most common state for this variable
					Pair<State, Double> common = zStatsSet.first();
					// do not generate errors for variables which are commonly in unlocked state
					if(!common.getFirst().isUnlocked()) {				
						CheckerErrorHolder generatedError = generateError(entry.getKey(), common, zStatsSet);
						if(generatedError!=null)
							errReciver.receive(generatedError);
					}					
				}
			}
		}		
	}
	
	/**
	 * Generate error
	 * 
	 * @param variable identifier
	 * @param pair common state,z-statistic pair
	 * @param zStatsSet all pairs
	 * @return
	 */
	private CheckerErrorHolder generateError(String variable, Pair<State, Double> pair,
			Set<Pair<State, Double>> zStatsSet) {
		List<CheckerErrorTrace> errTraces = new ArrayList<CheckerErrorTrace>();		
		
		// the higher the z stat the higher possibility of error (multiply to convert to int)
		int importance = (int) (Math.round((pair.getSecond()*1000.0)));
		// filter out errors under the threshold
		if(importance < threshold) return null;
		
		addTrace(variable, pair.getFirst(), errTraces, true);		
		State wrongState = null;
		for(Pair<State, Double> ent : zStatsSet) {
			if(!ent.equals(pair)) {
				// when there is more locks in wrong state than in goodState 
				//it is not considered a problem
				if(generateMoreLocksErrors || !ent.getFirst().contains(pair.getFirst())) {
					addTrace(variable, ent.getFirst(), errTraces, false);
					wrongState = ent.getFirst();
				} 
			}
		}
		// if we did not find really wrong state than return
		if(!generateMoreLocksErrors && wrongState == null) return null;
		String prettyVar = VarTransformations.prettyPrint(variable);
		String description;
		if(zStatsSet.size()<3) {
			description = "Variable \"" + prettyVar + "\" is commonly in state: \"" + pair.getFirst() + 
								"\" but sometimes it is in state: \"" + wrongState + "\" when the function \"" + dictionary.get(startNode).getFunctionName() + "\" was entered" +
								" in state \"" + startState + "\".";
		} else {
			description = "Variable \"" + prettyVar + "\" is commonly in state: \"" + pair.getFirst() + 
			"\" but sometimes it is in other states when the function \"" + dictionary.get(startNode).getFunctionName() + "\" was entered" +
			" in state \"" + startState + "\".";
		}
		
		String shortDesc = "Probable locking error on variable \"" + prettyVar + "\" in function \"" + dictionary.get(startNode).getFunctionName() + "\".";
		
		CheckerError error = new CheckerError(shortDesc, description, importance ,				
				"LockChecker", errTraces);
		
		CheckerErrorHolder res = new CheckerErrorHolder();
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
	private void addTrace(String variable, State state, List<CheckerErrorTrace> errTraces, boolean isGoodState) {
		if(isGoodState) {
			Map<CFGNode, Counter> path = new HashMap<CFGNode, Counter>();
			path.putAll(occurrences.get(variable).get(state));
			addTrace(errTraces, path, "Occurrenes in common state: \"" + state + "\".", state);			
		} else {
			Map<CFGNode, Counter> path = new HashMap<CFGNode, Counter>();
			path.putAll(occurrences.get(variable).get(state));
			addTrace(errTraces, path, "Occurrenes in wrong state: \"" + state + "\"." , state);
		}		
	}
	
	/**
	 * Add trace. Sorts locations according to the line number
	 * 
	 * @param errTraces to put trace into
	 * @param path locations
	 * @param tracename
	 * @param state
	 */
	private void addTrace(List<CheckerErrorTrace> errTraces, Map<CFGNode, Counter> path, String tracename, State state) {
		List<CheckerErrorTraceLocation> trace = new ArrayList<CheckerErrorTraceLocation>();
		
		CFGHandle handle = dictionary.get(path.keySet().iterator().next());
		
		for(Entry<CFGNode, Counter> entry : path.entrySet()) {
			CheckerErrorTraceLocation traceNode = new CheckerErrorTraceLocation(Stanse.getUnitManager().getUnitName(handle), entry.getKey().getLine(),
					entry.getKey().getColumn(), entry.getValue() + " flows through this node in state \"" + state.toString() + "\".");
			trace.add(traceNode);
		}		
		Collections.sort(trace, new LocationsComparator());
		errTraces.add(new CheckerErrorTrace(trace, tracename));		
	}	
}
