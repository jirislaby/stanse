package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.checker.CheckerErrorTrace;

import java.util.HashSet;
import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;

final class ErrorsTreeManager {

    // package-private section

    ErrorsTreeManager(final javax.swing.JTree errorsTree,
                      final javax.swing.JButton markBugButton,
                      final javax.swing.JButton markFalsePosButton,
                      final javax.swing.JButton unmarkReportButton) {
        this.errorsTree = errorsTree;
        bugs = new HashSet<CheckerError>();
        falses = new HashSet<CheckerError>();
        unchecked = new HashSet<CheckerError>();

        getErrorsTree().getSelectionModel().setSelectionMode(
                javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        getErrorsTree().addTreeSelectionListener(
        new javax.swing.event.TreeSelectionListener() {
            @Override public void valueChanged(
                                 final javax.swing.event.TreeSelectionEvent e) {
                onSelectionChanged();
            }
        });
        errorsTree.setCellRenderer(new javax.swing.tree.DefaultTreeCellRenderer() {
            @Override
            public java.awt.Component
            getTreeCellRendererComponent(javax.swing.JTree pTree,
                     Object pValue, boolean pIsSelected, boolean pIsExpanded,
                     boolean pIsLeaf, int pRow, boolean pHasFocus) {
                final java.awt.Color foreColour =
                        treeRowTextColour((DefaultMutableTreeNode)pValue);
                if (foreColour != null) {
                    setTextSelectionColor(foreColour);
                    setTextNonSelectionColor(foreColour);
                }
                final java.awt.Color bkgColour =
                        treeRowBkgColour((DefaultMutableTreeNode)pValue);
                if (bkgColour != null) {
                    setBackgroundSelectionColor(bkgColour);
                    setBackgroundNonSelectionColor(bkgColour);
                }
                super.getTreeCellRendererComponent(pTree, pValue, pIsSelected,
                                         pIsExpanded, pIsLeaf, pRow, pHasFocus);
                return this;
            }
        });
        markBugButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent e) {
                if (!JTreeAlgo.isSomethingSelected(getErrorsTree()))
                    return;
                markSelectedReportAsBug();
                JTreeAlgo.repaint(getErrorsTree());
            }
        });
        markFalsePosButton.addActionListener(
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(final java.awt.event.ActionEvent e){
                    if (!JTreeAlgo.isSomethingSelected(getErrorsTree()))
                        return;
                    markSelectedReportAsFalsePositive();
                    JTreeAlgo.repaint(getErrorsTree());
                }
            }
        );
        unmarkReportButton.addActionListener(
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(final java.awt.event.ActionEvent e){
                    if (!JTreeAlgo.isSomethingSelected(getErrorsTree()))
                        return;
                    unmarkSelectedReport();
                    JTreeAlgo.repaint(getErrorsTree());
                }
            }
        );
    }

    synchronized void clear() {
        JTreeAlgo.clear(getErrorsTree());
    }

    synchronized void present() {
        JTreeAlgo.present(getErrorsTree());
    }

    synchronized void addBugs(final Collection<CheckerError> bugs) {
        addAll(bugs);
        this.bugs.addAll(bugs);
    }

    synchronized void addFalsePositives(final Collection<CheckerError> falses) {
        addAll(falses);
        this.falses.addAll(falses);
    }

    synchronized void addUnchecked(final Collection<CheckerError> unchecked) {
        addAll(unchecked);
        this.unchecked.addAll(unchecked);
    }

    synchronized void addUnchecked(final CheckerError unchecked) {
        add(unchecked);
        this.unchecked.add(unchecked);
    }

    public HashSet<CheckerError> getBugs() {
        return bugs;
    }

    public HashSet<CheckerError> getFalsePositives() {
        return falses;
    }

    public HashSet<CheckerError> getUnchecked() {
        return unchecked;
    }

    // private section

    private void addAll(final Collection<CheckerError> collection) {
        for (final CheckerError error : collection)
            add(error);
    }

    private void add(final CheckerError error) {
        final DefaultMutableTreeNode errorNode =
                JTreeAlgo.add(getErrorsTree(),error);
        for (final CheckerErrorTrace trace : error.getTraces())
            add(errorNode,trace);
    }

    private void add(final DefaultMutableTreeNode parent,
                                      final CheckerErrorTrace trace) {
        final DefaultMutableTreeNode traceNode =
                JTreeAlgo.add(getErrorsTree(),parent,trace);
        for (final CheckerErrorTraceLocation location :
                   trace.getLocations())
            add(traceNode,location);
    }

    private void add(final DefaultMutableTreeNode parent,
                     final CheckerErrorTraceLocation location) {
        JTreeAlgo.add(getErrorsTree(),parent,location);
    }

    private Object getSelectedData() {
        return JTreeAlgo.getData(JTreeAlgo.getSelection(getErrorsTree()));
    }

    private void onSelectionChanged() {
        if (!JTreeAlgo.isSomethingSelected(getErrorsTree()))
            return;
        onSelectionChangedForErrorTracingManager(getSelectedData());
        final CheckerErrorTraceLocation location =
                            getErrorLocationOfSelectedObject(getSelectedData());
        getOpenedSourceFilesManager().showSourceFile(
                          new java.io.File(location.getUnitName()));
        getOpenedSourceFilesManager().selectLineInShowedSourceFile(
                          location.getLineNumber());
        getOpenedSourceFilesManager().gotoColumnInSelectedLine(
                                      location.getColumnNumber());
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

    private java.awt.Color
    treeRowTextColour(final DefaultMutableTreeNode node) {
        final CheckerError error = findCheckerErrorBy(node);
        assert(error != null);
        return isBug(error)         ? java.awt.Color.red    :
               isFalsePos(error)    ? java.awt.Color.green.darker()  :
                                      java.awt.Color.black  ;
    }

    private java.awt.Color
    treeRowBkgColour(final DefaultMutableTreeNode node) {
        return null; // Backgroung change is not needed at last...
    }

    private static CheckerError
    findCheckerErrorBy(DefaultMutableTreeNode node) {
        while (node != null) {
            Object obj = JTreeAlgo.getData(node);
            if (obj instanceof CheckerError)
                return (CheckerError)obj;
            assert(node.getParent() instanceof DefaultMutableTreeNode);
            node = (DefaultMutableTreeNode)node.getParent();
        }
        return null;
    }

    private CheckerError findCheckerErrorBySelectedNode() {
        assert(JTreeAlgo.isSomethingSelected(getErrorsTree()));
        return findCheckerErrorBy(JTreeAlgo.getSelection(getErrorsTree()));
    }

    private boolean isBug(final CheckerError error) {
        return getBugs().contains(error);
    }

    private boolean isFalsePos(final CheckerError error) {
        return getFalsePositives().contains(error);
    }

    private void markSelectedReportAsBug() {
        assert(JTreeAlgo.isSomethingSelected(getErrorsTree()));
        final CheckerError error = findCheckerErrorBySelectedNode();
        assert(error != null);
        falses.remove(error);
        unchecked.remove(error);
        bugs.add(error);
    }

    private void markSelectedReportAsFalsePositive() {
        assert(JTreeAlgo.isSomethingSelected(getErrorsTree()));
        final CheckerError error = findCheckerErrorBySelectedNode();
        assert(error != null);
        bugs.remove(error);
        unchecked.remove(error);
        falses.add(error);
    }

    private void unmarkSelectedReport() {
        assert(JTreeAlgo.isSomethingSelected(getErrorsTree()));
        final CheckerError error = findCheckerErrorBySelectedNode();
        assert(error != null);
        bugs.remove(error);
        falses.remove(error);
        unchecked.add(error);
    }

    private final javax.swing.JTree errorsTree;
    private final HashSet<CheckerError> bugs;
    private final HashSet<CheckerError> falses;
    private final HashSet<CheckerError> unchecked;
}
