package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.Stack;
import java.util.HashSet;

final class AutomatonStateCFGcontextAlgo {

    // package-private section

    static HashSet<Stack<CFGNode>>
    getContexts(final HashSet<AutomatonState> states) {
        final HashSet<Stack<CFGNode>> result = new HashSet<Stack<CFGNode>>();
        for (final AutomatonState state : filterUniqueStates(states,0,
                                                 new HashSet<AutomatonState>()))
            result.add(getContext(state));
        return result;
    }

    static Stack<CFGNode>
    getContext(final AutomatonState state) {
        return getContext(state.getContext());
    }

    static Stack<CFGNode>
    getContext(final Stack<AutomatonStateContextItem> context) {
        final Stack<CFGNode> result = new Stack<CFGNode>();
        for (final AutomatonStateContextItem item : context)
            if (item.getCFGNode() != null)
                result.push(item.getCFGNode());
        return result;
    }

    static final HashSet<AutomatonState>
    filterStatesByContext(final HashSet<AutomatonState> states,
                  final Stack<CFGNode> context) {
        final HashSet<AutomatonState> result =
            new HashSet<AutomatonState>();
        for (final AutomatonState state : states)
            if (firstIsSubcontextOfSecond(getContext(state),context))
                result.add(state);
        return result;
    }

    // private section

    private static boolean
    firstIsSubcontextOfSecond(final Stack<CFGNode> contextFirst,
                              final Stack<CFGNode> contextSecond) {
        return (contextFirst.size() <= contextSecond.size()) &&
                contextFirst.subList(0,contextFirst.size()).equals(
                    contextSecond.subList(0,contextFirst.size()));
    }

    private static HashSet<AutomatonState>
    filterUniqueStates(final HashSet<AutomatonState> states,
                       final int idx, final HashSet<AutomatonState> result) {
        if (states.size() <= 1) {
            result.addAll(states);
            return result;
        }
        java.util.HashMap<AutomatonStateContextItem,HashSet<AutomatonState>>
            spliter = new java.util.HashMap<AutomatonStateContextItem,
                                            HashSet<AutomatonState>>();
        for (final AutomatonState state : states)
            if (state.getContext().size() > idx) {
                final AutomatonStateContextItem item =
                    state.getContext().get(idx);
                if (!spliter.containsKey(item))
                    spliter.put(item,new HashSet<AutomatonState>());
                spliter.get(item).add(state);
            }
        if (spliter.isEmpty()) {
            result.addAll(states);
            return result;
        }
        for (final HashSet<AutomatonState> splitClass : spliter.values())
            filterUniqueStates(splitClass,idx+1,result);
        return result;
    }

    private AutomatonStateCFGcontextAlgo() {
    }
}
