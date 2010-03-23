/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.pointeranalyzer.steensgaard;

/**
 * Classes implementing this interface are able to process notifications when
 * two equivalence classes get joined.
 *
 * @author Michal Strehovsky
 */
public interface EquivalenceClassJoinListener {

    public void notifyEquivalenceClassJoined(EquivalenceClass oldClass, EquivalenceClass newClass);

}
