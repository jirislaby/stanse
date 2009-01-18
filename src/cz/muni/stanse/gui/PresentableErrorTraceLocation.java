package cz.muni.stanse.gui;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.Unit;

import cz.muni.stanse.utils.Triple;

import java.util.HashMap;

final class PresentableErrorTraceLocation {

    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((PresentableErrorTraceLocation)obj);
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

    // package-private section

    PresentableErrorTraceLocation(
            final Triple<CFGNode,String,CFG> location,
            final HashMap<CFG,Unit> cfgToUnitMapping) {
        unitName = cfgToUnitMapping.get(location.getThird()).getName();
        lineNumber = new Integer(

                       // TODO: following two lines of code can be removed, when
                       //       there are no CFGNodes without XML element and
                       //       each XML element has 'bl' attribute .
                       (location.getFirst().getElement() == null || null ==
                       location.getFirst().getElement().attribute("bl")) ? "1" :

                                 location.getFirst().getElement().
                                     attribute("bl").getValue());
        description = location.getSecond();
    }

    String getUnitName() {
        return unitName;
    }

    int getLineNumber() {
        return lineNumber;
    }

    String getDescription() {
        return description;
    }

    boolean isEqualWith(final PresentableErrorTraceLocation other) {
        return getUnitName().equals(other.getUnitName()) &&
               getLineNumber() == other.getLineNumber() &&
               getDescription().equals(other.getDescription());
    }

    // private section

    private final String unitName;
    private final int lineNumber;
    private final String description;
}
