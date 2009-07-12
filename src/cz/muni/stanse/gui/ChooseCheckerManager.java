package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerFactory;
import cz.muni.stanse.utils.ClassLogger;

import java.util.Set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

final class ChooseCheckerManager {

    // package-private section

    ChooseCheckerManager(final ChooseCheckerDialog dialog,
                         final JList checkerNamesList,
                         final JTextArea checkerInfoTextArea,
                         final JButton chooseCheckerButton,
                         final JButton cancelButton,
                         final JCheckBox interproceduralCheckBox) {
        this.checkerName = null;
        this.lastSelection = -1;
        this.interprocedural = interproceduralCheckBox.isSelected();

        this.checkerNamesList = checkerNamesList;
        this.checkerInfoTextArea = checkerInfoTextArea;
        this.chooseCheckerButton = chooseCheckerButton;
        this.cancelButton = cancelButton;
        this.interproceduralCheckBox = interproceduralCheckBox;

        fillCheckerNamesList(CheckerFactory.getRegisteredCheckers());

        getCheckerNamesList().addListSelectionListener(
			new ListSelectionListener() {
            @Override public void valueChanged(final ListSelectionEvent e) {
                onCheckersSelectionChanged();
            }
        });
        getChooseCheckerButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent e) {
                checkerName = readSelectedCheckerName();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        getCancelButton().addActionListener(new ActionListener(){
            @Override public void actionPerformed(final ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        getInterproceduralCheckBox().addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent e) {
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
        final DefaultListModel guiListModel =
		(DefaultListModel)getCheckerNamesList().getModel();
        for (String name: namesSet)
            guiListModel.addElement(name);
    }

    private void onCheckersSelectionChanged() {
	final int newSelection = getCheckerNamesList().getSelectedIndex();
	if (newSelection == getLastSelection())
	    return;
	setLastSelection(newSelection);
	String checkerInfo;
	checkerInfo = CheckerFactory.getCheckerCreationInfo(
		    readSelectedCheckerName());
	getCheckerInfoTextArea().setText(checkerInfo);
	getCheckerInfoTextArea().setCaretPosition(0);
    }

    private int getLastSelection() {
        return lastSelection;
    }

    private void setLastSelection(final int value) {
        lastSelection = value;
    }

    private JList getCheckerNamesList() {
        return checkerNamesList;
    }

    private JTextArea getCheckerInfoTextArea() {
        return checkerInfoTextArea;
    }

    private JButton getChooseCheckerButton() {
        return chooseCheckerButton;
    }

    private JButton getCancelButton() {
        return cancelButton;
    }

    private JCheckBox getInterproceduralCheckBox() {
        return interproceduralCheckBox;
    }

    private String checkerName;
    private int lastSelection;
    private boolean interprocedural;
    private final JList checkerNamesList;
    private final JTextArea checkerInfoTextArea;
    private final JButton chooseCheckerButton;
    private final JButton cancelButton;
    private final JCheckBox interproceduralCheckBox;
}
