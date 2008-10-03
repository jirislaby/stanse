/*
 * OwnershipChecker.java
 *
 * Created on 22. listopad 2006, 18:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.ownershipchecker;

import cz.muni.fi.iti.scv.callgraph.CallGraph;
import cz.muni.fi.iti.scv.checker.Checker;
import cz.muni.fi.iti.scv.checker.CheckerError;
import cz.muni.fi.iti.scv.xml2cfg.CFGNode;
import cz.muni.fi.iti.scv.xml2cfg.ControlFlowGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import org.dom4j.Element;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 * Provádí kontrolu paměťových chyb metodou určování vlastnictví ukazatelů.
 * @author Michal Fiedler
 */
public class OwnershipChecker implements Checker {
    
    private Vector<ControlFlowGraph> cfgs = new Vector<ControlFlowGraph>();
    
    private Map<CFGNode, Map<String, PointerOwnership>> nodesWithOwnerships;

    private Vector<String> globalPointers = new Vector<String>();
    
    private HashMap<String, FunctionOwnershipInfo> functions = new HashMap<String, FunctionOwnershipInfo>();
    
    private String functionName;
    
    private FunctionOwnershipInfo ownershipInfo;
    
    Vector<ControlFlowGraph> topologicalSortedCG;
    
    // obsahuje vzdy posledni uzel na spravne vetvi pred sloucenim dvou vetvi
    private HashSet<CFGNode> nodeOnCorrectTrace;
    
    private final boolean INTRA = false;
    private final boolean INTER = true;
    
    /** Creates a new instance of OwnershipChecker */
    public OwnershipChecker() {
    }
    
    public void addCFG(ControlFlowGraph cfg) {
        cfgs.add(cfg);
    }

    public void addAllCFG(Set<ControlFlowGraph> cfgs) {
        this.cfgs.addAll(cfgs);
    }

    public void removeCFG(ControlFlowGraph cfg) {
        cfgs.remove(cfg);
    }

    public void removeAllCFG(Set<ControlFlowGraph> cfgs) {
        this.cfgs.removeAll(cfgs);
    }

    public void clearCFG() {
        cfgs.clear();
    }

    public Set<CheckerError> check() {
        //nacteni globalnich ukazatelu
        Element rootElement = cfgs.elementAt(0).getFunctionDefinition().getDocument().getRootElement();
        for (Object declaration : rootElement.selectNodes("declaration")) {
            // zajimaji nas jen ukazatele na zakladni typy, nepotrebujeme ukazatele na structury apod.
            if ((((Element)declaration).selectSingleNode("declarationSpecifiers").selectSingleNode("typeSpecifier").selectSingleNode("baseType") != null) && (((Element)((Element)declaration).selectSingleNode("declarationSpecifiers")).attribute("storageClass") == null)) {
                Element initDeclarator = (Element) ((Element)declaration).selectSingleNode("initDeclarator");
                if (initDeclarator != null) {
                    Element declarator = (Element) ((Element)initDeclarator).selectSingleNode("declarator");
                    // chceme pouze ukazatele, a to takove, ze to nejsou ukazatele na funkci
                    if ((declarator.element("pointer") != null) && (declarator.selectSingleNode("parameter") == null)) {
                        String id = declarator.selectSingleNode("id").getText();
                        globalPointers.add(id);
                    }
                }
            }
        }
        
        Set<CheckerError> errors = new HashSet<CheckerError>();
        
        topologicalSortedCG = topologicalSortedCallGraph();
        
        // intraproceduralni analyza
        for (ControlFlowGraph cfg : topologicalSortedCG) {
            functionName = cfg.getFunctionName();
            Element fdElement = cfg.getFunctionDefinition();
            Element fdDeclarator = (Element) fdElement.selectSingleNode("declarator");
            List fdParameters = fdDeclarator.selectNodes("parameter");
            FunctionOwnershipInfo ownershipInfo = new FunctionOwnershipInfo();
            for (Object fdParameter : fdParameters) {
                Element fdDeclarator2 = (Element) ((Element)fdParameter).selectSingleNode("declarator");
                // v pripade, ze je napr. void funkce(void), tak parametr nema deklarator
                if (fdDeclarator2 != null) {
                    Element fdDeclarator3 = (Element) ((Element)fdDeclarator2).selectSingleNode("declarator");
                    if (fdDeclarator3 != null) {
                        ownershipInfo.addParamName(fdDeclarator3.selectSingleNode("id").getText());
                    } else {
                        ownershipInfo.addParamName(fdDeclarator2.selectSingleNode("id").getText());
                    }
                    ownershipInfo.addInputPointerOwnershipValue(null);                }
            }
            for (String globalPointer : globalPointers) {
                ownershipInfo.addParamName(globalPointer);
                ownershipInfo.addInputPointerOwnershipValue(null);
            }
            functions.put(functionName, ownershipInfo);
            // spousteni testu pro jednotlive CFG
            //errors.addAll(check(cfg, INTRA));
            check(cfg, INTRA);
            ownershipInfo.setChanged(true);
        }
        
        // interproceduralni analyza
        Vector<String> functionsToAnalyze = new Vector<String>();
        for (String fun : functions.keySet()) {
            HashSet<String> calledFunctions = functions.get(fun).getCalledFunctions();
            boolean noChange = true;
            Iterator<String> it = calledFunctions.iterator();
            // pokud se nektera z volanych funkci zmenila, muse se funkce znovu analyzovat
            while ((noChange) && (it.hasNext())) {
                try {
                    if (functions.get((String)it.next()).isChanged()) {
                        noChange = false;
                    }
                } catch (NullPointerException npe) {
                    // neni zaznam o funkcich, u kterych neni znam kod, napr. free, malloc, atd.
                }
            }
            if (!noChange) functionsToAnalyze.add(fun);
        }
        
        if (functionsToAnalyze.size() != 0) {
            // nastaveni vsech funkci do stavu - nebyly zmeneny
            for (String fun : functions.keySet()) {
                functions.get(fun).setChanged(false);
            }

            for (ControlFlowGraph cfg : topologicalSortedCG) {
                functionName = cfg.getFunctionName();
                if (functionsToAnalyze.contains(functionName)) {
                    functionName = cfg.getFunctionName();
                    Element fdElement = cfg.getFunctionDefinition();
                    Element fdDeclarator = (Element) fdElement.selectSingleNode("declarator");
                    List fdParameters = fdDeclarator.selectNodes("parameter");
                    FunctionOwnershipInfo ownershipInfo = new FunctionOwnershipInfo();
                    for (Object fdParameter : fdParameters) {
                        Element fdDeclarator2 = (Element) ((Element)fdParameter).selectSingleNode("declarator");
                        // v pripade, ze je napr. void funkce(void), tak parametr nema deklarator
                        if (fdDeclarator2 != null) {
                            ownershipInfo.addParamName(fdDeclarator2.selectSingleNode("id").getText());
                            ownershipInfo.addInputPointerOwnershipValue(null);
                        }                        
                    }
                    for (String globalPointer : globalPointers) {
                        ownershipInfo.addParamName(globalPointer);
                        ownershipInfo.addInputPointerOwnershipValue(null);
                    }
                    FunctionOwnershipInfo oldOwnershipInfo = functions.get(functionName);
                    functions.put(functionName, ownershipInfo);
                    // spousteni testu pro jednotlive CFG
                    //errors.addAll(check(cfg, INTER));
                    check(cfg, INTER);
                    // doslo-li k zmene nektereho z omezeni, ulozi se informace o zmene
                    if (!oldOwnershipInfo.equals(ownershipInfo)) ownershipInfo.setChanged(true);
                }
            }
        }
        
        // ulozeni chyb z jednotlivych funkci
        for (String fun : functions.keySet()) {
            errors.addAll(functions.get(fun).getErrors());
            printOutcome(fun);
        }
        
        return errors;
    }

    private void /*Set<CheckerError>*/ check(ControlFlowGraph cfg, boolean inter) {
        Set<CheckerError> errors = new HashSet<CheckerError>();
        nodesWithOwnerships = new HashMap<CFGNode, Map<String, PointerOwnership>>();
        nodeOnCorrectTrace = new HashSet<CFGNode>();
        ownershipInfo = functions.get(functionName);
        forwardAnalysis(cfg, inter);
        backwardAnalysis(cfg, inter);
        
        // vystupni omezeni funkci se urcuji v poslednim uzlu CFG
        CFGNode endNode = cfg.getEndNode();
        for (String pointerName : ownershipInfo.getInputPointerNames()) {
            // mezi parametry funkce nejsou jenom ukazatele
            if (nodesWithOwnerships.get(endNode).get(pointerName) != null) {
                int ownershipValue = nodesWithOwnerships.get(endNode).get(pointerName).getOwnershipValue();
                ownershipInfo.addOuputPointerOwnershipValue(ownershipValue);
                ownershipInfo.addSourceOfMemory(nodesWithOwnerships.get(endNode).get(pointerName).getSourceOfMemory());
            } else {
                ownershipInfo.addOuputPointerOwnershipValue(null);
                ownershipInfo.addSourceOfMemory(null);
            }
        }
        for (String pointerName : nodesWithOwnerships.get(cfg.getStartNode()).keySet()) {
            // najde se index polozky, ktera obsahuje pointerName a na prislusnou pozici se v druhem vektoru zapise hodnota ownershipValue
            int index = ownershipInfo.getInputPointerNames().indexOf(pointerName);
            ownershipInfo.getInputPointerOwnershipValues().set(index, nodesWithOwnerships.get(cfg.getStartNode()).get(pointerName).getOwnershipValue());
        }

        // musi se overit, ze vsechny ukazatele, ktere nejsou globalni jsou nevlastnici
        int indexOfFirstGlobal;
        if (globalPointers.size() != 0) {
            indexOfFirstGlobal = ownershipInfo.getInputPointerNames().indexOf(globalPointers.get(0));
        } else {
            indexOfFirstGlobal = ownershipInfo.getInputPointerNames().size();
        }
        Vector<Integer> outputPointerOwnershipValues = ownershipInfo.getOutputPointerOwnershipValues();
        HashSet<String> pointerNames = new HashSet<String>();
        for (int i = 0; i < indexOfFirstGlobal; i++) {
            // musi se overit, ze i-ty vstupni parametr je ukazatel
            if (outputPointerOwnershipValues.get(i) != null) {
                if ((outputPointerOwnershipValues.get(i) != 0)) {
                    if ((outputPointerOwnershipValues.get(i) == -1) && (ownershipInfo.getSourcesOfMemory().get(i).equalsIgnoreCase(ownershipInfo.getInputPointerNames().get(i)))) {
                        //---
                    } else {
                        pointerNames.add(ownershipInfo.getInputPointerNames().get(i));
                        errors.add(new OwnershipCheckerError(cfg.getEndNode(), pointerNames, OwnershipCheckerError.ErrorType.LOST_REFERENCE, nodesWithOwnerships, new HashSet<CFGNode>()));
                        //System.outSystem.out.println("chyba25");
                    }
                }
            }
        }
        ownershipInfo.addErrors(errors);

        //return errors;
    }
    
    // ####################################################################################################################################################################################
    /**
     * provádí analýzu shora dolů
     * @param cfg control flow graf funkce, která bude analyzována
     * @param inter true odpovídá interprocedurální analýze, false odpovídá intraprocedurální analýze
     */
    private void /*Set<CheckerError>*/ forwardAnalysis(ControlFlowGraph cfg, boolean inter) {
        Stack<CFGNode> nodesToDo = new Stack<CFGNode>();
        Set<CheckerError> errors = new HashSet<CheckerError>();
        
        nodesWithOwnerships.put(cfg.getStartNode(), new HashMap<String, PointerOwnership>());
        nodesToDo.push(cfg.getStartNode());
        
        // nalezeni ukazatelu mezi vstupnimi parametry funkce
        Element fdElement = cfg.getFunctionDefinition();
        Element fdDeclarator = (Element) fdElement.selectSingleNode("declarator");
        for (Object fdParameter : fdDeclarator.selectNodes("parameter")) {
            Element fdDeclarator2 = (Element) ((Element)fdParameter).selectSingleNode("declarator");
            // v pripade, ze je napr. void funkce(void), tak parametr nema deklarator
            if (fdDeclarator2 != null) {
                if (fdDeclarator2.element("pointer") != null) {
                    String fdId = fdDeclarator2.selectSingleNode("id").getText();
                    nodesWithOwnerships.get(cfg.getStartNode()).put(fdId, new PointerOwnership(fdId, -1, new IntObj(-1), fdId));
                }
            }
        }
        for (String globalPointer : globalPointers) {
            // na zacatku prvni spustene funkce programu jsou vlastnictvi globalnich ukazatelu -1
            nodesWithOwnerships.get(cfg.getStartNode()).put(globalPointer, new PointerOwnership(globalPointer, -1, new IntObj(-1), globalPointer));
        }
        
        while (!nodesToDo.empty()) {
            CFGNode node = nodesToDo.pop();
            
            for (CFGNode succ : node.getSuccessors()) {
                
                Element element = cfg.getEdgeElement(node, succ);
                
                Boolean trueBranch = cfg.getEdgeConditionType(node, succ);
                
                Map<String, PointerOwnership> nodeWithOwnership = new HashMap<String, PointerOwnership>();
                
                for (String id : nodesWithOwnerships.get(node).keySet()) {
                    nodeWithOwnership.put(id, nodesWithOwnerships.get(node).get(id));
                }

                if (!nodesWithOwnerships.containsKey(succ)) {
                    nodesToDo.push(succ);
                }

                // trueBranch je definovano pouze v pripade, ze se jedna o vetveni
                if (trueBranch == null) {
                    if (element == null) {
                        //--
                    // -------------------------------------------------------------------- deklarace ukazatele ----------------------------------------------------------
                    //} else if ((element.getName().equals("declaration")) && (element.selectSingleNode("declarationSpecifiers").selectSingleNode("typeSpecifier").selectSingleNode("baseType") != null)) {
                    } else if (element.getName().equals("declaration")) {
                        Element initDeclarator = (Element) element.selectSingleNode("initDeclarator");
                        Element binaryExpression = (Element) initDeclarator.selectSingleNode("binaryExpression");
                        // v pripade, ze se k ukazateli pricte nejaka hodnota, tak uz se nevi, kam ukazuje, takze je nam k nicemu, napr. char *end = addr + len;
                        //if (binaryExpression == null) {
                            Element declarator = (Element) initDeclarator.selectSingleNode("declarator");
                            // je to ukazatel
                            if (declarator.element("pointer") != null) {
                                String id = declarator.selectSingleNode("id").getText();
                                Element functionCall = (Element) initDeclarator.selectSingleNode("functionCall");
                                if (functionCall == null) {
                                    nodeWithOwnership.put(id, new PointerOwnership(id, 0, new IntObj(0), null));
                                } else {
                                    nodeWithOwnership.put(id, new PointerOwnership(id, -1, new IntObj(-1), null));
                                    ownershipInfo.addcalledFunction(functionCall.node(0).getText());
                                }
                            }
                            // nevlastnici ukazatel na pole, napr. char pole[123];
                            /*if (declarator.selectSingleNode("arrayDecl") != null) {
                                String id = declarator.selectSingleNode("id").getText();
                                nodeWithOwnership.put(id, new PointerOwnership(id, 0, new IntObj(0), null));
                            }*/
                        //}
                    // ----------------------------------------------------------------- prirazovaci prikaz ----------------------------------------------------------------
                    } else if (element.getName().equals("assignExpression")) {
                        String id1 = element.node(0).getText();
                        String id2 = element.node(1).getText();
                        PointerOwnership leftPointerOwnership = nodesWithOwnerships.get(node).get(id1);
                        // na leve strane je ukazatel
// az budou v xml fungovat pole - muze nastat sekvence prikazu int *p; p[0] = ...; v takovem pripade se nemeni ukazatel, ale pouze obsah, takze se muze toto prirazeni preskocit
// if (element.selectSingleNode("arrayExpression") != null) {
                        if (leftPointerOwnership != null) {
                            // je-li do ukazatele prirazovano nesmi byt vlastnici
                            if (leftPointerOwnership.getOwnershipValue() == 1) {
                                HashSet<String> pointerNames = new HashSet<String>();
                                pointerNames.add(id1);
                                errors.add(new OwnershipCheckerError(succ, pointerNames, OwnershipCheckerError.ErrorType.LOST_REFERENCE, nodesWithOwnerships, nodeOnCorrectTrace));
                                System.err.println("chyba3");
                            } else {
                                // at je ownershipValue -1 nebo 0, nastavi se na 0
                                leftPointerOwnership.setOwnershipValue(0);
                                leftPointerOwnership.getOwnershipSetValue().setValue(0);
                            }
                            // ------------------------ prava strana prirazeni --------------------------
                            Element elem = element;
                            if (elem.selectSingleNode("castExpression") != null) {
                                elem = (Element)elem.selectSingleNode("castExpression");
                                Element idElem = (Element)elem.selectSingleNode("id");
                                if (idElem != null) id2 = idElem.getText();

                            }
                            PointerOwnership rightPointerOwnership = nodesWithOwnerships.get(node).get(id2);
                            // do ukazatele je prirazeni NULL
                            if (id2.equalsIgnoreCase("null")) {
                                nodeWithOwnership.get(id1).getOwnershipSet().remove(id1);
                                nodeWithOwnership.remove(id1);
                                PointerOwnership newPointerOwnership = new PointerOwnership(id1, 0, new IntObj(0), null);
                                nodeWithOwnership.put(id1, newPointerOwnership);
                            // jestlize se pristupuje k pameti pres &, napr. segments = &default_ldt; takova pamet neni potreba uvolnovat, tudiz je nevlastnici
                            } else if (elem.selectSingleNode("prefixExpression") != null) {
                                nodeWithOwnership.remove(id1);
                                PointerOwnership newPointerOwnership = new PointerOwnership(id1, 0, new IntObj(0), null);
                                nodeWithOwnership.put(id1, newPointerOwnership);
                            // jestlize to je ukazatel na strukturu
                            /*} else if ((rightPointerOwnership == null) && (elem.node(0).getName().equalsIgnoreCase("id")) && (elem.node(1).getName().equalsIgnoreCase("id"))) {
                                //--
                            // jestlize se prirazuje ze struktury pres ->
                            } else if ((rightPointerOwnership == null) && (elem.node(0).getName().equalsIgnoreCase("id")) && (elem.node(1).getName().equalsIgnoreCase("arrowExpression"))) {
                                //--
                            //                        
                            // jestlize se prirazuje ze struktury pres .
                            } else if ((rightPointerOwnership == null) && (elem.node(0).getName().equalsIgnoreCase("id")) && (elem.node(1).getName().equalsIgnoreCase("dotExpression"))) {
                                //--
                            // 
                            } else if (elem.node(1).getName().equalsIgnoreCase("binaryExpression")) {
                                //--
                            // jestlize je ukazatel = (podminka)?ukaz1:ukaz2;
                            } else if (elem.node(1).getName().equalsIgnoreCase("conditionalExpression")) {
                                //--
                            // jestlize se ukazateli prirazuje z pole (az po oprave xml)
                            //} else if () {
                                //--*/
                            // jestlize je na prave strane ukazatel, tzn. mame o nem zaznam v nodesWithOwnership
                            } else if (rightPointerOwnership != null) {
                                // je potreba zrusit vazby na predchozi uzel
                                removePointer(id1, nodeWithOwnership);
                                // prirazeni polozky ze struktury ignorujeme
                                if (elem.selectSingleNode("arrayExpression") == null) {
                                    IntObj rightOwnershipSetValue;
                                    int rightOwnershipValue;
                                   // dochazi-li k prirazovani, nesmi byt vlastnici mnozina 0
                                    if (nodesWithOwnerships.get(node).get(id2).getOwnershipSetValue().getValue() == 0) {
                                        HashSet errorPointers = new HashSet<String>();
                                        errorPointers.add(id1);
                                        errors.add(new OwnershipCheckerError(node, errorPointers, OwnershipCheckerError.ErrorType.NULL_ASSIGNMENT, nodesWithOwnerships, nodeOnCorrectTrace));
                                        System.err.println("chyba24");
                                        rightOwnershipSetValue = new IntObj(1);
                                        rightOwnershipValue = -1;
                                    } else {
                                        rightOwnershipSetValue = new IntObj(nodesWithOwnerships.get(node).get(id2).getOwnershipSetValue());
                                        rightOwnershipValue = nodesWithOwnerships.get(node).get(id2).getOwnershipValue();
                                    }
                                    String sourceOfMemory = nodesWithOwnerships.get(node).get(id2).getSourceOfMemory();
                                    // jestlize pravy ukazatel je nevlastnici, jsou i oba nasledovnici nevlastnici
                                    // je-li pravy ukazatel vlastnici, vlastnictvi nasledovniku je nezname
                                    // je-li vlastnictvi praveho nezname, je i vlastnivtvi nasledovniku nezname
                                    if (rightOwnershipValue == 0) nodeWithOwnership.put(id1, new PointerOwnership(id1, 0, rightOwnershipSetValue, sourceOfMemory));
                                    else nodeWithOwnership.put(id1, new PointerOwnership(id1, -1, rightOwnershipSetValue, sourceOfMemory));
                                    for (String pointerName : nodesWithOwnerships.get(node).get(id2).getOwnershipSet()) {
                                        nodeWithOwnership.remove(pointerName);
                                        if (rightOwnershipValue == 0) nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, 0, rightOwnershipSetValue, nodeWithOwnership.get(id1).getOwnershipSet(), sourceOfMemory));
                                        else nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, -1, rightOwnershipSetValue, nodeWithOwnership.get(id1).getOwnershipSet(), sourceOfMemory));
                                    }
                                // jedna se o polozku struktury, nastavime vlastnictvi na -1
                                } else {
                                    nodeWithOwnership.put(id1, new PointerOwnership(id1, -1, new IntObj(-1), null));
                                }
                            // jestlize je na prave strane funkce (napr. malloc) a ne ukazatel, ani ukazatel na strukturu
                            } else if (elem.selectSingleNode("functionCall") != null) {
                                // ukazatel id1 je vlastnici a jeden z ukazatelu ukazujicich na stejnou pamet je vlastnici
                                nodeWithOwnership.remove(id1);
                                PointerOwnership newPointerOwnership = new PointerOwnership(id1, -1, new IntObj(-1), null);
                                nodeWithOwnership.put(id1, newPointerOwnership);
                                // navratova hodnota funkce se muze pretypovat
                                List params = new ArrayList(((Element)elem.selectSingleNode("functionCall")).elements());
                                // prvni parametr je nazev funkce
                                String calledFunctionName = ((Element)params.remove(0)).getText();
                                ownershipInfo.addcalledFunction(calledFunctionName);
                                if (inter) {
                                    FunctionOwnershipInfo calledFunctionOwnershipInfo = functions.get(calledFunctionName);
                                    // jeslize o funkci neni zaznam, znamena to, ze neni k dispozici jeji kod, vlastnictvi je -1
                                    // nebo je v opacnem topologickem usporadani funkci az za touto funkci
                                    if (calledFunctionOwnershipInfo == null) {

                                    } else {
                                        //ownershipInfo.addcalledFunction(calledFunctionName);
                                        Vector<String> paramNames = functionParamNames(params);
                                        functionInputParemetersCheck(paramNames, node, calledFunctionOwnershipInfo, errors);
                                        //functionOutputParametersCorrection(paramNames, nodeWithOwnership, calledFunctionOwnershipInfo);
                                        fixPointersAfterFunction(paramNames, nodeWithOwnership, calledFunctionOwnershipInfo);
                                        fixGlobalPointersAfterFunction(paramNames, nodeWithOwnership, calledFunctionOwnershipInfo);

                                        // prirazeni navatoveho vlastnictvi ukazateli
                                        String sourceOfMemory = calledFunctionOwnershipInfo.getOutputSourceOfMemory();
                                        if ((calledFunctionOwnershipInfo.getOutput() == -1) && (sourceOfMemory != null)) {
                                            int index = calledFunctionOwnershipInfo.getInputPointerNames().indexOf(sourceOfMemory);
                                            // sourceOfMemory je jmeno parametru ve volane funkci, musi se dohledat jmeno ve volajici funkci
                                            String name = paramNames.get(index);
                                            // je-li jako parametr funkce ukazatel ve strukture, tak ho ignorujeme, jelikoz se nezpracovavaji struktury
                                            if (!name.equalsIgnoreCase("struktura123")) {
                                                String source = nodeWithOwnership.get(name).getSourceOfMemory();
                                                HashSet<String> ownershipSet = nodeWithOwnership.get(name).getOwnershipSet();
                                                if (nodeWithOwnership.get(name).getOwnershipValue() == 1) {
                                                    // jestlize navratova hodnota funkce je 1 a na stejnou pamet ukazuji i globalni ukazatele, tak se nevi, ktery je vlastnici
                                                    if (ownershipSet.size() > 1)  {
                                                        nodeWithOwnership.get(id1).setOwnershipValue(-1);
                                                    } else {
                                                        nodeWithOwnership.get(id1).setOwnershipValue(1);
                                                    }
                                                    nodeWithOwnership.get(id1).getOwnershipSetValue().setValue(1);
                                                    for (String pointerName : ownershipSet) {
                                                        nodeWithOwnership.remove(pointerName);
                                                        if (ownershipSet.size() > 1)  {
                                                            nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, -1, nodeWithOwnership.get(id1).getOwnershipSetValue(), nodeWithOwnership.get(id1).getOwnershipSet(), source));
                                                        } else {
                                                            nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, 0, nodeWithOwnership.get(id1).getOwnershipSetValue(), nodeWithOwnership.get(id1).getOwnershipSet(), source));
                                                        }
                                                    }
                                                } else if (nodeWithOwnership.get(name).getOwnershipValue() == 0) {
                                                    nodeWithOwnership.get(id1).setOwnershipValue(0);
                                                    nodeWithOwnership.get(id1).getOwnershipSetValue().setValue(nodeWithOwnership.get(name).getOwnershipSetValue().getValue());
                                                    for (String pointerName : ownershipSet) {
                                                        int ownershipValue = nodeWithOwnership.get(pointerName).getOwnershipValue();
                                                        nodeWithOwnership.remove(pointerName);
                                                        nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, ownershipValue, nodeWithOwnership.get(id1).getOwnershipSetValue(), nodeWithOwnership.get(id1).getOwnershipSet(), source));
                                                    }
                                                }
                                            }
                                        } else {
                                            nodeWithOwnership.get(id1).setOwnershipValue(calledFunctionOwnershipInfo.getOutput());
                                            nodeWithOwnership.get(id1).getOwnershipSetValue().setValue(calledFunctionOwnershipInfo.getOutput());
                                        }
                                    }
                                }
                            }
                        }
                    // ---------------------------------------------------------------- volani funkce ----------------------------------------------------------------
                    } else if (element.getName().equals("functionCall")) {
                        String funkce = element.node(0).getText();
                        try {
                            String id = element.node(1).getText();
                            if (funkce.equalsIgnoreCase("free") || funkce.equalsIgnoreCase("kfree")) {
                                HashSet<String> ownershipSet = nodesWithOwnerships.get(node).get(id).getOwnershipSet();
                                // vlastnictvi vsech ukazatelu ukazujicich na uvolnovanou pamet se nastavi na 0
                                for (String pointerName : ownershipSet) {
                                    nodeWithOwnership.remove(pointerName);
                                    nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, 0, new IntObj(0), null));
                                }
                            }
                        } catch (NullPointerException npe) {
                            // funkce nemusi mit zadny parametr
                        }

                        List params = new ArrayList(element.elements());
                        String calledFunctionName = ((Element)params.remove(0)).getText();
                        ownershipInfo.addcalledFunction(calledFunctionName);
                        if (inter) {
                            //List params = new ArrayList(element.elements());
                            // prvni parametr je nazev funkce
                            //params.remove(0);
                            FunctionOwnershipInfo calledFunctionOwnershipInfo = functions.get(funkce);
                            // jeslize o funkci neni zaznam, znamena to, ze neni k dispozici jeji kod, vlastnictvi je -1
                            // nebo je v opacnem topologickem usporadani funkci az za touto funkci
                            if (calledFunctionOwnershipInfo == null) {

                            } else {
                                //ownershipInfo.addcalledFunction(calledFunctionName);
                                Vector<String> paramNames = functionParamNames(params);
                                functionInputParemetersCheck(paramNames, node, calledFunctionOwnershipInfo, errors);
                                //functionOutputParametersCorrection(paramNames, nodeWithOwnership, calledFunctionOwnershipInfo);
                                fixPointersAfterFunction(paramNames, nodeWithOwnership, calledFunctionOwnershipInfo);
                                fixGlobalPointersAfterFunction(paramNames, nodeWithOwnership, calledFunctionOwnershipInfo);
                                // kdyz vystup z funkce je vlastnici, musi byt tato funkce prirazena nejakemu ukazateli
                                try {
                                    //if (calledFunctionOwnershipInfo.getOutput() == 1) {
                                    if (calledFunctionOwnershipInfo.getOutput() != 0) {
                                        // mozna nazvat chybu jinak, napr. LOST_FUNCTION_OUTPUT
                                        errors.add(new OwnershipCheckerError(node, new HashSet<String>(), OwnershipCheckerError.ErrorType.LOST_FUNCTION_OUTPUT, nodesWithOwnerships, nodeOnCorrectTrace));
                                        System.err.println("chyba19");
                                    }
                                } catch (NullPointerException npe) {
                                    // pokud funkce nema navratovou hodnotu, tak je hodnota vystupu null
                                }
                            }
                        }
                    // ------------------------------------------------------------- prikaz return -------------------------------------------------------------------
                    } else if (element.getName().equals("returnStatement")) {
                        try {
                            String id = element.selectSingleNode("id").getText();
                            /*if (nodesWithOwnerships.get(node).get(id).getOwnershipSetValue().getValue() == 1) {
                                for (String pointerName : nodesWithOwnerships.get(node).get(id).getOwnershipSet()) {
                                    nodesWithOwnerships.get(node).get(pointerName).setOwnershipValue(0);
                                }
                                nodesWithOwnerships.get(node).get(id).setOwnershipValue(1);
                            }*/
                            // ulozi se zjistene vlastnictvi ukazatele predavane mimo funkci 
                            // neni-li zadne vystupni vlastnictvi definovano (muze uz byt z jineho return), zapise se vlastnictvi mnoziny ukazatele navratove hodnoty
                            int outputOwnershipValue = nodeWithOwnership.get(id).getOwnershipSetValue().getValue();
                            //int outputOwnershipValue = nodeWithOwnership.get(id).getOwnershipValue();
                            if (ownershipInfo.getOutput() == null) {
                                ownershipInfo.setOutput(new Integer(outputOwnershipValue));
                                ownershipInfo.setOutputSourceOfMemory(nodeWithOwnership.get(id).getSourceOfMemory());
                            } else {
                                // je-li vlastnictvi ukazatele 0 a zapisuje se cokoliv krome 0, vystupni vlastnictvi je -1
                                // je-li vlastnictvi ukazatele 1 a zapisuje se cokoliv krome 1, vystupni vlastnictvi je -1
                                // je-li vlastnictvi ukazatele -1. zapise se -1
                                if ((outputOwnershipValue == -1) ||
                                    ((outputOwnershipValue == 0) && (functions.get(functionName).getOutput() != 0)) ||
                                    ((outputOwnershipValue == 1) && (functions.get(functionName).getOutput() != 1))) {
                                    functions.get(functionName).setOutput(new Integer(-1));
                                }
                            }
                            // vsechny ukazatele na navracenou pamet jsou nastaveny jako nevlastnici
                            // vyjimnkou jsou globalni ukazatele na navracenou pamet
                            HashSet<String> ownershipSet = nodeWithOwnership.get(id).getOwnershipSet();
                            // vlastnictvi vsech ukazatelu ukazujicich na navracenou pamet se nastavi na 0
                            for (String pointerName : ownershipSet) {
                                if (!globalPointers.contains(pointerName)) {
                                    nodeWithOwnership.remove(pointerName);
                                    nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, 0, new IntObj(0), null));
                                }
                            }
                        } catch (NullPointerException npe) {
                            // return nemusi mit parametr, anebo to neni ukazatel
                            // v pripade, ze nastane varianta return funkce(); tak se musi navracene vlastnictvi prebirat z volane funkce
                            Element fun = (Element)element.selectSingleNode("functionCall");
                            if (fun != null) {
                                // nazev volane funkce
                                String calledFunctionName = fun.selectSingleNode("id").getText();
                                FunctionOwnershipInfo calledFunctionOwnershipInfo = functions.get(calledFunctionName);
                                ownershipInfo.addcalledFunction(calledFunctionName);
                                // jestlize vime neco o vlastnictvi volane funkce, tuto hodnotu prevezmeme, jinak je vlastnictvi nezname
                                if ((calledFunctionOwnershipInfo == null) || (calledFunctionOwnershipInfo.getOutput() == null)) {
                                    // navratova hodnota funkce je ukazatel
                                    if (element.selectSingleNode("/translationUnit/functionDefinition/declarator[id[1]=\"" + calledFunctionName + "\"]/pointer") != null) {
                                        ownershipInfo.setOutput(new Integer(-1));
                                    }
                                } else {
                                    ownershipInfo.setOutput(new Integer(calledFunctionOwnershipInfo.getOutput()));
                                }
                            // jeste je moznost, ze to bude vypadat takto: return &promenna;
                            } else if (element.selectSingleNode("prefixExpression") != null) {
                                Element prefixExpression = (Element)element.selectSingleNode("prefixExpression");
                                if (prefixExpression != null) {
                                    String attribute = prefixExpression.attributeValue("op");
                                    if ((attribute != null) && (attribute.equalsIgnoreCase("&&"))) {
                                        ownershipInfo.setOutput(new Integer(0));
                                    }
                                }
                            // jeste je moznost, ze je misto ukazatele vraceno pole, v takovem pripade se musi zjistit, ze navratova hodnota je ukazatel,
                            // ale o navracene promenne nemame zaznam; navraceny ukazatel je nevlastnici
                            } else {
                                if (element.selectSingleNode("/translationUnit/functionDefinition/declarator[id[1]=\"" + functionName + "\"]/pointer") != null) {
                                    ownershipInfo.setOutput(new Integer(0));
                                }                                
                            }

                        }
                        // vytvoreni mnozin souvisejicich globalnich ukazatelu
                        if (ownershipInfo.getGlobalPointerSets().size() == 0) {
                            int indexOfFirstGlobal;
                            if (globalPointers.size() != 0) {
                                indexOfFirstGlobal = ownershipInfo.getInputPointerNames().indexOf(globalPointers.get(0));
                            } else {
                                indexOfFirstGlobal = ownershipInfo.getInputPointerNames().size();
                            }
                            for (int i = 0; i < indexOfFirstGlobal; i++) {
                                ownershipInfo.getGlobalPointerSets().add(null);
                            }
                            for (int i = indexOfFirstGlobal; i < ownershipInfo.getInputPointerNames().size(); i++) {
                                HashSet<String> ownershipSet = nodeWithOwnership.get(ownershipInfo.getInputPointerNames().get(i)).getOwnershipSet();
                                HashSet<String> globalSet = new HashSet<String>();
                                for (String pointerName : ownershipSet) {
                                    if (globalPointers.contains(pointerName)) globalSet.add(pointerName);
                                }
                                ownershipInfo.getGlobalPointerSets().add(globalSet);
                            }
                        }
                    }
                // ------------------------------------------------------------------------ vetveni ----------------------------------------------------------------------------
                } else {
                    String id = "";
                    String podminka = "";
                    String assignOperator = "";
                    // ukazatel ve tvaru if (ukazatel)
                    if (element.getName().equals("id")) {
                        id = element.getText();
                        // mame od promenne zaznam, tudiz to je ukazatel
                        if (nodeWithOwnership.get(id) != null) {
                            podminka = "null";
                            assignOperator = "!=";
                        }         
                    // ukazatelu ve tvaru if (!ukazatel)
                    } else if (element.getName().equals("prefixExpression")) {
                        if ((element.attributeValue("op") != null) && (element.attributeValue("op").equalsIgnoreCase("!"))) {
                            Element idEl = (Element)element.selectSingleNode("id");
                            if (idEl != null) {
                                id = idEl.getText();
                                // mame od promenne zaznam, tudiz to je ukazatel
                                if (nodeWithOwnership.get(id) != null) {
                                    podminka = "null";
                                    assignOperator = "==";
                                }
                            }
                        }                        
                    } else if (element.getName().equals("binaryExpression")) {
                        id = element.node(0).getText();
                        podminka = element.node(1).getText();
                        assignOperator = element.attribute("op").getText();
                        // jestlize je podminka ve tvaru null == neco
                        if (id.equalsIgnoreCase("null")) {
                            String pom = id;
                            id = podminka;
                            podminka = pom;
                        } 
                    }
                    // ve vetvi, ktera bude dale povazovana za potencialne nespravnou se vytvori uplne nove prostredi s hodnotami
                    // predchudce, aby se jednotlive vetve neovlivnovali; spravna je false vetev
                    // vyjimku tvori podminka (ukazatel==null) a (ukazatel!=null), tam se vytvari nove prostredi ve vetvi (ukazatel==null)
                    // pokud jsme v true vetvi a v podmince neni null, nejedna se tedy o ukazatel, pak vytvarime nove prostredi v true vetvi
                    // pokud je podminka null, overujeme tedy ukazatel, potom musi platit pro true vetev (ukazatel==null) a pro false vetev (ukazatel!=null)
                    // (A && !B) || (B && ((C && A) || (!C && !A)))
                    if (((trueBranch) && (!podminka.equalsIgnoreCase("null"))) 
                        || ((podminka.equalsIgnoreCase("null")) 
                        && (assignOperator.equalsIgnoreCase("==") && trueBranch)
                        || (assignOperator.equalsIgnoreCase("!=") && !trueBranch))) {
                        // jestlize seznam uzlu ke zpracovani uz obsahuje sourozence a jsme v potencialne nespravne vetvi,
                        // musi se tato vetev zpracovavat az po tomto sourozenci, protoze po slouceni vetvi je zadouci, aby se pokracovalo 
                        // s hodnotami ze spravne vetve
                        if ((nodesToDo.size() > 1)) {
                            // jestlize na druhem miste je sourozenec, tohoto sourozence potrebujeme dostat na vrchol zasobniku,
                            // protoze ted jsme v potencialne nespravne vetvi a chceme, aby se spravna vetev zpracovala drive
                            if (node.getSuccessors().contains(nodesToDo.get(1))) {
                                nodesToDo.pop();
                                CFGNode tempNode = nodesToDo.pop();
                                nodesToDo.push(succ);
                                nodesToDo.push(tempNode);
                            }
                        }
                        // vytvoreni noveho prostredi se starymi hodnotami
                        nodeWithOwnership.clear();
                        for (String pointerName : nodesWithOwnerships.get(node).keySet()) {
                            if (!nodeWithOwnership.containsKey(pointerName)) {
                                int ownershipValue = nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue();
                                IntObj ownershipSetValue = nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue();
                                String sourceOfMemory = nodesWithOwnerships.get(node).get(pointerName).getSourceOfMemory();
                                nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, ownershipValue, ownershipSetValue, sourceOfMemory));
                                for (String pointerName2 : nodesWithOwnerships.get(node).get(pointerName).getOwnershipSet()) {
                                    if (!pointerName.equalsIgnoreCase(pointerName2)) {
                                        nodeWithOwnership.put(pointerName2, new PointerOwnership(pointerName2, ownershipValue, ownershipSetValue, nodeWithOwnership.get(pointerName).getOwnershipSet(), sourceOfMemory));
                                    }
                                }
                            }
                        }
                    }
                    // proti null by se mel dat overovat jen ukazatel, takze id je ukazatel
                    if (podminka.equalsIgnoreCase("null")) {
                        // informace o vsech ukazatelech miricich na stejnou pamet se musi znova vytvorit
                        removePointer(id, nodeWithOwnership);
                        // kdyz ukazatel==NULL jsme v true vetvi, nebo ukazatel!=NULL a jsme ve false vetvi
                        if ((assignOperator.equalsIgnoreCase("==") && trueBranch)
                        || (assignOperator.equalsIgnoreCase("!=") && !trueBranch)) {
                            //removePointer(id, nodeWithOwnership);
                            PointerOwnership newPointerOwnership = new PointerOwnership(id, 0, new IntObj(0), null);
                            nodeWithOwnership.put(id, newPointerOwnership);
                        // kdyz ukazatel==NULL jsme ve false vetvi, nebo ukazatel!=NULL a jsme v true vetvi
                        // jiny operator nez == a != by nemel byt mozny a 2. podminka nemuze byt jina nez true a false
                        } else {
                            PointerOwnership newPointerOwnership = new PointerOwnership(id, 1, new IntObj(1), null);
                            nodeWithOwnership.put(id, newPointerOwnership);
                        }
                    }
                }
                
                // ------------------------------------------------------------------------------- slouceni vetvi -----------------------------------------------------------------------
                if (!nodesWithOwnerships.containsKey(succ)) {
                    nodesWithOwnerships.put(succ, new HashMap<String, PointerOwnership>());
                    //System.out.println(element.getName());
                    //System.out.println(node.getNumber()+" -> "+succ.getNumber());
                    //System.out.println("-----");
                    for (String id : nodeWithOwnership.keySet()) {
                        nodesWithOwnerships.get(succ).put(id, nodeWithOwnership.get(id));
                        //System.out.println(id + " " + nodeWithOwnership.get(id).getOwnershipValue() + " " + nodeWithOwnership.get(id).getOwnershipSet().toString() + " " + nodeWithOwnership.get(id).getOwnershipSetValue().getValue() + " " + nodeWithOwnership.get(id).getSourceOfMemory());
                    }
                    // ulozi se posledni uzel na spravna ceste pred sloucenim vetvi
                    if (succ.getPredecessors().size() > 1) nodeOnCorrectTrace.add(node);
                } else /*if (succ.getSuccessors().size() != 0)*/ {
                    // existuje-li uz informace o nasledovnikovi, museji byt informace shodne s informacemi z teto vetve
                    // jinak je to chyba
                    HashSet<String> commonPointers = new HashSet<String>(nodeWithOwnership.keySet());
                    commonPointers.retainAll(nodesWithOwnerships.get(succ).keySet());
                    OwnershipCheckerError ownershipCheckerError = new OwnershipCheckerError(succ, new HashSet<String>(), OwnershipCheckerError.ErrorType.UNCONSISTENT_JOINING_BRANCHES, nodesWithOwnerships, nodeOnCorrectTrace);
                    // informace o ukazatelich musi byt stejne, jinak nekonsistence
                    for (String pointerName : commonPointers) {
                        if (nodesWithOwnerships.get(succ).get(pointerName).getOwnershipValue() != nodeWithOwnership.get(pointerName).getOwnershipValue()) {
                            errors.add(ownershipCheckerError);
                            ownershipCheckerError.addErroneousPointer(pointerName);
                            System.err.println("chyba9");
                        }
                        if (!nodesWithOwnerships.get(succ).get(pointerName).getOwnershipSet().equals(nodeWithOwnership.get(pointerName).getOwnershipSet())) {
                            errors.add(ownershipCheckerError);
                            ownershipCheckerError.addErroneousPointer(pointerName);
                            System.err.println("chyba10");
                        }
                        if (nodesWithOwnerships.get(succ).get(pointerName).getOwnershipSetValue().getValue() != nodeWithOwnership.get(pointerName).getOwnershipSetValue().getValue()) {
                            errors.add(ownershipCheckerError);
                            ownershipCheckerError.addErroneousPointer(pointerName);
                            System.err.println("chyba11");
                        }
                    }
                }
            }
        }
        ownershipInfo.addErrors(errors);
        //return errors;
    }

    // ####################################################################################################################################################################################
    /**
     * provádí analýzu shora dolů
     * @param cfg control flow graf funkce, která bude analyzována
     * @param inter true odpovídá interprocedurální analýze, false odpovídá intraprocedurální analýze
     */
    private void /*Set<CheckerError>*/ backwardAnalysis(ControlFlowGraph cfg, boolean inter) {
        Stack<CFGNode> nodesToDo = new Stack<CFGNode>();
        Stack<CFGNode> nodesDone = new Stack<CFGNode>();
        Set<CheckerError> errors = new HashSet<CheckerError>();
        
        nodesToDo.push(cfg.getEndNode());
        
        while (!nodesToDo.empty()) {
            CFGNode node = nodesToDo.pop();
            nodesDone.push(node);
            
            for (CFGNode pred : node.getPredecessors()) {

                Element element = cfg.getEdgeElement(pred, node);

                if ((!nodesDone.contains(pred)) && (element != null)) {
                    // aby se nestalo ze se zpracuje (horni) spojeni vetvi drive nez je zpracovana pravdiva vetev,
                    // ceka se na vsechny vetve, neni potreba u cyklu
                    if ((pred.getSuccessors().size() > 1) && (!element.getParent().getName().equals("forStatement")) && (!element.getParent().getName().equals("whileStatement"))) {
                    //if ((pred.getSuccessors().size() > 1) && (element.getParent().getName().equals("ifStatement"))) {
                        if (nodesDone.containsAll(pred.getSuccessors())) {
                            nodesToDo.push(pred);
                            // najdeme ukazatel na sourozence uzlu
                            Iterator it = pred.getSuccessors().iterator();
                            CFGNode sibling = (CFGNode) it.next();
                            if (node.equals(sibling)) sibling = (CFGNode) it.next();

                            String id = element.node(0).getText();
                            // jestlize uzel obsahuje jine hodnoty nez sourozenec, tak se napojuje nekonsitentni vetev
                            OwnershipCheckerError ownershipCheckerError = new OwnershipCheckerError(pred, new HashSet<String>(), OwnershipCheckerError.ErrorType.UNCONSISTENT_SPLITTING_BRANCHES, nodesWithOwnerships, nodeOnCorrectTrace);
                            // informace o ukazatelich musi byt stejne, jinak nekonsistence
                            for (String pointerName : nodesWithOwnerships.get(node).keySet()) {
                                // jedna-li se o podminku (ukazatel==null), tak ukazatel nemusi byt stejny v obou vetvich
                                if (!pointerName.equalsIgnoreCase(id)) {
                                    if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() != nodesWithOwnerships.get(sibling).get(pointerName).getOwnershipValue()) {
                                        if ((nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() != -1) && (nodesWithOwnerships.get(sibling).get(pointerName).getOwnershipValue() != -1)) {
                                            // je-li v jedne vetvi vlastnictvi ukazatele -1 a v druhe 0 nebo 1, propaguje se nahoru presnejsi udaj, tedy 0 nebo 1
                                            //if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() == -1) nodesWithOwnerships.get(node).get(pointerName).setOwnershipValue(nodesWithOwnerships.get(sibling).get(pointerName).getOwnershipValue());
                                            //else nodesWithOwnerships.get(sibling).get(pointerName).setOwnershipValue(nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue());
                                            // musi tam byt jen jednou, to by mela zajistit mnozina
                                            errors.add(ownershipCheckerError);
                                            // musi tam byt jen jednou, to by mela zajistit mnozina
                                            ownershipCheckerError.addErroneousPointer(pointerName);
                                            System.err.println("chyba13");
                                        }
                                    }
                                    if (!nodesWithOwnerships.get(node).get(pointerName).getOwnershipSet().equals(nodesWithOwnerships.get(sibling).get(pointerName).getOwnershipSet())) {
                                        // musi tam byt jen jednou, to by mela zajistit mnozina
                                        errors.add(ownershipCheckerError);
                                        // musi tam byt jen jednou, to by mela zajistit mnozina
                                        ownershipCheckerError.addErroneousPointer(pointerName);
                                        System.err.println("chyba14");
                                    }
                                    if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue().getValue() != nodesWithOwnerships.get(sibling).get(pointerName).getOwnershipSetValue().getValue()) {
                                        // musi tam byt jen jednou, to by mela zajistit mnozina
                                        errors.add(ownershipCheckerError);
                                        // musi tam byt jen jednou, to by mela zajistit mnozina
                                        ownershipCheckerError.addErroneousPointer(pointerName);
                                        System.err.println("chyba15");
                                    }
                                }
                            }
                        }
                    } else {
                        nodesToDo.push(pred);
                    }
                }
                
                /*System.out.println(element.getName());
                System.out.println(pred.getNumber()+" <- "+node.getNumber());
                System.out.println("-----");*/
                //for (String id : nodesWithOwnerships.get(node).keySet()) {
                    /*System.out.println(id + " " + nodesWithOwnerships.get(node).get(id).getOwnershipValue() + " " + nodesWithOwnerships.get(node).get(id).getOwnershipSet().toString() + " " + nodesWithOwnerships.get(node).get(id).getOwnershipSetValue().getValue());*/
                //}

                if (element == null) {
                    // vyraz nevraci getEdgeElement
                // --------------------------------------------------------------------- volani funkce ------------------------------------------------------------------------
                } else if (element.getName().equals("functionCall")) {
                    String funkce = element.node(0).getText();
                    try {
                        String id = element.node(1).getText();
                        if (funkce.equalsIgnoreCase("free") || funkce.equalsIgnoreCase("kfree")) {
                            // je-li hodnota vlastnici mnoziny 0, ukazatel vubec neukazuje na pamet
                            if (nodesWithOwnerships.get(pred).get(id).getOwnershipSetValue().getValue() == 0) {
                                HashSet<String> pointerNames = new HashSet<String>();
                                pointerNames.add(id);
                                errors.add(new OwnershipCheckerError(node, pointerNames, OwnershipCheckerError.ErrorType.FREE, nodesWithOwnerships, nodeOnCorrectTrace));
                                System.err.println("chyba16");
                            } else {
                                // kdyz je uvolnovan ukazatel, jehoz
                                //    vlastnictvi je 1, tak je to v poradku
                                //    vlastnictvi je -1, zmeni se na 1 a propaguje se nahoru, ale hodnota vlastnici mnoziny nesmi byt -1 jinak je to chyba
                                //    vlastnictvi je 0, je to chyba, bud byl uz uvolnen, nebo neukazuje na zadnou pamet
                                if (nodesWithOwnerships.get(pred).get(id).getOwnershipValue() == 0) {
                                    HashSet<String> pointerNames = new HashSet<String>();
                                    pointerNames.add(id);
                                    errors.add(new OwnershipCheckerError(node, pointerNames, OwnershipCheckerError.ErrorType.FREE, nodesWithOwnerships, nodeOnCorrectTrace));
                                    System.err.println("chyba7");
                                } else if (nodesWithOwnerships.get(pred).get(id).getOwnershipValue() == -1) {
                                    for (String pointerName : nodesWithOwnerships.get(pred).get(id).getOwnershipSet()) {
                                        nodesWithOwnerships.get(pred).get(pointerName).setOwnershipValue(0);
                                    }
                                    nodesWithOwnerships.get(pred).get(id).setOwnershipValue(1);
                                    nodesWithOwnerships.get(pred).get(id).getOwnershipSetValue().setValue(1);
                                // tento pripad by nemel nastat
                                } /*else if (nodesWithOwnerships.get(pred).get(id).getOwnershipValue() == 1) {
                                }*/
                            }
                        }
                    } catch (NullPointerException npe) {
                        //funkce nemusi mit zadny parametr
                    }
                // ----------------------------------------------------------------------------- prirazovaci prikaz --------------------------------------------------------------
                } else if (element.getName().equals("assignExpression")) {
                    String id1 = element.node(0).getText();
                    String id2 = element.node(1).getText();
                    PointerOwnership leftPointerOwnership = nodesWithOwnerships.get(pred).get(id1);
                    // na leve strane prirazeni je ukazatel
                    if (nodesWithOwnerships.get(pred).get(id1) != null) {
                        // ----------------------- prava strana prirazeni -------------------------
                        Element elem = element;
                        if (elem.selectSingleNode("castExpression") != null) {
                            elem = (Element)elem.selectSingleNode("castExpression");
                            Element idElem = (Element)elem.selectSingleNode("id");
                            if (idElem != null) id2 = idElem.getText();
                        }
                        PointerOwnership rightPointerOwnership = nodesWithOwnerships.get(pred).get(id2);
                        if (rightPointerOwnership != null)  {
                            // prirazeni ze struktury ignorujeme
                            if (elem.selectSingleNode("arrayExpression") == null) {
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
                        /*// na prave strane je &neco
                        if (elem.selectSingleNode("prefixExpression") != null) {
                            //---
                        // jestlize to je na prave strane ukazatel na strukturu
                        } else if ((rightPointerOwnership == null) && (elem.node(0).getName().equalsIgnoreCase("id")) && (elem.node(1).getName().equalsIgnoreCase("id"))) {
                            //--
                        // jestlize se na prave strane prirazuje ze struktury pres ->
                        } else if ((rightPointerOwnership == null) && (elem.node(0).getName().equalsIgnoreCase("id")) && (elem.node(1).getName().equalsIgnoreCase("arrowExpression"))) {
                            //--*/
                        // na prave strane prirazeni je funkce
                        } else if (elem.selectSingleNode("functionCall") != null) {
                            HashSet errorPointers = new HashSet<String>();
                            errorPointers.add(id1);
                            // overi se zda vlastnictvi ukazatele odpovida vystupnimu vlastnictvi funkce, je-li znamo,
                            // jinak musi byt vlastnictvi ukazatele -1
                            // u intraproc. anal. je vzdy -1, jelikoz vystupni vlastnictvi funkce nemusi byt jeste znamo
                            if (inter) {
                                List params = new ArrayList(((Element)elem.selectSingleNode("functionCall")).elements());
                                // prvni parametr je nazev funkce
                                String calledFunctionName = ((Element)params.remove(0)).getText();
                                FunctionOwnershipInfo calledFunctionOwnershipInfo = functions.get(calledFunctionName);
                                // o funkce neni zaznam
                                if (calledFunctionOwnershipInfo == null) {
                                    if (nodesWithOwnerships.get(node).get(id1).getOwnershipValue() != -1) {
                                   errors.add(new OwnershipCheckerError(pred, errorPointers, OwnershipCheckerError.ErrorType.UNCHECKED, nodesWithOwnerships, nodeOnCorrectTrace));
                                        System.err.println("chyba22");                                
                                    }
                                // o funkci je zaznam
                                } else {
                                    // jestlize vystup volane funkce je -1 a zdroj pameti je v teto volane funkci, potom informace propagovana zespoda musi byt -1 
                                    // takto se napriklad overi, ze po malloc nasleduje test na uspesnost
                                    if ((calledFunctionOwnershipInfo.getOutput() == -1) && (calledFunctionOwnershipInfo.getOutputSourceOfMemory() == null) &&(nodesWithOwnerships.get(node).get(id1).getOwnershipValue() != -1)) {
                                        errors.add(new OwnershipCheckerError(pred, errorPointers, OwnershipCheckerError.ErrorType.UNCHECKED, nodesWithOwnerships, nodeOnCorrectTrace));
                                        System.err.println("chyba23");
                                    }                   
                                    // prijde-li odspoda jina hodnota nez -1 a zdroj pameti je pred volanou funkci, tak se tato nova hodnota musi propagovat nahoru
                                }
                            // intraproc. anal.
                            } else {
                                if (nodesWithOwnerships.get(node).get(id1).getOwnershipValue() != -1) {
                                    errors.add(new OwnershipCheckerError(node, errorPointers, OwnershipCheckerError.ErrorType.UNCHECKED, nodesWithOwnerships, nodeOnCorrectTrace));
                                    System.err.println("chyba21");                                
                                }
                            }
                        // na prave strane prirazeni je ukazatel
                        }
                    }
                }
            }
        }
        ownershipInfo.addErrors(errors);
        //return errors;
    }
    
    private Vector<String> functionParamNames(List params) {
        Vector<String> paramNames = new Vector<String>();
        for (int i = 0; i < params.size(); i++) {
            Element par = (Element)params.get(i);
            if (par.getName().equalsIgnoreCase("castExpression")) {
                par = (Element) par.node(1);
            } 
            String name;
            if (par.getName().equalsIgnoreCase("dotExpression")) {
                // struktury se nezpracovavaji
                name = "struktura123";
            } else if (par.getName().equalsIgnoreCase("functionCall")) {
                name = ((Element)par.node(0)).getText() + "()";
            } /*else if (par.getName().equalsIgnoreCase("prefixExpression")) {
                // da se tam jmeno elementu
                name = par.node(0).getName();//par.attributeValue("op") + ;
            }*/ else {
                name = par.getText();
            }
            paramNames.add(name);
        }
        paramNames.addAll(globalPointers);
        return paramNames;
    }
    
    private void functionInputParemetersCheck(Vector<String> paramNames, CFGNode node, FunctionOwnershipInfo calledFunctionOwnershipInfo, Set<CheckerError> errors) {
        // dojde-li k dodatecnemu urceni hodnoty predchoziho parametru zmenou soucasneho, nemelo by to nicemu vadit,
        // protoze je-li pozadovano 0 nebo 1, tak tu hodnotu uz musi mit nebo se v pripade -1 nastavi nebo se nahlasi chyba,
        // a kdyz je -1 a je pozadovano neco jineho, tak se to nastavi
        // a je-li pozadovano -1, tak nicemu nevadi kdyz dojde k dodatecnemu zpresneni z -1 na 0 nebo 1
        int size = calledFunctionOwnershipInfo.getInputPointerOwnershipValues().size();
        for (int i = 0; i < size; i++) {
            String pointerName = paramNames.get(i);
            // musi se porovnat vlastnictvi parametru ve volani funkce a vlastnictvi ukazatele z deklarace funkce
            // zjistene pri intraproc. analyze
            // ---
            // jestlize se jedna o ukazatel
            if (nodesWithOwnerships.get(node).get(pointerName) != null) {
                // ma-li ukazatel hodnotu -1 a je pozadovana jina hodnota, tak se opravi podle pozadavku funkce
                int index = i;
                // jestlize se vola funkce s variabilnim poctem argumentu, zachazi se se vsemi dalsimi parametry jako s poslednim
                if (index >= size) index = size - 1;
                if ((nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() == -1) && calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index) != -1) {
                    if (calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index) == 1) {
                        for (String pName : nodesWithOwnerships.get(node).get(pointerName).getOwnershipSet()) {
                            nodesWithOwnerships.get(node).get(pName).setOwnershipValue(0);
                        }
                    }
                    if (nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue().getValue() == -1) {
                        nodesWithOwnerships.get(node).get(pointerName).getOwnershipSetValue().setValue(calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index));
                    }
                    nodesWithOwnerships.get(node).get(pointerName).setOwnershipValue(calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index));

                // jestlize volana funkce vyzaduje vlastnictvi ukazatele, muze byt jedine vlastnici
                // jestlize volana funkce vyzaduje nevlastnictvi ukazatele, muze byt jedine nevlastnici
                } else if ((calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index) == 1) && (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() != 1)
                    || (calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index) == 0) && (nodesWithOwnerships.get(node).get(pointerName).getOwnershipValue() != 0)) {
                    HashSet<String> erroneousPointers = new HashSet<String>();
                    erroneousPointers.add(paramNames.get(index));
                    errors.add(new OwnershipCheckerError(node, erroneousPointers, OwnershipCheckerError.ErrorType.FUNCTION_INPUT, nodesWithOwnerships, nodeOnCorrectTrace));
                    System.err.println("chyba20");
                }
            }
        }
    }
    
    private void fixPointersAfterFunction(Vector<String> paramNames, Map<String, PointerOwnership> nodeWithOwnership, FunctionOwnershipInfo calledFunctionOwnershipInfo) {
        int size = calledFunctionOwnershipInfo.getInputPointerOwnershipValues().size();
        for (int i = 0; i < paramNames.size(); i++) {
            // jestlize volana funkce pozaduje vlastnictvi, znamena to, ze bude ukazatel ve funkci uvolnen, je treba vse rozpojit a vynulovat
            try {
                int index = i;
                // jestlize se vola funkce s variabilnim poctem argumentu, zachazi se se vsemi dalsimi parametry jako s poslednim
                if (index >= size) index = size - 1;
                if (calledFunctionOwnershipInfo.getInputPointerOwnershipValues().get(index) == 1) {
                    String name = paramNames.get(index);
                    for (String paramName : nodeWithOwnership.get(name).getOwnershipSet()) {
                        nodeWithOwnership.remove(paramName);
                        nodeWithOwnership.put(paramName, new PointerOwnership(paramName, 0, new IntObj(0), null));
                    }
                }
            } catch (NullPointerException npe) {
                // jestlize parametr neni ukazatel, neni o jeho vlastnictvi zaznam
            }
        }
    }
    
    private void fixGlobalPointersAfterFunction(Vector<String> paramNames, Map<String, PointerOwnership> nodeWithOwnership, FunctionOwnershipInfo calledFunctionOwnershipInfo) {
        int indexOfFirstGlobal = 0;
        if (globalPointers.size() != 0) {
            indexOfFirstGlobal = calledFunctionOwnershipInfo.getInputPointerNames().indexOf(globalPointers.get(0));
            for (int i = indexOfFirstGlobal; i < calledFunctionOwnershipInfo.getInputPointerNames().size(); i++) {
                String sourceOfMemory = calledFunctionOwnershipInfo.getSourcesOfMemory().get(i);
                // jedna se o nazev ukazatele, tak jak se uziva ve volane funkce
                String name = calledFunctionOwnershipInfo.getInputPointerNames().get(i);
                // jestlize se jedna o ukazatel
                if (nodeWithOwnership.containsKey(name)) {
                    // jestlize zdoj pameti gl. uk. je ve volane funkci, musi se na tento globalni ukazatelel rozpojit soucasne vazby
                    if (sourceOfMemory == null) {
                        removePointer(name, nodeWithOwnership);
                        HashSet<String> globalSet = new HashSet<String>(calledFunctionOwnershipInfo.getGlobalPointerSets().get(i));
                        if (globalSet.size() > 1) {
                            for (String pointerName : globalSet) {
                                nodeWithOwnership.put(name, new PointerOwnership(name, -1, new IntObj(1), globalSet, null));
                            }
                        } else {
                            nodeWithOwnership.put(name, new PointerOwnership(name, 1, new IntObj(1), globalSet, null));
                        }

                    // jestlize zdroj pameti gl. uk. je uz pred volanou funkci a gl. ukazatel zmenil zdroj, tak se odpoji a pripoji na tento ukazatel                
                    } else {
                        // jedna se o nazev zrojoveho ukazatele, tak jak je pouzivan ve volajici funkci
                        String sourceName = paramNames.get(calledFunctionOwnershipInfo.getInputPointerNames().indexOf(sourceOfMemory));
                        if (!sourceOfMemory.equals(name)) {
                            int ownershipValue = calledFunctionOwnershipInfo.getOutputPointerOwnershipValues().get(i);
                            HashSet<String> ownershipSet = new HashSet<String>();
                            IntObj ownershipSetValue = nodeWithOwnership.get(sourceName).getOwnershipSetValue();
                            String source = nodeWithOwnership.get(sourceName).getSourceOfMemory();
                            removePointer(name, nodeWithOwnership);
                            nodeWithOwnership.put(name, new PointerOwnership(name, ownershipValue, ownershipSetValue, ownershipSet, source));
                            for (String pointerName : nodeWithOwnership.get(sourceName).getOwnershipSet()) {
                                int ownValue = nodeWithOwnership.get(pointerName).getOwnershipValue();
                                nodeWithOwnership.remove(pointerName);
                                nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, ownValue, ownershipSetValue, ownershipSet, source));
                            }
                        }
                        // jestlize zdroj pameti gl. uk. je uz pred volanou funkci a gl. ukazatel je sam sobe zdrojem, tak se nedela nic
                    }
                }
            }
        }
    }
    
    private void removePointer(String name, Map<String, PointerOwnership> nodeWithOwnership) {
        // musi se znova vybudovat vlastnici mnozina a z te je odebran odebirany ukazatel
        HashSet<String> ownershipSet = new HashSet<String>();
        IntObj ownershipSetValue = new IntObj(nodeWithOwnership.get(name).getOwnershipSetValue());
        for (String pointerName : nodeWithOwnership.get(name).getOwnershipSet()) {
            int ownershipValue = nodeWithOwnership.get(pointerName).getOwnershipValue();
            nodeWithOwnership.remove(pointerName);
            nodeWithOwnership.put(pointerName, new PointerOwnership(pointerName, ownershipValue, ownershipSetValue, ownershipSet, null));
        }
        nodeWithOwnership.get(name).getOwnershipSet().remove(name);
        nodeWithOwnership.remove(name);        
    }
    
    /**
     * vrací control flow grafy v reverzním topologickém pořadí podle call grafu
     * @return vector obsahující control flow grafy
     */
    private Vector<ControlFlowGraph> topologicalSortedCallGraph() {
    // funkce pouzije silne souvisle komponenty z call grafu, ktere by meli byt topologicky usporadane a urci navic nejake poradi
    // zpracovani jednotlivych funkci ze techto ss-komponent (v soucasnosti DFS)
        
        List<Element> rootElements = new ArrayList<Element>();
        
        for(ControlFlowGraph cfg : cfgs) {
            rootElements.add(cfg.getFunctionDefinition());
        }

        CallGraph callGraph = new CallGraph(rootElements);
        List<DirectedSubgraph<String, DefaultEdge>> stronglyConnectedSubgraphs = callGraph.stronglyConnected();
        Vector<String> topologicalSortedCG = new Vector();
        DirectedGraph directedGraph = callGraph.generateDirectedGraph();
        Set<DefaultEdge> outgoingEdges = new HashSet<DefaultEdge>(); // hrany, ktere vedou mezi ss-komponentami
        
        for (DirectedSubgraph<String, DefaultEdge> subgraph : stronglyConnectedSubgraphs) {

            DepthFirstIterator it;
            String in = null;
    
            for (DefaultEdge edge: outgoingEdges) {
                if (subgraph.containsVertex(subgraph.getEdgeTarget(edge))) in = subgraph.getEdgeTarget(edge);
            }
                    
            if (in != null) {
                it = new DepthFirstIterator(subgraph, in);
            }
            else {
                it = new DepthFirstIterator(subgraph);
            }
            
            in = null;
            
            // prochazeni silne souvislou komponentou
            while (it.hasNext())
            {
                String next = (String) it.next();
                topologicalSortedCG.add(0, next);
                // nerovna-li se pocet vystupnich hran, znamena to, ze nejaka opousti silne souvislou komponentu
                if (subgraph.outgoingEdgesOf(next).size() != directedGraph.outgoingEdgesOf(next).size()) {
                    // okopiruji se vsechny hrany, ktere opousteji uzel
                    Set<DefaultEdge> edges = new HashSet<DefaultEdge>(directedGraph.outgoingEdgesOf(next));
                    
                    // najdou se hrany, ktere neopousteji ss-komponentu
                    for (Object edge : directedGraph.outgoingEdgesOf(next)) {
                        for (Object edge2 : subgraph.outgoingEdgesOf(next)) {
                            if (edge.toString().equals(edge2.toString())) edges.remove(edge);
                        }
                    }
                    
                    // hrany do jine ss-komponenty se ulozi
                    outgoingEdges.addAll(edges);
                }
            }
        }
        
        Vector<ControlFlowGraph> topol = new Vector<ControlFlowGraph>();
        Vector<ControlFlowGraph> topol2 = new Vector<ControlFlowGraph>();
        
        topol.setSize(topologicalSortedCG.size());
        
        for(ControlFlowGraph cfg : cfgs) {
            // vytvori se poradi CFG podle urceneho poradi funkci
            topol.set(topologicalSortedCG.indexOf(cfg.getFunctionName()), cfg);
        }
        
        // musi se vyhazel prazdna misto po funkcich C, napr. free, malloc apod., tj. tech ktere nemaji CFG
        for(ControlFlowGraph cfg : topol) {
            if (cfg != null) topol2.add(cfg);
        }
        
        return topol2;
    }
    
    private void  printOutcome(String fun) {
        FunctionOwnershipInfo functionOwnershipInfo = functions.get(fun);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("Funkce: " + fun);
        System.out.println("Vstupni omezeni:");
        for (int i=0; i < functionOwnershipInfo.getInputPointerNames().size(); i++) {
            Integer paramValue = functionOwnershipInfo.getInputPointerOwnershipValues().get(i);
            if (paramValue != null) System.out.println(functionOwnershipInfo.getInputPointerNames().get(i) + ": " + paramValue);
        }
        System.out.println("-----");
        System.out.println("Vystupni omezeni:");
        for (int i=0; i < functionOwnershipInfo.getInputPointerNames().size(); i++) {
            Integer paramValue = functionOwnershipInfo.getOutputPointerOwnershipValues().get(i);
            if (paramValue != null) System.out.println(functionOwnershipInfo.getInputPointerNames().get(i) + ": " + paramValue/* + " zdroj: " + functionOwnershipInfo.getSourcesOfMemory().get(i)*/);
        }
        System.out.println("-----");
        System.out.println("Vlastnictvi funkce: " + functionOwnershipInfo.getOutput());
        System.out.println("Zdroj pameti: "  + functionOwnershipInfo.getOutputSourceOfMemory());
        //System.out.println("Volane funkce: " + functionOwnershipInfo.getCalledFunctions().toString());
        //System.out.println("GlobalPointerSets: " + functionOwnershipInfo.getGlobalPointerSets().toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
