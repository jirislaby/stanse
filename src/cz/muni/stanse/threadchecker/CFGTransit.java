package cz.muni.stanse.threadchecker;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.props.Properties.VerbosityLevel;
import cz.muni.stanse.threadchecker.graph.CFGGraphState;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.xmlpatterns.XMLPattern;
import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * Singleton class executes algorithm which traverse CFG the way, where every
 * cycles (while, for etc) are traversed only once.
 *
 * @brief Algorithm description:
 * if(node is marked as visited) -> stop process and ask for another from queue
 * if(node has only one predecessor) {
 *      -> process node's element and mark it as visited
 * } else {
 *      if(all node's predecessors are visited) {
 *          -> try to join all nodes with same CFGNode
 *          -> process actual node's element and mark it as visited
 *      } else {
 *          if(predecessor's is part of a cycle
 *              @note(predecessor's ID is higher then actual node)) {
 *              -> process all actual node's successors which compose cycles and
 *              -> add them to queue
 *          } else {
 *              -> try to join nodes with same CFGNode and add actual node to
 *              -> queue's bottom
 *          }
 *      }
 * }
 * @author Jan Kučera
 */
public class CFGTransit {

	private final static Logger logger =
		Logger.getLogger(CFGTransit.class.getName());
	private static CheckerSettings settings = CheckerSettings.getInstance();
	private static Map<String, List<XMLPattern>> patternMap =
		new HashMap<String, List<XMLPattern>>();
	private static VerbosityLevel verbLevel =
		Stanse.getInstance().getVerbosityLevel();

	/*
	 * This static code fills patterMap with function names for faster searching
	between patterns.
	 */
	static {
		Document document = settings.getConfigDocument();
		Element funcNameElement;
		String funcName;
		List<XMLPattern> newList;
		XMLPattern pattern;
		List<Element> elementPatterns =
			(List<Element>)document.getRootElement().selectNodes("//patterns/pattern");

		for (Element el : elementPatterns) {
			pattern = new XMLPattern(el);
			funcNameElement = (Element) el.selectSingleNode(
				"descendant-or-self::functionCall/id[1]");
			if (funcNameElement == null)
				continue;

			funcName = funcNameElement.getText();

			if (patternMap.containsKey(funcName)) {
				patternMap.get(funcName).add(pattern);
			} else {
				newList = new Vector<XMLPattern>();
				newList.add(pattern);
				patternMap.put(funcName, newList);
			}
		}
	}

	/**
	 * Traverse CFG graph with reducing same outputs from different branches
	 * and return instance of Function which holds every possible state of
	 * function.
	 * @param cfg CFG representing C function
	 * @return Function generated from traversing CFG graph
	 */
	public static Function analyseCFG(CFGHandle cfg) {
		final LinkedList<Function> queue = new LinkedList<Function>();
		final CFGGraphState graphState = new CFGGraphState(cfg);
		List<CFGNode> waitForNodes;
		settings.addOnStack(cfg);
		Function actualState = new Function(cfg);
		Function clonedState;
		CFGNode actualNode;

		logger.info("\n===============");
		logger.info("Analyzing CFG: " + cfg.getFunctionName());
		logger.info("===============\n");
		queue.add(actualState);

		while (!queue.isEmpty()) {
			actualState = queue.poll();
			actualNode = actualState.getActualNode();

			showProgress(actualState, graphState, queue);
			if (graphState.getVisitedNodes().contains(actualNode))
				continue;

			if (actualNode.getPredecessors().size() < 2) {
				processState(actualNode, actualState, graphState, queue);
				continue;
			}

			waitForNodes = graphState.waitingFor(actualNode);

			if (waitForNodes.isEmpty()) {
				joinNodesInQueue(actualState, queue);
				processState(actualNode, actualState, graphState, queue);
				continue;
			} else {
				Set<CFGNode> cycles = graphState.detectCycles(actualNode, waitForNodes);
				if (cycles.isEmpty()) {
					queue.offer(actualState);
					continue;
				}

				joinNodesInQueue(actualState, queue);
				queue.offer(actualState);

				clonedState = new Function(actualState);
				//Analyse node without marking it as visited or adding succesors
				//to queue. They will be added after returning from the cycle.

				analyseStatement(actualNode, clonedState, graphState);

				for (CFGNode branchNode : cycles)
					if (!graphState.getVisitedNodes().contains(branchNode))
						processState(branchNode, clonedState, graphState, queue);
				continue;
			}
		}
		settings.removeFromOnStack(cfg); //Remove cfg from Stack
		logger.debug("\n===============");
		logger.debug("CFG " + cfg.getFunctionName() + " result:\n" + actualState);
		logger.debug("===============\n");
		return actualState;
	}

	/**
	 * Method pick up all functions from queue which held the same CFGNode as
	 * the one in Function function and execute join procedure on them.
	 * Subsequently they are erased from queue.
	 * @param function Function
	 * @param queue LinkedList queue with Functions waiting to processed
	 */
	private static void joinNodesInQueue(Function function,
		LinkedList<Function> queue) {
		//All predecessors are visited, try to join all nodes in queue
		List<Function> nodeToErase = new Vector<Function>();
		for (Function state : queue) {
			//Compare ID of CFGNodes
			if (function.getActualNode().getNumber()
				== state.getActualNode().getNumber()) {
				function.join(state);
				nodeToErase.add(state);
			}
		}
		queue.removeAll(nodeToErase);
	}

	/**
	 * Method first marks this node as visited, then passes node's element to
	 * analyse and adds node's succesor to process queue.
	 * @param actualNode carries XML element
	 * @param function Function holding all possible states in this node.
	 * @param graphState represent state of analysed graph
	 * @param queue nodes prepared to be processed in next round
	 */
	private static void processState(CFGNode actualNode, Function function,
		CFGGraphState graphState, LinkedList<Function> queue) {
		graphState.getVisitedNodes().add(actualNode);
		logger.debug("Processing node " + actualNode + " with lockset!");

		if (actualNode.getElement() == null) {
			if (actualNode.equals(graphState.getCfg().getEndNode())) {
				queue.clear();
			}
			return;
		} else {
			analyseStatement(actualNode, function, graphState);
		}

		for (CFGNode succ : actualNode.getSuccessors())
			if (!graphState.getVisitedNodes().contains(succ))
				queue.addFirst(new Function(function, succ));
	}

	/**
	 * Method prints state of CFG transit - show actual node, all visited nodes
	 * and queue state.
	 * Checking wheter logger debug mode is enabled saves memory -> showing
	 * all nodes in CFG generates very long strings.
	 * @param function
	 * @param graphState
	 * @param queue
	 */
	private static void showProgress(Function function,
		CFGGraphState graphState, LinkedList<Function> queue) {
		if ((!logger.isEnabledFor(Priority.DEBUG))
			|| (!verbLevel.equals(VerbosityLevel.HIGH))) {
			return;
		}
		CFGNode actualNode = function.getActualNode();
		StringBuilder result = new StringBuilder("Actual:");
		result.append(actualNode.getNumber()).
			append('[').append(function).append("]\nVisited:[");
		for (CFGNode node : graphState.getVisitedNodes())
			result.append(node.getNumber()).append(',');
		result.append("]\nIn queue:[");

		for (Function state : queue)
			result.append(state.getActualNode().getNumber()).append(',');
		result.append(']');

		logger.debug(result.toString());
	}

	/**
	 * Determines, whether this node should be memorized in backTrack.
	 * @param node Node which should be analyzed
	 * @param function Function where CodeAnalyzer will insert all changes.
	 * @param graphState represent state of analysed graph
	 */
	private static void analyseStatement(CFGNode node, Function function,
		CFGGraphState graphState) {

		CFGNode startTrack;
		CFGNode predecessor;
		Integer nodeID;

		//This cycle determines, whether this CFGNode should be memorized for
		//backTracking.
		for (FunctionState data : function.getFunctionStates()) {
			nodeID = data.getBackTrack().peekLast().getCFGNodeID();
			startTrack = graphState.getIdToNode(nodeID);
			predecessor = startTrack;
			while (!predecessor.equals(node)
				&& predecessor.getSuccessors().size() < 2) {

				//Stops infinite cycling
				if (predecessor.getSuccessors().size() < 1) {
					break;
				}
				if (predecessor.getNumber()
					> predecessor.getSuccessors().get(0).getNumber()) {
					break;
				}
				predecessor = predecessor.getSuccessors().get(0);
			}

			if (predecessor.getSuccessors().size() > 1 && !startTrack.equals(node)
				&& !predecessor.equals(node)) {
				data.getBackTrack().addLast(new BackTrack(
					node.getNumber(), node.getLine(), node.getColumn(),
					"nothing important here - already locked: " +
					data.getLockStack(), function.getFileName()));
			} else {
				continue;
			}
		}

		CFGTransit.chooseAction(node, function);
	}

	/**
	 * Method choose proper code analysis from user's settings. If no pattern
	 * matches, tries to analyse as normal functionCall.
	 * @param node CFGNode contains Element describing source code
	 * @param function Function with actual state of analysis
	 */
	public static void chooseAction(CFGNode node, Function function) {
		Element element = node.getElement();
		Element parameter;
		String funcName;
		List<XMLPattern> patterns = new Vector<XMLPattern>();
		//Did match any pattern - now just try to find functionCalls
		List<Node> functionCalls = (List<Node>) element.selectNodes(
			"descendant-or-self::functionCall/id[1]");

		for (Node functionId : functionCalls)
			if (CFGTransit.patternMap.get(functionId.getText()) != null)
				patterns.addAll(CFGTransit.patternMap.get(functionId.getText()));

		for (XMLPattern pattern : patterns) {
			final Pair<Boolean, XMLPatternVariablesAssignment> result =
				pattern.matchesXMLElement(element);
			if (result.getFirst().booleanValue()) {
				if (pattern.getName().equals("lockFunction")) {
					parameter = result.getSecond().getVarsMap().get("lock");
					CodeAnalyzer.analyzeLockingFunction(
						node, function, parameter);
					return;
				} else if (pattern.getName().equals("unlockFunction")) {
					parameter = result.getSecond().getVarsMap().get("lock");
					CodeAnalyzer.analyzeUnlockingFunction(
						node, function, parameter);
					return;
				} else if (pattern.getName().equals("createThreadFunction")) {
					parameter = result.getSecond().getVarsMap().get("function");
					CodeAnalyzer.analyzeThreadFunction(node, function, parameter);
					return;
				} else {
					logger.error("Wrong type in choosing action: "
						+ pattern.getName());
				}
			}
		}

		//No match with patterns try analyse normal functions
		for (int index = functionCalls.size() - 1; index >= 0; index--) {
			if (!(functionCalls.get(index) instanceof Element)) {
				continue;
			}
			parameter = (Element) functionCalls.get(index);
			CodeAnalyzer.analyzeFunction(node, function, parameter);
		}
	}
}
