
package cz.muni.stanse.threadchecker.graph;

import cz.muni.stanse.threadchecker.ThreadInfo;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import cz.muni.stanse.threadchecker.locks.LockStack;
import cz.muni.stanse.threadchecker.locks.UnlockSet;
import java.util.LinkedList;

/**
 * Class representing one edge in dependencyGraph
 * @author Jan Kučera
 */
public class DependencyRule extends AbstractDependencyRule
	implements Cloneable {
    private LockStack lockStack;
    private UnlockSet unlockSet;
    private ThreadInfo thread;
    private LinkedList<BackTrack> backTrack;
    
    public DependencyRule(DependencyRule rule, LockStack lockSet,
                                                        UnlockSet unlockSet) {
        super(rule);
        this.lockStack = lockSet;
        this.unlockSet = unlockSet;
    }

    public DependencyRule(ResourceVertex sourceRule, ResourceVertex targetRule,
                                   LockStack lockedNow,UnlockSet unlockSet ) {
        super(sourceRule, targetRule);
        this.lockStack = lockedNow;
        this.unlockSet = unlockSet;
    }

    private DependencyRule(ResourceVertex sourceRule,
                                                    ResourceVertex targetRule) {
        super(sourceRule, targetRule);
        this.lockStack = new LockStack();
        this.unlockSet = new UnlockSet();
    }

    public static DependencyRule joinRules(DependencyRule sourceRule,
                                                    DependencyRule targetRule) {
        DependencyRule newRule = new DependencyRule(sourceRule.getSource(),
                                                       targetRule.getTarget(),
                                                   targetRule.lockStack.clone(),
                                                  targetRule.unlockSet.clone());
        return newRule;
    }
    public ThreadInfo getThread() {
        return thread;
    }

    public LinkedList<BackTrack> getBackTrack() {
        return backTrack;
    }

    public void setBackTrack(LinkedList<BackTrack> backTrack) {
        this.backTrack = backTrack;
    }

    public void setLockStack(LockStack lockstack) {
        this.lockStack = lockstack;
    }

    public void setUnlockSet(UnlockSet unlockSet) {
        this.unlockSet = unlockSet;
    }


    public void setThread(ThreadInfo thread) {
        this.thread = thread;
    }

    public LockStack getLockStack() {
        return lockStack;
    }

    public UnlockSet getUnlockSet() {
        return unlockSet;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DependencyRule other = (DependencyRule) obj;

        if(!this.getSource().equals(other.getSource())) {
            return false;
        }
        if(!this.getTarget().equals(other.getTarget())) {
            return false;
        }

        if(!this.lockStack.equals(other.getLockStack())) {
            return false;
        }

        if(!this.unlockSet.equals(other.getUnlockSet())) {
            return false;
        }

    
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash
                    + (this.lockStack != null ? this.lockStack.hashCode() : 0);
        hash = 59 * hash
                    + (this.unlockSet != null ? this.unlockSet.hashCode() : 0);
        hash = 59 * hash + (this.thread != null ? this.thread.hashCode() : 0);
        return hash;
    }

    @Override
    public DependencyRule clone() {
        DependencyRule clone
                    = new DependencyRule(super.getSource(), super.getTarget(),
                                                        this.lockStack.clone(),
                                                        this.unlockSet.clone());
        clone.thread = this.thread;
        return clone;
    }
    
    @Override
    public String toString() {
        if(thread == null) {
            return super.toString();
        } else {
            return super.getTarget().getName() +" <--"+thread.getId()
                                            +"-- "+super.getSource().getName();
        }
    }    
}
    