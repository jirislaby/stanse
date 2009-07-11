package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.utils.ProgressMonitor;
import cz.muni.stanse.utils.ColumnMessageFormater;
import cz.muni.stanse.utils.TimedMessageSequenceFormater;

final class AutomatonCheckerLogger {

    // package-private section

    AutomatonCheckerLogger(final ProgressMonitor monitor) {
        this.monitor = monitor;
        columnsFormater = new ColumnMessageFormater("  ");
        timingFormater = new TimedMessageSequenceFormater(
                                   columnsFormater.getTabPattern() + "done in ",
                                   " seconds","...");
    }

    void phaseLog(final String s) {
        note(getTimingFormater().write(s));
    }

    void phaseBreak(final String s) {
        note(getTimingFormater().interrupt(s));
    }

    void note(final String s) {
        getMonitor().write(getColumnsFormater().write(s + "\n"));
    }

    void pushTab() {
        getColumnsFormater().pushTab();
    }

    void popTab() {
        getColumnsFormater().popTab();
    }

    // package-private section

    private ProgressMonitor getMonitor() {
        return monitor;
    }

    public ColumnMessageFormater getColumnsFormater() {
        return columnsFormater;
    }

    public TimedMessageSequenceFormater getTimingFormater() {
        return timingFormater;
    }

    private final ProgressMonitor monitor;
    private final ColumnMessageFormater columnsFormater;
    private final TimedMessageSequenceFormater timingFormater;
}
