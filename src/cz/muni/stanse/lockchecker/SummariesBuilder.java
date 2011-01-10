package cz.muni.stanse.lockchecker;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import cz.muni.stanse.codestructures.ArgumentPassingManager;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.CFGsNavigator;

/**
 * Class used to build summaries
 * 
 * @author Radim Cebis
 *
 */
class SummariesBuilder {    	
	private ArgumentPassingManager passingManager;
	private Summaries summaries;
	private Map<CFGNode, CFGHandle> dictionary;
	private CallStack callStack = new CallStack();
	private Configuration conf;
	
	private final static Logger logger =
        Logger.getLogger(SummariesBuilder.class.getName());
	
	/**
	 * Constructs summaries builder
	 * @param dictionary to translate nodes to handles
	 * @param passingManager passing manager
	 * @param conf configuration
	 */
	public SummariesBuilder(Map<CFGNode, CFGHandle> dictionary, ArgumentPassingManager passingManager, Configuration conf) {
		super();
		this.summaries = new Summaries(dictionary, conf);
		this.dictionary = dictionary;
		this.passingManager = passingManager;
		this.conf = conf;
	}

	/**
	 * Traverses CFG from start node using start state as starting state
	 * @param startNode from which to start a traverse
	 * @param navigator
	 * @param startState initial state
	 * @return computed FunctionStateSummary for given parameters
	 */
	public FunctionStateSummary traverse(CFGNode startNode, CFGsNavigator navigator, State startState) {
		// use defensive copy
		startState = new State(startState);
		
		// hit the cache
		FunctionSummary fs = summaries.get(startNode);
		FunctionStateSummary summary = fs.getFromCache(startState);
		if(summary != null) return summary;
		else summary = fs.get(startState);
		
		State originalStartState = summaries.getRepos().get(startState);
		callStack.add(startNode, originalStartState);
		
		CFGStates cfgStates = new CFGStates(startNode, startState, summary.getErrHolder(), conf);		
		Deque<CFGNode> stack = new ArrayDeque<CFGNode>();		
		
		stack.addFirst(startNode);
		summary.changeVarsOccurrence(1, startNode, startState);
				
		while(!stack.isEmpty()) {
			CFGNode parent = stack.pollFirst();
			// propagate from parent
			CFGNode propagationNode = parent;
			
			if (navigator.isCallNode(parent)) {
				CFGNode child = navigator.getCalleeStart(parent);
				cfgStates.propagate(propagationNode, child);

				VarTransformations varTrans = new VarTransformations();
				for (Element el : Util.getArguments(parent.getElement())) {
					String id = VarTransformations.makeArgument(el);
					// TODO change this if you change VarTransformations.makeArgument
					String newId = passingManager.pass(parent, id, child);
					varTrans.addTransformation(newId, id);
				}
				
				// get entering state for a callee
				State enterState = State.getRenamedCFGState(cfgStates
						.get(child), varTrans, true);
				// remove unusable state
				cfgStates.remove(child);
				
				// do this only if it is not a recursive call, otherwise normal propagation takes place
				if(!callStack.contains(child, enterState)) {
					FunctionStateSummary sum = traverse(child, navigator,
							enterState);
					// join callee's summary with the caller's - transfer to caller's namespace
					summary.join(sum, varTrans);
					
					// propagate from callee's exit
					propagationNode = navigator.getCalleeEnd(parent);
					// save callee's exit state transfered to caller's namespace
					cfgStates.put(propagationNode, State
							.getRenamedCFGState(sum.getOutputState(), varTrans, false));
				} 
			}
			// propagate state to children and change occurrences count
			for(CFGNode child : parent.getSuccessors()) {
				
				// save old state
				State oldState = null;				
				if(cfgStates.get(child)!= null) oldState = new State(cfgStates.get(child));				
				
				// propagate
				if(cfgStates.propagate(propagationNode, child)) {						
						stack.addFirst(child);
				}			
				
				if(oldState != null) {
					// remove old occurrences
					summary.changeVarsOccurrence(-1, child, oldState);
				}
				// add new occurrences
				summary.changeVarsOccurrence(1, child, cfgStates.get(child));
				
			}
			
		}		
		callStack.remove(startNode, originalStartState);
		State output = cfgStates.get(dictionary.get(startNode).getEndNode());
		// this is used when the end node is unreachable from start node (can happen because of compiler optimization)
		// then just use originalStartState
		if(output == null)
			output = originalStartState;
		summary.setOutputState(output);	
		printCfgStates(dictionary.get(startNode).getFunctionName(), startState, summary.getOutputState(), cfgStates);
		return summary;
	}

	/**
	 * Pretty print cfg's node's states
	 * 
	 * @param functionName
	 * @param startState
	 * @param endState
	 * @param cfgStates
	 */
	private static void printCfgStates(String functionName, State startState, State endState, CFGStates cfgStates) {
		if (!logger.isDebugEnabled())
			return;
		final StringBuilder sb = new StringBuilder("/////////////////////////////////////////////\n");
		sb.append("/////////////////////////////////////////////\n");
		sb.append("Function ").append(functionName).append(" CFG states\n");
		sb.append("Function entered in state: ").append(startState).append('\n');
		sb.append("Function left in state: ").append(endState).append('\n');
		sb.append(cfgStates);
		logger.info(sb.toString());
	}	
	
	/**
	 * @return this instance's summaries object
	 */
	public Summaries getSummaries() {
		return summaries;
	}

	@Override
	public String toString() {
		return summaries.toString();
	}	
}
