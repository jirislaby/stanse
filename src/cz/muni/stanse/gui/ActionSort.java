package cz.muni.stanse.gui;

/**
 * This action sorts all the errors according to the importance
 * @author radim cebis
 *
 */
@SuppressWarnings("serial")
final class ActionSort extends javax.swing.AbstractAction {

    // public section

    @Override
    public synchronized void actionPerformed(java.awt.event.ActionEvent evt) {
    	getErrorsTreeManager().sortErrors();
    }
    // private section

    private ErrorsTreeManager getErrorsTreeManager() {
        return MainWindow.getInstance().getErrorsTreeManager();
    }
}

