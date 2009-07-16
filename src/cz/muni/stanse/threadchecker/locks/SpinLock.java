package cz.muni.stanse.threadchecker.locks;

public class SpinLock implements Lock, Cloneable  {
    private Integer nodeID;
    private final String name;
    private int state = 0;
    
    public SpinLock(String name) {
        if(name == null) {
            throw new NullPointerException("Lock name is null");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getNodeNumber() {
        return nodeID;
    }

    @Override
    public void setNodeNumber(Integer nodeID) {
        this.nodeID = nodeID;
    }


    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int lockState) {
        this.state = lockState;
    }
    
    @Override
    public int joinLocks(Lock obj) {
       SpinLock other = (SpinLock) obj;
       this.state = this.state + other.state;
       return this.state;
    }

    @Override
    public String toString() {
        return name+"{"+state+"}";
    }

    @Override
    public SpinLock clone() {
        SpinLock clone = new SpinLock(this.name);
        clone.state = this.state;
        if(this.nodeID != null) {
            clone.nodeID = this.nodeID;
        }

        return clone;
    }

    /**
     * Method increments value state and return proper value.
     * @return true whether before calling this method was semaphore unlocked
     * and now is locked.
     */
    @Override
    public boolean lockUp() {
        if(this.state==0) {
            this.state++;
            return true;
        } else {
            this.state++;
            return false;
        }
    }

    /**
     * Method decrements value state and return proper value.
     * @return true whether before calling this method was semaphore locked
     * and now is unlocked.
     */
    @Override
    public boolean lockDown() {
        if(this.state==1) {
            this.state--;
            return true;
        } else {
            this.state--;
            return false;
        }
    }


    /**
     * Function returns true if LockName is equal, because its unique and also
     * checks number of locking.
     * @param obj Object to equal
     * @return true if locks are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SpinLock other = (SpinLock) obj;
        
        if(!this.getName().equals(other.getName()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash *= getName().hashCode();
        hash *= state*2;
        return hash;
    }
}

