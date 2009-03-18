package cz.muni.stanse.gui;

@SuppressWarnings("serial")
final class ActionCloseAllTabs extends javax.swing.AbstractAction {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        GuiMainWindow.getInstance().getOpenedSourceFilesManager().
            closeAllFiles();
    }
}
