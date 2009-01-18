package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.Unit;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Collections;

final class PresentableError {

    // public section

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((PresentableError)obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + getShortDesc().hashCode();
        hash = 59 * hash + getFullDesc().hashCode();
        hash = 59 * hash + getTraces().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        final PresentableErrorTraceLocation errorLocation = getErrorLocation();
        return errorLocation.getUnitName() +
               " [" + errorLocation.getLineNumber() + "] : " +
               getFullDesc();
    }

    // package-private section

    PresentableError(final CheckerError checkerError,
                     final HashMap<CFG,Unit> cfgToUnitMapping) {
        shortDesc = checkerError.getShortDescription();
        fullDesc = checkerError.getFullDescription();
        traces = compileErrorTraces(checkerError.getErrorTraces(),
                                    cfgToUnitMapping);
    }

    String getShortDesc() {
        return shortDesc;
    }

    String getFullDesc() {
        return fullDesc;
    }

    List<PresentableErrorTrace> getTraces() {
        return Collections.unmodifiableList(traces);
    }

    PresentableErrorTraceLocation getCauseLocation() {
        return getTraces().iterator().next().getCauseLocation();
    }

    PresentableErrorTraceLocation getErrorLocation() {
        return getTraces().iterator().next().getErrorLocation();
    }

    boolean isEqualWith(final PresentableError other) {
        return getShortDesc().equals(other.getShortDesc()) &&
               getFullDesc().equals(other.getFullDesc()) &&
               getTraces().equals(other.getTraces());
    }

    // private section

    private static LinkedList<PresentableErrorTrace>
    compileErrorTraces(final List<ErrorTrace> traces,
                       final HashMap<CFG,Unit> cfgToUnitMapping) {
        final LinkedList<PresentableErrorTrace> result =
            new LinkedList<PresentableErrorTrace>();
        for (final ErrorTrace trace : traces)
            result.add(new PresentableErrorTrace(trace,cfgToUnitMapping));
        return result;
    }

    private final String shortDesc;
    private final String fullDesc;
    private final LinkedList<PresentableErrorTrace> traces;
}
