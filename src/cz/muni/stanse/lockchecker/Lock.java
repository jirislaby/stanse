package cz.muni.stanse.lockchecker;

/**
 * Class representing a lock. Lock has ID
 * and two indicators if it is locked or unlocked which are independent.
 * Lock can be locked and unlocked in the same time (both state can be the case).
 * @author Radim Cebis
 *
 */
class Lock {
	private String id;
	// lock has 2 states which are independent!!! b/c propagation when paths are joined
	// is this lock locked?
	private boolean locked = false;
	// is this lock unlocked?
	private boolean unlocked = true;

	/**
	 * Copy constructor
	 * @param lock Lock to be copied
	 */
	public Lock(Lock lock) {
		this.id = lock.id;
		this.locked = lock.locked;
		this.unlocked = lock.unlocked;
	}

	/**
	 * Constructor
	 * @param id of the lock
	 */
	public Lock(String id) {
		super();
		this.id = id;
	}

	private void lock() {
		this.locked = true;
		this.unlocked = false;
	}

	private void unlock() {
		this.locked = false;
		this.unlocked = true;
	}

	@Override
	public String toString() {
		String append = "CANNOT HAPPEN";
		if(locked && !unlocked) append = "locked";
		// this happens when two paths join and in one lock was locked and in other one unlocked
		else if(locked && unlocked) append = "locked&unlocked";
		else if(!locked && unlocked) append = "unlocked";
		return "Lock \"" + VarTransformations.prettyPrint(id) + "\" " + append;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + (unlocked ? 1231 : 1237);
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
		Lock other = (Lock) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locked != other.locked)
			return false;
		if (unlocked != other.unlocked)
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	/**
	 * Propagates argument lock to this lock.
	 * Executes boolean OR operation on lock/unlock indicators.
	 *
	 * @param lock Lock which is propagated to this lock
	 * @return true if the propagation changed this lock
	 */
	public boolean propagate(Lock lock) {
		boolean oldLocked = this.locked;
		boolean oldUnlocked = this.unlocked;
		this.locked = this.locked || lock.locked;
		this.unlocked = this.unlocked || lock.unlocked;
		return (locked != oldLocked) || (unlocked != oldUnlocked);
	}

	/**
	 * Force this lock to be also unlocked. (does not change lock indicator - only unlock)
	 * @return has this lock changed after operation?
	 */
	public boolean forceUnlocked() {
		boolean oldUnlocked = this.unlocked;
		unlocked = true;
		return oldUnlocked != unlocked;
	}

	/**
	 *
	 * @return locked indicator
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 *
	 * @return unlock indicator
	 */
	public boolean isUnlocked() {
		return unlocked;
	}

	/**
	 * Lock or unlock this lock
	 * @param isUnlock should this be unlock operation?
	 */
	public void op(boolean isUnlock) {
		if(isUnlock) this.unlock();
		else this.lock();

	}

	/**
	 * Set the lock identifier
	 * @param id to be set
	 */
	public void setId(String id) {
		this.id = id;
	}
}