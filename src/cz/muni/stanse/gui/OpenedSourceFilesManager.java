package cz.muni.stanse.gui;

import cz.muni.stanse.utils.ClassLogger;

import java.util.LinkedList;
import java.io.File;

import javax.swing.JTextArea;

final class OpenedSourceFilesManager {

    // package-private section

    OpenedSourceFilesManager(
                           final javax.swing.JTabbedPane sourceCodeTabbedPane) {
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
        final JTextArea sourceCodeText = getShowedTabTextArea();
        int start,end;
        try {
            start = sourceCodeText.getLineStartOffset(line - 1);
            end = sourceCodeText.getLineEndOffset(line - 1);
        }
        catch(final javax.swing.text.BadLocationException exception) {
            ClassLogger.error(this,"Cannot mark text in actual source " +
                                   "code file because line to be marked lies" +
                                   " outside of the file. See exception " +
                                   "trace for details:");
            ClassLogger.error(this,exception);
            return;
        }
        sourceCodeText.select(start,end);
        sourceCodeText.getCaret().setSelectionVisible(true);
    }


    String getActiveFile() {
        final int selectedIndex = getSourceCodeTabbedPane().getSelectedIndex();
        if (selectedIndex == -1)
            return null;
        return getFileAt(selectedIndex).toString();
    }

    java.util.List<String> getAllFiles() {
        final LinkedList<String> result = new LinkedList<String>();
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
        getSourceCodeTabbedPane().addTab(
                    sourceFile.getName(),null,
                    new javax.swing.JScrollPane(
                           loadSourceFileIntoTextArea(sourceFile,
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

    private JTextArea loadSourceFileIntoTextArea(final java.io.File sourcefile,
                                               final JTextArea sourceCodeArea) {
        try {
            final java.io.BufferedReader reader = new java.io.BufferedReader(
                                            new java.io.FileReader(sourcefile));
            String readedLine;
            while ((readedLine = reader.readLine()) != null)
                sourceCodeArea.append(readedLine + '\n');
            reader.close();
        }
        catch (final java.io.IOException exception) {
            ClassLogger.error(this,"Loading of source file '" + sourcefile +
                                   "' to Stanse has FAILED. See exception " +
                                   "trace for details:");
            ClassLogger.error(this,exception);
        }
        return sourceCodeArea;
    }

    private JTextArea getShowedTabTextArea() {
        return (JTextArea)
               ((javax.swing.JViewport)
               ((javax.swing.JScrollPane)getSourceCodeTabbedPane().
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

    private javax.swing.JTabbedPane getSourceCodeTabbedPane() {
        return sourceCodeTabbedPane;
    }

    private final javax.swing.JTabbedPane sourceCodeTabbedPane;
}
