package cz.muni.stanse.threadchecker.graph;

/**
 * Abstract class for DependencyRule
 * @author Jan Kučera
 */
public abstract class AbstractDependencyRule {
    private final ResourceVertex source;
    private final ResourceVertex target;
    
    public AbstractDependencyRule(AbstractDependencyRule rule) {
        this.source = rule.source;
        this.target = rule.target;
    }

    public AbstractDependencyRule(ResourceVertex sourceVertex,
                                                ResourceVertex targetVertex) {
        this.source = sourceVertex;
        this.target = targetVertex;
    }

    public ResourceVertex getSource() {
        return source;
    }

    public ResourceVertex getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return target.getName() +" <- "+source.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractDependencyRule other = (AbstractDependencyRule) obj;
        if(!this.getSource().equals(other.getSource())) {
            return false;
        }
        if(!this.getTarget().equals(other.getTarget())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.source != null ? this.source.hashCode() : 0);
        hash = 17 * hash + (this.target != null ? this.target.hashCode() : 0);
        return hash;
    }
}
