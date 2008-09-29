/*
 * ControlFlowGraph.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 * TODO: 1) goto statement and labels
 *       2) short-circuit evaluation
 *
 */

package cz.muni.fi.iti.scv.xml2cfg;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

/**
 * Represents a control-flow graph of a procedure
 */
public class ControlFlowGraph {
    
    private Document document; // xml document with definition
    
    private String functionName; // name of the corespondenting function
    private Element functionDefinition; // function definition in xml
    
    private CFGNode startNode; // start of the control-flow graph
    private CFGNode endNode;   // end of the control-flow graph
    
    private Stack<CFGNode> breakStack = new Stack<CFGNode>(); // return-nodes for breakSatement
    private Stack<CFGNode> continueStack = new Stack<CFGNode>(); // return-nodes for continueSatement
    
    private Map<CFGNode, Map<CFGNode, Element>> expressions = new HashMap<CFGNode, Map<CFGNode, Element>>(); // maping graph edges to expressions
 
    private Map<CFGNode, Map<CFGNode, Boolean>> conditions = new HashMap<CFGNode, Map<CFGNode, Boolean>>(); // maping graph edges to type of condition
    
    
    /**
     * Creates a new instance of ControlFlowGraph
     * @param functionDefinition function definition of procedure representated as org.dom4j.Element with name "functionDefinition"
     */
    public ControlFlowGraph(Element functionDefinition) {
        
        this.functionDefinition = functionDefinition;
        document = functionDefinition.getDocument();
        
        startNode = new CFGNode(this);
        endNode = new CFGNode(this);
        
        // find declarator
        Element declaratorId = (Element) functionDefinition.selectSingleNode("declarator/id");
        functionName = declaratorId.getText();
        
        // find compoundStatement
        Element compoundStatement = (Element) functionDefinition.selectSingleNode("compoundStatement");
        
        endNode = joinNodes(endNode, nodeCreator(startNode,compoundStatement));
        
        /*
        //Debugging info
        printNodesFromStart();
        printNodesFromEnd();
        System.out.println();*/
        
    }
    
    /**
     * Returns start of control-flow graph
     * @return start node 
     */
    public CFGNode getStartNode() {
        return startNode;
    }
    
    /**
     * Returns end of control-flow graph
     * @return end node 
     */
    public CFGNode getEndNode() {
        return endNode;
    }
    
    /**
     * Returns org.dom4j.Element representating expression's definition of edge
     * @param from start node of edge
     * @param to end node of edge
     * @return expression's definition
     */
    public Element getEdgeElement(CFGNode from, CFGNode to) {
        if (!expressions.containsKey(from)) {
            return null;
        }
        return expressions.get(from).get(to);
    }
    
    /**
     * Returns condition type on edge      
     * @param from start node of edge
     * @param to end node of edge
     * @return condition type
     */
    public Boolean getEdgeConditionType(CFGNode from, CFGNode to) {
        if (!conditions.containsKey(from)) {
            return null;
        }
        return conditions.get(from).get(to);
    }
    
    /**
     * Algorithm for recursive transormation from element to sub-graph 
     * @param start start node of sub-graph
     * @param actualElement element to transfer
     * @return end node of sub-graph
     */
    private CFGNode nodeCreator(CFGNode start, Element actualElement) {
        
        //=== unroll compoundStatement =========================================
        if (actualElement.getName().equals("compoundStatement")) {
            
            CFGNode end = start;
            
            for (int i = 0, size = actualElement.nodeCount(); i < size; i++) {
                Node node = actualElement.node(i);
                if (node instanceof Element) {
                    Element element = (Element) node;
                    end = nodeCreator(end, element);
                    if (end==null) {
                        
                        for (i++; i < size; i++) {
                            node = actualElement.node(i);
                            if (node instanceof Element) {
                                System.out.println("CFG: unreachable code in function '" + this.functionName + "'");
                                break;
                            }
                        }
                        
                        break;
                        
                    }
                }
            }
            
            return end;
        }
        
        //=== unroll ifStatement ===============================================
        if (actualElement.getName().equals("ifStatement")) {
            
            CFGNode end = new CFGNode(this);
            
            // false branch
            addEdge(start, end, (Element) actualElement.node(0), false);        
            
            // true branch
            CFGNode trueStart = new CFGNode(this);
            addEdge(start, trueStart, (Element) actualElement.node(0), true);
                       
            CFGNode trueEnd = nodeCreator(trueStart, (Element) actualElement.node(1));
            
            return joinNodes(trueEnd,end);
        }
        
        //=== unroll ifElseStatement ===========================================
        if (actualElement.getName().equals("ifElseStatement")) {
            
            CFGNode end = new CFGNode(this);
            
            // true branch
            CFGNode trueStart = new CFGNode(this);
            addEdge(start, trueStart, (Element) actualElement.node(0), true);
            
            CFGNode trueEnd = nodeCreator(trueStart, (Element) actualElement.node(1));
            
            // false branch
            CFGNode falseStart = new CFGNode(this);
            addEdge(start, falseStart, (Element) actualElement.node(0), false);
            
            CFGNode falseEnd = nodeCreator(falseStart, (Element) actualElement.node(2));
            
            return joinNodes(falseEnd,trueEnd);
            
        }
        
        //=== unroll whileStatement ============================================
        if (actualElement.getName().equals("whileStatement")) {
            
            CFGNode end = new CFGNode(this);
            breakStack.push(end);
            continueStack.push(start);
            
            // false branch
            addEdge(start, end, (Element) actualElement.node(0), false);
            
            // true branch
            CFGNode trueStart = new CFGNode(this);
            addEdge(start, trueStart, (Element) actualElement.node(0), true);
                       
            CFGNode trueEnd = nodeCreator(trueStart, (Element) actualElement.node(1));
            
            joinNodes(start,trueEnd);
            
            breakStack.pop();
            continueStack.pop();
            return end;
        }
        
        //=== unroll doStatement ==============================================
        if (actualElement.getName().equals("doStatement")) {
            
            CFGNode end = new CFGNode(this);
            breakStack.push(end);
            
            CFGNode whileEnd = new CFGNode(this);
            continueStack.push(whileEnd);
            
            CFGNode tmpWhileEnd = nodeCreator(start, (Element) actualElement.node(1)); // c2xml bug = spravne ma byt node(0)
            whileEnd = joinNodes(tmpWhileEnd, whileEnd); 

            // false branch
            addEdge(whileEnd, end, (Element) actualElement.node(0), false); // c2xml bug = spravne ma byt node(1)
            
            // true branch
            addEdge(whileEnd, start, (Element) actualElement.node(0), true); // c2xml bug = spravne ma byt node(1)
            
            breakStack.pop();
            continueStack.pop();
            return end;
            
        }
        
        //=== unroll forStatement ==============================================
        if (actualElement.getName().equals("forStatement")) {
            
            CFGNode forStart = nodeCreator(start, (Element) actualElement.node(0));
            
            CFGNode end = new CFGNode(this);
            breakStack.push(end);
            
            // false branch
            addEdge(forStart, end, (Element) actualElement.node(1), false); 
            
            // true branch
            CFGNode trueStart = new CFGNode(this);
            addEdge(forStart, trueStart, (Element) actualElement.node(1), true); 
            
            CFGNode trueEnd = new CFGNode(this);
            continueStack.push(trueEnd);
            
            trueEnd = joinNodes(trueEnd, nodeCreator(trueStart, (Element) actualElement.node(3)));
            
            joinNodes(forStart, nodeCreator(trueEnd, (Element) actualElement.node(2)));
            
            breakStack.pop();
            continueStack.pop();
            return end;
            
        }
        
        //=== unroll switchStatement ==============================================
        if (actualElement.getName().equals("switchStatement")) {
            
            CFGNode endSwitch = new CFGNode(this);
            breakStack.push(endSwitch);
            
            CFGNode end = null;
            //CFGNode trueSwitch = null;
            CFGNode falseSwitch = start;
            CFGNode defaultSwitch = null;
            
            Element compoundStatement = (Element) actualElement.node(1);
            
            for (int i = 0, size = compoundStatement.nodeCount(); i < size; i++) {
                Node node = compoundStatement.node(i);
                if (node instanceof Element) {
                    Element element = (Element) node;
                    
                    if (element.getName().equals("caseLabelStatement")) {
                        
                        CFGNode source = falseSwitch;
                        
                        falseSwitch = new CFGNode(this);
                        
                        CFGNode trueSwitch;
                        if (end == null) {
                            trueSwitch = new CFGNode(this);
                        } else {
                            trueSwitch = end;
                        }                     
                        
                        //T source -> trueSwitch  
                        addEdge(source, trueSwitch, (Element) element.node(0), true); 
                        //F source -> falseSwitch
                        addEdge(source, falseSwitch, (Element) element.node(0), false); 
                       
                        end = trueSwitch;
                        
                        //label next to label
                        Element caseLabelExpression = (Element) element.node(1);
                        while (caseLabelExpression.getName().equals("caseLabelStatement") ||
                               caseLabelExpression.getName().equals("defaultLabelStatement")) {
                            
                            if (caseLabelExpression.getName().equals("caseLabelStatement")) {
                                
                                source = falseSwitch;

                                falseSwitch = new CFGNode(this);

                                //T source -> trueSwitch      
                                addEdge(source, trueSwitch, (Element) caseLabelExpression.node(0), true);
                                //F source -> falseSwitch
                                addEdge(source, falseSwitch, (Element) caseLabelExpression.node(0), false);
                                
                                caseLabelExpression = (Element) caseLabelExpression.node(1);
                                
                            } else {
                                
                                defaultSwitch = end;
                                
                                caseLabelExpression = (Element) caseLabelExpression.node(0);
                            
                            }
                        
                        }
                        
                        end = nodeCreator(end, caseLabelExpression);
                        
                    } 
                    else if (element.getName().equals("defaultLabelStatement")) {
                        
                        if (end == null) {
                            defaultSwitch = new CFGNode(this);
                        } else {
                            defaultSwitch = end;
                        }
                        
                        end = defaultSwitch;
                        
                        //label next to label
                        Element labelExpression = (Element) element.node(0);
                        if (labelExpression.getName().equals("caseLabelStatement")) {
                            
                              System.out.println("CFG: duplicity code (switch) in function '" + this.functionName + "'");          
                                                
                        }
                        
                        end = nodeCreator(end, (Element) element.node(0));
                    
                    }    
                    else {
                    
                        if (end != null) { // is reachable
                            end = nodeCreator(end, element);                                                        
                        } 
                        else System.out.println("CFG: unreachable code (switch) in function '" + this.functionName + "'");
                        
                    }
                    
                }
            }

            if (defaultSwitch!=null) {
                falseSwitch = joinNodes(defaultSwitch,falseSwitch);                
            } else {
                falseSwitch = joinNodes(end,falseSwitch);
                endSwitch = joinNodes(falseSwitch,endSwitch);
            } 
            
            joinNodes(endSwitch,end);

            return endSwitch;
             
        }        
        
        //=== unroll emptyStatement ==============================================
        if (actualElement.getName().equals("emptyStatement")) {
            
            CFGNode end = new CFGNode(this);
            
            addEdge(start, end, actualElement, null);                               
            
            return end;
        }
        
        //=== unroll returnStatement ==============================================
        if (actualElement.getName().equals("returnStatement")) {
            
            addEdge(start, endNode, actualElement, null);
            
            return null;
        }
        
        //=== unroll breakStatement ==============================================
        if (actualElement.getName().equals("breakStatement")) {
            
            CFGNode breakNode = breakStack.peek();
            addEdge(start, breakNode, actualElement, null);
            
            return null;
        }
        
        //=== unroll continueStatement ==============================================
        if (actualElement.getName().equals("continueStatement")) {
            
            CFGNode continueNode = continueStack.peek();
            addEdge(start, continueNode, actualElement, null);
            
            return null;
        }
        
        //=== otherwise (expression) ===========================================
        CFGNode end = new CFGNode(this);        
        addEdge(start, end, actualElement, null);
        
        return end;
        
    }
    
    /**
     * Joins two nodes
     * @param node first node
     * @param temp second node
     * @return final node
     */
    private CFGNode joinNodes(CFGNode node, CFGNode temp) {
        
        if (temp==null) return temp=node; // break-continue propagation
        if (node==null) return node=temp; // break-continue propagation
        
        for (CFGNode from : expressions.keySet()) {
            if (from==temp) {
                Map<CFGNode, Element> toMap = expressions.get(from);
                expressions.remove(from);
                expressions.put(node, toMap);
            }
            Set<CFGNode> tmpNodeSet  = new HashSet<CFGNode>();
            tmpNodeSet.addAll(expressions.get(from).keySet());
            for (CFGNode to : tmpNodeSet) {
                if (to==temp) {
                    Element element = expressions.get(from).get(to);
                    expressions.get(from).remove(to);
                    expressions.get(from).put(node, element);
                }
            }            
        }
        
               
        for (CFGNode from : conditions.keySet()) {
            if (from==temp) {
                Map<CFGNode, Boolean> toMap = conditions.get(from);
                conditions.remove(from);
                conditions.put(node, toMap);
            }
            Set<CFGNode> tmpNodeSet  = new HashSet<CFGNode>();
            tmpNodeSet.addAll(conditions.get(from).keySet());
            for (CFGNode to : tmpNodeSet) {
                if (to==temp) {
                    Boolean bool = conditions.get(from).get(to);
                    conditions.get(from).remove(to);
                    conditions.get(from).put(node, bool);
                }
            }            
        }
        
        if (this.startNode==temp) {startNode=node;} 
        if (this.endNode==temp) {endNode=node;}         
        
        node.joinWithNode(temp);
        
        return node;
    }
    
    public String toString() {
        return "" + document.getName() + ", " + functionName + "()";
    }
    
    /**
     * Returns xml element with function definition 
     * @return function definition 
     */
    public Element getFunctionDefinition() {
        return functionDefinition;
    }
    
    /**
     * Returns function name of procedure
     * @return function name 
     */    
    public String getFunctionName() {
        return functionName;
    }
    
    private void printNodesFromStart() {
        
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
        
        nodesToDo.add(this.getStartNode());       
        
        System.out.println("======== Print CFG form Start (" + functionName + ")");
        
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            System.out.println(node);
            
            nodesDone.add(node);
            
            for (CFGNode succ : node.getSuccessors()) { 
                System.out.println("  -> "+succ);
                
                if (!nodesDone.contains(succ)) nodesToDo.add(succ);
            }
        }
    }
    
    private void printNodesFromEnd() {
        
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
        
        nodesToDo.add(this.getEndNode());       
        
        System.out.println("======== Print CFG form End (" + functionName + ")");
        
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            System.out.println(node);
            
            nodesDone.add(node);
            
            for (CFGNode pred : node.getPredecessors()) { 
                System.out.println("  <- "+pred);
                
                if (!nodesDone.contains(pred)) nodesToDo.add(pred);
            }
        }
    }
    
    public Set<CFGNode> getAllNodes() {
          
        Set<CFGNode> nodesToDo = new HashSet<CFGNode>();
        Set<CFGNode> nodesDone = new HashSet<CFGNode>();
        
        nodesToDo.add(this.getStartNode());       
        
        
        
        while (!nodesToDo.isEmpty()) {
            CFGNode node = nodesToDo.iterator().next();
            nodesToDo.remove(node);
            
            
            nodesDone.add(node);
            
            for (CFGNode succ : node.getSuccessors()) { 
                
                if (!nodesDone.contains(succ)) nodesToDo.add(succ);
            }
        }
        
        return nodesDone;
    }
    
    private void addEdge(CFGNode from, CFGNode to, Element element, Boolean condition) {
        
        from.addSucc(to);       
        to.addPred(from);
                            
        if (!expressions.containsKey(from)) {
            Map<CFGNode, Element> toMap = new HashMap<CFGNode, Element>();
            expressions.put(from,toMap);
        }
        
        if (!conditions.containsKey(from)) {
            Map<CFGNode, Boolean> toMap = new HashMap<CFGNode, Boolean>();
            conditions.put(from,toMap);
        }
       
        //System.out.println(from + " " + to + " :" + element.getName() + " " + condition);
        
        expressions.get(from).put(to,element);
        conditions.get(from).put(to,condition);
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ControlFlowGraph other = (ControlFlowGraph) obj;
        if (this.functionDefinition != other.functionDefinition && (this.functionDefinition == null || !this.functionDefinition.equals(other.functionDefinition))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.functionDefinition != null ? this.functionDefinition.hashCode() : 0);
        return hash;
    }

 
    
    
}
