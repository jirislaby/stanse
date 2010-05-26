package cz.muni.stanse.lockchecker;

import cz.muni.stanse.codestructures.CFGNode;
/**
 * Class representing lock error. (double lock/unlock)
 * @author Radim Cebis
 *
 */
class LockError {	
	private String lockId;
	private boolean isOnlyPossible;
	private boolean isUnlock;
	private CFGNode node;	
	
	/**
	 * Constructor of the lock error
	 * @param lockId Id of the lock
	 * @param isUnlock is this unlock error?
	 * @param isOnlyPossible is this only possible error (not certain error)?
	 */
	public LockError(String lockId, boolean isUnlock, boolean isOnlyPossible) {
		super();
		this.lockId = lockId;
		this.isOnlyPossible = isOnlyPossible;
		this.isUnlock = isUnlock;
	}
	
	/**
	 * @return lock id
	 */
	public String getLockId() {
		return lockId;
	}	
	
	/**
	 * 
	 * @param isOnlyPossible is this only possible error (not certain error) to be set
	 */
	public void setOnlyPossible(boolean isOnlyPossible) {
		this.isOnlyPossible = isOnlyPossible;
	}
	
	/**
	 * 
	 * @return is this unlock error?
	 */
	public boolean isUnlock() {
		return isUnlock;
	}

	/**
	 * 
	 * @return is this only possible error (not certain error)?
	 */
	public boolean isOnlyPossible() {
		return isOnlyPossible;
	}
	
	/**
	 * Get node in which this error happened
	 * @return CFGNode
	 */
	public CFGNode getNode() {
		return node;
	}

	/**
	 * Set node in which this error happened
	 * @param node CFGNode in which this error happened
	 */
	public void setNode(CFGNode node) {
		this.node = node;
	}
	
	
}
