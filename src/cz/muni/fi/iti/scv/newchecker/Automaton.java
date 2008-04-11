/**
 * Represents a singe automaton
 */

package cz.muni.fi.iti.scv.newchecker;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import cz.muni.fi.iti.scv.c2xml.CParser;
import cz.muni.fi.iti.scv.newchecker.exceptions.AutomatonRunException;
import cz.muni.fi.iti.scv.newchecker.exceptions.AutomatonSyntaxException;
import cz.muni.fi.iti.scv.props.LoggerConfigurator;
import cz.muni.fi.iti.scv.xml2cfg.CFGNode;
import cz.muni.fi.iti.scv.xml2cfg.ControlFlowGraph;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class Automaton {

    private Set<AutomatonState> states = new HashSet<AutomatonState>();
    private String name = "";
    private String description = "";
    
    private static Logger logger = Logger.getLogger(Automaton.class);
    
    private Map<String, ControlFlowGraph> CFGs = new HashMap<String, ControlFlowGraph>();
    
    private Document sourceDocument;
    
    private AutomatonState initialState = null;
    
    /**
     * Empty, user getInstance... methods instead
     */ 
    private Automaton() {}
    
    static public Automaton getInstanceByDocument(Document document) throws AutomatonSyntaxException {
        Automaton automaton = new Automaton();
        
        automaton.setName(document.getRootElement().selectSingleNode("name").getText());
        automaton.setDescription(((Element) document.getRootElement().selectSingleNode("description")).getTextTrim());
        
        List<Element> stateElements = document.getRootElement().selectNodes("state");
        
        automaton.initialState = null;
        for(Element stateElement: stateElements) {
            AutomatonState state = new AutomatonState(
                    stateElement.selectSingleNode("name").getText(),
                    ((Element) stateElement.selectSingleNode("description")).getTextTrim()
                    );
            automaton.states.add(state);
            
            if(Boolean.valueOf(stateElement.attributeValue("initial"))) {
                if(automaton.initialState != null) {
                    throw new AutomatonSyntaxException("There are more than one initial states");
                }
                automaton.initialState = state;
            }
        }
        
        if(automaton.initialState == null) {
            throw new AutomatonSyntaxException("No initial state");
        }
        
        for(Element stateElement: stateElements) {
            
            AutomatonState fromState = automaton.getStateByName(stateElement.selectSingleNode("name").getText());
            
            List<Node> transitions = stateElement.selectNodes("transition");
            for(Node transition: transitions) {
                Node to = transition.selectSingleNode("to");
                Node trigger = transition.selectSingleNode("trigger");
                Node errorMessage = transition.selectSingleNode("errmsg");
               
                if(trigger == null) {
                    throw new AutomatonSyntaxException("Transition doesn't contain <trigger>");
                }
                // Error transition with trigger
                if(errorMessage != null) {
                    fromState.addTransition(
                            AutomatonTransition.getInstanceError(
                                fromState, 
                                errorMessage.getText(), 
                                trigger.getText()
                                )
                    );
                } 
                // "Regular" transition
                else if(to != null) {
                    fromState.addTransition(
                            AutomatonTransition.getInstance(
                                fromState, 
                                automaton.getStateByName(to.getText()), 
                                trigger.getText()
                            )
                    );
                } 
                // Not a correct transition
                else {
                    // Transition contains "trigger" but to is not specified
                    throw new AutomatonSyntaxException(
                            "Transition contains \"trigger\" but \"to\" is not specified");
                }
                
            }
            
            List<Node> eorTransitions = stateElement.selectNodes("eor");
            if(eorTransitions.size() > 1) {
                throw new AutomatonSyntaxException("State contains more than 1 <eor> transitions.");
            }
            for(Node eorTransition: eorTransitions) {
                Node eorErrorMessage = eorTransition.selectSingleNode("errmsg");
                if(eorErrorMessage == null) {
                    throw new AutomatonSyntaxException("<eor> must contain <errmsg>");
                }
                fromState.addTransition(AutomatonTransition.getInstanceFinalError(fromState, eorErrorMessage.getText()));
            }
            
        }
            
        
        return automaton;
        
        
    }
    
    public void run() throws AutomatonRunException {
        if(sourceDocument == null) {
            throw new AutomatonRunException("No source document loaded");
        }
        
        
        for(Object nodeObj: sourceDocument.selectNodes("//functionDefinition")) {
            Node procedureNode = (Node) nodeObj;
            String procedureName = (procedureNode.selectSingleNode("./declarator/id").getText());
            ControlFlowGraph cfg = getCFGForProcedure(procedureName);
            
            Map<CFGNode, AutomatonState> nodesStatuses = new HashMap<CFGNode, AutomatonState>();
            nodesStatuses.put(cfg.getStartNode(), initialState);
            
            Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
            nodesToDo.add(cfg.getStartNode());
            
            while (!nodesToDo.isEmpty()) {
                boolean errorFound = false;
                CFGNode node = nodesToDo.iterator().next();
                nodesToDo.remove(node);
                
                for (CFGNode succ : node.getSuccessors()) {
                    // Pridame novou dvojici node-state. State se kopiruje od rodice.
                    nodesStatuses.put(succ, nodesStatuses.get(node));
                    Element edge = cfg.getEdgeElement(node, succ);
                    if(edge.getName().equals("functionCall")) {
                        String calledFunctionName = edge.selectSingleNode("./id").getText();
                        AutomatonTransition transition = nodesStatuses.get(succ).getTransitionByTrigger(calledFunctionName);
                        // Muzeme prejit pod nejakym triggerem do jineho stavu
                        if(transition != null) {
                            if(transition.isError()) {
                                errorFound = true;
                                logger.info("Error: "+transition.getErrorMessage()
                                        +" in function "+succ.getCFG().getFunctionName()
                                        +" at node "+succ.getNumber());
                            } else {
                                nodesStatuses.put(succ, transition.getTo());
                                //currentState = transition.getTo();
                                
                            }
                        } 
                        // Nikam nemuzeme prejit
                        else {
                            
                        }
                        
                        //logger.debug("functionCall: "+edge);
                    }
                    if(!errorFound) {
                        nodesToDo.add(succ);
                    } else {
                        //nodesToDo.clear();
                    }
                    if(cfg.getEndNode().equals(succ)) {
                        if(nodesStatuses.get(succ).getEOR() != null && nodesStatuses.get(succ).getEOR().isError()) {
                            logger.info("Error at the end of run: "+nodesStatuses.get(succ).getEOR().getErrorMessage());
                        }
                    }
                    
                }
            }
        
            
        }
        
        
        
    }
    
    public void loadDocument(Document source) {
        this.sourceDocument = source;
    }
    
    public ControlFlowGraph getCFGForProcedure(String procedureName) throws AutomatonRunException {
        if(!CFGs.containsKey(procedureName)) {
            Element functionDefinition = (Element) sourceDocument.selectSingleNode("//functionDefinition[declarator/id = \""+procedureName+"\"]");
            if(functionDefinition == null) {
                throw new AutomatonRunException("Trying to get CFG for function definition ("+procedureName+") that doesn't exist in the source document.");
            }
            ControlFlowGraph graph = new ControlFlowGraph(functionDefinition);
            CFGs.put(procedureName, graph);
        }
        return CFGs.get(procedureName);
    }
    
    /**
     * Searches for the name with given name
     * @param name Name of the state
     * @return State, matching the name
     * @throws AutomatonSyntaxException If no state by this name exists
     */
    public AutomatonState getStateByName(String name) throws AutomatonSyntaxException {
        for(AutomatonState state: states) {
            if(state.getName().equals(name)) {
                return state;
            }
        }
        throw new AutomatonSyntaxException("State by name "+name+" doesn't exist in the automaton.");
    }
    
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    static public void main(String[] args) {
        LoggerConfigurator.doConfigure();
        SAXReader reader = new SAXReader();
        try {
            File xml = new File("/home/xstastn/iti/repo/fa-desc/example.xml");
            Automaton a = Automaton.getInstanceByDocument(reader.read(xml));
            File sourceFile = new File("/home/xstastn/iti/repo/fa-desc/locktest.c");
            Document source = new CParser(new BufferedInputStream(new FileInputStream(sourceFile))).runXmlParser();
            a.loadDocument(source);
            a.run();
        } catch (AutomatonRunException ex) {
            logger.error(null, ex);
        } catch (NullPointerException ex) {
            logger.error(null, ex);
        } catch (RecognitionException ex) {
            logger.error(null, ex);
        } catch (TokenStreamException ex) {
            logger.error(null, ex);
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        } catch (DocumentException ex) {
            logger.error(null, ex);
        } catch (AutomatonSyntaxException ex) {
            logger.error(null, ex);
        }

        
        // Error tests
//        try {
//            File xml = new File("/home/xstastn/iti/repo/fa-desc/tests/error1.xml");
//            Automaton a = Automaton.getInstanceByDocument(reader.read(xml));
//        } catch (DocumentException ex) {
//            logger.error(null, ex);
//        } catch (AutomatonSyntaxException ex) {
//            logger.error(null, ex);
//        }
//        
//        try {
//            File xml = new File("/home/xstastn/iti/repo/fa-desc/tests/error2.xml");
//            Automaton a = Automaton.getInstanceByDocument(reader.read(xml));
//        } catch (DocumentException ex) {
//            logger.error(null, ex);
//        } catch (AutomatonSyntaxException ex) {
//            logger.error(null, ex);
//        }
//
//        try {
//            File xml = new File("/home/xstastn/iti/repo/fa-desc/tests/error3.xml");
//            Automaton a = Automaton.getInstanceByDocument(reader.read(xml));
//        } catch (DocumentException ex) {
//            logger.error(null, ex);
//        } catch (AutomatonSyntaxException ex) {
//            logger.error(null, ex);
//        }

    }
    
    
    
}
