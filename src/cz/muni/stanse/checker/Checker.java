package cz.muni.stanse.checker;

import cz.muni.stanse.xml2cfg.ControlFlowGraph;
import java.util.Set;
import java.util.List;

public abstract class Checker {

    public Checker(Set<ControlFlowGraph> CFGs)
    {
        this.CFGs = CFGs;
    }

    public abstract String getName();

    public abstract List<CheckerError> check() throws Exception;

    protected final Set<ControlFlowGraph> getCFGs() {
        return CFGs;
    }

    private final Set<ControlFlowGraph> CFGs;

}

// TODO: Interface does not handle control-flow analysis - i.e. high order
//       functions. All CFGs (of all the functions) must be know before
//       the checking is executed.

// TODO: No framework is provided to checkers. Each checker must implement
//       the same functionality, like parsing through CFGs, itself.
//       It is not good idea to put such functionality to CFG class. Just
//       provide separate framework class and pass it to the checkers.

// TODO: No methods for CFGs' set manipulating is provided. This strategy
//       forbids implementation of checkers based on iterative additions
//       CFGs to it. It also forbits incremental checking (so, if small change
//       in one CFGs is done, then it means to create new checker and start
//       completely new checking on whole set of CFGs.

// TODO: Errors could be sorted by theirs error-level. But I cannot find
//       Java equivalent for C++ std::multimap<ErrorLevel,CheckerError>
//       template.
