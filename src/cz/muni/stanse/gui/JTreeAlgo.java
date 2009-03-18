package cz.muni.stanse.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

final class JTreeAlgo {

    static void clear(final JTree tree) {
        getRoot(tree).removeAllChildren();
    }

    static void present(final JTree tree) {
        getModel(tree).reload();
    }

    static <T> DefaultMutableTreeNode add(final JTree tree, final T data) {
        final DefaultMutableTreeNode newNode =
                      new DefaultMutableTreeNode(data);
        getModel(tree).insertNodeInto(newNode,getRoot(tree),
                                      getRoot(tree).getChildCount());
        return newNode;
    }

    static <T> DefaultMutableTreeNode add(final JTree tree,
                                          final DefaultMutableTreeNode parent,
                                          final T data) {
        final DefaultMutableTreeNode newNode =
                      new DefaultMutableTreeNode(data);
        getModel(tree).insertNodeInto(newNode,parent,parent.getChildCount());
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
