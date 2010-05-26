package cz.muni.stanse.lockchecker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.xmlpatterns.XMLPattern;
import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;


public class VarUsesBuilder {    	
	private CFGStates cfgStates = null;
	// <variable name>, <cfgstate, #number of uses under that state>	
	private Map<String, Map<CFGState, Integer>> varUses = new HashMap<String, Map<CFGState,Integer>>();
	private XMLPattern patternAssert = new XMLPattern("<pattern name=\"lock\"><assert><any/></assert></pattern>");
	
	public VarUsesBuilder(CFGStates cfgStates) {
		super();
		this.cfgStates = cfgStates;
	}

	public Map<String, Map<CFGState, Integer>> getVarUses() {
		return Collections.unmodifiableMap(varUses);
	}	
	
	public void build() {
		Set<CFGNode> set = cfgStates.getCfgStates().keySet();
		for(CFGNode node : set) {	
			Element el = node.getElement();
			
			Pair<Boolean, XMLPatternVariablesAssignment> pair = patternAssert.matchesXMLElement(el);
			// skip assert nodes which are artificial
			if(!pair.getFirst()) {
				// find all id tags occurrences in element and for each one increment appropriate map
				for(String id : Util.getIDsInElement(el)) {					
					if(varUses.containsKey(id)) {
						Map<CFGState, Integer> map = varUses.get(id);
						CFGState state = cfgStates.get(node);
						if(map.containsKey(state)) {
							map.put(state, map.get(state) + 1);
						}
						else
							map.put(state, 1);
					}
					else {
						Map<CFGState, Integer> map = new HashMap<CFGState, Integer>();
						map.put(cfgStates.get(node), 1);
						varUses.put(id, map);
					}				
				}
			}
		}
	}
}
