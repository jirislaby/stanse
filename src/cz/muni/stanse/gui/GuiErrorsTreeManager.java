package cz.muni.stanse.gui;

import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;

final class GuiErrorsTreeManager {

    // package-private section

    GuiErrorsTreeManager(final javax.swing.JTree errorsTree) {
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
        GuiJTreeAlgo.clear(getErrorsTree());
    }

    void present() {
        GuiJTreeAlgo.present(getErrorsTree());
    }

    void addAll(final Collection<PresentableError> collection) {
        for (final PresentableError error : collection)
            add(error);
    }

    // private section

    private void add(final PresentableError error) {
        final DefaultMutableTreeNode errorNode =
                GuiJTreeAlgo.add(getErrorsTree(),error);
        for (final PresentableErrorTrace trace : error.getTraces())
            add(errorNode,trace);
    }

    private void add(final DefaultMutableTreeNode parent,
                                      final PresentableErrorTrace trace) {
        final DefaultMutableTreeNode traceNode =
                GuiJTreeAlgo.add(getErrorsTree(),parent,trace);
        for (final PresentableErrorTraceLocation location :
                   trace.getLocations())
            add(traceNode,location);
    }

    private void add(final DefaultMutableTreeNode parent,
                     final PresentableErrorTraceLocation location) {
        GuiJTreeAlgo.add(getErrorsTree(),parent,location);
    }

    private Object getSelectedData() {
        return GuiJTreeAlgo.getData(GuiJTreeAlgo.getSelection(getErrorsTree()));
    }

    private void onSelectionChenged() {
        if (!GuiJTreeAlgo.isSomethingSelected(getErrorsTree()))
            return;
        if (onSelectionChangedForErrorTracingManager(getSelectedData()))
            return;
        final PresentableErrorTraceLocation location =
                            getErrorLocationOfSelectedObject(getSelectedData());
        getOpenedSourceFilesManager().showSourceFile(
                          new java.io.File(location.getUnitName()));
        getOpenedSourceFilesManager().selectLineInShowedSourceFile(
                          location.getLineNumber());
        GuiMainWindow.getInstance().getConsoleManager().clear();
        GuiMainWindow.getInstance().getConsoleManager().appendText(
                                                     location.getDescription());
    }

    private boolean onSelectionChangedForErrorTracingManager(
                                                  final Object selectedObject) {
        GuiMainWindow.getInstance().getErrorTracingManager().
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

    private GuiOpenedSourceFilesManager getOpenedSourceFilesManager() {
        return GuiMainWindow.getInstance().getOpenedSourceFilesManager();
    }

    private javax.swing.JTree getErrorsTree() {
        return errorsTree;
    }

    private final javax.swing.JTree errorsTree;
}
