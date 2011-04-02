package cz.muni.stanse.threadchecker.locks;

public interface Lock extends Cloneable {

    String getName();
    
    void setNodeNumber(Integer nodeID);

    Integer getNodeNumber();

    @Override
    String toString();
    
    Lock clone();

    boolean lockUp() throws LockingException;

    boolean lockDown() throws LockingException;

    int getState();

    void setState(int lockState);

    int joinLocks(Lock other);
}


