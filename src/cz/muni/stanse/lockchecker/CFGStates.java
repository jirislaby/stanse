package cz.muni.stanse.lockchecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.xmlpatterns.XMLPattern;
import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;


/**
 * This class holds states assigned to the CFGNode.
 * It propagates, states, evaluates state changes and double locks/unlocks.
 *
 * @author Radim Cebis
 *
 */
class CFGStates {
	// maps state to the node
	private Map<CFGNode, State> cfgStates = new HashMap<CFGNode, State>();
	// holds error holder used to report double un/locking errors
	private ErrorHolder errHolder;
	private Configuration conf;


	/**
	 * Constructor for new CFGStates holder.
	 * @param startNode entering node
	 * @param enterState state in which the node is accessed
	 * @param errHandler error handler to put double lock errors into
	 * @param conf checker's configuration
	 */
	public CFGStates(CFGNode startNode, State enterState,
			ErrorHolder errHandler, Configuration conf) {
		this.errHolder = errHandler;
		this.conf = conf;
		cfgStates.put(startNode, enterState);
	}

	/**
	 * Propagation function.
	 * Propagates a state from @from node to @to node, transforms state
	 * needed by the node's content
	 *
	 * @param from node to propagate a state from
	 * @param to node to propagate a state to
	 * @return true if the state of the @to node has changed
	 */
	public boolean propagate(CFGNode from, CFGNode to) {
		LockError res = null;
		if (!cfgStates.containsKey(to))
			cfgStates.put(to, new State());
		State fromState = cfgStates.get(from);
		State toState = cfgStates.get(to);

		Element el = from.getElement();
		for (XMLPattern pattern : conf.getLocks()) {
			Pair<Boolean, XMLPatternVariablesAssignment> pair =
				pattern.matchesXMLElement(el);
			if (pair.getFirst()) {
				// from node is a lock function
				res = State.checkDoubles(false,
					VarTransformations.makeArgument(pair.getSecond().getVarsMap().get("A")), fromState);
				fromState = State.transformState(false,
					VarTransformations.makeArgument(pair.getSecond().getVarsMap().get("A")), fromState);
				break;
			}
		}
		for (XMLPattern pattern : conf.getAssertLocked()) {
			Pair<Boolean, XMLPatternVariablesAssignment> pair =
				pattern.matchesXMLElement(el);
			if (pair.getFirst()) {
				// from node is a assert lock - do not check doubles
				fromState = State.transformState(false,
					VarTransformations.makeArgument(pair.getSecond().getVarsMap().get("A")), fromState);
				break;
			}
		}
		for (XMLPattern pattern : conf.getUnlocks()) {
			Pair<Boolean, XMLPatternVariablesAssignment> pair =
				pattern.matchesXMLElement(el);
			if (pair.getFirst()) {
				// from node is an unlock function
				res = State.checkDoubles(true,
					VarTransformations.makeArgument(pair.getSecond().getVarsMap().get("A")), fromState);
				fromState = State.transformState(true,
					VarTransformations.makeArgument(pair.getSecond().getVarsMap().get("A")), fromState);
				break;
			}
		}
		for (XMLPattern pattern : conf.getAssertUnlocked()) {
			Pair<Boolean, XMLPatternVariablesAssignment> pair =
				pattern.matchesXMLElement(el);
			if (pair.getFirst()) {
				// from node is assert unlock - do not check doubles
				fromState = State.transformState(true,
					VarTransformations.makeArgument(pair.getSecond().getVarsMap().get("A")), fromState);
				break;
			}
		}
		if (res != null) {
			// there was a locking error, save it to the errHolder
			res.setNode(from);
			errHolder.handleLockingError(res);
		}
		return toState.propagate(fromState);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final Entry<CFGNode, State> entry : cfgStates.entrySet()) {
			if (first)
				first = false;
			else
				sb.append("\n");
			sb.append(entry.getKey().getElement().asXML());
			sb.append("\n");
			sb.append(entry.getValue());
		}
		return sb.toString();
	}

	/**
	 * Get state of the node
	 * @param node for which the state will be returned
	 * @return state of the node
	 */
	public State get(CFGNode node) {
		return cfgStates.get(node);
	}

	/**
	 * Set node's state. Overwrites existing state for a node
	 * @param node to set state for
	 * @param state to be saved for a node
	 */
	public void put(CFGNode node, State state) {
		cfgStates.put(node, state);
	}

	/**
	 * Removes node
	 * @param node to be removed from this holder
	 */
	public void remove(CFGNode node) {
		cfgStates.remove(node);
	}
}
