package cz.muni.stanse.lockchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;

/**
 * Class to hold locking errors (double locks and unlocks). Provides filtering and generation for errors.
 * @author Radim Cebis
 *
 */
class ErrorHolder {
	// locks errors specified in the node
	private Map<CFGNode, Set<LockError>> lockErrors = new HashMap<CFGNode, Set<LockError>>();
	private Map<CFGNode, CFGHandle> dictionary;
	private State startState;
	private CFGNode startNode;

	/**
	 * Construct ErrorHolder
	 * @param dictionary for translating from nodes to handles
	 * @param startNode 
	 * @param startState 
	 */
	public ErrorHolder(Map<CFGNode, CFGHandle> dictionary, State startState, CFGNode startNode) {
		this.dictionary = dictionary;
		this.startState = startState;
		this.startNode = startNode;
	}

	/**
	 * Internally saves and handles locking error
	 * @param error to handle
	 */
	public void handleLockingError(LockError error) {		
		if(lockErrors.containsKey(error.getNode())) {
			for(LockError err : lockErrors.get(error.getNode())) {
				if(err.getLockId().equals(error.getLockId()) && err.isUnlock() == error.isUnlock()) {					
					if(error.isOnlyPossible()) {
						// keep the old stack trace and change possibility
						err.setOnlyPossible(true);
					} 
					return;
				} 
			}	
		} else {
			lockErrors.put(error.getNode(), new HashSet<LockError>());			
		}
		// insert new error
		Set<LockError> set = lockErrors.get(error.getNode());
		set.add(error);		
	}

	/**
	 * Saves locking errors into the receiver
	 * @param errReceiver receiver
	 */
	public void save(CheckerErrorReceiver errReceiver) {
		for(Set<LockError> set : lockErrors.values()) {
			for(LockError err : set) {
				errReceiver.receive(getCheckerError(err));
			}
		}
	}
	
	/**
	 * Generate CheckerError for specified LockError
	 * @param err LockError
	 * @return CheckerError
	 */
	private CheckerError getCheckerError(LockError err) {		
		List<CheckerErrorTrace> errTraces = new ArrayList<CheckerErrorTrace>();
		
		List<CFGNode> path = new ArrayList<CFGNode>();
		path.add(err.getNode());
		addTrace(errTraces, path);
		
		
		String operation = (err.isUnlock())? "unlock" : "lock";
		String description;
		if(err.isOnlyPossible()){
			description = "Possible double "+operation+" on lock \"" + VarTransformations.prettyPrint(err.getLockId()) + "\" when function \"" 
			+ dictionary.get(startNode).getFunctionName() + "\" was entered in state \""+startState+"\".";
		} else {
			description = "Double "+operation+" on lock \"" + VarTransformations.prettyPrint(err.getLockId()) + "\" when function \"" 
			+ dictionary.get(startNode).getFunctionName() + "\" was entered in state \""+startState+"\".";
		}
			
		CheckerError error = new CheckerError(description, description, (err.isOnlyPossible())? 10000 : 20000,
				"LockChecker", errTraces);
		return error;
	}
	/**
	 * Add path to error traces
	 * @param errTraces List of CheckerErrorTraces to be filled with new error trace
	 * @param path new error trace
	 */
	private void addTrace(List<CheckerErrorTrace> errTraces, List<CFGNode> path) {
		List<CheckerErrorTraceLocation> trace = new ArrayList<CheckerErrorTraceLocation>();
		
		CFGHandle handle = dictionary.get(path.get(0));
		
		ListIterator<CFGNode> it = path.listIterator();
		for(;it.hasNext();) it.next();
		
		for(; it.hasPrevious(); ) {	
			CFGNode node = it.previous();
			CheckerErrorTraceLocation traceNode = new CheckerErrorTraceLocation(Stanse.getUnitManager().getUnitName(handle), node.getLine(),
					node.getColumn(), node.getElement().asXML());
			trace.add(traceNode);
		}		
		errTraces.add(new CheckerErrorTrace(trace, "trace " + (errTraces.size()+1)));		
	}	
}
