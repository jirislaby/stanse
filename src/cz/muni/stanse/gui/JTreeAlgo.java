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

    static void present(final JTree tree, final DefaultMutableTreeNode node) {
        getModel(tree).reload(node.getParent());
//        getModel(tree).nodeChanged(node.getParent());
//        getModel(tree).nodeStructureChanged(node);
//        int[] aa = {getModel(tree).getIndexOfChild(node.getParent(),node)};
//        Object oo = getModel(tree).getChild(node.getParent(),aa[0]);
//        getModel(tree).nodesWereInserted(node.getParent(),aa);
//        getModel(tree).fireTreeStructureChanged();
    }

    static <T> DefaultMutableTreeNode add(final JTree tree, final T data) {
        final DefaultMutableTreeNode newNode =
                      new DefaultMutableTreeNode(data);
        getRoot(tree).add(newNode);
//getModel(tree).insertNodeInto(newNode,getRoot(tree),getRoot(tree).getChildCount());
//getModel(tree).nodeChanged(newNode);
        return newNode;
    }

    static <T> DefaultMutableTreeNode add(final JTree tree,
                                          final DefaultMutableTreeNode parent,
                                          final T data) {
        final DefaultMutableTreeNode newNode =
                      new DefaultMutableTreeNode(data);
        parent.add(newNode);
//getModel(tree).insertNodeInto(newNode,parent,parent.getChildCount());
//getModel(tree).nodeChanged(newNode);
        return newNode;
    }

    static void remove(final JTree tree, final DefaultMutableTreeNode node) {
//final DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();
        getModel(tree).removeNodeFromParent(node);
//getModel(tree).nodeChanged(parentNode);
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
