package cz.muni.stanse.lockchecker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class representing state of the locks for a node
 *
 * @author Radim Cebis
 */
class State {
	// <lock_id, lock>
	private Map<String, Lock> locks = null;
	private boolean wasVisited = false;

	public State() {
	}


	/**
	 * Copy constructor
	 * @param state to copy
	 */
	public State(State state) {
		if (state.locks != null) {
			locks = new HashMap<String, Lock>();
			for (final Lock lock : state.locks.values())
				locks.put(lock.getId(), new Lock(lock));
		}
	}

	/**
	 * Is this state unlocked
	 * @return returns true if all the locks are unlocked - or there are no locks
	 */
	public boolean isUnlocked() {
		if (locks == null)
			return true;
		for (final Lock l : locks.values())
			if(l.isLocked())
				return false;
		return true;
	}

	private boolean propagateLock(final Lock lock) {
		boolean changed = false;
		if (locks != null && locks.containsKey(lock.getId())) {
			if (locks.get(lock.getId()).propagate(lock))
				changed = true;
		} else {
			final Lock newLock = new Lock(lock);
			if (newLock.isLocked()) {
				if (locks == null)
					locks = new HashMap<String, Lock>();
				locks.put(lock.getId(), newLock);
				changed = true;
				/*
				 * if this node has been visited and we did not
				 * save information about lock it means it was
				 * on other path which is by default unlocked.
				 */
				if (wasVisited)
					newLock.forceUnlocked();
			}
			changed = true;
		}
		return changed;
	}

	/**
	 * Propagates From State to this state
	 * @param from State to propagate from
	 * @return has this state changed?
	 */
	public boolean propagate(State from) {
		boolean changed = false;
		// propagate and add all locks to the TO node
		if (from.locks != null)
			for(final Lock lock : from.locks.values())
				changed = propagateLock(lock);
		/*
		 * all locks which are in this node and missing in FROM node ->
		 * means unlocked in FROM node
		 */
		if (locks != null) {
			for (final Lock lock : this.locks.values())
				if (from.locks == null || !from.locks.containsKey(lock.getId()))
					if(lock.forceUnlocked())
						changed = true;
		}

		boolean oldWasVisited = wasVisited;
		wasVisited = true;
		return changed || oldWasVisited != wasVisited;
	}


	/**
	 * Returns transformed copy of the state
	 *
	 * @param isUnlock is this operation unlock operation?
	 * @param lockId id of the lock which will be transformed
	 * @param state State from which we want to make transformation
	 * @return transformed copy of the state
	 */
	public static State transformState(boolean isUnlock, String lockId, State state) {
		State transformedState = new State(state);
		Lock lock = new Lock(lockId);
		lock.op(isUnlock);
		if (transformedState.locks == null)
			transformedState.locks = new HashMap<String, Lock>();
		transformedState.locks.put(lockId, lock);
		return transformedState;
	}

	/**
	 * Check if there is double lock/unlock error when there would be operation on the state
	 * @param isUnlock
	 * @param lockId
	 * @param state
	 * @return LockError if one occurs, null otherwise
	 */
	public static LockError checkDoubles(boolean isUnlock,
			final String lockId, final State state) {
		LockError res = null;
		if (state.locks != null && state.locks.containsKey(lockId)) {
			Lock lock = state.locks.get(lockId);
			if (!isUnlock && lock.isLocked()) {
				res = new LockError(lock.getId(), false,
					lock.isUnlocked());
			}
			if (isUnlock && lock.isUnlocked()) {
				res = new LockError(lock.getId(), true,
					lock.isLocked());
			}
		}
		if (state.locks == null || !state.locks.containsKey(lockId)) {
			if (isUnlock)
				res = new LockError(lockId, true, false);
		}

		return res;
	}

	@Override
	public String toString() {
		if (locks == null)
			return "unlocked";
		return Arrays.toString(locks.values().toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		if (locks != null)
			result += locks.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		if (locks == null) {
			if (other.locks != null)
				return false;
		} else if (!locks.equals(other.locks))
			return false;
		return true;
	}

	/**
	 * Returns renamed copy of the state
	 * @param state State to be copied
	 * @param varTransformations name transformations
	 * @param fromCallerToCallee should this rename from caller to callee?
	 * @return renamed copy of the state
	 */
	public static State getRenamedCFGState(final State state,
			final VarTransformations varTransformations,
			boolean fromCallerToCallee) {
		final State res = new State();
		if (state.locks == null)
			return res;

		res.locks = new HashMap<String, Lock>();

		for (final Lock lock : state.locks.values()) {
			String newId = varTransformations.transform(
				lock.getId(), fromCallerToCallee);
			if (newId != null) {
				Lock l = new Lock(lock);
				l.setId(newId);
				res.locks.put(newId, l);

			} else
				res.locks.put(lock.getId(), new Lock(lock));
		}
		return res;
	}


	/**
	 * @return number of locks registered in this state
	 */
	public int size() {
		return locks == null ? 0 : locks.size();
	}


	/**
	 * Returns true if this state contains all the locks contained
	 * in parameter.
	 * @param state to look into for the locks
	 * @return true if this state contains all locks in same state in the given parameter
	 */
	public boolean contains(State state) {
		if (locks == null)
			return state.locks == null || state.locks.size() == 0;

		if (locks.size() < state.locks.size())
			return false;

		for (final Entry<String, Lock> entry : state.locks.entrySet()) {
			final Lock lock = locks.get(entry.getKey());
			if (lock == null)
				return false;
			if (!lock.equals(entry.getValue()))
				return false;
		}
		return true;
	}
}
