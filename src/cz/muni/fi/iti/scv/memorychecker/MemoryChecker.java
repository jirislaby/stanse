package cz.muni.fi.iti.scv.memorychecker;

import cz.muni.fi.iti.scv.xml2cfg.CFGNode;
import cz.muni.fi.iti.scv.xml2cfg.ControlFlowGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.dom4j.Element;


public class MemoryChecker extends OldChecker {
    
    public enum State {unknown, is_null, not_null, freed};
    
    // pro kazdy uzel CFGNode uchovava vsechny mozne stavy pointeru
    private Map<CFGNode, Map<String, Set<State>>> nodesWithPointers = new HashMap<CFGNode, Map<String, Set<State>>>();
    
    public MemoryChecker() {
    }

    public Set<ErrorNode> check() {
        Set<ErrorNode> errors = new HashSet<ErrorNode>();
        
        for (ControlFlowGraph cfg : cfgs) {
            errors.addAll(check(cfg));
        }
        
        return errors;   
    }

    //private Set<ErrorNode> check(ControlFlowGraph cfg, Set startState) {
    private Set<ErrorNode> check(ControlFlowGraph cfg) {
        Stack<CFGNode> nodesToDo = new Stack<CFGNode>();
        Set<ErrorNode> errors = new HashSet<ErrorNode>();
        
        int size;
        
        nodesWithPointers.put(cfg.getStartNode(), new HashMap<String, Set<State>>());
        nodesToDo.push(cfg.getStartNode());
        
        while (!nodesToDo.empty()) {
            CFGNode node = nodesToDo.pop();
            
            for (CFGNode succ : node.getSuccessors()) {                
                boolean toDo = false;
                
                // pridani nasledovnika do nodesWithPointers
                if (!nodesWithPointers.containsKey(succ)) {
                    // okopirovani ukazetelu a jejich stavu z node do succ
                    nodesWithPointers.put(succ, new HashMap<String, Set<State>>());
                    for (String id : nodesWithPointers.get(node).keySet()) {
                        Set<State> pointerSet = new HashSet<State>(nodesWithPointers.get(node).get(id));
                        nodesWithPointers.get(succ).put(id, pointerSet);
                    }
                    //Map<String, Set<State>> pointersMap = new HashMap<String, Set<State>>(nodesWithPointers.get(node));
                    //nodesWithPointers.put(succ, pointersMap);
                    toDo = true;
                } else {
                    // pri opakovanem pruchodu uzlu slouceni ukazatelu a stavu
                    for (String id : nodesWithPointers.get(node).keySet()) {
                        size = nodesWithPointers.get(succ).get(id).size();
                        for (State stav : nodesWithPointers.get(node).get(id)) {
                            nodesWithPointers.get(succ).get(id).add(stav);
                        }
                        if (size != nodesWithPointers.get(succ).get(id).size()) toDo = true;
                    }
                    /*// nahrazeni stavu pointeru z dalsiho predchudce
                    for (String id : nodesWithPointers.get(node).keySet()) {
                        Set<State> pointerSetOld = new HashSet(nodesWithPointers.get(succ).get(id));
                        Set<State> pointerSet = new HashSet(nodesWithPointers.get(node).get(id));
                        nodesWithPointers.get(succ).put(id, pointerSet);
                        if (!pointerSetOld.equals(pointerSet)) toDo = true;
                    }*/
                }
                
                Element element = cfg.getEdgeElement(node, succ);
                
                if (element == null) {/* vyraz nevraci getEdgeElement */}
                
                // kopirovani jednoho ukazatele na druhy
                else if (element.getName().equals("assignExpression")) {
                    String id1 = element.node(0).getText();
                    String id2 = element.node(1).getText();
                    // prirazuji se pointery
                    if ((nodesWithPointers.get(succ).get(id1) != null) && (nodesWithPointers.get(succ).get(id2) != null)) {
                        Set<State> pointerSetID1 = new HashSet(nodesWithPointers.get(succ).get(id1));
                        //Set<State> pointerSet = new HashSet(nodesWithPointers.get(succ).get(id2));
                        //nodesWithPointers.get(succ).remove(id1);
                        // napoji ukazatel mnoziny stavu pointeru na mnozinu prirazovaneho pointeru (mnozina by pak mela byt spolecna)
                        nodesWithPointers.get(succ).put(id1, nodesWithPointers.get(succ).get(id2));
                        //nodesWithPointers.get(succ).put(id1, pointerSet);
                        // jestlize se mnozina stavu zmenila, zpracuj potomky uzlu
                        //if (!pointerSetID1.equals(pointerSet)) toDo = true;
                        if (!pointerSetID1.equals(nodesWithPointers.get(succ).get(id2))) toDo = true;
                    }
                }
                
                // jestlize se deklaruje novy ukazatel
                else if (element.getName().equals("declaration")) {
                    Element initDeclarator = (Element) element.selectSingleNode("initDeclarator");
                    Element declarator = (Element) initDeclarator.selectSingleNode("declarator");
                    if (declarator.element("pointer") != null) {
                        String id = declarator.selectSingleNode("id").getText();
                        // danemu ukazateli se priradi mnozina jeho stavu v danem uzlu
                        size = nodesWithPointers.get(succ).size();
                        Set<State> pointerSet = new HashSet<State>();
                        nodesWithPointers.get(succ).put(id, pointerSet);
                        nodesWithPointers.get(succ).get(id).add(State.unknown);
                        if (size != nodesWithPointers.get(succ).size()) toDo = true;
                    }
                }
                
                // jestlize se vyskytuje prikaz if (kvuli overeni pointeru na null a !null)
                //else if (element.getName().equals("ifStatement")) {
                else if (element.getName().equals("binaryExpression")) {
                    //Element binaryExpression = (Element) element.selectSingleNode("binaryExpression");
                    //String id = binaryExpression.node(0).getText();
                    //String podminka = binaryExpression.node(1).getText();
                    String id = element.node(0).getText();
                    String podminka = element.node(1).getText();
                    // overeni, ze se zdarilo pridelit pamet, jinak ukazatel obsahuje null
                    if (podminka.equalsIgnoreCase("null")) {
                        //if (binaryExpression.attribute("op").getText().equalsIgnoreCase("==")) {
                        nodesWithPointers.get(succ).get(id).remove(State.unknown);
                        size = nodesWithPointers.get(succ).get(id).size();
                        // pointer == null
                        if (element.attribute("op").getText().equalsIgnoreCase("==")) {
                            // true vetev
                            if (cfg.getEdgeConditionType(node, succ)) {
                                nodesWithPointers.get(succ).get(id).remove(State.not_null);
                                nodesWithPointers.get(succ).get(id).add(State.is_null);
                            // false vetev
                            } else {
                                nodesWithPointers.get(succ).get(id).remove(State.is_null);
                                nodesWithPointers.get(succ).get(id).add(State.not_null);
                            }
                        //} else if (binaryExpression.attribute("op").getText().equalsIgnoreCase("!=")) {
                        // pointer != null
                        } else if (element.attribute("op").getText().equalsIgnoreCase("!=")) {
                            // true vetev
                            if (cfg.getEdgeConditionType(node, succ)) {
                                nodesWithPointers.get(succ).get(id).remove(State.is_null);
                                nodesWithPointers.get(succ).get(id).add(State.not_null);
                            // false vetev
                            } else {
                                nodesWithPointers.get(succ).get(id).remove(State.not_null);
                                nodesWithPointers.get(succ).get(id).add(State.is_null);
                            }
                        }
                        if (size != nodesWithPointers.get(succ).get(id).size()) toDo = true;
                    }
                }
                
                // hleda se funkce free
                else if (element.getName().equals("functionCall")) {
                    String funkce = element.node(0).getText();
                    String id = element.node(1).getText();
                    if (funkce.equalsIgnoreCase("free")) {
                        // je-li uvolnovana pamet a pointer je take v jinem stavu nez not_null, potom je to chyba
                        if (nodesWithPointers.get(succ).get(id).contains(State.is_null)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.IS_NULL_FREED));
                        if (nodesWithPointers.get(succ).get(id).contains(State.unknown)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_FREED));
                        if (nodesWithPointers.get(succ).get(id).contains(State.freed)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.DOUBLE_FREED));
                        nodesWithPointers.get(succ).get(id).remove(State.is_null);
                        nodesWithPointers.get(succ).get(id).remove(State.not_null);
                        nodesWithPointers.get(succ).get(id).remove(State.unknown);
                        size = nodesWithPointers.get(succ).get(id).size();
                        nodesWithPointers.get(succ).get(id).add(State.freed);
                        if (size != nodesWithPointers.get(succ).get(id).size()) toDo = true;
                    }
                }
                
                else if (element.getName().equals("returnStatement")) {
                    Element id = (Element) element.selectSingleNode("id");
                    // jelikoz vsechny returny vedou do stejneho uzlu, maji spolecneho nasledovnika,
                    // proto se hodnoty overuji v node misto succ
                    for (String key : nodesWithPointers.get(node).keySet()) {
                    //for (String key : nodesWithPointers.get(succ).keySet()) {
                        //if (nodesWithPointers.get(succ).get(key).contains(State.is_null)) System.err.println(key+" is_null");
                        //if (nodesWithPointers.get(succ).get(key).contains(State.not_null)) System.err.println(key+" not_null");
                        //if (nodesWithPointers.get(succ).get(key).contains(State.unknown)) System.err.println(key+" unknown");
                        //if (nodesWithPointers.get(succ).get(key).contains(State.freed)) System.err.println(key+" freed");
                        //if (nodesWithPointers.get(node).get(key).contains(State.is_null)) System.err.println(key+" is_null");
                        //if (nodesWithPointers.get(node).get(key).contains(State.not_null)) System.err.println(key+" not_null");
                        //if (nodesWithPointers.get(node).get(key).contains(State.unknown)) System.err.println(key+" unknown");
                        //if (nodesWithPointers.get(node).get(key).contains(State.freed)) System.err.println(key+" freed");
                        // return nema parametr
                        if (id == null) {
                            // povolene stavy pointeru jsou freed a is_null
                            //if (nodesWithPointers.get(succ).get(key).contains(State.not_null)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.NOT_NULL_RETURN));
                            //if (nodesWithPointers.get(succ).get(key).contains(State.unknown)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_RETURN));
                            if (nodesWithPointers.get(node).get(key).contains(State.not_null)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.NOT_NULL_RETURN));
                            //if (nodesWithPointers.get(node).get(key).contains(State.unknown)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_RETURN));
                        } else { // return ma parametr
                            // parametr return neni pointer
                            if (nodesWithPointers.get(succ).get(id.getText()) == null) {
                                //if (nodesWithPointers.get(succ).get(key).contains(State.not_null)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.NOT_NULL_RETURN));
                                //if (nodesWithPointers.get(succ).get(key).contains(State.unknown)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_RETURN));
                                if (nodesWithPointers.get(node).get(key).contains(State.not_null)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.NOT_NULL_RETURN));
                                //if (nodesWithPointers.get(node).get(key).contains(State.unknown)) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_RETURN));
                            } else {
                                // parametr return je pointer (nesmi byt freed, vsechny ostatni musi byt pouze freed nebo is_null)
                                //if ((key.equalsIgnoreCase(id.getText())) && (nodesWithPointers.get(succ).get(key).contains(State.freed))) errors.add(new ErrorNode(node,ErrorNode.ErrorType.FREED_RETURN));
                                //if ((!key.equalsIgnoreCase(id.getText())) && (nodesWithPointers.get(succ).get(key).contains(State.not_null))) errors.add(new ErrorNode(node,ErrorNode.ErrorType.NOT_NULL_RETURN));
                                //if ((!key.equalsIgnoreCase(id.getText())) && (nodesWithPointers.get(succ).get(key).contains(State.unknown))) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_RETURN));
                                if ((key.equalsIgnoreCase(id.getText())) && (nodesWithPointers.get(node).get(key).contains(State.freed))) errors.add(new ErrorNode(node,ErrorNode.ErrorType.FREED_RETURN));
                                if ((!key.equalsIgnoreCase(id.getText())) && (nodesWithPointers.get(node).get(key).contains(State.not_null))) errors.add(new ErrorNode(node,ErrorNode.ErrorType.NOT_NULL_RETURN));
                                //if ((!key.equalsIgnoreCase(id.getText())) && (nodesWithPointers.get(node).get(key).contains(State.unknown))) errors.add(new ErrorNode(node,ErrorNode.ErrorType.UNKNOWN_RETURN));
                            }                            
                        }
                    }                    
                }
                
                if (toDo) {nodesToDo.push(succ);}
            }
        }

        //Set<ErrorNode> errors = new HashSet<ErrorNode>();
        
        /*for (CFGNode node : errNodes) {
            errors.add(new ErrorNode(node,ErrorNode.ErrorType.DEFINITIVE));
        }
        
        for (CFGNode node : possibleErrNodes) {
            errors.add(new ErrorNode(node,ErrorNode.ErrorType.POSSIBLE));
        }*/
        
        return errors;
    }
    
}
