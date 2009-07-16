
package cz.muni.stanse.threadchecker.locks;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Jan Kučera
 */
public class LockStack {
    private Map<String,Lock> lockMap = new HashMap(0);
    private Stack<Lock> locks = new Stack<Lock>();

    public void add(Lock lock) {
        lockMap.put(lock.getName(), lock);
        locks.push(lock);
    }

    public void clear() {
        this.lockMap.clear();
        this.locks.clear();
    }

    public boolean contains(String lockName) {
        return this.lockMap.containsKey(lockName);
    }

    public Stack<Lock> getLocks() {
        return locks;
    }

    public ListIterator<Lock> listIterator() {
        return this.locks.listIterator();
    }

    public Lock getLastLock() {
        try {
            return locks.peek();
        } catch(EmptyStackException ex) {
            return null;
        }
    }

    @Override
    public LockStack clone() {
        LockStack clone = new LockStack();
        ListIterator<Lock> it = this.locks.listIterator();
        for(;it.hasNext();)
            clone.add(it.next().clone());
        
        return clone;
    }

    public boolean isEmpty() {
        return locks.empty();
    }

    public int size() {
        return locks.size();
    }

    public boolean contains(Lock lock) {
        return this.lockMap.containsKey(lock.getName());
    }

    public void remove(Lock lock) {
        Lock lockToRemove = this.lockMap.remove(lock.getName());
        this.locks.remove(lockToRemove);
    }

    @Override
    public String toString() {
        return this.locks.toString();
    }

    public Lock getLock(String lockName) {
        return this.lockMap.get(lockName);
    }

    public void merge(LockStack other) {
        Lock thisLock;
        for(Lock otherLock : other.locks) {
            thisLock = this.lockMap.get(otherLock.getName());
            if(thisLock != null) {
                thisLock.joinLocks(otherLock);
            } else {
                this.add(otherLock);
            }
        }
    }

    public void merge(UnlockSet unlockSet) {
        Set<Lock> toRemove = new HashSet(2);
        Lock lockedLock;
        int lockState;
        for(Lock unlockedLock : unlockSet.getLocks()) {
            lockedLock = this.getLock(unlockedLock.getName());
            if(lockedLock == null)
                continue;
            lockState = lockedLock.joinLocks(unlockedLock);
            if(lockState == 0) {
                toRemove.add(unlockedLock);
                this.remove(lockedLock);
                continue;
            }
            if(lockState > 0) {
                toRemove.add(unlockedLock);
                continue;
            } else {
                this.remove(lockedLock);
                unlockedLock.setState(lockState);
            }
        }
        unlockSet.remove(toRemove);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LockStack other = (LockStack) obj;
        if (this.locks != other.locks && !this.locks.equals(other.locks)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.locks.hashCode();
        return hash;
    }


}
