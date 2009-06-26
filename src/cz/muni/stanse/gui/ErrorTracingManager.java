package cz.muni.stanse.gui;

import cz.muni.stanse.PresentableErrorTraceLocation;
import cz.muni.stanse.PresentableErrorTrace;
import java.util.List;

final class ErrorTracingManager {

    // package-private section

    ErrorTracingManager(final javax.swing.JButton gotoFirstButton,
                           final javax.swing.JButton gotoNextButton,
                           final javax.swing.JButton gotoPrevButton,
                           final javax.swing.JButton gotoLastButton,
                           final javax.swing.JMenuItem gotoFirstItem,
                           final javax.swing.JMenuItem gotoNextItem,
                           final javax.swing.JMenuItem gotoPrevItem,
                           final javax.swing.JMenuItem gotoLastItem) {
        errorTrace = null;
        traceLocationIndex = 0;

        gotoFirstButton.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoFirstTraceLocation();
            }
        });
        gotoNextButton.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoNextTraceLocation();
            }
        });
        gotoPrevButton.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoPreviousTraceLocation();
            }
        });
        gotoLastButton.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoLastTraceLocation();
            }
        });
        gotoFirstItem.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoFirstTraceLocation();
            }
        });
        gotoNextItem.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoNextTraceLocation();
            }
        });
        gotoPrevItem.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoPreviousTraceLocation();
            }
        });
        gotoLastItem.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                gotoLastTraceLocation();
            }
        });
    }

    void onSelectionChanged(final PresentableErrorTrace errorTrace) {
        if (getErrorTrace() == errorTrace)
            return;
        setErrorTrace(errorTrace);
        setTraceLocationIndex(getTraceLocationsStartIndex());
    }

    void markActualErrorTraceLocation() {
        if (getErrorTrace() == null)
            return;
        final PresentableErrorTraceLocation location =
                    getErrorTrace().getLocations().get(getTraceLocationIndex());
        getOpenedSourceFilesManager().showSourceFile(
                                      new java.io.File(location.getUnitName()));
        getOpenedSourceFilesManager().selectLineInShowedSourceFile(
                                      location.getLineNumber());
        MainWindow.getInstance().getConsoleManager().clear();
        MainWindow.getInstance().getConsoleManager().appendText(
                                                     location.getDescription());
    }

    void gotoFirstTraceLocation() {
        if (getErrorTrace() == null)
            return;
        if (getTraceLocationIndex() != 0) {
            setTraceLocationIndex(0);
            markActualErrorTraceLocation();
        }
    }

    void gotoNextTraceLocation() {
        if (getErrorTrace() == null)
            return;
        if (getTraceLocationIndex() < getTraceLocations().size() - 1) {
            setTraceLocationIndex(getTraceLocationIndex() + 1);
            markActualErrorTraceLocation();
        }
    }

    void gotoPreviousTraceLocation() {
        if (getErrorTrace() == null)
            return;
        if (getTraceLocationIndex() > 0) {
            setTraceLocationIndex(getTraceLocationIndex() - 1);
            markActualErrorTraceLocation();
        }
    }

    void gotoLastTraceLocation() {
        if (getErrorTrace() == null)
            return;
        if (getTraceLocationIndex() != getTraceLocations().size() - 1) {
            setTraceLocationIndex(getTraceLocations().size() - 1);
            markActualErrorTraceLocation();
        }
    }

    // private section

    private int getTraceLocationsStartIndex() {
        return (getErrorTrace() != null &&
                !getErrorTrace().getLocations().get(0).isContextLocation())
                    ? 0 : -1;
    }

    private List<PresentableErrorTraceLocation> getTraceLocations() {
        return getErrorTrace().getLocations();
    }

    private OpenedSourceFilesManager getOpenedSourceFilesManager() {
        return MainWindow.getInstance().getOpenedSourceFilesManager();
    }

    private PresentableErrorTrace getErrorTrace() {
        return errorTrace;
    }

    private void setErrorTrace(final PresentableErrorTrace trace) {
        errorTrace = trace;
    }

    private int getTraceLocationIndex() {
        return traceLocationIndex;
    }

    private void setTraceLocationIndex(final int value) {
        traceLocationIndex = value;
    }

    private PresentableErrorTrace errorTrace;
    private int traceLocationIndex;
}
