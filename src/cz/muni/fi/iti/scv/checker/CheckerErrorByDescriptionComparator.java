/*
 * CheckerErrorComparator.java
 *
 * Compares two checker errors by description
 */

package cz.muni.fi.iti.scv.checker;

import java.util.Comparator;

/**
 *
 * @author stastny
 */
public class CheckerErrorByDescriptionComparator implements Comparator<CheckerError> {
    
    public int compare(CheckerError a, CheckerError b) {
        return a.getDescription().compareTo(b.getDescription());
    }
    
}
