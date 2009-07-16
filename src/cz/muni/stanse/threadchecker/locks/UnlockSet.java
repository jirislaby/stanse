
package cz.muni.stanse.threadchecker.locks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jan Kučera
 */
public class UnlockSet {
    private Map<String,Lock> locks = new HashMap<String,Lock>(0);

    public boolean isEmpty() {
        return locks.keySet().isEmpty();
    }

    @Override
    public UnlockSet clone() {
        UnlockSet clone = new UnlockSet();
        for(Lock lock : this.locks.values()) {
            clone.locks.put(lock.getName(), lock.clone());
        }
        return clone;
    }

    public Collection<Lock> getLocks() {
        return locks.values();
    }

    public Lock getLock(String lockName) {
        return locks.get(lockName);
    }

    public void add(Lock lock) {
        locks.put(lock.getName(), lock);
    }

    public void clear() {
        this.locks.clear();
    }

    @Override
    public String toString() {
        return this.locks.values().toString();
    }



    public int size() {
        return locks.keySet().size();
    }

    public boolean contains(Lock lock) {
        return this.locks.containsKey(lock.getName());
    }

    public boolean contains(String lockName) {
        return this.locks.containsKey(lockName);
    }

    public void merge(UnlockSet other) {
        Lock thisLock;
        for(Lock otherLock : other.locks.values()) {
            thisLock = this.locks.get(otherLock.getName());
            if(thisLock != null) {
                thisLock.joinLocks(otherLock);
            } else {
                this.add(otherLock);
            }
        }
    }

    public void remove(Lock lock) {
        this.locks.remove(lock.getName());
    }

    public void remove(Collection<Lock> toRemove) {
        for(Lock lock : toRemove)
            this.locks.remove(lock.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnlockSet other = (UnlockSet) obj;
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
