package cz.muni.stanse.gui;

import cz.muni.stanse.CheckerConfiguration;
import cz.muni.stanse.utils.ClassLogger;

import java.util.LinkedList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

final class CheckersConfurationManager {

    // package-private section

    CheckersConfurationManager(final javax.swing.JTree checkersTree,
                                  final javax.swing.JButton addCheckerButton,
                                  final javax.swing.JButton removeCheckerButton,
                                  final javax.swing.JButton addDataButton,
                                  final javax.swing.JButton removeDataButton) {
        this.checkersTree = checkersTree;
        this.addCheckerButton = addCheckerButton;
        this.removeCheckerButton = removeCheckerButton;
        this.addDataButton = addDataButton;
        this.removeDataButton = removeDataButton;

        fillTreeByCheckersConfiguration(MainWindow.getInstance().
                                 getConfiguration().getCheckerConfigurations());
        JTreeAlgo.present(getCheckersTree());

        getAddCheckerButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onAddChecker(); expandTree();
            }
        });
        getRemoveCheckerButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onRemoveChecker(); expandTree();
            }
        });
        getAddDataButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onAddData(); expandTree();
            }
        });
        getRemoveDataButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onRemoveData(); expandTree();
            }
        });

        expandTree();
    }

    LinkedList<CheckerConfiguration> getCheckersConfiguration() {
        final LinkedList<CheckerConfiguration> result =
                 new LinkedList<CheckerConfiguration>();
        for (Enumeration treeNodesEnumetration = JTreeAlgo.getRoot(
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
            final DefaultMutableTreeNode checkerTreeNode = JTreeAlgo.add(
                         getCheckersTree(),buildCheckerTreeID(configuration));
            checkerTreeNode.setAllowsChildren(true);
            for (java.io.File arg : configuration.getCheckerArgumentsList())
                JTreeAlgo.add(getCheckersTree(),checkerTreeNode,
                                 arg.toString()).setAllowsChildren(false);
        }
    }

    private CheckerConfiguration
    createCheckerConfiguration(final DefaultMutableTreeNode checkerTreeNode) {
        final LinkedList<java.io.File> arguments =
                 new LinkedList<java.io.File>();
        for (Enumeration treeNodesEnumetration = checkerTreeNode.children();
                treeNodesEnumetration.hasMoreElements(); )
            arguments.add(new java.io.File((String)JTreeAlgo.getData(
                (DefaultMutableTreeNode)treeNodesEnumetration.nextElement())));
        final String treeID = (String)JTreeAlgo.getData(checkerTreeNode);
        return new CheckerConfiguration(parseCheckerNameFromTreeID(treeID),
                                        arguments,
                                        parseInterproceduralFromTreeID(treeID));
    }

    private void onAddChecker() {
        final ChooseCheckerDialog chooseCheckerDialog =
                                                   new ChooseCheckerDialog();
        chooseCheckerDialog.pack();
        chooseCheckerDialog.setVisible(true);

        final String checkerName = chooseCheckerDialog.getChooseCheckerManager()
                                                              .getCheckerName();
        if (checkerName != null) {
            final boolean interpocedural = chooseCheckerDialog.
                    getChooseCheckerManager().isInterprocedural();
            JTreeAlgo.add(getCheckersTree(),buildCheckerTreeID(checkerName,
                                                               interpocedural))
                     .setAllowsChildren(true);
            JTreeAlgo.present(getCheckersTree());
        }
    }

    private void onRemoveChecker() {
        final DefaultMutableTreeNode node = getSelectedCheckerTreeNode();
        if (node != null)
            JTreeAlgo.remove(getCheckersTree(),node);
    }

    private void onAddData() {
        final DefaultMutableTreeNode node = getSelectedCheckerTreeNode();
        if (node == null)
            return;
        String data;
        try {
            data = chooseDataFileOnDisc((String)JTreeAlgo.getData(node));
        }
        catch(final Exception exception) {
            ClassLogger.error(this,exception);
            return;
        }
        if (data != null)
            JTreeAlgo.add(getCheckersTree(),node,data)
                        .setAllowsChildren(false);
    }

    private void onRemoveData() {
        final DefaultMutableTreeNode node = getSelectedDataTreeNode();
        if (node != null)
            JTreeAlgo.remove(getCheckersTree(),node);
    }

    private DefaultMutableTreeNode getSelectedCheckerTreeNode() {
        if (!JTreeAlgo.isSomethingSelected(getCheckersTree()))
            return null;
        DefaultMutableTreeNode selectedNode =
                                   JTreeAlgo.getSelection(getCheckersTree());
        for ( ; !selectedNode.getAllowsChildren(); )
            selectedNode = (DefaultMutableTreeNode)selectedNode.getParent();
        return selectedNode;
    }

    private DefaultMutableTreeNode getSelectedDataTreeNode() {
        if (!JTreeAlgo.isSomethingSelected(getCheckersTree()))
            return null;
        DefaultMutableTreeNode selectedNode =
                                   JTreeAlgo.getSelection(getCheckersTree());
        return (selectedNode.getAllowsChildren()) ? null : selectedNode;
    }

    private String chooseDataFileOnDisc(String checkerName) throws Exception {
        checkerName = parseCheckerNameFromTreeID(checkerName);
        final javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileChooserFileFilter(
                cz.muni.stanse.utils.FileAlgo.getExtension(checkerName) +
                    " data file types",
                cz.muni.stanse.checker.CheckerFactory.getDataFilesExtensions(
                    checkerName),
                null));
        return (chooser.showDialog(getAddDataButton(),"Add") !=
                    javax.swing.JFileChooser.APPROVE_OPTION) ? null :
                                           chooser.getSelectedFile().toString();
    }

    private void expandTree() {
        for (int i = 0; i < getCheckersTree().getRowCount(); i++)
             getCheckersTree().expandRow(i);
    }

    private static String
    buildCheckerTreeID(final CheckerConfiguration configuration) {
        return buildCheckerTreeID(configuration.getCheckerClassName(),
                                  configuration.isInterprocedural());
    }

    private static String
    buildCheckerTreeID(final String checkerName, final boolean interprocedural){
        return checkerName + ((interprocedural) ? "" : " --intraprocedural");
    }

    private static String
    parseCheckerNameFromTreeID(final String treeID) {
        final int spaceIdx = treeID.indexOf(' ');
        return (spaceIdx == -1) ? treeID : treeID.substring(0,spaceIdx);
    }

    private static boolean
    parseInterproceduralFromTreeID(final String treeID) {
        return !treeID.contains("--intraprocedural");
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
