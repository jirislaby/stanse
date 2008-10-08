/*
 * PointerOwnership.java
 *
 * Created on 1. březen 2007, 10:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.stanse.ownershipchecker;

import java.util.HashSet;

/**
 * Třída slouží k uchovávání informací o jednotlivých ukazatelech.
 * @author Michal Fiedler
 */
public class PointerOwnership {
    private int ownershipValue;
    private HashSet<String> ownershipSet = new HashSet<String>();
    private IntObj ownershipSetValue;
    private String sourceOfMemory;

    public PointerOwnership() {

    }

    public PointerOwnership(String nameOfPointer, int ownershipValue, IntObj ownershipSetValue, String sourceOfMemory) {
        this.ownershipValue = ownershipValue;
        this.ownershipSetValue = ownershipSetValue;
        this.sourceOfMemory = sourceOfMemory;
        ownershipSet.add(nameOfPointer);
    }

    public PointerOwnership(String nameOfPointer, int ownershipValue, IntObj ownershipSetValue, HashSet<String> ownershipSet, String sourceOfMemory) {
        this.ownershipValue = ownershipValue;
        this.ownershipSetValue = ownershipSetValue;
        this.ownershipSet = ownershipSet;
        this.sourceOfMemory = sourceOfMemory;
        ownershipSet.add(nameOfPointer);
    }

    public int getOwnershipValue() {
        return ownershipValue;
    }

    public void setOwnershipValue(int ownershipValue) {
        this.ownershipValue = ownershipValue;
    }

    public HashSet<String> getOwnershipSet() {
        return ownershipSet;
    }

    public void setOwnershipSet(HashSet<String> ownershipSet) {
        this.ownershipSet = ownershipSet;
    }

    public IntObj getOwnershipSetValue() {
        return ownershipSetValue;
    }

    public void setOwnershipSetValue(IntObj ownershipSetValue) {
        this.ownershipSetValue = ownershipSetValue;
    }

    public String getSourceOfMemory() {
        return sourceOfMemory;
    }

}

