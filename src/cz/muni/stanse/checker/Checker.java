package cz.muni.stanse.checker;

import cz.muni.stanse.xml2cfg.ControlFlowGraph;
import java.util.Set;
import java.util.List;

public abstract class Checker {

    // public section

    public Checker(Set<ControlFlowGraph> CFGs) {
        this.CFGs = CFGs;
    }

    public abstract String getName();

    public abstract List<CheckerError> check() throws Exception;

    @Override
    public String toString() {
        return "Checker: " + getName();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((CFGs == null) ? 0 : CFGs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((Checker)obj);
    }

    public boolean isEqualWith(final Checker other) {
        return getCFGs().equals(other.getCFGs());
    }

    // protected section

    protected final Set<ControlFlowGraph> getCFGs() {
        return CFGs;
    }

    // private section

    private final Set<ControlFlowGraph> CFGs;
}
