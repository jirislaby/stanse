package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.ArgumentPassingManager;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.LinkedList;

final class AutomatonStateTransferManager {

    // package-private section

    AutomatonStateTransferManager(final ArgumentPassingManager passingManager,
                                  final CallSiteDetector callDetector) {
        this.passingManager = passingManager;
        this.callDetector = callDetector;
    }

    AutomatonState transfer(final CFGNode from,
                            final AutomatonState state,
                            final CFGNode to) {
        switch (getTransferType(from,to)) {
            case 1 : return transferFromCallToStartImpl(from,state,to);
            case 2 : return transferFromEndToCallImpl(from,state,to);
            default : return state;
        }
    }

    ComposedAutomatonID transfer(final CFGNode from,
                                 final ComposedAutomatonID id,
                                 final CFGNode to) {
        if (isIdentityTransfer(from,to)) return id;
        else return transferImpl(from,id,to);
    }

    SimpleAutomatonID transfer(final CFGNode from,
                               final SimpleAutomatonID id,
                               final CFGNode to) {
        if (isIdentityTransfer(from,to)) return id;
        else return transferImpl(from,id,to);
    }

    // private section

    private boolean isIdentityTransfer(final CFGNode from, final CFGNode to) {
        return getTransferType(from,to) == 0;
    }

    private int getTransferType(final CFGNode from, final CFGNode to) {
        if (getPassingManager().isIdentityPass(from,to))
            return 0;
        if (getCallDetector().isCallNode(from))
            return 1;
        if (getCallDetector().isCallNode(to))
            return 2;
        return 0;
    }

    private AutomatonState transferFromCallToStartImpl(final CFGNode from,
                                 final AutomatonState state, final CFGNode to) {
        final ComposedAutomatonID transformedID =
                    transferImpl(from,state.getAutomatonID(),to);
        if (transformedID == null ||
            !AutomatonStateContextAlgo
                .canPush(state.getContext(),from,transformedID))
            return null;
        return (transformedID == null) ? null :
                    new AutomatonState(state.getSymbol(),
                            AutomatonStateContextAlgo.push(state.getContext(),
                                                           from,transformedID));
    }

    private AutomatonState transferFromEndToCallImpl(final CFGNode from,
                                 final AutomatonState state, final CFGNode to) {
        if (!to.equals(state.getCFGNode()))
            return null;
        final java.util.Stack<AutomatonStateContextItem> context =
                AutomatonStateContextAlgo.pop(state.getContext());
        return (context.isEmpty()) ?
                       null : new AutomatonState(state.getSymbol(),context);
    }

    private ComposedAutomatonID transferImpl(final CFGNode from,
                                 final ComposedAutomatonID id,
                                 final CFGNode to) {
        final LinkedList<SimpleAutomatonID> transformedIDs =
            new LinkedList<SimpleAutomatonID>();
        for (final SimpleAutomatonID simpleID : id.getSimpleAutomataIDs()) {
            final SimpleAutomatonID transformedID =
                transferImpl(from,simpleID,to);
            if (transformedID == null)
                return null;
            transformedIDs.add(transformedID);
        }
        return new ComposedAutomatonID(transformedIDs);
    }

    private SimpleAutomatonID transferImpl(final CFGNode from,
                               final SimpleAutomatonID id,
                               final CFGNode to) {
        if (id.isGlobal())
            return id;
        final LinkedList<String> transformedVarsAssignments =
                new LinkedList<String>();
        for (final String varsAssignment : id.getVarsAssignment()) {
            final String transformedVarAssign =
                getPassingManager().pass(from,varsAssignment,to);
            if (transformedVarAssign == null)
                return null;
            transformedVarsAssignments.add(transformedVarAssign);
        }
        return new SimpleAutomatonID(transformedVarsAssignments,id.isGlobal());
    }

    private CallSiteDetector getCallDetector() {
        return callDetector;
    }

    private ArgumentPassingManager getPassingManager() {
        return passingManager;
    }

    private final ArgumentPassingManager passingManager;
    private final CallSiteDetector callDetector;
}
