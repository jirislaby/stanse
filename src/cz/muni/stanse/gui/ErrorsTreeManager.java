package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.checker.CheckerErrorTrace;
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

    synchronized void clear() {
        JTreeAlgo.clear(getErrorsTree());
    }

    synchronized void present() {
        JTreeAlgo.present(getErrorsTree());
    }

    synchronized void addAll(final Collection<CheckerError> collection) {
        for (final CheckerError error : collection)
            add(error);
    }

    synchronized void add(final CheckerError error) {
        final DefaultMutableTreeNode errorNode =
                JTreeAlgo.add(getErrorsTree(),error);
        for (final CheckerErrorTrace trace : error.getTraces())
            add(errorNode,trace);
//JTreeAlgo.present(getErrorsTree(),errorNode);
    }

    // private section

    private void add(final DefaultMutableTreeNode parent,
                                      final CheckerErrorTrace trace) {
        final DefaultMutableTreeNode traceNode =
                JTreeAlgo.add(getErrorsTree(),parent,trace);
        for (final CheckerErrorTraceLocation location :
                   trace.getLocations())
            add(traceNode,location);
//JTreeAlgo.present(getErrorsTree(),traceNode);
    }

    private void add(final DefaultMutableTreeNode parent,
                     final CheckerErrorTraceLocation location) {
//final DefaultMutableTreeNode locationNode =
        JTreeAlgo.add(getErrorsTree(),parent,location);
//JTreeAlgo.present(getErrorsTree(),locationNode);
    }

    private Object getSelectedData() {
        return JTreeAlgo.getData(JTreeAlgo.getSelection(getErrorsTree()));
    }

    private void onSelectionChenged() {
        if (!JTreeAlgo.isSomethingSelected(getErrorsTree()))
            return;
        onSelectionChangedForErrorTracingManager(getSelectedData());
        final CheckerErrorTraceLocation location =
                            getErrorLocationOfSelectedObject(getSelectedData());
        getOpenedSourceFilesManager().showSourceFile(
                          new java.io.File(location.getUnitName()));
        getOpenedSourceFilesManager().selectLineInShowedSourceFile(
                          location.getLineNumber());
        MainWindow.getInstance().getConsoleManager().clear();
        MainWindow.getInstance().getConsoleManager().appendText(
                                                     location.getDescription());
    }

    private void onSelectionChangedForErrorTracingManager(
                                                  final Object selectedObject) {
        MainWindow.getInstance().getErrorTracingManager().
            onSelectionChanged((selectedObject instanceof CheckerErrorTrace)
                                ? (CheckerErrorTrace)selectedObject : null);
    }

    private CheckerErrorTraceLocation getErrorLocationOfSelectedObject(
                                                  final Object selectedObject) {
        if (selectedObject instanceof CheckerErrorTraceLocation)
            return (CheckerErrorTraceLocation)selectedObject;
        if (selectedObject instanceof CheckerErrorTrace)
            return ((CheckerErrorTrace)selectedObject).getCauseLocation();
        return ((CheckerError)selectedObject).getErrorLocation();
    }

    private OpenedSourceFilesManager getOpenedSourceFilesManager() {
        return MainWindow.getInstance().getOpenedSourceFilesManager();
    }

    private javax.swing.JTree getErrorsTree() {
        return errorsTree;
    }

    private final javax.swing.JTree errorsTree;
}
