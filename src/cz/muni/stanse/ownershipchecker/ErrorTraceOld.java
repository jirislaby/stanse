/*
 * ErrorTraceOld.java
 *
 * Created on 14. březen 2007, 12:50
 *
 * Class representing an error trace. It was created to have toString
 * description of the trace.
 */

package cz.muni.stanse.ownershipchecker;

import cz.muni.stanse.xml2cfg.CFGNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends ArrayList by adding a description to it.
 * 
 * @author xstastn
 */
public class ErrorTraceOld extends ArrayList<CFGNode> {
    
    private String description = "";
    
    /**
     * Creates a new instance of ErrorTrace with given description
     * Same as ErrorTrace(new ArrayList<CFGNode>(), "No description")
     */
    public ErrorTraceOld() {
        this(new ArrayList<CFGNode>(), "No description");
    }
    
    /**
     * Creates a new instance of ErrorTrace with given description
     * Same as ErrorTrace(new ArrayList<CFGNode>(), description)
     * @param description Error trace description
     */
    public ErrorTraceOld(String description) {
        this(new ArrayList<CFGNode>(), description);
    }
    
    /**
     * Creates a new instance of ErrorTrace with given trace
     * Same as ErrorTrace(trace, "No description")
     * @param trace Error trace
     */
    public ErrorTraceOld(List<CFGNode> trace) {
        this(trace, "No description");
    }
    
    /**
     * Creates a new instance of ErrorTrace with given trace and description
     * @param trace Error trace
     * @param description Trace description
     */
    public ErrorTraceOld(List<CFGNode> trace, String description) {
        super(trace);
        this.description = description;
    }
    
    /**
     * Gets the string representation of the error trace
     * @return String representation
     */
    public String toString() {
        return description;
    }
    
    /**
     * Returns Error trace description
     * @return Error trace description
     */
    public String getDescription() {
        return description;
    }
    
}
