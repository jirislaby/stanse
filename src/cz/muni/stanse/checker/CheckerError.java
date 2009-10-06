/**
 *  @brief Defines public final class CheckerError. It represents the error
 *         found in source program (reprezented by set of CFGs).
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

/**
 * @brief Represent output from checkers, which is the error found in the
 *        source file (reprezented by set of CFGs). 
 *
 * Found error is described by short and full string description, level of
 * error importance and by list of error-traces. Each checker is under
 * obligation to report all its errors via instances of this class.  
 *
 * @see cz.muni.stanse.checker#Checker
 *      cz.muni.stanse.checker#CheckerErrorTrace
 */
public final class CheckerError implements Comparable<CheckerError> {

    // public section

    public CheckerError(final String shortDesc, final String fullDesc,
                        final int importance, final String checkerName,
                        final List<CheckerErrorTrace> traces) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.importance = importance;
        this.checkerName = checkerName;
        this.traces = traces;
    }

    public CheckerError(final String shortDesc, final String fullDesc,
                        final int importance, final String checkerName,
                        final List<CFGNode> trace, final String startMsg,
                        final String innerMsg, final String endMsg,
                        final LazyInternalStructures internals) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.importance = importance;
        this.checkerName = checkerName;
        this.traces = new Vector();
        traces.add(new CheckerErrorTrace(trace,startMsg,innerMsg,endMsg,
                                         internals));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((CheckerError)obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + getShortDesc().hashCode();
        hash = 59 * hash + getFullDesc().hashCode();
        hash = 59 * hash + getImportance();
        hash = 59 * hash + getCheckerName().hashCode();
        hash = 59 * hash + getTraces().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        final CheckerErrorTraceLocation errorLocation = getErrorLocation();
        return new java.io.File(errorLocation.getUnitName()).getAbsolutePath()
                  .replaceFirst(cz.muni.stanse.Stanse.getInstance()
                                                     .getRootDirectory()+'/',"")
               + " [" + errorLocation.getLineNumber() + "] : "
               + getFullDesc();
    }

    public String dump() {
        StringBuilder result = new StringBuilder();
        result.append(toString());
	result.append('\n');
        for (final CheckerErrorTrace trace : getTraces())
            result.append(trace.dump());
        return result.toString();
    }

    public Element xmlDump() {
	Element result = DocumentFactory.getInstance().createElement("error");
	result.addElement("short_desc").addText(getShortDesc());
	result.addElement("full_desc").addText(getFullDesc());
	result.addElement("importance").addText(
			Integer.toString(getImportance()));
	result.addElement("checker_name").addText(getCheckerName());
	Element traces = result.addElement("traces").addText(getCheckerName());

        for (final CheckerErrorTrace trace: getTraces())
            traces.add(trace.xmlDump());
        return result;
    }

    @Override
    public int compareTo(CheckerError other) {
        return getImportance() - other.getImportance();
    }

    public CheckerErrorTraceLocation getCauseLocation() {
        return getTraces().iterator().next().getCauseLocation();
    }

    public CheckerErrorTraceLocation getErrorLocation() {
        return getTraces().iterator().next().getErrorLocation();
    }

    public boolean isEqualWith(final CheckerError other) {
        return getShortDesc().equals(other.getShortDesc()) &&
               getFullDesc().equals(other.getFullDesc()) &&
               getImportance() == other.getImportance() &&
               getCheckerName().equals(other.getCheckerName()) &&
               getTraces().equals(other.getTraces());
    }

    public String getShortDesc() {
        return new String(shortDesc);
    }

    public String getFullDesc() {
        return new String(fullDesc);
    }

    public int getImportance() {
        return importance;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public List<CheckerErrorTrace> getTraces() {
        return Collections.unmodifiableList(traces);
    }

    // private section

    private final String shortDesc;
    private final String fullDesc;
    private final int importance;
    private final String checkerName;
    private final List<CheckerErrorTrace> traces;
}
