package cz.muni.stanse.lockchecker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;


/**
 * Class holding CheckerErrors. This class filters the errors.
 * 
 * If the error has same z-statistic, variable id, traces as any of the previously
 * received errors, it is thrown away.
 * 
 * @author Radim Cebis
 *
 */
class CheckerErrorFilter {

	// importance, variable, error
	private Map<Integer, Map<String, Set<CheckerError>>> map = new HashMap<Integer, Map<String,Set<CheckerError>>>();
	
	
	/**
	 * Receives the error
	 * @param holder error
	 */
	public void receive(CheckerErrorHolder holder) {
		Map<String, Set<CheckerError>> accImportance = map.get(holder.getError().getImportance());
		if(accImportance == null) {
			accImportance = new HashMap<String, Set<CheckerError>>();
			map.put(holder.getError().getImportance(), accImportance);
		}
		
		Set<CheckerError> set = accImportance.get(holder.getVariable());
		if(set == null) {
			set = new HashSet<CheckerError>();
			accImportance.put(holder.getVariable(), set);
			// add a new error
			set.add(holder.getError());
		} else {
			boolean found = false;
			for(CheckerError err : set) {
				// if the error has same importance and is on same variable and has same
				// error traces and is already here: filter it out
				if(err.getTraces().equals(holder.getError().getTraces())) {
					found = true;
				}
			}
			if(!found) set.add(holder.getError());
		}
	}

	/**
	 * Generates errors to the argument
	 * @param errReciver CheckerErrorReceiver to generate errors into
	 */
	public void generateErrors(CheckerErrorReceiver errReciver) {
		for(Map<String, Set<CheckerError>> imp : map.values()) {
			for(Set<CheckerError> var: imp.values()) {
				for(CheckerError err:var) {
					errReciver.receive(err);
				}
			}
		}
	}
	
	

}
