/**
 * @brief
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.checker;

import java.util.List;
import java.util.Collections;

/**
 * @brief
 *
 * @see
 */
public final class CheckerErrorTrace {

    // public section

    public CheckerErrorTrace(final List<CheckerErrorTraceLocation> locations,
                             final String description){
        this.locations = locations;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((CheckerErrorTrace)obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + getLocations().hashCode();
        hash = 79 * hash + getDescription().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "trace [locations: " + getLocations().size() + "]";
    }

    public String dump() {
        StringBuilder result = new StringBuilder();
        result.append(toString());
	result.append('\n');
        for (final CheckerErrorTraceLocation location : getLocations()) {
            result.append(location.toString());
            result.append('\n');
	}
        return result.toString();
    }

    public String xmlDump(final String tab, final String seek) {
        String result = tab + "<trace>\n";
        result += tab + seek + "<description>" + getDescription() +
                               "</description>\n";
        result += tab + seek + "<locations>\n";
        for (final CheckerErrorTraceLocation location : getLocations())
            result += location.xmlDump(tab + seek + seek,seek);
        result += tab + seek + "</locations>\n";
        result += tab + "</trace>\n";
        return result;
    }

    public CheckerErrorTraceLocation getCauseLocation() {
        for (final CheckerErrorTraceLocation location : getLocations())
            if (!location.isContextLocation())
                return location;
        assert(false);
        return getLocations().get(0);
    }

    public CheckerErrorTraceLocation getErrorLocation() {
        return getLocations().get(getLocations().size() - 1);
    }

    public boolean isEqualWith(final CheckerErrorTrace other) {
        return getLocations().equals(other.getLocations()) &&
               getDescription().equals(other.getDescription());
    }

    public List<CheckerErrorTraceLocation> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public String getDescription() {
        return description;
    }

    // private section

    private final List<CheckerErrorTraceLocation> locations;
    private final String description;
}
