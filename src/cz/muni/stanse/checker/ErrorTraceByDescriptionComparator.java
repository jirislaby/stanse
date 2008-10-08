/*
 * ErrorTraceComparator.java
 *
 * Compares two Error traces by description
 */

package cz.muni.stanse.checker;

import java.util.Comparator;

/**
 *
 * @author stastny
 */
public class ErrorTraceByDescriptionComparator implements Comparator<ErrorTrace> {
    
    public int compare(ErrorTrace a, ErrorTrace b) {
        return a.getDescription().compareTo(b.getDescription());
    }

}
