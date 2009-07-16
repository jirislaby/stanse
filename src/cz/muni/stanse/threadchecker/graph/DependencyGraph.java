package cz.muni.stanse.threadchecker.graph;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Class holds List of DependencyRules and also provides some usefull methods
 * for comparing and reducing number of rules.
 * @author Jan Kučera
 */
public class DependencyGraph {
    private final List<DependencyRule> rules = new Vector<DependencyRule>();

    public DependencyGraph() {    }

    public DependencyGraph(List<DependencyRule> rules) {
        this.rules.addAll(rules);
    }

    public Collection<DependencyRule> getRules() {
        return rules;
    }

    public void addRule(DependencyRule newRule){
        if(rules.isEmpty()) {
            rules.add(newRule);
            return;
        }
        for(DependencyRule rule : rules) {
            if(rule.equals(newRule))
                return;
        }
        rules.add(newRule);
    }
    
    /**
     * Method compare otherGraph wheter these two graphs are subset or similar
     * @param otherGraph DependencyGraph
     * @return true whether otherGraph has similar DependencyRules.
     */
    public boolean isSubset(DependencyGraph otherGraph) {
        boolean isSimilar=false;
        for(DependencyRule otherRule : otherGraph.rules) {
            if(this.rules.contains(otherRule))
                continue;
            isSimilar=false;
            for(DependencyRule superSetRule : this.rules) {
                if(superSetRule.equals(otherRule)) {
                    isSimilar=true;
                    break;
                }
            }
            if(!isSimilar) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Method create new DependencyGraph and merge there Dep. rules from
     * this and other graph.
     * @param other DependencyGraph
     * @return DependencyGraph merged graph
     */
    public DependencyGraph merge(DependencyGraph other) {
        DependencyGraph newGraph = new DependencyGraph(this.rules);
        for(DependencyRule rule : other.rules) {
            newGraph.addRule(rule);
        }
        return newGraph;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DependencyGraph other = (DependencyGraph) obj;
        if(this.rules.size() != other.rules.size())
            return false;
        return (this.hashCode() == other.hashCode());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        int number;
        for(DependencyRule rule : this.rules) {
            number = ((AbstractDependencyRule) rule).hashCode();
            number *= rule.getLockStack().hashCode();
            number *= rule.getThread().hashCode();
            hash += number;
        }
        return hash;
    }

    public int size() {
        return this.rules.size();
    }

    @Override
    public String toString() {
        return rules.toString();
    }


}
