package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.utils.msgformat.ColumnMessageFormatter;
import cz.muni.stanse.utils.msgformat.TimedMessageSequenceFormatter;

final class AutomatonCheckerLogger {

    // package-private section

    AutomatonCheckerLogger(final CheckerProgressMonitor monitor) {
        this.monitor = monitor;
        columnsFormatter = new ColumnMessageFormatter("  ");
        timingFormatter = new TimedMessageSequenceFormatter(
                                   columnsFormatter.getTabPattern() + "done in ",
                                   " seconds","...");
    }

    void phaseLog(final String s) {
        note(getTimingFormatter().write(s));
    }

    void phaseBreak(final String s) {
        note(getTimingFormatter().interrupt(s));
    }

    void note(final String s) {
        getMonitor().write(getColumnsFormatter().write(s + "\n"));
    }

    void pushTab() {
        getColumnsFormatter().pushTab();
    }

    void popTab() {
        getColumnsFormatter().popTab();
    }

    // package-private section

    private CheckerProgressMonitor getMonitor() {
        return monitor;
    }

    public ColumnMessageFormatter getColumnsFormatter() {
        return columnsFormatter;
    }

    public TimedMessageSequenceFormatter getTimingFormatter() {
        return timingFormatter;
    }

    private final CheckerProgressMonitor monitor;
    private final ColumnMessageFormatter columnsFormatter;
    private final TimedMessageSequenceFormatter timingFormatter;
}
