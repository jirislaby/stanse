package cz.muni.stanse;

import cz.muni.stanse.checker.ErrorTrace;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.Unit;

import cz.muni.stanse.utils.Triple;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Collections;

public final class PresentableErrorTrace {

    // public section

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((PresentableErrorTrace)obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + getLocations().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "trace [locations: " + getLocations().size() + "]";
    }

    public PresentableErrorTrace(final ErrorTrace errorTrace,
                          final HashMap<CFG,Unit> cfgToUnitMapping) {
        locations = compileErrorTraceLocations(errorTrace,cfgToUnitMapping);
    }

    public List<PresentableErrorTraceLocation> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public PresentableErrorTraceLocation getCauseLocation() {
        return getLocations().get(0);
    }

    public PresentableErrorTraceLocation getErrorLocation() {
        return getLocations().get(getLocations().size() - 1);
    }

    public boolean isEqualWith(final PresentableErrorTrace other) {
        return getLocations().equals(other.getLocations());
    }

    // private section

    private static LinkedList<PresentableErrorTraceLocation>
    compileErrorTraceLocations(final ErrorTrace errorTrace,
                               final HashMap<CFG,Unit> cfgToUnitMapping) {
        final LinkedList<PresentableErrorTraceLocation> result =
            new LinkedList<PresentableErrorTraceLocation>();
        for (final Triple<CFGNode,String,CFG> location :
                errorTrace.getErrorTrace())
            result.add(new PresentableErrorTraceLocation(location,
                                                         cfgToUnitMapping));
        return result;
    }

    private final LinkedList<PresentableErrorTraceLocation> locations;
}
