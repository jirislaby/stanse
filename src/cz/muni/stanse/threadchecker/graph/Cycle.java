package cz.muni.stanse.threadchecker.graph;

import cz.muni.stanse.threadchecker.ThreadInfo;
import cz.muni.stanse.threadchecker.exceptions.CycleException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Data class which accepts list of DependencyRules and it create some searching
 * functions for easier manipulation with rules.
 * @author Jan Kučera
 */
public class Cycle {
    private final Map<ThreadInfo,List<DependencyRule>> rulesByThread
                            = new HashMap<ThreadInfo, List<DependencyRule>>();
    private final Map<ResourceVertex,AbstractDependencyRule> rulesBySourceNode
                        = new HashMap<ResourceVertex,AbstractDependencyRule>();
    private final Map<ResourceVertex,AbstractDependencyRule> rulesByTargetNode
                        = new HashMap<ResourceVertex,AbstractDependencyRule>();
    private final List<AbstractDependencyRule> rules;
    private final List<ResourceVertex> resources = new Vector<ResourceVertex>();
    private final Set<String> resourceNames = new HashSet<String>();

    public Cycle(List<AbstractDependencyRule> rules) throws CycleException {
        List<DependencyRule> ruleList;
        this.rules = rules;
        DependencyRule depRule;
        for(AbstractDependencyRule rule : rules) {
            resources.add(rule.getSource());
            resourceNames.add(rule.getSource().getName());
            resources.add(rule.getTarget());
            resourceNames.add(rule.getTarget().getName());

            if(!rulesBySourceNode.containsKey(rule.getSource())) {
                rulesBySourceNode.put(rule.getSource(),rule);
            } else {
                throw new CycleException("Rule "+rule
                                            +" is already defined in cycle");
            }

            if(!rulesByTargetNode.containsKey(rule.getTarget())) {
                rulesByTargetNode.put(rule.getTarget(),rule);
            } else {
                throw new CycleException("Rule "+rule
                                            +" is already defined in cycle");
            }

            if(rule instanceof DependencyRule) {
                depRule = (DependencyRule) rule;
                if(rulesByThread.containsKey(depRule.getThread())) {
                    rulesByThread.get(depRule.getThread()).add(depRule);
                } else {
                    ruleList = new Vector<DependencyRule>();
                    ruleList.add(depRule);
                    rulesByThread.put(depRule.getThread(),ruleList);
                }
            }
        }
    }

    public Map<ResourceVertex, AbstractDependencyRule> getRulesBySourceNode() {
        return rulesBySourceNode;
    }

    public Map<ResourceVertex, AbstractDependencyRule> getRulesByTargetNode() {
        return rulesByTargetNode;
    }

    public Map<ThreadInfo, List<DependencyRule>> getRulesByThread() {
        return rulesByThread;
    }

    public List<AbstractDependencyRule> getRules() {
        return rules;
    }

    public List<ResourceVertex> getResources() {
        return resources;
    }

    public Set<String> getResourceNames() {
        return resourceNames;
    }

    @Override
    public String toString() {
        return "Cycle:"+this.rules;
        }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cycle other = (Cycle) obj;
        if(this.rules.size()!=other.rules.size()) {
            return false;
        }

        return this.rules.containsAll(other.rules);
    }

    @Override
    public int hashCode() {
        int hash = 7 * this.rules.hashCode();
        return hash;
    }

}
