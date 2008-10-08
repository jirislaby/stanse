/*
 * OwnershipCheckerError.java
 *
 * Created on 26. únor 2007, 23:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.stanse.ownershipchecker;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.xml2cfg.CFGNode;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;
import org.dom4j.Element;

/**
 * Třída slouží k označení uzlu control flow grafu, ve kterém byla odhalena chyba. Umožňuje chybu popsat. Vytváří pro danou chybu chybovou trasu.
 * @author Michal Fiedler
 */
public class OwnershipCheckerError extends CheckerError {

    public enum ErrorType {FREE, UNCHECKED, FUNCTION_OUTPUT, UNCONSISTENT_JOINING_BRANCHES, UNCONSISTENT_SPLITTING_BRANCHES, NULL_ASSIGNMENT, LOST_REFERENCE, FUNCTION_INPUT, LOST_FUNCTION_OUTPUT};

    private CFGNode errorNode;
    private Set<ErrorTrace> errorTraces = new HashSet<ErrorTrace>();
    private String description;
    private HashSet<String> erroneousPointers;
    private Map<CFGNode, Map<String, PointerOwnership>> nodesWithOwnerships;
    private ErrorType errorType;
    private HashSet<CFGNode> nodeOnCorrectTrace;
    private ResourceBundle bundle = ResourceBundle.getBundle("cz/muni/stanse/ownershipchecker/OwnershipChecker");
    
    /** Creates a new instance of OwnershipCheckerError */
    public OwnershipCheckerError(CFGNode errorNode, HashSet<String> erroneousPointers, ErrorType errorType, Map<CFGNode, Map<String, PointerOwnership>> nodesWithOwnerships, HashSet<CFGNode> nodeOnCorrectTrace) {
        this.errorNode = errorNode;
        this.nodesWithOwnerships = nodesWithOwnerships;
        this.errorType = errorType;
        this.erroneousPointers = erroneousPointers;
        this.nodeOnCorrectTrace = nodeOnCorrectTrace;
        description = "";
        if (errorType == ErrorType.FREE) description += bundle.getString("freeError");
        else if (errorType == ErrorType.UNCHECKED) description += bundle.getString("uncheckedError");
        else if (errorType == ErrorType.FUNCTION_OUTPUT) description += bundle.getString("functionOutputOwnershipError");
        else if (errorType == ErrorType.UNCONSISTENT_JOINING_BRANCHES) description += bundle.getString("unconsistentBranchesError");
        else if (errorType == ErrorType.UNCONSISTENT_SPLITTING_BRANCHES) description += bundle.getString("unconsistentSplittingBranchesError");
        else if (errorType == ErrorType.NULL_ASSIGNMENT) description += bundle.getString("nullAssignmentError");
        else if (errorType == ErrorType.LOST_REFERENCE) description += bundle.getString("lostRefenceError");
        else if (errorType == ErrorType.FUNCTION_INPUT) description += bundle.getString("functionInputOwnershipError");
        else if (errorType == ErrorType.LOST_FUNCTION_OUTPUT) description += bundle.getString("lostFunctionOutputError");
        
    }

    public CFGNode getNode() {
        return errorNode;
    }

    public Set<ErrorTrace> getErrorTraces() {
        CFGNode node = errorNode;
        if (errorType == ErrorType.FREE) {
            ErrorTrace trace = new ErrorTrace(this.getDescription());
            trace.add(node);
            String pointerName = erroneousPointers.iterator().next();
            try {
                // chyba je bud v aktualnim cyklu nebo na spravne vetvi
                while (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() == 0) {
                    Set<CFGNode> predecessors = node.getPredecessors();
                    if (predecessors.size() > 1) {
                        Set<CFGNode> successors = node.getSuccessors();
                        if (successors.size() > 1) {
                            // zjisti se jaka hrana vede z uzlu do nejakeho naslednika
                            Element element = node.getCFG().getEdgeElement(node, successors.iterator().next());
                            // jestlize se nejdena o vetveni, tak je to cyklus
// ??? switch
                            if (!element.getParent().getName().equals("ifStatement")) {
                                CFGNode node2 = node;
                                for (CFGNode pred : predecessors) {
                                    if (!nodeOnCorrectTrace.contains(pred)) node2 = pred;
                                }
                                ErrorTrace cyclicTrace = new ErrorTrace(this.getDescription());
                                cyclicTrace.add(node2);
                                while (!node2.equals(errorNode)) {
                                    //cyclicTrace.add(0, node2);
                                    Set<CFGNode> preds = node2.getPredecessors();
                                    if (preds.size() > 1) {
                                        // vybere se predchudce na spravne ceste
                                        for (CFGNode pred : preds) {
                                            if (nodeOnCorrectTrace.contains(pred)) node2 = pred;
                                        }
                                    } else {
                                        node2 = preds.iterator().next();
                                    }
                                    cyclicTrace.add(0, node2);
                                }
                                // pridaji se uzli zjistene jeste pred vstupem do cyklu
                                cyclicTrace.addAll(trace);
                                errorTraces.add(cyclicTrace);
                            }
                        }
                        // vybere se predchudce na spravne ceste
                        for (CFGNode pred : predecessors) {
                            if (nodeOnCorrectTrace.contains(pred)) node = pred;
                        }
                    } else {
                        node = predecessors.iterator().next();
                    }
                    trace.add(0, node);
                }
            } catch (NullPointerException npe) {
                // jestlize se dostane az pred deklaraci daneho ukazatele, int *p; ... free(p);
            } catch (NoSuchElementException nsee) {
                // jestlize je ukazatel jako vstupni parametr funkce a dostane se az pred prvni uzel CFG
            }
            errorTraces.add(trace);
        } /*else if (errorType == ErrorType.UNCONSISTENT_SPLITTING_BRANCHES) {
            for (CFGNode succ : node.getSuccessors()) {
                List<CFGNode> trace = new ErrorTrace(this.getDescription());
                trace.add(node);
                CFGNode succnode = succ;
                Boolean allSame = true;
                while (allSame) {
                    allSame = true;
                    for (String erroneousPointer : erroneousPointers) {
                        if (nodesWithOwnerships.get(succnode).get(erroneousPointer).getOwnershipValue() != nodesWithOwnerships.get(succ).get(erroneousPointer).getOwnershipValue()) allSame = false;
                    }
                    trace.add(succnode);
                    succnode = succnode.getSuccessors().iterator().next();
                }
                errorTraces.add(trace);
            }
        }*/ else if (errorType == ErrorType.FUNCTION_INPUT) {
            ErrorTrace trace = new ErrorTrace(this.getDescription());
            trace.add(node);
            trace.add(node.getSuccessors().iterator().next());
            String pointerName = erroneousPointers.iterator().next();
            int functionInputValue = nodesWithOwnerships.get(errorNode).get((String)erroneousPointers.iterator().next()).getOwnershipValue();
            try {
                while (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() == functionInputValue) {
                    node = node.getPredecessors().iterator().next();
                    trace.add(0, node);
                }
            } catch (NullPointerException npe) {
                // jestlize se dostane az pred deklaraci daneho ukazatele, int *p; ... free(p);
            } catch (NoSuchElementException nsee) {
                // jestlize je ukazatel jako vstupni parametr funkce a dostane se az pred prvni uzel CFG
            }
            errorTraces.add(trace);
        } else if (errorType == ErrorType.UNCONSISTENT_JOINING_BRANCHES) {
            for (CFGNode pred : node.getPredecessors()) {
                ErrorTrace trace = new ErrorTrace(this.getDescription());
                trace.add(pred);
                trace.add(node);
                trace.add(node.getSuccessors().iterator().next());
                errorTraces.add(trace);                
            }
        } else {
            ErrorTrace trace = new ErrorTrace(this.getDescription());
            trace.add(node);
            trace.add(node.getSuccessors().iterator().next());
            errorTraces.add(trace);
        }
        return errorTraces;
    }

    public String getDescription() {
        return errorNode.getCFG().getFunctionName() + "(): " + erroneousPointers.toString() + ": " + description;
    }

    public String nodeToText(CFGNode node) {
        String output = "";
        try {
            for (String erroneousPointer : erroneousPointers) {
                output += (nodesWithOwnerships.get(node).get(erroneousPointer) == null)? "" : erroneousPointer + ": " + nodesWithOwnerships.get(node).get(erroneousPointer).getOwnershipValue();
            }
        } catch (NullPointerException npe) {
            // erroneousPointers muze byt prazdne, pokud za chybu nemuze ukazatel
        }
        return output;
    }

    public String errorTraceEdgeToText(ErrorTrace errorTrace, CFGNode from, CFGNode to) {
        return "";
    }
    
    public void addErroneousPointer(String pointerName) {
        erroneousPointers.add(pointerName);
    }
    
}
