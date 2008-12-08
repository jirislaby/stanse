package cz.muni.stanse.checker;

import cz.muni.stanse.parser.ControlFlowGraph;

import java.util.Set;
import java.util.List;

public abstract class Checker {

    // public section

    public Checker() {
    }

    public abstract String getName();

    public abstract List<CheckerError> check(final Set<ControlFlowGraph> CFGs)
			throws Exception;

    @Override
    public String toString() {
        return "Checker: " + getName();
    }
}
