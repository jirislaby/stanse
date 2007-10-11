/*
 * OwnershipChecker.java
 *
 * Created on 22. listopad 2006, 18:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.memorychecker;

import cz.muni.fi.iti.scv.xml2cfg.CFGNode;
import cz.muni.fi.iti.scv.xml2cfg.ControlFlowGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.dom4j.Element;

/**
 *
 * @author Michal Fiedler
 */
public class OwnershipChecker extends OldChecker {
    
    private class IntObj {
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
    
    private class PointerOwnership {
        private int ownershipValue;
        private HashSet<String> ownershipSet = new HashSet<String>();
        private IntObj ownershipSetValue;
        
        public PointerOwnership() {
            
        }

        public PointerOwnership(String nameOfPointer, int ownershipValue, IntObj ownershipSetValue) {
            this.ownershipValue = ownershipValue;
            this.ownershipSetValue = ownershipSetValue;
            ownershipSet.add(nameOfPointer);
        }
        
        public PointerOwnership(String nameOfPointer, int ownershipValue, IntObj ownershipSetValue, HashSet<String> ownershipSet) {
            this.ownershipValue = ownershipValue;
            this.ownershipSetValue = ownershipSetValue;
            this.ownershipSet = ownershipSet;
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

    }
    
    private Map<CFGNode, Map<String, PointerOwnership>> nodesWithOwnerships = new HashMap<CFGNode, Map<String, PointerOwnership>>();
    
    /** Creates a new instance of OwnershipChecker */
    public OwnershipChecker() {
    }

    public Set<ErrorNode> check() {
        Set<ErrorNode> errors = new HashSet<ErrorNode>();
        
        for (ControlFlowGraph cfg : cfgs) {
            errors.addAll(check(cfg));
        }
        
        return errors;   
    }

    private Set<ErrorNode> check(ControlFlowGraph cfg) {
        Set<ErrorNode> errors = new HashSet<ErrorNode>();
        errors.addAll(forwardAnalysis(cfg));
        System.out.println("");
        errors.addAll(backwardAnalysis(cfg));
        return errors;
    }
    
    private Set<ErrorNode> forwardAnalysis(ControlFlowGraph cfg) {
        Stack<CFGNode> nodesToDo = new Stack<CFGNode>();
        Set<ErrorNode> errors = new HashSet<ErrorNode>();
        
        nodesWithOwnerships.put(cfg.getStartNode(), new HashMap<String, PointerOwnership>());
        nodesToDo.push(cfg.getStartNode());
        
        // nalezeni ukazatelu mezi vstupnimi parametry funkce
        Element fdElement = cfg.getFunctionDefinition();
        Element fdDeclarator = (Element) fdElement.selectSingleNode("declarator");
        for (Object fdParameter : fdDeclarator.selectNodes("parameter")) {
            Element fdDeclarator2 = (Element) ((Element)fdParameter).selectSingleNode("declarator");
            if (fdDeclarator2.element("pointer") != null) {
                String fdId = fdDeclarator2.selectSingleNode("id").getText();
                nodesWithOwnerships.get(cfg.getStartNode()).put(fdId, new PointerOwnership(fdId, -1, new IntObj(-1)));
            }
        }
        
        while (!nodesToDo.empty()) {
            CFGNode node = nodesToDo.pop();
            
            for (CFGNode succ : node.getSuccessors()) {
                
                Element element = cfg.getEdgeElement(node, succ);
                
                Map<String, PointerOwnership> nodeWithOwnership = new HashMap<String, PointerOwnership>();
                
                for (String id : nodesWithOwnerships.get(node).keySet()) {
                    nodeWithOwnership.put(id, nodesWithOwnerships.get(node).get(id));
                }

                if (!nodesWithOwnerships.containsKey(succ)) {
                    nodesToDo.push(succ);
                }

                if (element == null) {
                    // vyraz nevraci getEdgeElement
                } else if (element.getName().equals("declaration")) {
                    Element initDeclarator = (Element) element.selectSingleNode("initDeclarator");
                    Element declarator = (Element) initDeclarator.selectSingleNode("declarator");
                    // je to ukazatel
                    if (declarator.element("pointer") != null) {
                        String id = declarator.selectSingleNode("id").getText();
                        nodeWithOwnership.put(id, new PointerOwnership(id, 0, new IntObj(0)));
                    }
                } else if (element.getName().equals("assignExpression")) {
                    String id1 = element.node(0).getText();
                    String id2 = element.node(1).getText();
                    PointerOwnership leftPointerOwnership = nodesWithOwnerships.get(node).get(id1);
                    // na leve strane je ukazatel
                    if (leftPointerOwnership != null) {
                        // je-li do ukazatele prirazovano nesmi byt vlastnici
                        if (leftPointerOwnership.getOwnershipValue() == 1) {
                            errors.add(new ErrorNode(succ, ErrorNode.ErrorType.DEFINITIVE));
                            System.err.println("chyba3");
                        } else {
                            // at je ownershipValue -1 nebo 0, nastavi se na 0
                            leftPointerOwnership.setOwnershipValue(0);
                            leftPointerOwnership.getOwnershipSetValue().setValue(0);
                        }
                        PointerOwnership rightPointerOwnership = nodesWithOwnerships.get(node).get(id2);
                        // do ukazatele je prirazeni NULL
                        if (id2.equalsIgnoreCase("null")) {
                            nodeWithOwnership.get(id1).getOwnershipSet().remove(id1);
                            nodeWithOwnership.remove(id1);
                            PointerOwnership newPointerOwnership = new PointerOwnership(id1, 0, new IntObj(0));
                            nodeWithOwnership.put(id1, newPointerOwnership);
                        // jestlize je na prave strane funkce (napr. malloc) a ne ukazatel
                        } else if (rightPointerOwnership == null) {
                            // ukazatel id1 je vlastnici a jeden z ukazatelu ukazujicich na stejnou pamet je vlastnici
                            nodeWithOwnership.remove(id1);
                            PointerOwnership newPointerOwnership = new PointerOwnership(id1, 1, new IntObj(1));
                            nodeWithOwnership.put(id1, newPointerOwnership);
                        } else {// jestlize je na prave strane ukazatel
                            // je potreba zrusit vazby na predchozi uzel
                            HashSet<String> ownershipSet = new HashSet<String>();
                            IntObj ownershipSetValue = new IntObj(nodeWithOwnership.get(id1).getOwnershipSetValue());
                            for (String pointerName : nodeWithOwnership.get(id1).getOwnershipSet()) {
                                int ownershipValue = nodeWithOwnership.get(pointerName).getOwnershipValue();
                                nodeWithOwnership.remove(pointerName);
                                nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, ownershipValue, ownershipSetValue, ownershipSet));
                            }
                            nodeWithOwnership.get(id1).getOwnershipSet().remove(id1);
                            nodeWithOwnership.remove(id1);
                            IntObj rightOwnershipSetValue = new IntObj(nodesWithOwnerships.get(node).get(id2).getOwnershipSetValue());
                            int rightOwnershipValue = nodesWithOwnerships.get(node).get(id2).getOwnershipValue();
                            // jestlize pravy ukazatel je nevlastnici, jsou i oba nasledovnici nevlastnici
                            // je-li pravy ukazatel vlastnici, vlastnictvi nasledovniku je nezname
                            // je-li vlastnictvi praveho nezname, je i vlastnivtvi nasledovniku nezname
                            if (rightOwnershipValue == 0) nodeWithOwnership.put(id1, new PointerOwnership(id1, 0, rightOwnershipSetValue));
                            else nodeWithOwnership.put(id1, new PointerOwnership(id1, -1, rightOwnershipSetValue));
                            for (String pointerName : nodesWithOwnerships.get(node).get(id2).getOwnershipSet()) {
                                nodeWithOwnership.remove(pointerName);
                                if (rightOwnershipValue == 0) nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, 0, rightOwnershipSetValue, nodeWithOwnership.get(id1).getOwnershipSet()));
                                else nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, -1, rightOwnershipSetValue, nodeWithOwnership.get(id1).getOwnershipSet()));
                            }
                        }
                    }
                } else if (element.getName().equals("functionCall")) {
                    String funkce = element.node(0).getText();
                    String id = element.node(1).getText();
                    if (funkce.equalsIgnoreCase("free")) {
                        HashSet<String> ownershipSet = nodesWithOwnerships.get(node).get(id).getOwnershipSet();
                        // vlastnictvi vsech ukazatelu ukazujicich na uvolnovanou pamet se nastavi na 0
                        for (String pointerName : nodesWithOwnerships.get(node).get(id).getOwnershipSet()) {
                            // a=b
                            // free(a)
                            // a=malloc
                            // b=?
                            nodeWithOwnership.remove(pointerName);
                            nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, 0, new IntObj(0), ownershipSet));
                        }
                    }
                } else if (element.getName().equals("returnStatement")) {
                    // !!! dopsat - osetrit i ostatni ukazatele ukazujici na stejnou pamet
                    try {
                        String id = element.selectSingleNode("id").getText();
                        nodeWithOwnership.remove(id);
                        nodeWithOwnership.put(id, new PointerOwnership(id, 0, new IntObj(0)));
                    } catch (NullPointerException npe) {
                        // return nemusi mit parametr
                    }
                } else if (element.getName().equals("binaryExpression")) {
                    String id = element.node(0).getText();
                    String podminka = element.node(1).getText();
                    // ve vetvi, ktera bude dale povazovana za potencialne nespravnou se vytvori uplne nove prostredi s hodnotami
                    // predchudce, aby se jednotlive vetve neovlivnovali, prozatim je spravna false vetev
                    // vyjimku tvori podminka (ukazatel==null) a (ukazatel!=null), tam se vytvari nove prostredi ve vetvi (ukazatel==null)
                    // (A && !B) || (B && ((C && A) || (D && !A)))
                    //if ((cfg.getEdgeConditionType(node, succ)) /*&& (!podminka.equalsIgnoreCase("null"))*/) {
                    if (((cfg.getEdgeConditionType(node, succ)) && (!podminka.equalsIgnoreCase("null"))) 
                        || ((podminka.equalsIgnoreCase("null")) 
                        && (element.attribute("op").getText().equalsIgnoreCase("==") && cfg.getEdgeConditionType(node, succ))
                        || (element.attribute("op").getText().equalsIgnoreCase("!=") && !cfg.getEdgeConditionType(node, succ)))) {
                        // jestlize seznam uzlu ke zpracovani uz obsahuje sourozence s jsme ve vetvi, kde se vytvari nove prostredi,
                        // musi se tato vetev zpracovavat az po tomto sourozenci
                        if ((nodesToDo.size() > 1)) {
                            // tento uzel uz byl prirazen nazacatku, tak se musi vyhodit
                            nodesToDo.pop();
                            // overuje se, jestli je ke uz zpracovani zarazen sourozenec,
                            // pokud ano, zaradi se tento na 2. misto a sourozenec zustane 1.
                            if (node.getSuccessors().contains(nodesToDo.firstElement())) {
                                CFGNode tempNode = nodesToDo.pop();
                                nodesToDo.push(succ);
                                nodesToDo.push(tempNode);
                            } else {
                                nodesToDo.push(succ);
                            }                                
                        }
                        // vytvoreni noveho prostredi se starymi hodnotami
                        nodeWithOwnership.clear();
                        for (String pointerName : nodesWithOwnerships.get(node).keySet()) {
                            if (!nodeWithOwnership.containsKey(pointerName)) {
                                int ownershipValue = nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue();
                                IntObj ownershipSetValue = nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue();
                                nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, ownershipValue, ownershipSetValue));
                                for (String pointerName2 : nodesWithOwnerships.get(node).get(pointerName).getOwnershipSet()) {
                                    if (!pointerName.equalsIgnoreCase(pointerName2)) {
                                        nodeWithOwnership.put(pointerName2, new PointerOwnership(pointerName2, ownershipValue, ownershipSetValue, nodeWithOwnership.get(pointerName).getOwnershipSet()));
                                    }
                                }
                            }
                        }
                    }
                    // proti null by se mel dat overovat jen ukazatel, takze id je ukazatel
                    if (podminka.equalsIgnoreCase("null")) {
                        // kdyz ukazatel==NULL jsme v true vetvi, nebo ukazatel!=NULL a jsme ve false vetvi
                        if ((element.attribute("op").getText().equalsIgnoreCase("==") && cfg.getEdgeConditionType(node, succ))
                        || (element.attribute("op").getText().equalsIgnoreCase("!=") && !cfg.getEdgeConditionType(node, succ))) {
                            nodeWithOwnership.get(id).getOwnershipSet().remove(id);
                            nodeWithOwnership.remove(id);
                            PointerOwnership newPointerOwnership = new PointerOwnership(id, 0, new IntObj(0));
                            nodeWithOwnership.put(id, newPointerOwnership);
                        // kdyz ukazatel==NULL jsme ve false vetvi, nebo ukazatel!=NULL a jsme v true vetvi
                        // jiny operator nez == a != by nemel byt mozny a 2. podminka nemuze byt jina nez true a false
                        } else {
                            // v tomto pripade se nedela nic
                        }
                    }
                }
                    
                if (!nodesWithOwnerships.containsKey(succ)) {
                    nodesWithOwnerships.put(succ, new HashMap<String, PointerOwnership>());
                    System.out.println(element.getName());
                    System.out.println(node.getNumber()+" -> "+succ.getNumber());
                    System.out.println("-----");
                    for (String id : nodeWithOwnership.keySet()) {
                        nodesWithOwnerships.get(succ).put(id, nodeWithOwnership.get(id));
                        System.out.println(id);
                        System.out.println(nodeWithOwnership.get(id).getOwnershipValue());
                        System.out.println(nodeWithOwnership.get(id).getOwnershipSet().toString());
                        System.out.println(nodeWithOwnership.get(id).getOwnershipSetValue().getValue());
                    }
                } else {
                    // existuje-li uz informace o nasledovnikovi, museji byt informace shodne s informacemi z teto vetve
                    // jinak je to chyba
                    // ---
                    // v obou vetvich musi byt stejne ukazatele
                    if (!nodeWithOwnership.keySet().equals(nodesWithOwnerships.get(node).keySet())) {
                        errors.add(new ErrorNode(succ, ErrorNode.ErrorType.DEFINITIVE));
                        System.err.println("chyba8");
                    } else {
                        // informace o ukazatelich musi byt stejne, jinak nekonsistence
                        for (String pointerName : nodeWithOwnership.keySet()) {
                            if (nodesWithOwnerships.get(succ).get(pointerName).getOwnershipValue() != nodeWithOwnership.get(pointerName).getOwnershipValue()) {
                                errors.add(new ErrorNode(succ, ErrorNode.ErrorType.DEFINITIVE));
                                System.err.println("chyba9");
                            }
                            if (!nodesWithOwnerships.get(succ).get(pointerName).getOwnershipSet().equals(nodeWithOwnership.get(pointerName).getOwnershipSet())) {
                                errors.add(new ErrorNode(succ, ErrorNode.ErrorType.DEFINITIVE));
                                System.err.println("chyba10");
                            }
                            if (nodesWithOwnerships.get(succ).get(pointerName).getOwnershipSetValue().getValue() != nodeWithOwnership.get(pointerName).getOwnershipSetValue().getValue()) {
                                errors.add(new ErrorNode(succ, ErrorNode.ErrorType.DEFINITIVE));
                                System.err.println("chyba11");
                            }
                            /*System.err.println(node.getNumber()+" -> "+succ.getNumber());
                            System.err.println(pointerName);
                            System.err.println(nodesWithOwnerships.get(succ).get(pointerName).getOwnershipValue());
                            System.err.println(nodesWithOwnerships.get(succ).get(pointerName).getOwnershipSet().toString());
                            System.err.println(nodesWithOwnerships.get(succ).get(pointerName).getOwnershipSetValue().getValue());
                            System.err.println(pointerName);
                            System.err.println(nodeWithOwnership.get(pointerName).getOwnershipValue());
                            System.err.println(nodeWithOwnership.get(pointerName).getOwnershipSet().toString());
                            System.err.println(nodeWithOwnership.get(pointerName).getOwnershipSetValue().getValue());*/
                        }
                    }
                }

            }
        }
        return errors;
    }

    private Set<ErrorNode> backwardAnalysis(ControlFlowGraph cfg) {
        Stack<CFGNode> nodesToDo = new Stack<CFGNode>();
        Stack<CFGNode> nodesDone = new Stack<CFGNode>();
        Set<ErrorNode> errors = new HashSet<ErrorNode>();
        
        nodesToDo.push(cfg.getEndNode());
        
        while (!nodesToDo.empty()) {
            CFGNode node = nodesToDo.pop();
            nodesDone.push(node);
            
            for (CFGNode pred : node.getPredecessors()) {

                Element element = cfg.getEdgeElement(pred, node);

                if (!nodesDone.contains(pred)) {
                    // aby se nestalo ze se zpracuje (horni) spojeni vetvi drive nez je zpracovana pravdiva vetev,
                    // ceka se na vsechny vetve
                    if (pred.getSuccessors().size() > 1) {
                        if (nodesDone.containsAll(pred.getSuccessors())) {
                            nodesToDo.push(pred);
                        }
                    } else {
                        nodesToDo.push(pred);
                    }
                }
                
                System.out.println(element.getName());
                System.out.println(pred.getNumber()+" <- "+node.getNumber());
                System.out.println("-----");
                for (String id : nodesWithOwnerships.get(node).keySet()) {
                    nodesWithOwnerships.get(node).put(id, nodesWithOwnerships.get(node).get(id));
                    System.out.println(id);
                    System.out.println(nodesWithOwnerships.get(node).get(id).getOwnershipValue());
                    System.out.println(nodesWithOwnerships.get(node).get(id).getOwnershipSet().toString());
                    System.out.println(nodesWithOwnerships.get(node).get(id).getOwnershipSetValue().getValue());
                }

                // jestlize uzel ma sourozence, musi kazdy obsahovat to same, jinak chyba
                if (pred.getSuccessors().size() > 1) {
                    // pravdivost teto podminky asi plyne uz z predchozi
                    //if (element.getName().equals("binaryExpression")) {
                        String id = element.node(0).getText();
                    //}
                    // jestlize uzel obsahuje jine hodnoty nez predchudce, tak se napojuje nekonsitentni vetev
                    if (!nodesWithOwnerships.get(node).keySet().equals(nodesWithOwnerships.get(pred).keySet())) {
                        errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                        System.err.println("chyba12");
                    } else {
                        // informace o ukazatelich musi byt stejne, jinak nekonsistence
                        for (String pointerName : nodesWithOwnerships.get(node).keySet()) {
                            // jedna-li se o podminku (ukazatel==null), tak ukazatel nemusi byt stejny v obou vetvich
                            if (!pointerName.equalsIgnoreCase(id)) {
                                if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() != nodesWithOwnerships.get(pred).get(pointerName).getOwnershipValue()) {
                                    errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                                    System.err.println("chyba13");
                                }
                                if (!nodesWithOwnerships.get(node).get(pointerName).getOwnershipSet().equals(nodesWithOwnerships.get(pred).get(pointerName).getOwnershipSet())) {
                                    errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                                    System.err.println("chyba14");
                                }
                                if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue().getValue() != nodesWithOwnerships.get(pred).get(pointerName).getOwnershipSetValue().getValue()) {
                                    errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                                    System.err.println("chyba15");
                                }
                            }
                            /*System.err.println(pred.getNumber()+" <- "+node.getNumber());
                            System.err.println(pointerName);
                            System.err.println(nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue());
                            System.err.println(nodesWithOwnerships.get(node).get(pointerName).getOwnershipSet().toString());
                            System.err.println(nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue());
                            System.err.println(pointerName);
                            System.err.println(nodesWithOwnerships.get(pred).get(pointerName).getOwnershipValue());
                            System.err.println(nodesWithOwnerships.get(pred).get(pointerName).getOwnershipSet().toString());
                            System.err.println(nodesWithOwnerships.get(pred).get(pointerName).getOwnershipSetValue());*/
                        }
                    }
                }

                if (element == null) {
                    // vyraz nevraci getEdgeElement
                } else if (element.getName().equals("returnStatement")) {
                    String id;
                    try {
                        id = element.selectSingleNode("id").getText();
                    } catch (NullPointerException npe) {
                        // return nemusi mit parametr
                        id = "";
                    }
                    for (String pointerName : nodesWithOwnerships.get(pred).keySet()) {
                        // kdyz mnozina ownershipSet obsahuje ukazatel id, je ukazatel na pamet predan pres return
                        // neobsahuje-li mnozina ownershipSet ukazatel id, potom ownershipSetValue nesmi byt 1
                        if (!nodesWithOwnerships.get(pred).get(pointerName).getOwnershipSet().contains(id) && (nodesWithOwnerships.get(pred).get(pointerName).getOwnershipSetValue().getValue() == 1)) {
                            errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                            System.err.println("chyba6");
                        }
                    }
                } else if (element.getName().equals("functionCall")) {
                    String funkce = element.node(0).getText();
                    String id = element.node(1).getText();
                    if (funkce.equalsIgnoreCase("free")) {
                        // zjisteni, zda zadny jiny krome uvolnovaneho neni vlastnici
                        boolean allNotOwning = true;
                        for (String pointerName : nodesWithOwnerships.get(pred).get(id).getOwnershipSet()) {
                            if (!(id.equalsIgnoreCase(pointerName)) && (nodesWithOwnerships.get(pred).get(pointerName).getOwnershipValue() == 1)) {
                                allNotOwning = false;
                            }
                        }
                        // kdyz je uvolnovan ukazatel, jehoz
                        //    vlastnictvi je 1, tak je to v poradku
                        //    vlastnictvi je -1, zmeni se na 1 a propaguje se nahoru
                        //    vlastnictvi je 0, je to chyba, bud byl uz uvolnen, nebo neukazuje na zadnou pamet
                        if (nodesWithOwnerships.get(pred).get(id).getOwnershipValue() == 0) {
                            errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                            System.err.println("chyba7");
                        } else if (nodesWithOwnerships.get(pred).get(id).getOwnershipValue() == -1) {
                            // jestlize vsechny ukazatele ukazujici na stejnou pamet jako id jsou nevlastnici
                            // a jeden z ukazatelu ma byt vlastnici, priradi se id vlastnictvi
                            if (allNotOwning && (nodesWithOwnerships.get(pred).get(id).getOwnershipSetValue().getValue() != 0)) {
                                for (String pointerName : nodesWithOwnerships.get(pred).get(id).getOwnershipSet()) {
                                    nodesWithOwnerships.get(pred).get(pointerName).setOwnershipValue(0);
                                }
                                nodesWithOwnerships.get(pred).get(id).setOwnershipValue(1);
                                // at je ownershipSetValue 1 nebo -1, nastavi se na 1
                                nodesWithOwnerships.get(pred).get(id).getOwnershipSetValue().setValue(1);
                            } else {
                                errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                                System.err.println("chyba16");
                            }
                        } else if (nodesWithOwnerships.get(pred).get(id).getOwnershipValue() == 1) {
                            if (!allNotOwning || (nodesWithOwnerships.get(pred).get(id).getOwnershipSetValue().getValue() == 0)) {
                                errors.add(new ErrorNode(node, ErrorNode.ErrorType.DEFINITIVE));
                                System.err.println("chyba17");                                
                            }
                            nodesWithOwnerships.get(pred).get(id).getOwnershipSetValue().setValue(1);
                        }
                    }
                } else if (element.getName().equals("assignExpression")) {
                    String id1 = element.node(0).getText();
                    String id2 = element.node(1).getText();
                    PointerOwnership leftPointerOwnership = nodesWithOwnerships.get(pred).get(id1);
                    PointerOwnership rightPointerOwnership = nodesWithOwnerships.get(pred).get(id2);
                    // na obou stranach prirazeni je ukazatel
                    if ((nodesWithOwnerships.get(pred).get(id1) != null) && (nodesWithOwnerships.get(pred).get(id2) != null)) {
                        // jestlize ukazatel na prave strane vstupuje do prirazeni s hodnotou ownershipValue = -1
                        // a tuto hodnotu se podarilo zjistit, tak se nova hodnota propaguje nahoru
                        // jestlize je vlastnictvi nejakeho ukazatele z dolni mnoziny 1, potom horni vlastnictvi praveho ukazatele je 1
                        // a ostatnich z horni prave mnoziny je 0
                        // je-li vlastnictvi vsech z dolni mnoziny 0, je i 0 u vsech z horni mnoziny
                        // je-li vlastnictvi vsech z dolni mnoziny -1, je i -1 u vsech z horni mnoziny - to se nastavovat nemusi
                        if ((nodesWithOwnerships.get(pred).get(id2).getOwnershipValue() == -1) && (nodesWithOwnerships.get(node).get(id2).getOwnershipValue() != -1)) {
                            // vsechny ukazatele z horni mnoziny se nastavi na 0
                            for (String pointerName : nodesWithOwnerships.get(pred).get(id2).getOwnershipSet()) {
                                nodesWithOwnerships.get(pred).get(pointerName).setOwnershipValue(0);
                            }
                            // vlastnictvi cele mnoziny se nastavi podle spodni
                            nodesWithOwnerships.get(pred).get(id2).getOwnershipSetValue().setValue(nodesWithOwnerships.get(node).get(id2).getOwnershipSetValue().getValue());
                            // je-li ve spodni vlastnici ukazatel, nastavi se pravi ukazatel jako vlastnici 
                            for (String pointerName : nodesWithOwnerships.get(node).get(id2).getOwnershipSet()) {
                                if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() == 1) nodesWithOwnerships.get(pred).get(id2).setOwnershipValue(1);
                            }
                        }
                    }
                }
            }
        }
        return errors;
    }

}
