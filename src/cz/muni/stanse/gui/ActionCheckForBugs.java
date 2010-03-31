package cz.muni.stanse.gui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import cz.muni.stanse.configuration.Configuration;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;

@SuppressWarnings("serial")
final class ActionCheckForBugs extends javax.swing.AbstractAction {

    // public section

    @Override
    public synchronized void actionPerformed(java.awt.event.ActionEvent e) {
        if (isOnProgress())
            return;
        setOnProgress(true);

        getErrorTracingManager().onSelectionChanged(null);
        getErrorsTreeManager().clear();
        getErrorsTreeManager().present();
        getConsoleManager().clear();

        getConfiguration().evaluate_EachUnitSeparately(
            new CheckerErrorReceiver() {
                @Override
                public void receive(final CheckerError error) {
                	// GUI MUST be updated in EDT
                    try {
						SwingUtilities.invokeAndWait(new Runnable() {						
							@Override
							public void run() {
								getErrorsTreeManager().addUnchecked(error);
						        getErrorsTreeManager().present();
							}
						});
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                @Override
                public void onEnd() {
                    assert(isOnProgress() == true);
                    setOnProgress(false);
                }
            },
            new CheckerProgressMonitor() {
                @Override
                public void write(final String s) {
                    getConsoleManager().appendText(s);
                }
            });
    }

    ActionCheckForBugs() {
        super();
        onProgress = false;
    }

    // private section

    private ErrorsTreeManager getErrorsTreeManager() {
        return MainWindow.getInstance().getErrorsTreeManager();
    }

    private ErrorTracingManager getErrorTracingManager() {
        return MainWindow.getInstance().getErrorTracingManager();
    }

    private ConsoleManager getConsoleManager() {
        return MainWindow.getInstance().getConsoleManager();
    }

    private Configuration getConfiguration() {
        return MainWindow.getInstance().getConfiguration();
    }

    private boolean isOnProgress() {
        return onProgress;
    }

    private synchronized void setOnProgress(boolean onProgress) {
        this.onProgress = onProgress;
    }

    private boolean onProgress;
}
