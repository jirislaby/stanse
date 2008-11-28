/*
 * ControlFlowGraph.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 * TODO: 1) goto statement and labels
 *       2) short-circuit evaluation
 *
 */

package cz.muni.stanse.xml2cfg;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;

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

    private CFGNode entryNode;
    private CFGNode exitNode;
    
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

	Element d, declarator = (Element)functionDefinition.node(1);
	while ((d = (Element)declarator.selectSingleNode("declarator")) != null)
		declarator = d;

	Element id = (Element)declarator.selectSingleNode("id");
	if (id == null) {
	    dump("strange functionDefinition", functionDefinition);
	    return;
	}

	functionName = id.getText();

	// find compoundStatement
	Element compoundStatement = (Element)functionDefinition.selectSingleNode("compoundStatement");

	endNode = joinNodes(endNode, nodeCreator(startNode,compoundStatement));

        {
        entryNode = new CFGNode(this);
        entryNode.addSucc(startNode);
        startNode.addPred(entryNode);

        final Map<CFGNode,Element> entryMap = new HashMap<CFGNode,Element>();
        entryMap.put(startNode,(Element)functionDefinition.selectSingleNode(
                                                       ".//compoundStatement"));
        expressions.put(entryNode,entryMap);

        exitNode = new CFGNode(this);
        exitNode.addPred(endNode);
        endNode.addSucc(exitNode);

        final Map<CFGNode,Element> exitMap = new HashMap<CFGNode,Element>();
        exitMap.put(exitNode,(Element)functionDefinition.selectSingleNode(
                                                       ".//compoundStatement"));
        expressions.put(endNode,exitMap);
        }

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

    public CFGNode getEntryNode() { return entryNode; }
    public CFGNode getExitNode() { return exitNode; }

    public CFGEdge getEntryEdge() {
        return CFGEdge.getInstance(getEntryNode(),getStartNode());
    }
    public CFGEdge getExitEdge() {
        return CFGEdge.getInstance(getEndNode(),getExitNode());
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

    private void dump(final String reason, final Element subtree) {
	System.err.println("CFG: " + reason + " in function '" +
		this.functionName + "' at:");
	Thread.dumpStack();
	System.err.println("Code:");
	OutputFormat format = OutputFormat.createPrettyPrint();
	try {
	    XMLWriter writer = new XMLWriter(System.err, format);
	    writer.write(subtree);
	} catch (IOException e) {
	    System.err.println(" <none>");
	}
	System.err.println("");
    }

    /**
     * Algorithm for recursive transormation from element to sub-graph
     * @param start start node of sub-graph
     * @param actualElement element to transfer
     * @return end node of sub-graph
     */
    private CFGNode nodeCreator(CFGNode start, Element actualElement) {

	if (actualElement.getName().equals("statement"))
	    return nodeCreator(start, (Element)actualElement.node(0));

	//=== unroll compoundStatement ========================================
	if (actualElement.getName().equals("compoundStatement")) {
	    /* (declaration, statement, functionDefinition)*
	     */

	    CFGNode end = start;

	    for (int i = 0, size = actualElement.nodeCount(); i < size; i++) {
		Node node = actualElement.node(i);
		if (!(node instanceof Element))
		    continue;

		Element element = (Element)node;
		end = nodeCreator(end, element);
		if (end == null) {

		    for (i++; i < size; i++) {
			node = actualElement.node(i);
			if (!(node instanceof Element))
			    continue;

			/* ignore unreachable labelStatement so far */
			element = (Element)node;
			if (element.nodeCount() > 0 && element.node(0).
				    getName().equals("labelStatement"))
			    break;

			dump("unreachable code (compoundStatement)",
				(Element)node);
			break;
		    }

		    break;
		}
	    }

	    return end;
	}

	//=== unroll ifStatement ==============================================
	if (actualElement.getName().equals("ifStatement")) {
	    /* 0: expression
	     * 1: statement
	     */
	    CFGNode end = new CFGNode(this);

	    // false branch
	    addEdge(start, end, (Element)actualElement.node(0), false);

	    // true branch
	    CFGNode trueStart = new CFGNode(this);
	    addEdge(start, trueStart, (Element)actualElement.node(0), true);

	    CFGNode trueEnd = nodeCreator(trueStart,
			(Element)actualElement.node(1));

	    return joinNodes(trueEnd,end);
	}

	//=== unroll ifElseStatement ==========================================
	if (actualElement.getName().equals("ifElseStatement")) {
	    /* 0: expression
	     * 1: statement
	     * 2: statement
	     */
	    CFGNode end = new CFGNode(this);

	    // true branch
	    CFGNode trueStart = new CFGNode(this);
	    addEdge(start, trueStart, (Element)actualElement.node(0), true);

	    CFGNode trueEnd = nodeCreator(trueStart,
			(Element)actualElement.node(1));

	    // false branch
	    CFGNode falseStart = new CFGNode(this);
	    addEdge(start, falseStart,
			(Element)actualElement.node(0), false);

	    CFGNode falseEnd = nodeCreator(falseStart,
			(Element)actualElement.node(2));

	    return joinNodes(falseEnd, trueEnd);

	}

	//=== unroll whileStatement ===========================================
	if (actualElement.getName().equals("whileStatement")) {
	    /* 0: expression
	     * 1: statement
	     */
	    CFGNode end = new CFGNode(this);
	    breakStack.push(end);
	    continueStack.push(start);

	    // false branch
	    addEdge(start, end, (Element)actualElement.node(0), false);

	    // true branch
	    CFGNode trueStart = new CFGNode(this);
	    addEdge(start, trueStart, (Element)actualElement.node(0), true);

	    CFGNode trueEnd = nodeCreator(trueStart,
			(Element)actualElement.node(1));

	    joinNodes(start,trueEnd);

	    breakStack.pop();
	    continueStack.pop();
	    return end;
	}

	//=== unroll doStatement ==============================================
	if (actualElement.getName().equals("doStatement")) {
	    /* 0: statement
	     * 1: expression
	     */
	    CFGNode end = new CFGNode(this);
	    breakStack.push(end);

	    CFGNode whileEnd = new CFGNode(this);
	    continueStack.push(whileEnd);

	    CFGNode tmpWhileEnd = nodeCreator(start,
			(Element)actualElement.node(0));
	    whileEnd = joinNodes(tmpWhileEnd, whileEnd);

	    // false branch
	    addEdge(whileEnd, end, (Element)actualElement.node(1), false);
	    // true branch
	    addEdge(whileEnd, start, (Element)actualElement.node(1), true);

	    breakStack.pop();
	    continueStack.pop();
	    return end;

	}

	//=== unroll forStatement =============================================
	if (actualElement.getName().equals("forStatement")) {
	    /* 0: (expression | declaration)
	     * 1: expression
	     * 2: expression
	     * 3: statement
	     */
	    CFGNode forStart = nodeCreator(start,
			(Element)actualElement.node(0));

	    CFGNode end = new CFGNode(this);
	    breakStack.push(end);

	    // false branch
	    addEdge(forStart, end, (Element) actualElement.node(1), false);

	    // true branch
	    CFGNode trueStart = new CFGNode(this);
	    addEdge(forStart, trueStart, (Element) actualElement.node(1), true);

	    CFGNode trueEnd = new CFGNode(this);
	    continueStack.push(trueEnd);

	    trueEnd = joinNodes(trueEnd,
		    nodeCreator(trueStart, (Element)actualElement.node(3)));

	    joinNodes(forStart,
		    nodeCreator(trueEnd, (Element)actualElement.node(2)));

	    breakStack.pop();
	    continueStack.pop();
	    return end;

	}

	//=== unroll switchStatement ==========================================
	if (actualElement.getName().equals("switchStatement")) {
	    /* 0: expression -> (id, ...)
	     * 1: statement -> compoundStatement -> S=statement*
	     *
	     * S -> (caseLabelStatement, defaultLabelStatement, UNREACHABLE)
	     */

	    CFGNode endSwitch = new CFGNode(this);
	    breakStack.push(endSwitch);

	    CFGNode end = null;
	    CFGNode falseSwitch = start;
	    CFGNode defaultSwitch = null;

	    Element statement = (Element)actualElement.node(1);
	    Element compoundStatement = (Element)statement.node(0);

	    for (int i = 0, size = compoundStatement.nodeCount(); i < size; i++) {
		Node node = compoundStatement.node(i);
		if (!(node instanceof Element))
		    continue;

		statement = (Element)node;
		Element element = (Element)statement.node(0);

		if (element.getName().equals("caseLabelStatement")) {
		    /* 0: expression
		     * 1: statement
		     */
		    CFGNode source = falseSwitch;

		    falseSwitch = new CFGNode(this);

		    if (end == null)
			end = new CFGNode(this);

		    CFGNode trueSwitch = end;

		    //T source -> trueSwitch
		    addEdge(source, trueSwitch, (Element)element.node(0), true);
		    //F source -> falseSwitch
		    addEdge(source, falseSwitch, (Element)element.node(0), false);

		    //label next to label
		    statement = (Element)element.node(1);
		    Element caseLabelExpression = (Element)statement.node(0);
		    while (caseLabelExpression.getName().equals("caseLabelStatement") ||
			   caseLabelExpression.getName().equals("defaultLabelStatement")) {

			if (caseLabelExpression.getName().equals("caseLabelStatement")) {
			    source = falseSwitch;

			    falseSwitch = new CFGNode(this);

			    //T source -> trueSwitch
			    addEdge(source, trueSwitch,
					(Element)caseLabelExpression.node(0),
					true);
			    //F source -> falseSwitch
			    addEdge(source, falseSwitch,
					(Element)caseLabelExpression.node(0),
					false);

			    statement = (Element)caseLabelExpression.node(1);
			    caseLabelExpression = (Element)statement.node(0);
			} else {
			    defaultSwitch = end;

			    statement = (Element)caseLabelExpression.node(0);
			    caseLabelExpression = (Element)statement.node(0);
			}

		    }

		    end = nodeCreator(end, caseLabelExpression);
		} else if (element.getName().equals("defaultLabelStatement")) {
		    if (end == null)
			end = new CFGNode(this);

		    defaultSwitch = end;

		    //label next to label
		    statement = (Element)element.node(0);
		    Element labelExpression = (Element)statement.node(0);
		    if (labelExpression.getName().equals("caseLabelStatement"))
			dump("redundant code (switch)", element);
		    else
			end = nodeCreator(end, (Element) element.node(0));
		} else {
		    if (end != null) // is reachable
			end = nodeCreator(end, element);
		    else
			dump("unreachable code (switch)", (Element)node);
		}
	    } /* for */

	    if (defaultSwitch != null) {
		falseSwitch = joinNodes(defaultSwitch, falseSwitch);
	    } else {
		falseSwitch = joinNodes(end, falseSwitch);
		endSwitch = joinNodes(falseSwitch, endSwitch);
	    }

	    joinNodes(endSwitch, end);

	    return endSwitch;
	}

	//=== unroll emptyStatement ===========================================
	if (actualElement.getName().equals("emptyStatement")) {
	    CFGNode end = new CFGNode(this);

	    addEdge(start, end, actualElement, null);

	    return end;
	}

	//=== unroll returnStatement ==========================================
	if (actualElement.getName().equals("returnStatement")) {
	    /* expression?
	     */
	    addEdge(start, endNode, actualElement, null);

	    return null;
	}

	//=== unroll breakStatement ===========================================
	if (actualElement.getName().equals("breakStatement")) {
	    CFGNode breakNode = breakStack.peek();
	    addEdge(start, breakNode, actualElement, null);

	    return null;
	}

	//=== unroll continueStatement ========================================
	if (actualElement.getName().equals("continueStatement")) {
	    CFGNode continueNode = continueStack.peek();
	    addEdge(start, continueNode, actualElement, null);

	    return null;
	}

	if (actualElement.getName().equals("expression")) {
	    /* intConst | realConst | stringConst | id | *Expression |
	     * compoundStatement | functionCall | arrayAccess | ...
	     */
	    CFGNode end = new CFGNode(this);
	    addEdge(start, end, actualElement, null);
	    if (actualElement.nodeCount() > 0)
		    return nodeCreator(end, (Element)actualElement.node(0));
	    return end;
	}

	//=== otherwise (expression) ==========================================
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

	if (temp == null)
	    return temp = node; // break-continue propagation
	if (node == null)
	    return node = temp; // break-continue propagation

	for (CFGNode from : expressions.keySet()) {
	    if (from == temp) {
		Map<CFGNode, Element> toMap = expressions.get(from);
		expressions.remove(from);
		expressions.put(node, toMap);
	    }
	    Set<CFGNode> tmpNodeSet = new HashSet<CFGNode>();
	    tmpNodeSet.addAll(expressions.get(from).keySet());
	    for (CFGNode to : tmpNodeSet) {
		if (to == temp) {
		    Element element = expressions.get(from).get(to);
		    expressions.get(from).remove(to);
		    expressions.get(from).put(node, element);
		}
	    }
	}


	for (CFGNode from : conditions.keySet()) {
	    if (from == temp) {
		Map<CFGNode, Boolean> toMap = conditions.get(from);
		conditions.remove(from);
		conditions.put(node, toMap);
	    }
	    Set<CFGNode> tmpNodeSet = new HashSet<CFGNode>();
	    tmpNodeSet.addAll(conditions.get(from).keySet());
	    for (CFGNode to : tmpNodeSet) {
		if (to == temp) {
		    Boolean bool = conditions.get(from).get(to);
		    conditions.get(from).remove(to);
		    conditions.get(from).put(node, bool);
		}
	    }
	}

	if (this.startNode == temp)
	    startNode=node;
	if (this.endNode == temp)
	    endNode=node;

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
