package cz.muni.stanse.gui;

import cz.muni.stanse.utils.ClassLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;

final class OpenedSourceFilesManager {

    // package-private section

    OpenedSourceFilesManager(final JTabbedPane sourceCodeTabbedPane) {
        this.sourceCodeTabbedPane = sourceCodeTabbedPane;
    }

    void showSourceFile(final File file) {
        int tabIndex = getTabIndex(file);
        if (tabIndex == -1) {
            createTabWithSourceCodeFile(file);
            tabIndex = getSourceCodeTabbedPane().getTabCount() - 1;
        }
        if (getSourceCodeTabbedPane().getSelectedIndex() != tabIndex)
            getSourceCodeTabbedPane().setSelectedIndex(tabIndex);
    }

    void selectLineInShowedSourceFile(final int line) {
        final JTextArea sourceCodeText = getShownTabTextArea();
        int start,end;
        try {
            start = sourceCodeText.getLineStartOffset(line - 1);
            end = sourceCodeText.getLineEndOffset(line - 1);
        } catch (final BadLocationException e) {
            ClassLogger.error(this, "Cannot mark text in actual source " +
			    "code file because line to be marked lies outside " +
			    "of the file. See exception trace for details:", e);
            return;
        }
	/*
	 * do the set/move from end to start, so that we see the selection even
	 * when jumping from the bottom up
	 */
	sourceCodeText.setCaretPosition(end);
	sourceCodeText.moveCaretPosition(start);
        sourceCodeText.getCaret().setSelectionVisible(true);
    }

    void gotoColumnInSelectedLine(final int column) {
        boolean useColumns = false;
        if (useColumns) {
            final JTextArea sourceCodeText = getShownTabTextArea();
	    try {
		sourceCodeText.setCaretPosition(
			sourceCodeText.getLineStartOffset(
			sourceCodeText.getLineOfOffset(
			sourceCodeText.getCaretPosition() - 1)) + column - 1);
	    } catch (BadLocationException e) { }
            sourceCodeText.getCaret().setVisible(true);
        }
    }


    String getActiveFile() {
        final int selectedIndex = getSourceCodeTabbedPane().getSelectedIndex();
        if (selectedIndex == -1)
            return null;
        return getFileAt(selectedIndex).toString();
    }

    List<String> getAllFiles() {
        final List<String> result = new LinkedList<String>();
        for (int i = 0; i < getSourceCodeTabbedPane().getTabCount(); ++i)
            result.add(getFileAt(i).toString());
        return result;
    }

    void closeActiveFile() {
        final int currentTabIdx = getSourceCodeTabbedPane().getSelectedIndex();
        if (currentTabIdx != -1)
            getSourceCodeTabbedPane().remove(currentTabIdx);
    }

    void closeAllFiles() {
        getSourceCodeTabbedPane().removeAll();
    }

    // private section

    private void createTabWithSourceCodeFile(final File sourceFile) {
	getSourceCodeTabbedPane().addTab(sourceFile.getName(), null,
			new JScrollPane(loadSourceFileIntoTextArea(sourceFile,
                                            createTextAreaForSourceCodeFile())),
                    sourceFile.toString());
        final int tabIndex = getSourceCodeTabbedPane().getTabCount() - 1;
        getSourceCodeTabbedPane().setTabComponentAt(tabIndex,
                             new ButtonTabComponent(getSourceCodeTabbedPane()));
    }

    private JTextArea createTextAreaForSourceCodeFile() {
        final JTextArea textArea = new JTextArea();
        textArea.setFont(new java.awt.Font("Monospaced",
                                           java.awt.Font.PLAIN,13));
        return textArea;
    }

    private JTextArea loadSourceFileIntoTextArea(final File sourcefile,
                                               final JTextArea sourceCodeArea) {
        try {
	    final BufferedReader reader = new BufferedReader(
			    new FileReader(sourcefile));
            String readedLine;
            while ((readedLine = reader.readLine()) != null)
                sourceCodeArea.append(readedLine + '\n');
            reader.close();
	} catch (final IOException e) {
	    ClassLogger.error(this, "Loading of source file '" + sourcefile +
			    "' to Stanse has FAILED. See exception trace for " +
			    "details:", e);
        }
        return sourceCodeArea;
    }

    private JTextArea getShownTabTextArea() {
        return (JTextArea)((JViewport)((JScrollPane)getSourceCodeTabbedPane().
                       getSelectedComponent()).getComponent(0)).getComponent(0);
    }

    private int getTabIndex(final File file) {
        for (int i = 0; i < getSourceCodeTabbedPane().getTabCount(); ++i) {
            if (file.equals(getFileAt(i)))
                return i;
        }
        return -1;
    }

    private File getFileAt(final int tabIndex) {
        return new File(getFileStringAt(tabIndex));
    }

    private String getFileStringAt(final int tabIndex) {
        return getSourceCodeTabbedPane().getToolTipTextAt(tabIndex);
    }

    private JTabbedPane getSourceCodeTabbedPane() {
        return sourceCodeTabbedPane;
    }

    private final JTabbedPane sourceCodeTabbedPane;
}
