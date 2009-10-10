
package cz.muni.stanse.threadchecker.locks;

import cz.muni.stanse.threadchecker.graph.ResourceVertex;

/**
 *
 * @author Jan Kučera
 */
public class JoinNode implements Cloneable {
    private ResourceVertex vertex;
    private LockStack lockStack;
    private UnlockSet unlockSet;

    private JoinNode() {}

    public JoinNode(ResourceVertex vertex, LockStack lockStack
                                                        , UnlockSet unlockSet) {
        this.vertex = vertex;
        this.lockStack = lockStack;
        this.unlockSet = unlockSet;
    }

    public LockStack getLockStack() {
        return lockStack;
    }

    public UnlockSet getUnlockSet() {
        return unlockSet;
    }

    public ResourceVertex getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
        String result = vertex.getName()+"{"+lockStack+"}"+"{"+unlockSet+"}";
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final JoinNode other = (JoinNode) obj;
        if (this.vertex != other.vertex && !this.vertex.equals(other.vertex)) {
            return false;
        }
        if (this.lockStack != other.lockStack
                                && !this.lockStack.equals(other.lockStack)) {
            return false;
        }
        if (this.unlockSet != other.unlockSet
                                && !this.unlockSet.equals(other.unlockSet)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3*vertex.hashCode();
        hash = hash * lockStack.hashCode();
        hash = hash * unlockSet.hashCode();
        return hash;
    }

    @Override
    public JoinNode clone() {
        JoinNode clone = new JoinNode();
        clone.vertex = this.vertex;
        clone.lockStack = this.lockStack.clone();
        clone.unlockSet = this.unlockSet.clone();
        return clone;
    }
}
