package cz.muni.stanse.lockchecker;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.traversal.CFGPathVisitor;
import cz.muni.stanse.props.Properties.VerbosityLevel;


public class CFGStatesBuilder extends CFGPathVisitor {    	
	private CFGStates cfgStates = new CFGStates();
	// this is just for debug purposes - to see jumps
	private int pathLength = 1;
	// is this node first visit of this visitor?
	private boolean firstVisit = true;
	private ErrorHandler errHandler;
	
	public CFGStatesBuilder(CheckerErrorReceiver errReceiver, Map<CFGNode, CFGHandle> dictionary) {
		super();
		errHandler = new ErrorHandler(errReceiver, dictionary);
	}
	
	public void saveErrors() {
		errHandler.save();
	}

	@Override
	public boolean onCFGchange(CFGNode from, CFGNode to) {
		return true;
	}
	
	@Override
	public boolean visit(List<CFGNode> path, Stack<CFGNode> cfgContext) {	
		CFGNode node = path.get(0);
		
		// only if we need this info
		if(Stanse.getInstance().getVerbosityLevel() == VerbosityLevel.HIGH) {
			System.out.println(node.getElement().asXML());
			System.out.println("path:" + path.size());			
			if(pathLength != path.size()) {
				System.out.println("Jump");
				pathLength = path.size();
			}
			pathLength++;
		}
	
		if(!firstVisit) {
			CFGNode propNode = null;
			// go back in time and look if there is state associated with a node - this way we will skip the functionCalls
			// and get to the exists of previous functions
			for(CFGNode pathEntry : path.subList(1, path.size())) {
				if(cfgStates.get(pathEntry)!=null) {
					propNode = pathEntry;
					break;
				}
			}			
			LockingResult res = cfgStates.propagate(propNode, node);
			if(res!=null) errHandler.handleLockingError(res, path, propNode);
		} else {
			cfgStates.init(node);
			firstVisit = false;
		}
		
		if(Stanse.getInstance().getVerbosityLevel() == VerbosityLevel.HIGH) {
			System.out.println("Held locks: " + cfgStates.get(node));
		}
		return true;	
	}

	public CFGStates getCfgStates() {
		return cfgStates;
	}
}
