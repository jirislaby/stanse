package cz.muni.stanse.threadchecker.locks;

public interface Lock extends Cloneable {

    String getName();
    
    void setNodeNumber(Integer nodeID);

    Integer getNodeNumber();

    @Override
    String toString();
    
    Lock clone();

    boolean lockUp();

    boolean lockDown();

    int getState();

    void setState(int lockState);

    int joinLocks(Lock other);
}


