package cz.muni.stanse.lockchecker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CFGState {
	// <lock_id, lock>
	private Map<String, Lock> locks = new HashMap<String, Lock>();
	private boolean wasVisited = false;
	
	public CFGState() {
	}
	
	public CFGState(boolean wasVisited) {
		this.wasVisited = wasVisited;
	}

	public void propagate(CFGState from) {		
		// propagate and add all locks to the TO node
		for(Lock lock : from.locks.values()) {
			if(locks.containsKey(lock.getId())) {	
				locks.get(lock.getId()).propagate(lock);
			} else {
				Lock newLock = new Lock(lock);
				locks.put(lock.getId(), newLock);
				// if this node has been visited and we did not save information about lock it means it was
				// on other path which is by default unlocked.
				if(wasVisited) newLock.forceUnlocked();
			}
		}
		// all locks which are in this node and missing in FROM node -> means unlocked in FROM node
		for(Lock lock : this.locks.values()) {
			if(!from.locks.containsKey(lock.getId())) {
				lock.forceUnlocked();
			}
		}
		wasVisited = true;
	}
	
	public LockingResult propagateLocking(boolean isUnlock, String lockId, CFGState from) {
		boolean found = false;		
		LockingResult res = checkDoubles(isUnlock, lockId, from);		
		// propagate and optionally process lock/unlock operation on all lock from FROM node
		for(Lock lock : from.locks.values()) {
			if(locks.containsKey(lock.getId())) {
				if(lock.getId().equals(lockId)) {
					found = true;					
					locks.get(lock.getId()).set(isUnlock);
				} else {
					locks.get(lock.getId()).propagate(lock);
				}
			} else {
				Lock newLock = new Lock(lock);				
				locks.put(lock.getId(), newLock);				
				if(newLock.getId().equals(lockId)) { 
					found = true;
					newLock.op(isUnlock);
				}
				else if(wasVisited) {
					// if this node has been visited and we did not save information about lock it means it was
					// on other path which is by default unlocked.
					newLock.forceUnlocked();
				}
			}
		}	
		// process lock/unlock op on all lock in this node, which were not used in FROM node
		for(Lock lock : locks.values()) {
			if(!from.locks.containsKey(lock.getId())) {
				if(lock.getId().equals(lockId)) {
					found = true;
					lock.op(isUnlock);
				} else {
					// all locks which are in this node and missing in FROM node -> means unlocked in FROM node
					lock.forceUnlocked();
				}
			}
		}
		
		// it is a new lock information
		if(!found) {
			Lock newLock = new Lock(lockId);				
			locks.put(lockId, newLock);
			newLock.op(isUnlock);
		}		
		
		wasVisited = true;
		return res;
	}

	private LockingResult checkDoubles(boolean isUnlock, String lockId,
			CFGState from) {
		LockingResult res = null;		
		if(from.locks.containsKey(lockId)) {
			Lock lock = from.locks.get(lockId);
			if(!isUnlock && lock.isLocked()) {
				if(lock.isUnlocked()) {
					res = new LockingResult(lock.getId(), false, true);
				} else {
					res = new LockingResult(lock.getId(), false, false);
				}
			}
			if(isUnlock && lock.isUnlocked()) {
				if(lock.isLocked()) {
					res = new LockingResult(lock.getId(), true, true);
				} else {
					res = new LockingResult(lock.getId(), true, false);
				}
			}		
		}
		return res;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(locks.values().toArray());// + ((wasVisited)? "visited":"unvisited");		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locks == null) ? 0 : locks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CFGState other = (CFGState) obj;
		if (locks == null) {
			if (other.locks != null)
				return false;
		} else if (!locks.equals(other.locks))
			return false;
		return true;
	}
}
