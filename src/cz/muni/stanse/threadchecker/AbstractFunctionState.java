
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.graph.DependencyRule;
import cz.muni.stanse.threadchecker.graph.ResourceVertex;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import cz.muni.stanse.threadchecker.locks.JoinNode;
import cz.muni.stanse.threadchecker.locks.Lock;
import cz.muni.stanse.threadchecker.locks.LockStack;
import cz.muni.stanse.threadchecker.locks.SpinLock;
import cz.muni.stanse.threadchecker.locks.UnlockSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * Abstract Class which represents dependency rules in one function and all
 * necessary data for stitching with other FunctionState.
 * @author Jan Kučera
 */
public class AbstractFunctionState {
    private final LockStack lockStack = new LockStack();
    private final UnlockSet unlockSet = new UnlockSet();
    private final List<DependencyRule> rules = new Vector<DependencyRule>();
    private final Set<JoinNode> joins = new HashSet<JoinNode>();
    private final Map<String,List<DependencyRule>> rulesBySourceNode
                                   = new HashMap<String,List<DependencyRule>>();
    private final Map<String,List<DependencyRule>> rulesByTargetNode
                                   = new HashMap<String,List<DependencyRule>>();
    private final LinkedList<BackTrack> backTrack;

    private final static Logger logger
                      = Logger.getLogger(AbstractFunctionState.class.getName());

    public AbstractFunctionState() {
        this.backTrack = new LinkedList<BackTrack>();
    }

    public AbstractFunctionState(LinkedList<BackTrack> backTrack) {
        this.backTrack = backTrack;
    }

    protected final LockStack getLockStack() {
        return lockStack;
    }

    protected UnlockSet getUnlockSet() {
        return unlockSet;
    }

    public List<DependencyRule> getRules() {
        return rules;
    }

    public LinkedList<BackTrack> getBackTrack() {
        return backTrack;
    }

    public void addRules(Collection<DependencyRule> rules) {
        for(DependencyRule rule : rules) {
            this.addRule(rule);
        }
    }
    
    public void addJoin(ResourceVertex srcNode,
                                        LockStack locked, UnlockSet unlocked) {
        JoinNode newJoin = new JoinNode(srcNode, locked, unlocked);
        this.joins.add(newJoin);
    }

    /**
     * Method add rule to this.rules list and creates keys for all maps
     * for searching.
     * @param rule DependencyRule which should be added.
     */
    public void addRule(DependencyRule rule) {
        rules.add(rule);
        String sourceNode = rule.getSource().getName();
        String targetNode = rule.getTarget().getName();
        if(rulesBySourceNode.containsKey(sourceNode)) {
            rulesBySourceNode.get(sourceNode).add(rule);
        } else {
            List<DependencyRule> ruleList = new Vector<DependencyRule>();
            ruleList.add(rule);
            rulesBySourceNode.put(sourceNode, ruleList);
        }

        if(rulesByTargetNode.containsKey(targetNode)) {
            rulesByTargetNode.get(targetNode).add(rule);
        } else {
            List<DependencyRule> ruleList = new Vector<DependencyRule>();
            ruleList.add(rule);
            rulesByTargetNode.put(targetNode, ruleList);
        }
    }

    /**
     * Method pick up lock from lockStack or unlockStack, otherwise create a
     * new instanse of lock named as lockName.
     * @param lockName String lock's name
     * @return Lock
     */
    public Lock getLock(String lockName) {
        if(this.lockStack.contains(lockName)){
            if(this.unlockSet.contains(lockName)) {
                throw new IllegalArgumentException("Lock "+lockName
                           +" is in lockStack and unlockset at the same time!");
            }
            return this.lockStack.getLock(lockName);
        }
        if(this.unlockSet.contains(lockName)) {
            return this.unlockSet.getLock(lockName);
        } else {
            return new SpinLock(lockName);
        }
    }

    public Set<JoinNode> getJoins() {
        return joins;
    }
    
    public Collection<Lock> getScope() {
        Collection<Lock> scope = new Vector<Lock>();
        scope.addAll(this.lockStack.getLocks());
        scope.addAll(this.unlockSet.getLocks());
        return scope;
    }

    public Map<String, List<DependencyRule>> getRulesBySourceNode() {
        return rulesBySourceNode;
    }

    public Map<String, List<DependencyRule>> getRulesByTargetNode() {
        return rulesByTargetNode;
    }

    /**
     * Method determine, whether data contains any valuable data
     * @return boolean whether data doesn't contain something usefull
     */
    public boolean isEmpty() {
        if(this.joins.isEmpty() && this.lockStack.isEmpty() 
                        && this.rules.isEmpty() && this.unlockSet.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FunctionState other = (FunctionState) obj;
        if(!this.getLockStack().equals(other.getLockStack()))
            return false;
        if(!this.getUnlockSet().equals(other.getUnlockSet()))
            return false;
        if(!this.getJoins().equals(other.getJoins()))
            return false;
        if(!this.getRules().equals(other.getRules()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.getLockStack().hashCode();
        hash = 47 * hash + this.getRules().hashCode();
        hash = 47 * hash + this.getJoins().hashCode();
        hash = 47 * hash + this.getUnlockSet().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("|");
        if (getLockStack().size() > 0) {
            result.append("LockSet:[");
            for (Lock lock: this.getLockStack().getLocks())
                   result.append(lock).append(',');
            result.append("] ");
        }
        if (getUnlockSet().size() > 0) {
            result.append("UnlockSet:[");
            for (Lock unlock: this.getUnlockSet().getLocks())
                   result.append(unlock).append(',');
            result.append("] ");
        }
        if (getRules().size() > 0) {
            result.append("Rules:").append(getRules());
        }
        if (getJoins().size() > 0) {
            result.append(" Joins:[");
            for (JoinNode node: getJoins())
                result.append(node).append(',');
            result.append("] ");
        }
	result.append('|');
        return result.toString();
    }

    /**
     * Method tries to unlock semaphore, if so, removes lock from lockSet.
     * If lock is still lock (recursive type) decrement lock's state or set lock
     * to unlockSet.
     * @param lockName String name of semaphore
     */
    public void lockDown(String lockName) {
        Lock lock;
        lock = this.getLock(lockName);

        if(lockStack.contains(lock)) {
            if(!lockStack.getLastLock().equals(lock)) {
                logger.warn("Unlocking lock "+lock.getName()
                                        +" isn't at the peek of lockStack");
            }
            if(lock.lockDown()) {
               lockStack.remove(lock);
            }
        } else {
            lock.lockDown();
            unlockSet.add(lock);
        }
    }
    /**
     * Tries to lock semaphore -> if lockSet is isEmpty, creates join node,
     * otherwise creates dependency rule. If lock is already lock, just increase
     * lock state.
     * @param lockName String name of lock
     * @param node CFGNode for setting spot where lock was set to locked state
     */
    public void lockUp(String lockName, CFGNode node) {
            Lock lock;
            Lock lastLock;
            ResourceVertex srcNode;
            ResourceVertex targetNode;

            lock = this.getLock(lockName);
            srcNode = new ResourceVertex(lock.getName(), node.getNumber());

            if(lockStack.isEmpty()) { //No lock is held, create only join node
               if(lock.lockUp()) {
                   lock.setNodeNumber(node.getNumber());
                   lockStack.add(lock);
                   this.addJoin(srcNode,new LockStack(),unlockSet.clone());
               } else {
                   if(lock.getState()==0) {
                       unlockSet.remove(lock);
                   }
               }
            } else {
                if(lock.lockUp()) {
                   lastLock = lockStack.getLastLock();
                   targetNode = new ResourceVertex(lastLock.getName(),
                                                      lastLock.getNodeNumber());
                   this.addRule(new DependencyRule(srcNode,targetNode,
                                                           lockStack.clone(),
                                                           unlockSet.clone()));
                   lock.setNodeNumber(node.getNumber());
                   lockStack.add(lock);
                } else {
                    if(lock.getState()==0) {
                        unlockSet.remove(lock);
                    }
                    logger.info("Lock "+lock.getName()+" is recursive lock");
                }
            }
    }
}
