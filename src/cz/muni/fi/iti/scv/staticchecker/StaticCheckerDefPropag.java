/*
 * StaticCheckerDefRule.java
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
 * Represents a rule in checekr definition to state propagating
 */
public class StaticCheckerDefPropag {   
         
    Set<Character> addState = null; 
    Set<Character> removeState = null;
    Set<Character> setState = null;
    
    Set<Character> addStateFalse = null; 
    Set<Character> removeStateFalse = null;
    Set<Character> setStateFalse = null;
    
    Set<Character> addStateTrue = null; 
    Set<Character> removeStateTrue = null;
    Set<Character> setStateTrue = null;
    
    Set<Character> containsState = null;
    Set<Character> notContainsState = null;
    Set<Character> isState = null;
    Set<Character> isNotState = null;
    
    String template; // tepmlate of edge element
    
    String description; // description of this error
    
    /**
     * Creates a new instance of StaticCheckerDefRule
     * @param element element with rule definition
     */     
    public StaticCheckerDefPropag(Element element) {
        
        description = ((Element) element.selectSingleNode("description")).getTextTrim();
        
        template = ((Element) element.selectSingleNode("source")).getText();
        
        Element state = (Element) element.selectSingleNode("state");
        
        Element stateAction = (Element) element.selectSingleNode("stateAction");
        Element stateActionTrue = (Element) element.selectSingleNode("stateActionTrue");
        Element stateActionFalse = (Element) element.selectSingleNode("stateActionFalse");
        
        if (state != null) {
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
    
        if (stateAction != null) {
            for (int i = 0, size = stateAction.nodeCount(); i < size; i++) {
                Node node = stateAction.node(i);
                if (node instanceof Element) { 
                    Element el = (Element) node;
                    if (el.getName().equals("add")) {
                        if (addState == null) {
                            addState = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            addState.add(stateStr.charAt(j));
                        }
                    } 
                    if (el.getName().equals("remove")) {
                        if (removeState == null) {
                            removeState = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            removeState.add(stateStr.charAt(j));
                        }
                    } 
                    if (el.getName().equals("set")) {
                        if (setState == null) {
                            setState = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            setState.add(stateStr.charAt(j));
                        }
                    } 
                }
            }            
        }
        
        if (stateActionTrue != null) {
            for (int i = 0, size = stateActionTrue.nodeCount(); i < size; i++) {
                Node node = stateActionTrue.node(i);
                if (node instanceof Element) { 
                    Element el = (Element) node;
                    if (el.getName().equals("add")) {
                        if (addStateTrue == null) {
                            addStateTrue = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            addStateTrue.add(stateStr.charAt(j));
                        }
                    } 
                    if (el.getName().equals("remove")) {
                        if (removeStateTrue == null) {
                            removeStateTrue = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            removeStateTrue.add(stateStr.charAt(j));
                        }
                    } 
                    if (el.getName().equals("set")) {
                        if (setStateTrue == null) {
                            setStateTrue = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            setStateTrue.add(stateStr.charAt(j));
                        }
                    } 
                }
            } 
        }
        
        if (stateActionFalse != null) {
            for (int i = 0, size = stateActionFalse.nodeCount(); i < size; i++) {
                Node node = stateActionFalse.node(i);
                if (node instanceof Element) { 
                    Element el = (Element) node;
                    if (el.getName().equals("add")) {
                        if (addStateFalse == null) {
                            addStateFalse = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            addStateFalse.add(stateStr.charAt(j));
                        }
                    } 
                    if (el.getName().equals("remove")) {
                        if (removeStateFalse == null) {
                            removeStateFalse = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            removeStateFalse.add(stateStr.charAt(j));
                        }
                    } 
                    if (el.getName().equals("set")) {
                        if (setStateFalse == null) {
                            setStateFalse = new HashSet<Character>();
                        }
                        String stateStr = el.getTextTrim();
                        for (byte j = 0; j < stateStr.length(); j++) {
                            setStateFalse.add(stateStr.charAt(j));
                        }
                    } 
                }
            } 
        }

    }   

    protected boolean propagate(Map<StaticCheckerDefVar,List<String>> defAssign, Element edgeElement, Boolean edgeConditionType, Set<Character> curentState) {
        //System.out.println("RULE " + this.description + " ON EDGE " + edgeElement.getName());
        if (StaticCheckerDef.elementCompare(defAssign, edgeElement, template) && checkState(curentState)) {
            
            if (edgeConditionType == null) { 
                if (addState != null) {
                    curentState.addAll(addState);                
                }
                if (removeState != null) {            
                    curentState.removeAll(removeState);                
                }
                if (setState != null) {
                    curentState.clear();
                    curentState.addAll(setState);
                } 
                return true;
            }
            
            if (edgeConditionType == true) { 
                if (addStateTrue != null) {
                    curentState.addAll(addStateTrue);                
                }
                if (removeStateTrue != null) {            
                    curentState.removeAll(removeStateTrue);                
                }
                if (setStateTrue != null) {
                    curentState.clear();
                    curentState.addAll(setStateTrue);
                } 
                return true;
            }
            
            if (edgeConditionType == false) { 
                if (addStateFalse != null) {
                    curentState.addAll(addStateFalse);                
                }
                if (removeStateFalse != null) {            
                    curentState.removeAll(removeStateFalse);                
                }
                if (setStateFalse != null) {
                    curentState.clear();
                    curentState.addAll(setStateFalse);
                } 
                return true;
            }
            
        }
        return false;
       
    }
    
    private boolean checkState(Set<Character> state) {
        
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
    
    /**
     * Get static checker rule destcription
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
}
