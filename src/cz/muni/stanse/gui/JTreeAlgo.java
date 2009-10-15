package cz.muni.stanse.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

final class JTreeAlgo {

    static void clear(final JTree tree) {
        getRoot(tree).removeAllChildren();
    }

    static void present(final JTree tree) {
        getModel(tree).reload();
    }

    static void repaint(final JTree tree) {
        tree.repaint();
    }

    static void present(final JTree tree, final DefaultMutableTreeNode node) {
        getModel(tree).reload(node.getParent());
    }

    static <T> DefaultMutableTreeNode add(final JTree tree, final T data) {
        final DefaultMutableTreeNode newNode =
                      new DefaultMutableTreeNode(data);
        getRoot(tree).add(newNode);
        return newNode;
    }

    static <T> DefaultMutableTreeNode add(final JTree tree,
                                          final DefaultMutableTreeNode parent,
                                          final T data) {
        final DefaultMutableTreeNode newNode =
                      new DefaultMutableTreeNode(data);
        parent.add(newNode);
        return newNode;
    }

    static void remove(final JTree tree, final DefaultMutableTreeNode node) {
        getModel(tree).removeNodeFromParent(node);
    }

    static DefaultMutableTreeNode getSelection(final JTree tree) {
        return (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
    }

    static boolean isSomethingSelected(final JTree tree) {
        return getSelection(tree) != null;
    }

    static void selectNode(final JTree tree, final DefaultMutableTreeNode node){
        tree.setSelectionPath(new TreePath(node.getPath()));
        tree.scrollPathToVisible(new TreePath(node.getPath()));
    }

    static Object getData(final DefaultMutableTreeNode node) {
        return node.getUserObject();
    }

    static DefaultMutableTreeNode getRoot(final JTree tree) {
        return (DefaultMutableTreeNode)getModel(tree).getRoot();
    }

    // private section

    private static DefaultTreeModel getModel(final JTree tree) {
        return (DefaultTreeModel)tree.getModel();
    }

    private JTreeAlgo() {
    }
}
