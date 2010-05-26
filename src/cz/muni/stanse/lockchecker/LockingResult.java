package cz.muni.stanse.lockchecker;

public class LockingResult {
	
	private static final long serialVersionUID = 1L;
	private String lockId;
	// is this error only possible (lock on locked&unlocked node) or real one (lock on locked node)
	private boolean isOnlyPossible;
	private boolean isUnlock;
	
	public LockingResult(String lockId, boolean isUnlock, boolean isOnlyPossible) {
		super();
		this.lockId = lockId;
		this.isOnlyPossible = isOnlyPossible;
		this.isUnlock = isUnlock;
	}

	public String getLockId() {
		return lockId;
	}

	public boolean isOnlyPossible() {
		return isOnlyPossible;
	}

	public boolean isUnlock() {
		return isUnlock;
	}
}
