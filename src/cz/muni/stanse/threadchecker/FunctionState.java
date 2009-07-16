
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.threadchecker.graph.DependencyGraph;
import cz.muni.stanse.threadchecker.graph.ResourceVertex;
import cz.muni.stanse.threadchecker.locks.Lock;
import cz.muni.stanse.threadchecker.graph.DependencyRule;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import cz.muni.stanse.threadchecker.locks.JoinNode;
import cz.muni.stanse.threadchecker.locks.LockStack;
import cz.muni.stanse.threadchecker.locks.UnlockSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;


/**
 * Class which is used in CFGTransit algorithm. Inherits all methods from
 * AbstractFunctionState and contains methods for stitching two
 * FunctionState to new one.
 *
 * @author Jan Kučera
 */
public class FunctionState extends AbstractFunctionState implements Cloneable {

    public FunctionState() {
        super();
    }

    public FunctionState(FunctionState other) {
        super(new LinkedList<BackTrack>(other.getBackTrack()));
    }

    @Override
    public FunctionState clone() {
        FunctionState other = new FunctionState(this);
        ListIterator<Lock> it = this.getLockStack().listIterator();
        for(;it.hasNext();){
            other.getLockStack().add(it.next().clone());
        }
        for(Lock lock: this.getUnlockSet().getLocks()) {
            other.getUnlockSet().add(lock.clone());
        }
        
        for(DependencyRule rule: this.getRules()) 
            other.addRule(rule.clone());
        
        for(JoinNode node : this.getJoins())
            other.getJoins().add(node.clone());
        return other;
    }

    /**
     * Function returns boolean value comparing if this object and parameter are
     * equal or others is a subset.
     * @param others FunctionState
     * @return true if object others is subset of this FunctionState object
     */
    public boolean isSubset(FunctionState others) {
        if(!this.getJoins().containsAll(others.getJoins()))
            return false;

        if(!this.getLockStack().equals(others.getLockStack())) {
                return false;
        }

        if(!this.getUnlockSet().equals(others.getUnlockSet())) {
                return false;
        }
        
        if(!this.getRules().containsAll(others.getRules())) {
            return false;
        }
        return true;
    }

    /**
     * Function gets FunctionState others and merge both structures. Creates new
     * rules between locked locks if other.getJoins() holds lock(s).
     * Also join all new data from others to this.
     * @param others FunctionState which should be stitched to this object
     */
    public void stitchFunctions(FunctionState others) {
        DependencyRule newRule;

        while(others.containsRecursiveLocks(this.getLockStack())) {
            others = this.removeRecursiveLocks(others);

            if(others.isEmpty()) //non-recursive data could be isEmpty - skip
                return;
        }

        //caller held no locks - add only new joins and unlocked locks
        if(this.getLockStack().isEmpty()) {
            for(JoinNode node : others.getJoins()) {
                this.getJoins().add(node);
            }
        } else {
            for(JoinNode join : others.getJoins()) {
                Object result = this.mergeJoin(join);

                if(result.getClass()==JoinNode.class) {
                    JoinNode newJoin = (JoinNode) result;
                    this.getJoins().add(newJoin);
                    continue;
                }
                if(result.getClass()==DependencyRule.class) {
                    newRule = (DependencyRule) result;
                    if(!this.getRules().contains(newRule)) {
                        this.addRule(newRule);
                    }
                } else {
                    throw new IllegalArgumentException("Wrong class of result");
                }

            }
        }

        for(DependencyRule rule : others.getRules()) {
            //Merge locks of caller function with calle and store them to rule
            this.updateLocksInRule(rule);

            if(!this.getRules().contains(rule))
                this.getRules().add(rule);
        }
        
        //Add locked locks to lockset
        this.mergeLockStates(others);

        this.getBackTrack().addAll(others.getBackTrack());
    }

    /**
     * Method generate DepenencyGraph. Clone all rules, set thread to every rule
     * and clear their unlockSet -> it's not used in RAG building and can help
     * with merging same Cycles
     * @param thread
     * @return DependencyGraph
     */
    public DependencyGraph generateGraph(ThreadInfo thread) {
        DependencyGraph graph = new DependencyGraph();
        for(DependencyRule rule : this.getRules()) {
            rule = rule.clone();
            //Unlockset is not relevant for dep. rules
            rule.getUnlockSet().clear();
            rule.setBackTrack(this.getBackTrack());
            rule.setThread(thread);
            graph.addRule(rule);
        }
        return graph;
    }
    
    /**
     * Method transforms FunctionState others to non-recursive form. Method
     * removes already locked semaphores, removed or edit dependencyRules and
     * set this FunctionState recursive semaphores to proper states
     * @param function FunctionState which should be transformed
     */
    private FunctionState removeRecursiveLocks(FunctionState function) {
        List<DependencyRule> rulesWhereLockIsSource;
        List<DependencyRule> rulesWhereLockIsTarget;
        DependencyRule newRule;
        boolean isJoin = false;
        List<DependencyRule> rulesToAdd = new Vector<DependencyRule>();
        List<JoinNode> joinsToRemove = new Vector<JoinNode>();
        Set<String> recursiveLocks = new HashSet<String>();

        for(Lock lock : this.getLockStack().getLocks()) {
            if(function.getRulesBySourceNode().containsKey(lock.getName()))
                recursiveLocks.add(lock.getName());
            for(JoinNode joinNode : function.getJoins()) {
                if(joinNode.getVertex().getName().equals(lock.getName()))
                    recursiveLocks.add(lock.getName());
            }
        }

        // Removing recursive A: X <- A <- Y to X<-Y
        for(String lockName : recursiveLocks) {
            if(function.getRulesByTargetNode().containsKey(lockName)
                && function.getRulesBySourceNode().containsKey(lockName)) {
                rulesWhereLockIsSource
                               = function.getRulesBySourceNode().get(lockName);
                rulesWhereLockIsTarget
                               = function.getRulesByTargetNode().get(lockName);

                for(DependencyRule targetRule : rulesWhereLockIsSource){
                    for(DependencyRule sourceRule : rulesWhereLockIsTarget){
                        newRule = DependencyRule.joinRules(sourceRule,
                                                                    targetRule);
                        rulesToAdd.add(newRule);
                    }
                }
                function.getRules().removeAll(rulesWhereLockIsSource);
                function.getRules().removeAll(rulesWhereLockIsTarget);
                function.getRulesBySourceNode().remove(lockName);
                function.getRulesByTargetNode().remove(lockName);
            }

            //Remove rules where recursive lock is as target in rule,
            // Recursive lock A is join: A <- X, X becomes joinNode
            if(function.getRulesByTargetNode().containsKey(lockName)) {
                rulesWhereLockIsTarget
                               = function.getRulesByTargetNode().get(lockName);
                isJoin = false;
                Set<JoinNode> toRemove = new HashSet<JoinNode>(1);
                for(JoinNode join : function.getJoins()) {
                    if(join.getVertex().getName().equals(lockName)) {
                        toRemove.add(join);
                        isJoin=true;
                    }
                }
                function.getJoins().removeAll(toRemove);
                
                if(isJoin) {
                    for(DependencyRule rule : rulesWhereLockIsTarget) {
                        //no need to clone -> rule is removed soon
                        function.addJoin(rule.getSource(),rule.getLockStack(),
                                                        rule.getUnlockSet());
                    }
                }
                function.getRulesByTargetNode().remove(lockName);
                function.getRules().removeAll(rulesWhereLockIsTarget);
            }

            //Remove recursive locks in joins
            for(JoinNode join : function.getJoins()) {
                if(join.getVertex().getName().equals(lockName))
                    joinsToRemove.add(join);
            }
            function.getJoins().removeAll(joinsToRemove);
            joinsToRemove.clear();

            //remove rest of rules with recursive locks
            rulesWhereLockIsSource
                                = function.getRulesBySourceNode().get(lockName);
            function.getRulesBySourceNode().remove(lockName);
            if(rulesWhereLockIsSource != null)
                function.getRules().removeAll(rulesWhereLockIsSource);
        }
        function.addRules(rulesToAdd);
        return function;
    }
    
    /**
     * Method checks wheter this FunctionState contains locks in rules or
     * joins which are already locked in caller FunctionState.
     * @param callerLockSet
     * @return boolean whether this FunctionState contains recursive locks
     */
    private boolean containsRecursiveLocks(LockStack callerLockSet) {
        String lockName;
        for(Lock lock : callerLockSet.getLocks()) {
            lockName = lock.getName();
            if(this.getRulesBySourceNode().containsKey(lockName))
                return true;
            for(JoinNode joinNode : this.getJoins()) {
                if(joinNode.getVertex().getName().equals(lockName))
                    return true;
            }
        }
        return false;
    }

    /**
     * * Method merges this and callee's relative locksets.
     * @param callee FunctionState
     */
    private void mergeLockStates(FunctionState callee) {
        this.getLockStack().merge(callee.getUnlockSet());
        this.getLockStack().merge(callee.getLockStack());
        this.getUnlockSet().merge(callee.getUnlockSet());
        this.getLockStack().merge(this.getUnlockSet());
    }

    /**
     * Method perform lockUpdate to rule with locks of this instance.
     * @param rule DependencyRule
     */
    private void updateLocksInRule(DependencyRule rule) {
        //Merge locks of caller function with calle and store them to rule
            LockStack lockstack = this.getLockStack().clone();
            UnlockSet unlockSet = this.getUnlockSet().clone();
            lockstack.merge(rule.getUnlockSet());
            lockstack.merge(rule.getLockStack());
            unlockSet.merge(unlockSet);

            //Reverse merging -
            rule.setLockStack(lockstack);
            rule.setUnlockSet(unlockSet);
    }

    /**
     * Method merge join with this instance of FunctionsState =>
     * creates new JoinNode or DependencyRules.
     * @param join JoinNode
     * @return JoinNode whether this caller has no locked lock or new
     * DependencyRule between this.laskLockedLock and Join join
     */
    private Object mergeJoin(JoinNode join) {
        LockStack callerLockStack = this.getLockStack().clone();
        UnlockSet callerUnlockSet = this.getUnlockSet().clone();
        callerLockStack.merge(join.getUnlockSet());
        callerLockStack.merge(join.getLockStack());
        callerUnlockSet.merge(join.getUnlockSet());

        Lock lock = callerLockStack.getLastLock();

        if(lock == null) {
            JoinNode newJoin = new JoinNode(join.getVertex(), callerLockStack,
                                                            callerUnlockSet);
            return newJoin;
        }

        ResourceVertex target = new ResourceVertex(lock.getName(),
                                                          lock.getNodeNumber());
        DependencyRule newRule = new DependencyRule(join.getVertex(), target,
                                               callerLockStack,callerUnlockSet);
        return newRule;
    }

}
