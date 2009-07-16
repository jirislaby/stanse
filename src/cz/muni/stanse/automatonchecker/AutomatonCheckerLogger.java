package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.utils.msgformat.ColumnMessageFormater;
import cz.muni.stanse.utils.msgformat.TimedMessageSequenceFormater;

final class AutomatonCheckerLogger {

    // package-private section

    AutomatonCheckerLogger(final CheckerProgressMonitor monitor) {
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

    private CheckerProgressMonitor getMonitor() {
        return monitor;
    }

    public ColumnMessageFormater getColumnsFormater() {
        return columnsFormater;
    }

    public TimedMessageSequenceFormater getTimingFormater() {
        return timingFormater;
    }

    private final CheckerProgressMonitor monitor;
    private final ColumnMessageFormater columnsFormater;
    private final TimedMessageSequenceFormater timingFormater;
}
