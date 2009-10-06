/**
 * @brief
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGNode;

import cz.muni.stanse.codestructures.LazyInternalStructures;
import java.util.List;
import java.util.Vector;
import java.util.Collections;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

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

    public CheckerErrorTrace(final List<CFGNode> trace, final String startMsg,
                             final String innerMsg, final String endMsg,
                             final LazyInternalStructures internals) {
        this.locations = buildLocations(trace,startMsg,innerMsg,endMsg,
                                        internals);
        this.description = "the only trace";
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

    public Element xmlDump() {
	Element result = DocumentFactory.getInstance().createElement("trace");
	result.addElement("description").addText(getDescription());
	Element locs = result.addElement("locations");
	for (final CheckerErrorTraceLocation location: getLocations())
	    locs.add(location.xmlDump());
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

    private static Vector<CheckerErrorTraceLocation>
    buildLocations(final List<CFGNode> trace, final String startMsg,
                   final String innerMsg, final String endMsg,
                   final LazyInternalStructures internals) {
        final Vector<CheckerErrorTraceLocation> result =
                new Vector<CheckerErrorTraceLocation>();

        assert(!trace.isEmpty());
        result.add(new CheckerErrorTraceLocation(
                                getNodeUnitName(trace.get(0),internals),
                                trace.get(0).getLine(),startMsg));
        for (CFGNode node : trace.subList(1,trace.size() - 1))
            if (!node.getElement().getName().equals("assert"))
                result.add(new CheckerErrorTraceLocation(
                                 getNodeUnitName(node,internals),node.getLine(),
                                 innerMsg));
        result.add(new CheckerErrorTraceLocation(
                           getNodeUnitName(trace.get(trace.size()-1),internals),
                           trace.get(trace.size()-1).getLine(),endMsg));
        return result;
    }

    private static String getNodeUnitName(final CFGNode node,
                                       final LazyInternalStructures internals) {
        return Stanse.getUnitManager().getUnitName(
                    internals.getNodeToCFGdictionary().get(node));
    }

    private final List<CheckerErrorTraceLocation> locations;
    private final String description;
}
