package cz.muni.stanse.gui;

import cz.muni.stanse.PresentableError;
import cz.muni.stanse.CheckForBugs;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.ClassLogger;

import java.util.LinkedList;

@SuppressWarnings("serial")
final class ActionCheckForBugs extends javax.swing.AbstractAction {

    // public section

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        new java.lang.Thread(new Executor()).start();
    }

    ActionCheckForBugs() {
        super();
    }

    // private section

    private final class Executor implements Runnable {
        @Override
        public void run() {
            Pair<LinkedList<CheckerError>,LinkedList<PresentableError> > errors;
            try {
                errors = CheckForBugs.run(MainWindow.getInstance().
                                          getConfiguration(),
                                          new CheckingProgressHandler());
            }
            catch(final Exception exception) {
                ClassLogger.error(this,"Checking for bugs has failed (see " +
                                       "following exception trace for " +
                                       "details):");
                ClassLogger.error(this,exception);
                ClassLogger.error(this,exception.getStackTrace());
                getConsoleManager().appendText(
                    "Checking for bugs has failed (see following exception" +
                    "trace for details):\n");
                getConsoleManager().appendText(
                    exception.toString() + '\n');
                getConsoleManager().appendText(
                    exception.getStackTrace().toString() + '\n');
                getErrorsTreeManager().clear();
                getErrorsTreeManager().present();

                getErrorTracingManager().onSelectionChanged(null);
                return;
            }
            getConsoleManager().appendText("Delivering errors to GUI...");
            getErrorsTreeManager().clear();
            getErrorsTreeManager().addAll(errors.getSecond());
            getErrorsTreeManager().present();

            getErrorTracingManager().onSelectionChanged(null);
            getConsoleManager().appendText("Done.\n");
        }
    }

    private ErrorsTreeManager getErrorsTreeManager() {
        return MainWindow.getInstance().getErrorsTreeManager();
    }

    private ErrorTracingManager getErrorTracingManager() {
        return MainWindow.getInstance().getErrorTracingManager();
    }

    private ConsoleManager getConsoleManager() {
        return MainWindow.getInstance().getConsoleManager();
    }
}
