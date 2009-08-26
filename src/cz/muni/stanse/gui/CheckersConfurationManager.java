package cz.muni.stanse.gui;

import cz.muni.stanse.configuration.CheckerConfiguration;
import cz.muni.stanse.checker.CheckerFactory;
import cz.muni.stanse.utils.ClassLogger;
import cz.muni.stanse.utils.FileAlgo;

import java.io.File;

import java.util.LinkedList;
import java.util.List;
import java.util.Enumeration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

final class CheckersConfurationManager {

    // package-private section

    CheckersConfurationManager(final JTree checkersTree,
                                  final JButton addCheckerButton,
                                  final JButton removeCheckerButton,
                                  final JButton addDataButton,
                                  final JButton removeDataButton) {
        this.checkersTree = checkersTree;
        this.addCheckerButton = addCheckerButton;
        this.removeCheckerButton = removeCheckerButton;
        this.addDataButton = addDataButton;
        this.removeDataButton = removeDataButton;

        fillTreeByCheckersConfiguration(MainWindow.getInstance()
                                                  .getConfiguration()
                                                  .getCheckerConfigurations());
        JTreeAlgo.present(getCheckersTree());

        getAddCheckerButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent e) {
                onAddChecker(); expandTree();
            }
        });
        getRemoveCheckerButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent e) {
                onRemoveChecker(); expandTree();
            }
        });
        getAddDataButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent e) {
                onAddData(); expandTree();
            }
        });
        getRemoveDataButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent e) {
                onRemoveData(); expandTree();
            }
        });

        expandTree();
    }

    List<CheckerConfiguration> getCheckersConfiguration() {
	final List<CheckerConfiguration> result =
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
                 final List<CheckerConfiguration> checkersConfiguration) {
        for (CheckerConfiguration configuration : checkersConfiguration) {
            final DefaultMutableTreeNode checkerTreeNode = JTreeAlgo.add(
                         getCheckersTree(), buildCheckerTreeID(configuration));
            checkerTreeNode.setAllowsChildren(true);
            for (File arg : configuration.getCheckerArgumentsList())
                JTreeAlgo.add(getCheckersTree(), checkerTreeNode,
                                 arg.toString()).setAllowsChildren(false);
        }
    }

    private CheckerConfiguration
    createCheckerConfiguration(final DefaultMutableTreeNode checkerTreeNode) {
        final List<File> arguments = new LinkedList<File>();
        for (Enumeration treeNodesEnumetration = checkerTreeNode.children();
                treeNodesEnumetration.hasMoreElements(); )
            arguments.add(new File((String)JTreeAlgo.getData(
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
        } catch (final UnsupportedOperationException e) {
	    ClassLogger.error(this, e);
            return;
        }
        if (data != null)
	    JTreeAlgo.add(getCheckersTree(),node,data).setAllowsChildren(false);
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

    private String chooseDataFileOnDisc(String checkerName) {
        checkerName = parseCheckerNameFromTreeID(checkerName);
	final JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new File("."));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileChooserFileFilter(
		FileAlgo.getExtension(checkerName) + " data file types",
		CheckerFactory.getDataFilesExtensions(checkerName), null));
        return (chooser.showDialog(getAddDataButton(), "Add") !=
			JFileChooser.APPROVE_OPTION) ? null :
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

    private JTree getCheckersTree() {
        return checkersTree;
    }

    private JButton getAddCheckerButton() {
        return addCheckerButton;
    }

    private JButton getRemoveCheckerButton() {
        return removeCheckerButton;
    }

    private JButton getAddDataButton() {
        return addDataButton;
    }

    private JButton getRemoveDataButton() {
        return removeDataButton;
    }

    private final JTree checkersTree;
    private final JButton addCheckerButton;
    private final JButton removeCheckerButton;
    private final JButton addDataButton;
    private final JButton removeDataButton;
}
