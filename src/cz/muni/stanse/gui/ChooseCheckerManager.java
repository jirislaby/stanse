package cz.muni.stanse.gui;

import cz.muni.stanse.utils.ClassLogger;

import java.util.Set;

final class ChooseCheckerManager {

    // package-private section

    ChooseCheckerManager(final ChooseCheckerDialog dialog,
                         final javax.swing.JList checkerNamesList,
                         final javax.swing.JTextArea checkerInfoTextArea,
                         final javax.swing.JButton chooseCheckerButton,
                         final javax.swing.JButton cancelButton,
                         final javax.swing.JCheckBox interproceduralCheckBox) {
        this.checkerName = null;
        this.lastSelection = -1;
        this.interprocedural = interproceduralCheckBox.isSelected();

        this.checkerNamesList = checkerNamesList;
        this.checkerInfoTextArea = checkerInfoTextArea;
        this.chooseCheckerButton = chooseCheckerButton;
        this.cancelButton = cancelButton;
        this.interproceduralCheckBox = interproceduralCheckBox;

        fillCheckerNamesList(cz.muni.stanse.checker.CheckerFactory.
                             getRegisteredCheckers());

        getCheckerNamesList().addListSelectionListener(
        new javax.swing.event.ListSelectionListener() {
            @Override public void valueChanged(
                                 final javax.swing.event.ListSelectionEvent e) {
                onCheckersSelectionChanged();
            }
        });
        getChooseCheckerButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                checkerName = readSelectedCheckerName();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        getCancelButton().addActionListener(new java.awt.event.ActionListener(){
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        getInterproceduralCheckBox().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                interprocedural = !interprocedural;
            }
        });
    }

    String getCheckerName() {
        return checkerName;
    }

    boolean isInterprocedural() {
        return interprocedural;
    }

    // private section

    private String readSelectedCheckerName() {
        return (String)getCheckerNamesList().getSelectedValue();
    }

    private void fillCheckerNamesList(final Set<String> namesSet) {
        final javax.swing.DefaultListModel guiListModel =
                 (javax.swing.DefaultListModel)getCheckerNamesList().getModel();
        for (String name : namesSet)
            guiListModel.addElement(name);
    }

    private void onCheckersSelectionChanged() {
        final int newSelection = getCheckerNamesList().getSelectedIndex();
        if (newSelection == getLastSelection())
            return;
        setLastSelection(newSelection);
        String checkerInfo;
        try {
            checkerInfo = cz.muni.stanse.checker.CheckerFactory.
                              getCheckerCreationInfo(readSelectedCheckerName());
        }
        catch (final Exception exception) {
            ClassLogger.error(this,"Cannot retrieve creation info for checker" +
                              ": " + readSelectedCheckerName());
            ClassLogger.error(this,exception);
            ClassLogger.error(this,exception.getStackTrace());
            return;
        }
        getCheckerInfoTextArea().setText(checkerInfo);
        getCheckerInfoTextArea().setCaretPosition(0);
    }

    private int getLastSelection() {
        return lastSelection;
    }

    private void setLastSelection(final int value) {
        lastSelection = value;
    }

    private javax.swing.JList getCheckerNamesList() {
        return checkerNamesList;
    }

    private javax.swing.JTextArea getCheckerInfoTextArea() {
        return checkerInfoTextArea;
    }

    private javax.swing.JButton getChooseCheckerButton() {
        return chooseCheckerButton;
    }

    private javax.swing.JButton getCancelButton() {
        return cancelButton;
    }

    private javax.swing.JCheckBox getInterproceduralCheckBox() {
        return interproceduralCheckBox;
    }

    private String checkerName;
    private int lastSelection;
    private boolean interprocedural;
    private final javax.swing.JList checkerNamesList;
    private final javax.swing.JTextArea checkerInfoTextArea;
    private final javax.swing.JButton chooseCheckerButton;
    private final javax.swing.JButton cancelButton;
    private final javax.swing.JCheckBox interproceduralCheckBox;
}
