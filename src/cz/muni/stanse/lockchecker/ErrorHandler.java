package cz.muni.stanse.lockchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;

public class ErrorHandler {
	private CheckerErrorReceiver errReceiver;
	private Map<CFGNode, Set<LockError>> lockErrors = new HashMap<CFGNode, Set<LockError>>();
	private Map<CFGNode, CFGHandle> dictionary;

	public ErrorHandler(CheckerErrorReceiver errReceiver, Map<CFGNode, CFGHandle> dictionary) {
		this.errReceiver = errReceiver;
		this.dictionary = dictionary;
	}

	public void handleLockingError(LockingResult e, List<CFGNode> path, CFGNode propNode) {
		List<CFGNode> shortenedPath = new ArrayList<CFGNode>(path);		
		shortenedPath = shortenedPath.subList(shortenedPath.indexOf(propNode), shortenedPath.size());
		
		if(lockErrors.containsKey(shortenedPath.get(0))) {
			for(LockError err : lockErrors.get(shortenedPath.get(0))) {
				if(err.getLockId().equals(e.getLockId()) && err.isUnlock() == e.isUnlock()) {					
					if(e.isOnlyPossible()) {
						// keep the old stack trace and change possibility
						err.setOnlyPossible(true);
					} else {
						err.addTrace(shortenedPath);
					}
					return;
				} 
			}	
		} else {
			lockErrors.put(shortenedPath.get(0), new HashSet<LockError>());			
		}
		// insert new error
		Set<LockError> set = lockErrors.get(shortenedPath.get(0));
		LockError err = new LockError(e.getLockId(), e.isUnlock(), e.isOnlyPossible(), dictionary);
		err.addTrace(shortenedPath);
		set.add(err);		
	}

	public void save() {
		for(Set<LockError> set : lockErrors.values()) {
			for(LockError err : set) {
				errReceiver.receive(err.getCheckerError());
			}
		}
	}
}
