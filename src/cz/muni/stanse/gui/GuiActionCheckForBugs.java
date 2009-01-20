package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.ClassLogger;

import java.util.LinkedList;

@SuppressWarnings("serial")
final class GuiActionCheckForBugs extends javax.swing.AbstractAction {

    // public section

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        Pair<LinkedList<CheckerError>,LinkedList<PresentableError> > errors;
        try {
            errors = CheckForBugs.run(GuiMainWindow.getInstance().
                                      getConfiguration());
        }
        catch(final Exception exception) {
            ClassLogger.error(this,"Checking for bugs has failed (see " +
                                   "following exception trace for details):");
            ClassLogger.error(this,exception);
            ClassLogger.error(this,exception.getStackTrace());
            return;
        }

        getErrorsTreeManager().clear();
        getErrorsTreeManager().addAll(errors.getSecond());
        getErrorsTreeManager().present();

        getErrorTracingManager().onSelectionChanged(null);
    }

    GuiActionCheckForBugs() {
        super();
    }

    // private section

    private GuiErrorsTreeManager getErrorsTreeManager() {
        return GuiMainWindow.getInstance().getErrorsTreeManager();
    }

    private GuiErrorTracingManager getErrorTracingManager() {
        return GuiMainWindow.getInstance().getErrorTracingManager();
    }
}
