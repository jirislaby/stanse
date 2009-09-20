/**
 * @brief
 *
 * Copyright (c) 2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */

package cz.muni.stanse.checker;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public final class CheckerErrorTraceLocation {

    public CheckerErrorTraceLocation(final String unitName,final int lineNumber,
                                     final String description) {
        this.unitName = unitName;
        this.lineNumber = lineNumber;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((CheckerErrorTraceLocation)obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + getUnitName().hashCode();
        hash = 71 * hash + getLineNumber();
        hash = 71 * hash + getDescription().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return getDescription() + "[file: " + getUnitName() + " , line: " +
               getLineNumber() + "]";
    }

    public Element xmlDump() {
	Element result = DocumentFactory.getInstance().
		    createElement("location");
	result.addElement("unit").addText(getUnitName());
	result.addElement("line").addText(Integer.toString(getLineNumber()));
	result.addElement("description").addText(getDescription());
        return result;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEqualWith(final CheckerErrorTraceLocation other) {
        return getUnitName().equals(other.getUnitName()) &&
               getLineNumber() == other.getLineNumber() &&
               getDescription().equals(other.getDescription());
    }

    public boolean isContextLocation() {
        return getDescription().startsWith("<context>");
    }

    // private section

    private final String unitName;
    private final int lineNumber;
    private final String description;
}
