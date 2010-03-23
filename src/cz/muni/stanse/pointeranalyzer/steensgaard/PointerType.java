/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.pointeranalyzer.steensgaard;

/**
 *
 * @author Michal Strehovsky
 */
public interface PointerType extends EquivalenceClassJoinListener {

    public void unifyWith(PointerType that);

    

}
