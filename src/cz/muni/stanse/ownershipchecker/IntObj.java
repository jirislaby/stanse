/*
 * IntObj.java
 *
 * Created on 1. březen 2007, 10:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.stanse.ownershipchecker;

/**
 *
 * @author Michal Fiedler
 */
public class IntObj {
    private int value;

    public IntObj(int value) {
        this.value = value;
    }

    public IntObj(IntObj io) {
        this.value = io.value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

