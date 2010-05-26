package cz.muni.stanse.lockchecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.xmlpatterns.XMLPattern;
import cz.muni.stanse.utils.xmlpatterns.XMLPatternVariablesAssignment;

/**
 * Utility class for LockChecker
 * @author Radim Cebis
 */
class Util {
	
	
	/**
	 * Get all variable IDs in provided element
	 * @param el element
	 * @param conf Configuration
	 * @return Set of variable IDs
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getIDsInElement(Element el, Configuration conf) {		
		Set<String> result = new HashSet<String>();
		
		//skip nodes where we do not want to count occurrences (i.e. lock nodes, artificial assert nodes)
		for(XMLPattern pattern : conf.getSkipOccurrences()) {			
			Pair<Boolean, XMLPatternVariablesAssignment> pair = pattern.matchesXMLElement(el);
			if(pair.getFirst()) {
				return result;
			}
		}
		
		if(conf.getExprs().contains(el.getName())) {
			String fullId = VarTransformations.makeArgument(el);
			result.add(fullId);
			// TODO should not we somehow weight inner variables to get better results?
			if(conf.countSubvars()) {
				// get first element if there is one and get name of it (this way all the sub-variables are obtained			
				for(Element inner : (List<Element>)el.elements()) {
					result.addAll(getIDsInElement(inner, conf));
					break;
				}						
			}
		}
		else {
			
			if(el.getName().equals("functionCall")) {
				for(Element inner : (List<Element>)el.elements().subList(((conf.countFunctions())?0:1), el.elements().size())) {
					result.addAll(getIDsInElement(inner, conf));
				}
			}
			else {				
				for(Element inner : (List<Element>)el.elements()) {
					result.addAll(getIDsInElement(inner, conf));					
				}
			}
		}
		return result;			
	}
	
	/**
	 * Get arguments of the function call in the specified element
	 * @param el element
	 * @return list of arguments of the function call in the specified element
	 */
	@SuppressWarnings("unchecked")	
	public static List<Element> getArguments(Element el) {
		List<Element> result = new ArrayList<Element>();
		
		//if this element is not function call find function call element in this element
		if(!el.getName().equals("functionCall")) {
			boolean found = false;
			
			for(Element inner : (List<Element>)el.elements()) {
				if(inner.getName().equals("functionCall")) {
					el = inner;
					found = true;
					break;
				}
			}
			if(!found) throw new IllegalArgumentException("Element does not contain function call.");
		}

		// first element is function name not an argument,  so return only the rest...
		if(el.elements().size()>1) {
			for(Element inner : (List<Element>)el.elements().subList(1, el.elements().size())) {
				result.add(inner);
			}
		}
		return result;		
	}
	
	/**
	 * zStatistic evaluation
	 * @param passedChecks
	 * @param allChecks
	 * @return zValue
	 */
	public static double zStat(int passedChecks, int allChecks) {
		double n = allChecks;
		double e = passedChecks;
		final double p0 = 0.9;
		// one proportion z-test http://en.wikipedia.org/wiki/Statistical_hypothesis_testing
		// assumptions: 	n .p0 > 10 and n (1 − p0) > 10 and it is a SRS (Simple Random Sample)
		// assumptions might be invalid in our case...
		return (e/n - p0)/Math.sqrt((p0*(1-p0))/n);
	}
}
