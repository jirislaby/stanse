/*
 * OwnershipInfo.java
 *
 * Created on 7. březen 2007, 12:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.stanse.ownershipchecker;

import cz.muni.stanse.checker.CheckerError;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Třída slouží k uchovávání zjištěných omezení o jednotlivých funkcích.
 * @author Michal Fiedler
 */
public class FunctionOwnershipInfo {
    private Vector<String> inputPointerNames = new Vector<String>();
    private Vector<Integer> inputPointerOwnershipValues = new Vector<Integer>();
    private Vector<Integer> outputPointerOwnershipValues = new Vector<Integer>();
    private Vector<String> sourcesOfMemory = new Vector<String>();
    private String outputSourceOfMemory;
    private Vector<HashSet<String>> globalPointerSets = new Vector<HashSet<String>>();
    private Integer output;
    private HashSet<String> calledFunctions = new HashSet<String>();
    private boolean changed;
    private Set<CheckerError> errors = new HashSet<CheckerError>();

    public FunctionOwnershipInfo() {

    }

    public int hashCode() {
        // jsou-li stejna vlastnictvi vstupu a vystupu, zdroje pameti, ..., potom se jedna o stejnou mnozinu omezeni
        return inputPointerOwnershipValues.hashCode() + outputPointerOwnershipValues.hashCode() + sourcesOfMemory.hashCode() + outputSourceOfMemory.hashCode() + getGlobalPointerSets().hashCode() + output.hashCode();
    }

    public Vector<Integer> getInputPointerOwnershipValues() {
        return inputPointerOwnershipValues;
    }

    public Vector<Integer> getOutputPointerOwnershipValues() {
        return outputPointerOwnershipValues;
    }
    
    public void setInputPointerOwnershipValues(Vector<Integer> inputOwnershipValues) {
        this.inputPointerOwnershipValues = inputOwnershipValues;
    }

    public Vector<String> getInputPointerNames() {
        return inputPointerNames;
    }

    public void setInputPointerNames(Vector<String> inputPointerNames) {
        this.inputPointerNames = inputPointerNames;
    }

    public void addParamName(String paramName) {
        inputPointerNames.add(paramName);
    }

    public void addInputPointerOwnershipValue(Integer pointerOwnershipValue) {
        inputPointerOwnershipValues.add(pointerOwnershipValue);
    }

    public void addOuputPointerOwnershipValue(Integer pointerOwnershipValue) {
        outputPointerOwnershipValues.add(pointerOwnershipValue);
    }

    public Integer getOutput() {
        return output;
    }

    public void setOutput(Integer output) {
        this.output = output;
    }

    public String getOutputSourceOfMemory() {
        return outputSourceOfMemory;
    }

    public void setOutputSourceOfMemory(String outputSourceOfMemory) {
        this.outputSourceOfMemory = outputSourceOfMemory;
    }

    public Vector<String> getSourcesOfMemory() {
        return sourcesOfMemory;
    }
    
    public void addSourceOfMemory(String sourceOfMemory) {
        sourcesOfMemory.add(sourceOfMemory);
    }

    public HashSet<String> getCalledFunctions() {
        return calledFunctions;
    }

    public void addcalledFunction(String functionName) {
        calledFunctions.add(functionName);
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Set<CheckerError> getErrors() {
        return errors;
    }

    public void addErrors(Set<CheckerError> errors) {
        this.errors.addAll(errors);
    }

    public Vector<HashSet<String>> getGlobalPointerSets() {
        return globalPointerSets;
    }

    public void setGlobalPointerSets(Vector<HashSet<String>> globalPointerSets) {
        this.globalPointerSets = globalPointerSets;
    }
} 