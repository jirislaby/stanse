package cz.muni.stanse.gui;

@SuppressWarnings("serial")
final class ActionCloseActiveTab extends javax.swing.AbstractAction {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        MainWindow.getInstance().getOpenedSourceFilesManager().
            closeActiveFile();
    }
}
