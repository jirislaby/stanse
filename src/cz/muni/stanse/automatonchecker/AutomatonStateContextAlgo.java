package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;

import java.util.Stack;

final class AutomatonStateContextAlgo {

    // package-private section

    static boolean
    canPush(final Stack<AutomatonStateContextItem> context, final CFGNode node,
                                                 final ComposedAutomatonID id) {
        final AutomatonStateContextItem newItem =
            new AutomatonStateContextItem(node,id);
        for (final AutomatonStateContextItem item : context)
            if (item.equals(newItem))
                return false;
        return true;
    }

    static Stack<AutomatonStateContextItem>
    push(final Stack<AutomatonStateContextItem> context, final CFGNode node,
                                                 final ComposedAutomatonID id) {
        final Stack<AutomatonStateContextItem> result = copy(context);
        result.push(new AutomatonStateContextItem(node,id));
        return result;
    }

    static Stack<AutomatonStateContextItem>
    pop(final Stack<AutomatonStateContextItem> context) {
        final Stack<AutomatonStateContextItem> result = copy(context);
        result.pop();
        return result;
    }

    static Stack<AutomatonStateContextItem>
    swop(final Stack<AutomatonStateContextItem> context, final CFGNode node,
                                                 final ComposedAutomatonID id) {
        final Stack<AutomatonStateContextItem> result = pop(context);
        result.push(new AutomatonStateContextItem(node,id));
        return result;
    }

    static Stack<AutomatonStateContextItem>
    copy(final Stack<AutomatonStateContextItem> context) {
        final Stack<AutomatonStateContextItem> copy =
            new Stack<AutomatonStateContextItem>();
        copy.addAll(context);
        return copy;
    }

    // private section

    private AutomatonStateContextAlgo() {
    }
}
