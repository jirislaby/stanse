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
        String result = new String();
        result += toString() + '\n';
        for (final CheckerErrorTraceLocation location : getLocations())
            result += location.toString() + '\n';
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
