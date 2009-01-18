package cz.muni.stanse.gui;

import cz.muni.stanse.utils.ClassLogger;

import java.util.LinkedList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

final class GuiCheckersConfurationManager {

    // package-private section

    GuiCheckersConfurationManager(final javax.swing.JTree checkersTree,
                                  final javax.swing.JButton addCheckerButton,
                                  final javax.swing.JButton removeCheckerButton,
                                  final javax.swing.JButton addDataButton,
                                  final javax.swing.JButton removeDataButton) {
        this.checkersTree = checkersTree;
        this.addCheckerButton = addCheckerButton;
        this.removeCheckerButton = removeCheckerButton;
        this.addDataButton = addDataButton;
        this.removeDataButton = removeDataButton;

        fillTreeByCheckersConfiguration(GuiMainWindow.getInstance().
                                 getConfiguration().getCheckerConfigurations());
        GuiJTreeAlgo.present(getCheckersTree());

        getAddCheckerButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onAddChecker();
            }
        });
        getRemoveCheckerButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onRemoveChecker();
            }
        });
        getAddDataButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onAddData();
            }
        });
        getRemoveDataButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onRemoveData();
            }
        });
    }

    LinkedList<CheckerConfiguration> getCheckersConfiguration() {
        final LinkedList<CheckerConfiguration> result =
                 new LinkedList<CheckerConfiguration>();
        for (Enumeration treeNodesEnumetration = GuiJTreeAlgo.getRoot(
                                                  getCheckersTree()).children();
                treeNodesEnumetration.hasMoreElements(); )
            result.add(createCheckerConfiguration((DefaultMutableTreeNode)
                                          treeNodesEnumetration.nextElement()));
        return result;
    }

    // private section

    private void fillTreeByCheckersConfiguration(
                 final LinkedList<CheckerConfiguration> checkersConfiguration) {
        for (CheckerConfiguration configuration : checkersConfiguration) {
            final DefaultMutableTreeNode checkerTreeNode = GuiJTreeAlgo.add(
                         getCheckersTree(),configuration.getCheckerClassName());
            checkerTreeNode.setAllowsChildren(true);
            for (java.io.File arg : configuration.getCheckerArgumentsList())
                GuiJTreeAlgo.add(getCheckersTree(),checkerTreeNode,
                                 arg.toString()).setAllowsChildren(false);
        }
    }

    private CheckerConfiguration
    createCheckerConfiguration(final DefaultMutableTreeNode checkerTreeNode) {
        final LinkedList<java.io.File> arguments =
                 new LinkedList<java.io.File>();
        for (Enumeration treeNodesEnumetration = checkerTreeNode.children();
                treeNodesEnumetration.hasMoreElements(); )
            arguments.add(new java.io.File((String)GuiJTreeAlgo.getData(
                (DefaultMutableTreeNode)treeNodesEnumetration.nextElement())));
        return new CheckerConfiguration((String)GuiJTreeAlgo.
                                            getData(checkerTreeNode),arguments);
    }

    private void onAddChecker() {
        final GuiChooseCheckerDialog chooseCheckerDialog =
                                                   new GuiChooseCheckerDialog();
        chooseCheckerDialog.pack();
        chooseCheckerDialog.setVisible(true);

        final String checkerName = chooseCheckerDialog.getChooseCheckerManager()
                                                              .getCheckerName();
        if (checkerName != null) {
            GuiJTreeAlgo.add(getCheckersTree(),checkerName)
                        .setAllowsChildren(true);
            GuiJTreeAlgo.present(getCheckersTree());
        }
    }

    private void onRemoveChecker() {
        final DefaultMutableTreeNode node = getSelectedCheckerTreeNode();
        if (node != null)
            GuiJTreeAlgo.remove(getCheckersTree(),node);
    }

    private void onAddData() {
        final DefaultMutableTreeNode node = getSelectedCheckerTreeNode();
        if (node == null)
            return;
        String data;
        try {
            data = chooseDataFileOnDisc((String)GuiJTreeAlgo.getData(node));
        }
        catch(final Exception exception) {
            ClassLogger.error(this,exception);
            return;
        }
        if (data != null)
            GuiJTreeAlgo.add(getCheckersTree(),node,data)
                        .setAllowsChildren(false);
    }

    private void onRemoveData() {
        final DefaultMutableTreeNode node = getSelectedDataTreeNode();
        if (node != null)
            GuiJTreeAlgo.remove(getCheckersTree(),node);
    }

    private DefaultMutableTreeNode getSelectedCheckerTreeNode() {
        if (!GuiJTreeAlgo.isSomethingSelected(getCheckersTree()))
            return null;
        DefaultMutableTreeNode selectedNode =
                                   GuiJTreeAlgo.getSelection(getCheckersTree());
        for ( ; !selectedNode.getAllowsChildren(); )
            selectedNode = (DefaultMutableTreeNode)selectedNode.getParent();
        return selectedNode;
    }

    private DefaultMutableTreeNode getSelectedDataTreeNode() {
        if (!GuiJTreeAlgo.isSomethingSelected(getCheckersTree()))
            return null;
        DefaultMutableTreeNode selectedNode =
                                   GuiJTreeAlgo.getSelection(getCheckersTree());
        return (selectedNode.getAllowsChildren()) ? null : selectedNode;
    }

    private String chooseDataFileOnDisc(final String checkerName)
                                                              throws Exception {
        final javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new GuiFileChooserFileFilter(
                cz.muni.stanse.utils.FileAlgo.getExtension(checkerName) +
                    " data file types",
                cz.muni.stanse.checker.CheckerFactory.getDataFilesExtensions(
                    checkerName),
                null));
        return (chooser.showDialog(getAddDataButton(),"Add") !=
                    javax.swing.JFileChooser.APPROVE_OPTION) ? null :
                                           chooser.getSelectedFile().toString();
    }

    private javax.swing.JTree getCheckersTree() {
        return checkersTree;
    }

    private javax.swing.JButton getAddCheckerButton() {
        return addCheckerButton;
    }

    private javax.swing.JButton getRemoveCheckerButton() {
        return removeCheckerButton;
    }

    private javax.swing.JButton getAddDataButton() {
        return addDataButton;
    }

    private javax.swing.JButton getRemoveDataButton() {
        return removeDataButton;
    }

    private final javax.swing.JTree checkersTree;
    private final javax.swing.JButton addCheckerButton;
    private final javax.swing.JButton removeCheckerButton;
    private final javax.swing.JButton addDataButton;
    private final javax.swing.JButton removeDataButton;
}
