/**
 * Represents a single automaton
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.automatonchecker.exceptions.AutomatonException;
import cz.muni.stanse.automatonchecker.exceptions.AutomatonRunException;
import cz.muni.stanse.automatonchecker.exceptions.AutomatonSyntaxException;
import cz.muni.stanse.props.LoggerConfigurator;
import cz.muni.stanse.xml2cfg.CFGEdge;
import cz.muni.stanse.xml2cfg.CFGNode;
import cz.muni.stanse.xml2cfg.ControlFlowGraph;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author xstastn
 */
public final class Automaton extends Checker {

    private Set<AutomatonState> states = new HashSet<AutomatonState>();
    private String name = "";
    private String description = "";
    private static Logger logger = Logger.getLogger(Automaton.class);
    private static final boolean DEBUG = false;
    
    private AutomatonState initialState = null;
    private Set<AutomatonParam> params = new HashSet<AutomatonParam>();
    
    // The CFGs that will be checked for errors
    private Set<ControlFlowGraph> cfgsToCheck = new HashSet<ControlFlowGraph>();
    
    // Key = function name, Value = CFG of the function
    private Map<String, ControlFlowGraph> CFGs = new HashMap<String, ControlFlowGraph>();


    public Automaton(final Set<ControlFlowGraph> cfgs) {
        super(cfgs);
    }

    public void setConfigurations(final Set<File> configFiles) {
        try {
            getInstanceByDocument(
                    (new SAXReader()).read( configFiles.iterator().next() ) );
        }
        catch (DocumentException ex) {
        }
        catch (AutomatonException ex) {
        }
    }
    
    public String getName() {
        return "AutomatonChecker";
    }
    
    public List<CheckerError> check() {
        return convertAllErrorsFromOld( checkOld( getCFGs() ) );
    }

    ////////////////////////////////////////////////////////////////////////////

    private LinkedList<CheckerError> convertAllErrorsFromOld(
                                           final Set<CheckerErrorOld> oldErrs) {
        
        final LinkedList<CheckerError> result = new LinkedList<CheckerError>();
        for (CheckerErrorOld errIter : oldErrs)
            result.addLast(
                new CheckerError(errIter.getDescription(),
                                 errIter.getDescription(),
                                 CheckerError.ErrorLevel.ERROR,
                                 convertAllTracesFromOld(
                                        errIter.getErrorTracesFromCache() ) ) );

        return result;
    }

    private List<ErrorTrace> convertAllTracesFromOld(
                                           final Set<ErrorTraceOld> oldTraces) {

        final LinkedList<ErrorTrace> result = new LinkedList<ErrorTrace>();
        for (ErrorTraceOld traceIter : oldTraces)
            result.addLast(
                new ErrorTrace(traceIter.getDescription(),
                               traceIter.getDescription(),
                               convertAllCFGNodesFromOld(traceIter) ) );

        return result;
    }

    private List< cz.muni.stanse.utils.Pair<CFGNode,String> >
                       convertAllCFGNodesFromOld(final ErrorTraceOld oldTrace) {
        final LinkedList< cz.muni.stanse.utils.Pair<CFGNode,String> > result =
                new LinkedList< cz.muni.stanse.utils.Pair<CFGNode,String> >();
        for (CFGNode node : oldTrace)
            result.addLast( new cz.muni.stanse.utils.Pair<CFGNode,String>(
                                 node,"No description " + "provided. Sorry.") );

        return result;
    }

    /**
     * Factory method. Creates a new instance of Automaton, described by the XML definition file
     * @param document XML definition file
     * @return New instance of Automaton
     * @throws cz.muni.stanse.automatonchecker.exceptions.AutomatonSyntaxException If the syntax of the document is not correct
     */
    private void getInstanceByDocument(Document document) throws AutomatonSyntaxException {
        Automaton automaton = this; //new Automaton(null);

        automaton.setName(document.getRootElement().selectSingleNode("name").getText());
        automaton.setDescription(((Element) document.getRootElement().selectSingleNode("description")).getTextTrim());


        List<Element> paramNodes = document.getRootElement().selectNodes("param");
        for (Element el : paramNodes) {
            try {
                Class<AutomatonParam> paramClass = (Class<AutomatonParam>) Class.forName(((Element) el.selectSingleNode("class")).getText());
                if (!Arrays.asList(paramClass.getInterfaces()).contains(AutomatonParam.class)) {
                    throw new AutomatonSyntaxException("Param class " + paramClass + " doesn't implement the AutomatonParam interface");
                }
                AutomatonParam instance = paramClass.newInstance();
                String id = ((Element) el.selectSingleNode("id")).getTextTrim();
                instance.setId(id);
                automaton.params.add(instance);

            } catch (ClassNotFoundException ex) {
                logger.error(ex);
            } catch (InstantiationException ex) {
                logger.error(ex);
            } catch (IllegalAccessException ex) {
                logger.error(ex);
            }

        }

        List<Element> stateElements = document.getRootElement().selectNodes("state");

        automaton.initialState = null;
        for (Element stateElement : stateElements) {
            AutomatonState state = new AutomatonState(
                    stateElement.selectSingleNode("name").getText(),
                    ((Element) stateElement.selectSingleNode("description")).getTextTrim());
            automaton.states.add(state);

            if (Boolean.valueOf(stateElement.attributeValue("initial"))) {
                if (automaton.initialState != null) {
                    throw new AutomatonSyntaxException("There are more than one initial states");
                }
                automaton.initialState = state;
            }
        }

        if (automaton.initialState == null) {
            throw new AutomatonSyntaxException("No initial state");
        }

        for (Element stateElement : stateElements) {

            AutomatonState fromState = automaton.getStateByName(stateElement.selectSingleNode("name").getText());

            List<Node> transitions = stateElement.selectNodes("transition");
            for (Node transition : transitions) {
                try {
                    Node to = transition.selectSingleNode("to");
                    Node triggerNode = transition.selectSingleNode("trigger");
                    Node errorMessage = transition.selectSingleNode("errmsg");

                    if (triggerNode == null) {
                        throw new AutomatonSyntaxException("Transition doesn't contain <trigger>");
                    }

                    TransitionTrigger trigger =
                            (TransitionTrigger) Class.forName(((Element) triggerNode.selectSingleNode("class")).getTextTrim()).
                            newInstance();

                    trigger.loadTrigger(((Element) triggerNode.selectSingleNode("pass")).getTextTrim());

                    for (Element paramNode : (List<Element>) triggerNode.selectNodes("param")) {
                        String paramName = paramNode.getTextTrim();
                        trigger.addTriggerParam(automaton.getParamById(paramName));
                    }


                    // Error transition with trigger
                    if (errorMessage != null) {

                        fromState.addTransition(AutomatonTransition.getInstanceError(fromState, errorMessage.getText(), trigger));
                    } else if (to != null) {
                        fromState.addTransition(AutomatonTransition.getInstance(fromState, automaton.getStateByName(to.getText()), trigger));
                    } else {
                        // Transition contains "trigger" but "to" is not specified
                        throw new AutomatonSyntaxException("Transition contains \"trigger\" but \"to\" is not specified");
                    }
                } catch (InstantiationException ex) {
                    logger.error(ex);
                } catch (IllegalAccessException ex) {
                    logger.error(ex);
                } catch (ClassNotFoundException ex) {
                    logger.error(ex);
                }

            }

            List<Node> eorTransitions = stateElement.selectNodes("eor");
            if (eorTransitions.size() > 1) {
                throw new AutomatonSyntaxException("State contains more than 1 <eor> transitions.");
            }
            for (Node eorTransition : eorTransitions) {
                Node eorErrorMessage = eorTransition.selectSingleNode("errmsg");
                if (eorErrorMessage == null) {
                    throw new AutomatonSyntaxException("<eor> must contain <errmsg>");
                }
                fromState.addTransition(AutomatonTransition.getInstanceFinalError(fromState, ((Element) eorErrorMessage).getTextTrim()));
            }

        }

    }
    
    private Set<CheckerErrorOld> runForFunctionCfg(ControlFlowGraph cfg) {
        Set<CheckerErrorOld> errors = new HashSet<CheckerErrorOld>();
        
        //=======================
        //   DRAW GRAPH
        //=======================
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
        nodesToDo.add(cfg.getStartNode());

        EdgeStack edgeStack = new EdgeStack();

        // Zde si pamatuji, jake bezici automaty uz jsem na dane hrane poustel a znova to nedelam. 
        // Kdyz s danym bezicim automatem na dane hrane jednou pohnu, vicekrat uz to neudelam.
        // Ke kazde hrane je pridelena mnozina uspporadanych svojic idRunningAutomaton -- idCurrentState
        // Vse jsou jenom ID pro rychlost (nepocita se, jenom se vraci hodnota)
        Map<CFGEdge, Set<Pair<Integer, Integer>>> edgeHistory = new HashMap<CFGEdge, Set<Pair<Integer, Integer>>>();


        // For the first node and its edge
        CFGNode startNode = cfg.getStartNode();
        for (CFGNode succ : startNode.getSuccessors()) {
            EdgeWithAutomata edge = new EdgeWithAutomata(CFGEdge.getInstance(startNode, succ));
            edgeStack.push(edge);
            if(DEBUG) {
                logger.debug("Pridal jsem na zasobnik (zacatek) "+edge);
            }
        }

        // ========================================
        //      MAIN LOOP
        // ========================================
        while (!edgeStack.isEmpty()) {
            EdgeWithAutomata currentEdgeWithAutomata = edgeStack.pop();
            CFGEdge currentEdge = currentEdgeWithAutomata.getEdge();
            RunningAutomataSet currentRunningAutomata = currentEdgeWithAutomata.getAutomataSet();
            if(DEBUG) {
                logger.debug("--------------------------\nJedu po hrane "+currentEdgeWithAutomata);
            }


            AutomatonTransition transition = null;
            boolean automatonInErrorState = false;

            // Try to find a transition in a running automaton.
            for(RunningAutomaton runningAutomaton: currentRunningAutomata.getAutomata()) {
                transition = runningAutomaton.getTransitionByTrigger(currentEdge);
                automatonInErrorState = runningAutomaton.isInErrorState();
                // Pokud bychom nechteli stejnou chybu hlasit dvakrat, je do podminky potreba pridat 
                // && !automatonInErrorState
                if(transition != null) {
                    // If the transition is the "Error transition" the automaton is deleted from the set of running automata
                    // and the error message is dumped
                    if(transition.isError()) {
                        
                        logger.info("Error: "+transition.getErrorMessage()
                            +" in automaton "+runningAutomaton
                            +" in function "+currentEdge.getFrom().getCFG().getFunctionName()
                            +" at edge "+currentEdge);
                        Set<ErrorTraceOld> traces = new HashSet<ErrorTraceOld>();
                        traces.add(runningAutomaton.traceAsErrorTrace());
                        errors.add(
                                new DefaultCheckerError(currentEdge.getTo(), 
                                traces, 
                                transition.getErrorMessage()));
                        runningAutomaton.setInErrorState(true);
                    } else {
                        // The transition is not an error transition
                        runningAutomaton.setCurrentState(transition.getTo());
                    }
                    // Break if a transition was found
                    break;
                }
            }

            // If no transition in running automaton was found, try to start a new automaton
            if(!automatonInErrorState && transition == null) {
                transition = initialState.getTransitionByTriggeredTrigger(currentEdge);
                // We found a transition that fits
                if(transition != null) {
                    try {
                        RunningAutomaton newRunningAutomaton = new RunningAutomaton(this, transition.getFrom());
                        Set<AssignedParam> assignedParams = 
                                transition.getTrigger().
                                assignParams(currentEdge.getFrom(), currentEdge.getTo());
                        newRunningAutomaton.addAssignedParams(assignedParams);
                        // Put the the "from" node as the beginning of the trace
                        newRunningAutomaton.getTrace().add(currentEdge.getFrom());

                        currentRunningAutomata.getAutomata().add(newRunningAutomaton);


                        // Error message is dumped.
                        // Automaton automatically put in the virtual state
                        if(transition.isError()) {
                            logger.info("Error in newly running: "+transition.getErrorMessage()
                                +" in function "+currentEdge.getFrom().getCFG().getFunctionName()
                                +" at edge "+currentEdge);
                            newRunningAutomaton.setInErrorState(true);
                            Set<ErrorTraceOld> traces = new HashSet<ErrorTraceOld>();
                            traces.add(newRunningAutomaton.traceAsErrorTrace());
                            errors.add(
                                    new DefaultCheckerError(currentEdge.getTo(), 
                                    traces, 
                                    transition.getErrorMessage()));
                        }
                        
                        
                        // The transition is not and Error transition, change the state of the newly created automaton
                        else {
                            newRunningAutomaton.setCurrentState(transition.getTo());
                        }

                    } catch (AutomatonException ex) {
                        logger.error(null, ex);
                    }
                }
            }


            // ============================================================
            //          LAST NODE TEST
            // ============================================================
            if(currentEdge.getTo().equals(cfg.getEndNode())) {
                for(RunningAutomaton runningAutomaton: currentRunningAutomata.getAutomata()) {
                    if(runningAutomaton.getCurrentState() != null 
                            && runningAutomaton.getCurrentState().getEOR() != null
                            && runningAutomaton.getCurrentState().getEOR().isError()) {
                        logger.info(
                                "Error at the end of run: "+runningAutomaton.getCurrentState().getEOR().getErrorMessage()
                                +" in automaton "+runningAutomaton
                                +" in function "+currentEdge.getFrom().getCFG().getFunctionName()
                                +" At edge: "+currentEdge);
                    }
                }
            }



            // ============================================================
            //          HISTORY
            // ============================================================
            // The history has to be filled sooner thatn adding edges to stack 
            // (history is used to filter the duplicate automata)
            //if (!currentRunningAutomata.isEmpty()) {
                if (!edgeHistory.containsKey(currentEdge)) {
                    if(DEBUG) {
                        logger.debug("Hrana "+currentEdge+" nebyla v histroii, pridavam");
                    }
                    edgeHistory.put(currentEdge, new HashSet<Pair<Integer, Integer>>());
                }

                for (RunningAutomaton automaton : currentRunningAutomata.getAutomata()) {
                    edgeHistory.get(currentEdge).add(Pair.getInstance(automaton.getId(), automaton.getCurrentStateId()));
                    if(DEBUG) {
                        logger.debug("Do historie pridano k hrane "+currentEdge+" "+automaton);
                    }
                    // Pridava se jenom "spodni" node, prvni se privada pri startu automatu
                    automaton.getTrace().add(currentEdge.getTo());
                }

            //}

            // currentRunningAutomata.getAutomata().removeAll(automataToBeDeleted);

            // ============================================================
            //          ADD NEXT EDGES ON STACK
            // ============================================================
            // Pridani dalsich hran na zasobnik
            CFGNode toNode = currentEdge.getTo();
            Set<CFGNode> successors = toNode.getSuccessors();

            if (successors.size() > 0) {
                CFGNode currentSuccessor;

                CFGEdge nextEdge;

                Iterator<CFGNode> it = successors.iterator();

                // First child -- put on the stack
                currentSuccessor = it.next();
                nextEdge = CFGEdge.getInstance(toNode, currentSuccessor);

                EdgeWithAutomata edgeWithAutomata = new EdgeWithAutomata(nextEdge, currentRunningAutomata);
                boolean deletedAll = false;
                // If the edge is already in the history, it has to be filtered
                if (edgeHistory.containsKey(nextEdge)) {
                    edgeWithAutomata.deleteDuplicateAutomata(edgeHistory.get(nextEdge));
                    deletedAll = edgeWithAutomata.getAutomataSet().isEmpty();

                }
                if(!deletedAll && (!edgeHistory.containsKey(nextEdge) || !edgeWithAutomata.getAutomataSet().isEmpty())) {
                    edgeStack.push(edgeWithAutomata);
                    if(DEBUG) {
                        logger.debug("Pridal jsem na zasobnik "+edgeWithAutomata);
                    }
                }



                // The node can have one more successor
                // No more than two successors are allowed by the definition.
                if(it.hasNext()) {
                    CFGNode otherSuccessor = it.next();
                    CFGEdge otherEdge = CFGEdge.getInstance(toNode, otherSuccessor);
                    EdgeWithAutomata otherEdgeWithAutomata = new EdgeWithAutomata(otherEdge, currentRunningAutomata.getCopy());
                    deletedAll = false;
                    // If the edge is already in the history, it has to be filtered
                    if (edgeHistory.containsKey(otherEdge)) {
                        otherEdgeWithAutomata.deleteDuplicateAutomata(edgeHistory.get(otherEdge));
                        deletedAll = otherEdgeWithAutomata.getAutomataSet().isEmpty();
                    }
                    if(!deletedAll && (!edgeHistory.containsKey(otherEdge) || !otherEdgeWithAutomata.getAutomataSet().isEmpty())) {
                        edgeStack.push(otherEdgeWithAutomata);
                        if(DEBUG) {
                            logger.debug("Pridal jsem na zasobnik alternativni cestu "+otherEdgeWithAutomata);
                        }
                    }
                }

            }
            if(DEBUG) {
                logger.debug("Na konci smycky je na zasobniku "+ edgeStack);
                logger.debug("Na konci smycky je v historii "+ edgeHistory);
            }

        }
        
        return errors;

    }

    private void run() throws AutomatonRunException {
        
        for (ControlFlowGraph cfg: cfgsToCheck) {
            this.runForFunctionCfg(cfg);
        }
    }

    
    
    /**
     * Searches for the name with given name
     * @param name Name of the state
     * @return State, matching the name
     * @throws AutomatonSyntaxException If no state by this name exists
     */
    private AutomatonState getStateByName(String name) throws AutomatonSyntaxException {
        for (AutomatonState state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        throw new AutomatonSyntaxException("State by name " + name + " doesn't exist in the automaton.");
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private String getDescription() {
        return description;
    }

    private void setName(String name) {
        this.name = name;
    }
/*
    public String getName() {
        return name;
    }
*/
    private Set<AutomatonParam> getParams() {
        return params;
    }

    private void setParams(Set<AutomatonParam> params) {
        this.params = params;
    }

    /**
     * Returns param with the given id. Null if the automaton doesn't include param with this id
     * @param id ID of the param to be found
     * @return param with the given id. Null if the automaton doesn't include param with this id
     */
    private AutomatonParam getParamById(String id) {
        for (AutomatonParam param : params) {
            if (param.getId().equals(id)) {
                return param;
            }
        }
        return null;
    }

//     public ControlFlowGraph getCFGForProcedure(String procedureName) throws AutomatonRunException {
//        if (!CFGs.containsKey(procedureName)) {
//            Element functionDefinition = (Element) sourceDocument.selectSingleNode("//functionDefinition[declarator/id = \"" + procedureName + "\"]");
//            if (functionDefinition == null) {
//                throw new AutomatonRunException("Trying to get CFG for function definition (" + procedureName + ") that doesn't exist in the source document.");
//            }
//            ControlFlowGraph graph = new ControlFlowGraph(functionDefinition);
//            CFGs.put(procedureName, graph);
//        }
//        return CFGs.get(procedureName);
//    }

    
    private void addCFG(ControlFlowGraph cfg) {
        cfgsToCheck.add(cfg);
    }

    private void addAllCFG(Set<ControlFlowGraph> cfgs) {
        cfgsToCheck.addAll(cfgs);
    }

    private void removeCFG(ControlFlowGraph cfg) {
        cfgsToCheck.remove(cfg);
    }

    private void removeAllCFG(Set<ControlFlowGraph> cfgs) {
        cfgsToCheck.removeAll(cfgs);
    }

    private void clearCFG() {
        cfgsToCheck.clear();
    }

    /**
     * Runs the checker for the CFGs of the procedures added by {@link #addCFG addCFG} and {@link #addAllCFG addAllCFG} methods
     * @return Set of errors found by the checker
     */
    private Set<CheckerErrorOld> checkOld(final Set<ControlFlowGraph> cfgs) {
        Set<CheckerErrorOld> errors = new HashSet<CheckerErrorOld>();
        for(ControlFlowGraph cfg: cfgs) {
            errors.addAll(this.runForFunctionCfg(cfg));
        }
        
        return errors;
        
    }
    
}