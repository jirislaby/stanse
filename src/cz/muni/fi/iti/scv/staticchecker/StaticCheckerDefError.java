/*
 * StaticCheckerDefError.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.staticchecker;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents a error in checker definition
 */
public class StaticCheckerDefError {
    
    Set<Character> containsState; // it's error if state contains all of this 
    Set<Character> notContainsState; // it's error if state doesn't contain this
  
    Set<Character> isState; // it's error if state set equals to this
    Set<Character> isNotState; // it's error if state doesn't equal to this
    
    String template = null; // tepmlate of edge element (error on end of the procedure if it is null)
    
    String name; // name of this error
    String description; // description of this error
    
    /**
     * Creates a new instance of StaticCheckerDefError
     * @param element element with rule definition
     */     
    public StaticCheckerDefError(Element element) {
        
        name = ((Element) element.selectSingleNode("name")).getTextTrim();
        description = ((Element) element.selectSingleNode("description")).getTextTrim();
                
        if (element.selectSingleNode("source") != null) template = ((Element) element.selectSingleNode("source")).getText();
        
        Element state = (Element) element.selectSingleNode("state");
        
        for (int i = 0, size = state.nodeCount(); i < size; i++) {
            Node node = state.node(i);
            if (node instanceof Element) { 
                Element el = (Element) node;
                if (el.getName().equals("contains")) {
                    if (containsState == null) {
                        containsState = new HashSet<Character>();
                    }
                    String stateStr = el.getTextTrim();
                    for (byte j = 0; j < stateStr.length(); j++) {
                        containsState.add(stateStr.charAt(j));
                    }
                } 
                if (el.getName().equals("notContains")) {
                    if (notContainsState == null) {
                        notContainsState = new HashSet<Character>();
                    }
                    String stateStr = el.getTextTrim();
                    for (byte j = 0; j < stateStr.length(); j++) {
                        notContainsState.add(stateStr.charAt(j));
                    }
                }      
                if (el.getName().equals("is")) {
                    if (isState == null) {
                        isState = new HashSet<Character>();
                    }
                    String stateStr = el.getTextTrim();
                    for (byte j = 0; j < stateStr.length(); j++) {
                        isState.add(stateStr.charAt(j));
                    }
                } 
                if (el.getName().equals("isNot")) {
                    if (isNotState == null) {
                        isNotState = new HashSet<Character>();
                    }
                    String stateStr = el.getTextTrim();
                    for (byte j = 0; j < stateStr.length(); j++) {
                        isNotState.add(stateStr.charAt(j));
                    }
                }      
            }
        } 
        
    }
    
    protected boolean isError(Map<StaticCheckerDefVar,List<String>> defAssign, Element edgeElement, Set<Character> state) {
        if (!StaticCheckerDef.elementCompare(defAssign, edgeElement, template)) return false;
        //System.out.println("XXXXXXXXXXX" + edgeElement + " " + state);
        return isError(state);
    }
    
    protected boolean isError(Set<Character> state) {
        
        if (isState != null) {
            return (state.containsAll(isState) && isState.containsAll(state));
        }
        if (isNotState != null) {
            return !(state.containsAll(isNotState) && isNotState.containsAll(state));
        }
        
        if (containsState != null && !state.containsAll(containsState)) return false;
        
        if (notContainsState != null)      
            for (Character ch : notContainsState) {
                if (state.contains(ch)) return false;
            }
       
        return true;
    }
    
    protected boolean isEndErrorRule() {
        return template==null;
    }
    
    /**
     * Get static checker error name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get static checker error destcription
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    protected Set<Character> getContainsState() {
        return containsState;
    }
    /*protected Set<Character> getNotContainsState() {
        return notContainsState;
    }*/
}
