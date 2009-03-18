package cz.muni.stanse.gui;

import cz.muni.stanse.PresentableError;
import cz.muni.stanse.PresentableErrorTraceLocation;
import cz.muni.stanse.PresentableErrorTrace;
import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;

final class ErrorsTreeManager {

    // package-private section

    ErrorsTreeManager(final javax.swing.JTree errorsTree) {
        this.errorsTree = errorsTree;
        getErrorsTree().getSelectionModel().setSelectionMode(
                javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        getErrorsTree().addTreeSelectionListener(
        new javax.swing.event.TreeSelectionListener() {
            @Override public void valueChanged(
                                 final javax.swing.event.TreeSelectionEvent e) {
                onSelectionChenged();
            }
        });
    }

    void clear() {
        JTreeAlgo.clear(getErrorsTree());
    }

    void present() {
        JTreeAlgo.present(getErrorsTree());
    }

    void addAll(final Collection<PresentableError> collection) {
        for (final PresentableError error : collection)
            add(error);
    }

    // private section

    private void add(final PresentableError error) {
        final DefaultMutableTreeNode errorNode =
                JTreeAlgo.add(getErrorsTree(),error);
        for (final PresentableErrorTrace trace : error.getTraces())
            add(errorNode,trace);
    }

    private void add(final DefaultMutableTreeNode parent,
                                      final PresentableErrorTrace trace) {
        final DefaultMutableTreeNode traceNode =
                JTreeAlgo.add(getErrorsTree(),parent,trace);
        for (final PresentableErrorTraceLocation location :
                   trace.getLocations())
            add(traceNode,location);
    }

    private void add(final DefaultMutableTreeNode parent,
                     final PresentableErrorTraceLocation location) {
        JTreeAlgo.add(getErrorsTree(),parent,location);
    }

    private Object getSelectedData() {
        return JTreeAlgo.getData(JTreeAlgo.getSelection(getErrorsTree()));
    }

    private void onSelectionChenged() {
        if (!JTreeAlgo.isSomethingSelected(getErrorsTree()))
            return;
        if (onSelectionChangedForErrorTracingManager(getSelectedData()))
            return;
        final PresentableErrorTraceLocation location =
                            getErrorLocationOfSelectedObject(getSelectedData());
        getOpenedSourceFilesManager().showSourceFile(
                          new java.io.File(location.getUnitName()));
        getOpenedSourceFilesManager().selectLineInShowedSourceFile(
                          location.getLineNumber());
        MainWindow.getInstance().getConsoleManager().clear();
        MainWindow.getInstance().getConsoleManager().appendText(
                                                     location.getDescription());
    }

    private boolean onSelectionChangedForErrorTracingManager(
                                                  final Object selectedObject) {
        MainWindow.getInstance().getErrorTracingManager().
            onSelectionChanged((selectedObject instanceof PresentableErrorTrace)
                                ? (PresentableErrorTrace)selectedObject : null);
        return selectedObject instanceof PresentableErrorTrace;
    }

    private PresentableErrorTraceLocation getErrorLocationOfSelectedObject(
                                                  final Object selectedObject) {
        if (selectedObject instanceof PresentableErrorTraceLocation)
            return (PresentableErrorTraceLocation)selectedObject;
        if (selectedObject instanceof PresentableErrorTrace)
            return ((PresentableErrorTrace)selectedObject).getCauseLocation();
        return ((PresentableError)selectedObject).getErrorLocation();
    }

    private OpenedSourceFilesManager getOpenedSourceFilesManager() {
        return MainWindow.getInstance().getOpenedSourceFilesManager();
    }

    private javax.swing.JTree getErrorsTree() {
        return errorsTree;
    }

    private final javax.swing.JTree errorsTree;
}
