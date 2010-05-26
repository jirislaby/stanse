package cz.muni.stanse.lockchecker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.muni.stanse.codestructures.CFGNode;

/**
 * This call represents call stack
 * 
 * @author Radim Cebis
 *
 */
class CallStack {
	private Map<CFGNode, Set<State>> callStack = new HashMap<CFGNode, Set<State>>();

	
	/**
	 * Add the function to the call stack
	 * 
	 * @param startNode start node of the function
	 * @param enterState entered state of the function
	 */
	public void add(CFGNode startNode, State enterState) {
		Set<State> set = callStack.get(startNode);
		if(set == null) {
			set = new HashSet<State>();
			callStack.put(startNode, set);
		}
		set.add(enterState);
	}

	
	/**
	 * Is the function contained in the call stack?
	 * 
	 * @param startNode start node of the function
	 * @param enterState entered state of the function
	 * @return true if the (function,enterState) is in this call stack
	 */
	public boolean contains(CFGNode startNode, State enterState) {
		Set<State> set = callStack.get(startNode);
		if(set == null) return false;
		
		if(enterState.size() > 10 || set.size() > 50) {
			System.err.println("Suspiciously big state - analyzer probably cannot handle some recursive code.");
			return true;
		}
		return set.contains(enterState);
	}

	
	/**
	 * Remove function from the call stack
	 * 
	 * @param startNode start node of the function
	 * @param enterState entered state of the function
	 */
	public void remove(CFGNode startNode, State enterState) {
		callStack.get(startNode).remove(enterState);
	}
}
