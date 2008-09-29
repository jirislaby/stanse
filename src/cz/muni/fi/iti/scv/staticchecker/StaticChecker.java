/*
 * StaticChecker.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.staticchecker;

import cz.muni.fi.iti.scv.checker.*;
import cz.muni.fi.iti.scv.xml2cfg.*;
import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

/**
 * Represents an universal static checker
 */
public class StaticChecker {
    
    private Document source; //document to check    
    private Set<StaticCheckerDef> defs; //set of static checker definitions (checkers)
    
    private Map<String, ControlFlowGraph> cfgs; //map function name to its control-flow graph
    
    private Map<StaticCheckerDef, //checker definition
                Map<Map<StaticCheckerDefVar, List<String>>, //var assign
                    Map<ControlFlowGraph, //control-flow graph
                        Map<Set<Character>, //start state
                            Map<CFGNode, //node
                                Set<Character> //node evaluation
                            >
                        >
                    >
                >
            > nodesEvaluation;
    
    private Stack<Set<Character>> recursionS; //recursion detection (start state)
    private Stack<ControlFlowGraph> recursionF; //recursion detection (function)
    
    private static Logger logger = Logger.getLogger(StaticChecker.class);
    
    /**
     * Creates a new instance of StaticChecker
     */
    public StaticChecker(Document source) {
        //initialize
        this.source = source; 
        defs = new HashSet<StaticCheckerDef>();        
        cfgs = new HashMap<String, ControlFlowGraph>();  
        recursionS = new Stack<Set<Character>>();
        recursionF = new Stack<ControlFlowGraph>();        
        nodesEvaluation = new HashMap<StaticCheckerDef, Map<Map<StaticCheckerDefVar, List<String>>, Map<ControlFlowGraph, Map<Set<Character>, Map<CFGNode, Set<Character>>>>>>();
        
        //find all functions and create control-flow graphs
        for (Element functionDef : (List<Element>) source.selectNodes("//functionDefinition")) {         
            ControlFlowGraph cfg = new ControlFlowGraph(functionDef);  
            if (cfgs.put(cfg.getFunctionName(), cfg)!=null) {
                //nested functions with same id
                logger.error("StaticChecker: !!! document contains nested function definition with the same name !!!");
            }
        }         
        
    }

       
    /**
     * Add definition to static checker
     * @param def static checker definition
     */
    public void addDefinition(Document definition) {
        defs.add(new StaticCheckerDef(definition));       
    }
    
    
    /**
     * Check source document with all added definitions
     * @return set of found errors
     */  
    public Set<CheckerError> check() { 
        
        Set<CheckerError> errors = new HashSet<CheckerError>();
        
        nodesEvaluation.clear();   
        recursionF.clear();
        recursionS.clear();
            
        //for all checker definitions
        for (StaticCheckerDef def : defs) {   
            
            nodesEvaluation.put(def, new HashMap<Map<StaticCheckerDefVar, List<String>>, Map<ControlFlowGraph, Map<Set<Character>, Map<CFGNode, Set<Character>>>>>());
            
            Set<StaticCheckerDefVar> vars = def.getVariables();
            
            Map<StaticCheckerDefVar,Set<List<String>>> varAssigns = null;
           
            if (!vars.isEmpty()) { //definition has variables
                
                varAssigns = new HashMap<StaticCheckerDefVar,Set<List<String>>>();
                
                //find all possible assigns to all variables in definition
                for (StaticCheckerDefVar var : vars) {
                    varAssigns.put(var, var.init(source));
                }  
                
                //find combinations of possible variable's assigns (in cause of more variables in definition)
                Set<Map<StaticCheckerDefVar,List<String>>> defAssigns = new HashSet<Map<StaticCheckerDefVar,List<String>>>();
                StaticCheckerDefVar.varComb(varAssigns, new HashMap<StaticCheckerDefVar,List<String>>(), defAssigns);
                
                //for every variable's assign
                for (Map<StaticCheckerDefVar,List<String>> defAssign : defAssigns) {
                    nodesEvaluation.get(def).put(defAssign, new HashMap<ControlFlowGraph, Map<Set<Character>, Map<CFGNode, Set<Character>>>>());
                
                    if (def.isInterprocedural()) { //interprocedural check
                        for (String cfgName : (def.interproceduralFunctions().isEmpty() ? cfgs.keySet() : def.interproceduralFunctions())) {
                            //#################################### interprocedural with vars
                            ControlFlowGraph cfg = cfgs.get(cfgName);
                            if (cfg != null) {
                                recursionF.push(cfg);
                                recursionS.push(def.startState());
                                check(def, defAssign, cfg, def.startState());
                                errors.addAll(findErrors(def, defAssign, cfg, def.startState()));     
                                recursionF.pop();
                                recursionS.pop();
                            }
                        }
                    } else { //intraprocedural check
                        for (ControlFlowGraph cfg : cfgs.values()) { //for every function
                            //#################################### intraprocedural with vars
                            check(def, defAssign, cfg, null);
                            errors.addAll(findErrors(def,defAssign, cfg, null));
                        }
                    }
                                        
                }//end for (defAssign)               
                
            } else { //definition without variables
                
                nodesEvaluation.get(def).put(null, new HashMap<ControlFlowGraph, Map<Set<Character>, Map<CFGNode, Set<Character>>>>());
            
                if (def.isInterprocedural()) { //interprocedural check
                    for (String cfgName : (def.interproceduralFunctions().isEmpty() ? cfgs.keySet() : def.interproceduralFunctions())) {
                        //#################################### interprocedural without vars
                        ControlFlowGraph cfg = cfgs.get(cfgName);
                        if (cfg != null) {
                            recursionF.push(cfg);
                            recursionS.push(def.startState());
                            check(def, null, cfg, def.startState());
                            errors.addAll(findErrors(def, null, cfg, def.startState()));     
                            recursionF.pop();
                            recursionS.pop();
                        }                       
                    }
                } else { //intraprocedural check
                    for (ControlFlowGraph cfg : cfgs.values()) { //for every function
                        //#################################### intraprocedural without vars                        
                        check(def, null, cfg, null);
                        errors.addAll(findErrors(def, null, cfg, null));
                    }
                }
            
            }
        
        }//end for (def)
        
        // HTML report output
        if(errors.isEmpty()) {
            logger.info("No errors found");
        } else {
            System.out.println("Logging an errror!!!");
            logger.info("Error found in "+source.getName());
            for(CheckerError error: errors) {
                logger.info(error.getDescription());
            }
        }
        
        return errors;
    }
    
    
    /**
     * Check cfg with concrete checker definition and variables assign 
     * @param def static checker definition
     * @param defAssign assign to variables in this static checker definition
     * @param cfg control flow graph to be check
     * @param startState start-state or null (intraprocedural)
     */  
    private void check(StaticCheckerDef def, Map<StaticCheckerDefVar,List<String>> defAssign, ControlFlowGraph cfg, Set<Character> startState) {
        
        boolean interprocedural = true; // interprocedural check
        if (startState == null) {
            startState = def.startState();
            interprocedural = false;
        }
        
        
        { //print debug info
            StringBuffer output = new StringBuffer("StaticChecker: check ");
            for (int i=1; i<recursionF.size(); i++) output.append("  ");
            logger.debug(output + cfg.getFunctionName() + " " + startState.toString());
        }
                
        //if it's necessary initialice part of nodesEvaluation
        if (!nodesEvaluation.get(def).get(defAssign).containsKey(cfg))
            nodesEvaluation.get(def).get(defAssign).put(cfg, new HashMap<Set<Character>, Map<CFGNode, Set<Character>>>());
        if (!nodesEvaluation.get(def).get(defAssign).get(cfg).containsKey(startState))
            nodesEvaluation.get(def).get(defAssign).get(cfg).put(startState, new HashMap<CFGNode, Set<Character>>());
           
        //START OF MAIN ALGORITHM (evaulating nodes)
        nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).put(cfg.getStartNode(), startState);
        
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        nodesToDo.add(cfg.getStartNode());
        
        boolean succNull;
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            for (CFGNode succ : node.getSuccessors()) {
                
                //if it's necessary initialice part of nodesEvaluation
                succNull = false;
                if (!nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).containsKey(succ)) {
                    nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).put(succ, new HashSet<Character>());
                    succNull = true;
                }
                
                //copy previous evaulation
                Set<Character> oldStateSet = new HashSet<Character>();
                oldStateSet.addAll(nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ));
                
                Set<Character> propag = def.propagate(defAssign, cfg.getEdgeElement(node, succ), cfg.getEdgeConditionType(node, succ), nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node));
                
                if (interprocedural && propag == null) { //interprocedural extension
                    boolean findInterprocedural = false;
                    //find all function calls on the edge
                    List<Element> functionCalls = cfg.getEdgeElement(node, succ).selectNodes(".//self::node()[name()=\"functionCall\"]/id[1]");

                    for (Element functionCall : functionCalls) { //for every function call
                        
                        String fuctionName = functionCall.getText();
                        Set<Character> functionSS = nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node);
             
                        ControlFlowGraph functionCFG = cfgs.get(fuctionName);
                        if (functionCFG != null) { //we have cfg of called function
                            findInterprocedural = true;
                            if (nodesEvaluation.get(def).get(defAssign).containsKey(functionCFG)) { //we have already checked same function

                                // find start state
                                boolean findSS = false;
                                for (Set<Character> sS : nodesEvaluation.get(def).get(defAssign).get(functionCFG).keySet()) {
                                    if (sS.containsAll(functionSS) && functionSS.containsAll(sS)) {
                                        functionSS = sS;
                                        findSS = true;
                                        break;
                                    }
                                }

                                if (!findSS) { //we need new check
                                    if (!inRecursionStack(functionCFG,functionSS)) { //check for recursion
                                        recursionF.push(functionCFG);
                                        recursionS.push(functionSS);
                                        check(def, defAssign, functionCFG, functionSS);
                                        recursionF.pop();
                                        recursionS.pop();
                                    }                             
                                }
                            } else { //we need new check
                                 if (!inRecursionStack(functionCFG,functionSS)) { //check for recursion
                                    recursionF.push(functionCFG);
                                    recursionS.push(functionSS);
                                    check(def, defAssign, functionCFG, functionSS);
                                    recursionF.pop();
                                    recursionS.pop(); 
                                 }
                            }


                            if (nodesEvaluation.get(def).get(defAssign).get(functionCFG).get(functionSS).get(functionCFG.getEndNode())==null) {
                                nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ).addAll(functionSS);
                            } else {
                                nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ).addAll(nodesEvaluation.get(def).get(defAssign).get(functionCFG).get(functionSS).get(functionCFG.getEndNode()));
                            }    

                        } 
                        
                        //nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ).addAll(def.propagate(defAssign, cfg.getEdgeElement(node, succ), cfg.getEdgeConditionType(node, succ), nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node)));        

                    } // end for (functionCall)
                    
                    if (!findInterprocedural) {
                        nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ).addAll(nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node));
                    }
                } //end of interprocedural extension
                else {
                    //propagation
                    if (propag == null) propag = nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node); //no rule found
                    nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ).addAll(propag);
                }
                
                if (succNull || !oldStateSet.equals(nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(succ))) {
                    nodesToDo.add(succ);
                }
                
            }
            
        } //end while
        //END OF MAIN ALGORITHM (evaulating nodes)
        
    }    
    
    
    /**
     * Is function with start state in recursion Stack (waiting to evaluate)?
     * @param cfg control flow graph
     * @param startState start state
     * @return true/false
     */    
    private boolean inRecursionStack(ControlFlowGraph cfg, Set<Character> state) {
        for (int i = 0; i < recursionF.size(); i++) {
            if (recursionF.get(i)==cfg && recursionS.get(i)==state) return true;     
        }
        return false;
    }
    
    
    /**
     * Find errors in nodes evaulation
     * @param def static checker definition
     * @param defAssign assign to variables in this static checker definition
     * @param cfg control flow graph to be controlled
     * @param startState start-state or null (intraprocedural)
     * @return set of found errors
     */  
    private Set<CheckerError> findErrors(StaticCheckerDef def, Map<StaticCheckerDefVar,List<String>> defAssign, ControlFlowGraph cfg, Set<Character> startState) {
        
        boolean interprocedural = true; // interprocedural check
        if (startState == null) {
            startState = def.startState();
            interprocedural = false;
        }
        
        Set<CheckerError> errors = new HashSet<CheckerError>();
        
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
        
        nodesToDo.add(cfg.getStartNode());
              
        Map<CFGNode,Set<Character>> errorCopyNodeToStateSet = new HashMap<CFGNode,Set<Character>>();
        errorCopyNodeToStateSet.putAll(nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState));
        
        while (!nodesToDo.isEmpty()) {
            
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            nodesDone.add(node);
            
            for (CFGNode succ : node.getSuccessors()) {
                
                //find errors                
                for (StaticCheckerDefError errorRule : def.getViolatedErrorRules(defAssign, cfg.getEdgeElement(node, succ), nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node))) {
                    errors.add(new StaticCheckerError(node,this,def,defAssign,errorRule,errorCopyNodeToStateSet,startState));
                }
                
                if (interprocedural) { //interprocedural extension
                    
                    //find all function calls on the edge
                    List<Element> functionCalls = cfg.getEdgeElement(node, succ).selectNodes(".//self::node()[name()=\"functionCall\"]/id[1]");
                
                    for (Element functionCall : functionCalls) { //for every function call
                        
                        String fuctionName = functionCall.getText();   
                        Set<Character> functionSS = nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(node);

                        ControlFlowGraph functionCFG = cfgs.get(fuctionName);

                        if (functionCFG != null) { //we have cfg of called function
                            
                            if (nodesEvaluation.get(def).get(defAssign).containsKey(functionCFG)) {
                                
                                //find start state
                                boolean findSS = false;
                                for (Set<Character> sS : nodesEvaluation.get(def).get(defAssign).get(functionCFG).keySet()) {
                                    if (sS.containsAll(functionSS) && functionSS.containsAll(sS)) {
                                        functionSS = sS;
                                        findSS = true;
                                        break;
                                    }
                                }

                                if (findSS) {//we have result
                                    if (!inRecursionStack(functionCFG,functionSS)) {
                                        recursionF.push(functionCFG);
                                        recursionS.push(functionSS);
                                        errors.addAll(findErrors(def, defAssign, functionCFG, functionSS));
                                        recursionF.pop();
                                        recursionS.pop();     
                                    }                          
                                }
                            } 
                        }               
                    } //end for (functionCall)     
                }  //end of interprocedural extension  
                    
                if (!nodesDone.contains(succ)) nodesToDo.add(succ);
            }
        } //end while
        
        //find erros on endNode
        if (recursionF.size() == 1 || !interprocedural) {
            for (StaticCheckerDefError errorRule : def.getViolatedErrorRules(defAssign, nodesEvaluation.get(def).get(defAssign).get(cfg).get(startState).get(cfg.getEndNode()))) {
               errors.add(new StaticCheckerError(cfg.getEndNode(),this,def,defAssign,errorRule,errorCopyNodeToStateSet,startState));
            }
        }
        
        return errors;
    }
     
    
    /**
     * For error, start-state and some cfg node find state set in checker's results 
     * @param error static checker error
     * @param node node in control flow graph of procedure coresponding to error
     * @param cfgStartState cfg's start state to select correct results    
     * @return state set of node 
     */    
    protected Set<Character> getState(StaticCheckerError error, CFGNode node, Set<Character> cfgStartState) {
        return  nodesEvaluation.get(error.getDef()).get(error.getDefAssign()).get(node.getCFG()).get(cfgStartState).get(node);
    }

}
