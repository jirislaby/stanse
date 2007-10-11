/*
 * StaticCheckerDef.java
 *
 * @author Jaroslav Novotný <jarek@jarek.cz>
 *
 */

package cz.muni.fi.iti.scv.staticchecker;

import javax.xml.xpath.XPathExpressionException;
import net.sf.saxon.Configuration;
import net.sf.saxon.dom4j.DocumentWrapper;
import net.sf.saxon.xpath.XPathEvaluator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a definition of concrete checker
 */
public class StaticCheckerDef {
    
    private Set<StaticCheckerDefVar> variables;  //varialbes used in definition
    
    private Set<Character> startState; //state at the cfg's start node
    
    private Set<StaticCheckerDefPropag> rules; //rules (define how states are propagated)
    private Set<StaticCheckerDefError> errorRules; //error rules (define what is an error)
    
    private String name; //checker name
    private String description; //checker description
    
    private boolean interprocedural = false; //interprocedural definition
    Set<String> interproceduralFunctions; //set of function names for interprocedural check
    
    /**
     * Creates a new instance of StaticCheckerDef
     * @param document xml representation of static checker definition
     */
    public StaticCheckerDef(Document document) {
        
        //initialize
        variables = new HashSet<StaticCheckerDefVar>();         
        startState = new HashSet<Character>();
        rules = new HashSet<StaticCheckerDefPropag>();
        errorRules = new HashSet<StaticCheckerDefError>();         
        
        //text info
        name = ((Element) document.getRootElement().selectSingleNode("info/name")).getTextTrim();
        description = ((Element) document.getRootElement().selectSingleNode("info/description")).getTextTrim();    
        
        //interprocedural
        Element inter = (Element) document.getRootElement().selectSingleNode("interprocedural");    
        if (inter != null) {
            interprocedural = true;
            interproceduralFunctions = new HashSet<String>();            
            for (Element function : (List<Element>) inter.selectNodes("function")) {
                interproceduralFunctions.add(function.getText());
            }
        }
        
        //variables
        for (Element var : (List<Element>) document.getRootElement().selectNodes("var")) {
            variables.add(new StaticCheckerDefVar(var));
        }
        
        //checker definition
        Element definition = (Element) document.getRootElement().selectSingleNode("definition");        
        for (int i = 0, size = definition.nodeCount(); i < size; i++) {
            Node node = definition.node(i);
            if (node instanceof Element) { 
                Element element = (Element) node;
                if (element.getName().equals("beginState")) {                    
                    for (byte j = 0; j < element.getText().length(); j++) {
                        startState.add(element.getText().charAt(j));                    
                    }
                    continue;
                }
                if (element.getName().equals("propagationRule")) {                    
                    rules.add(new StaticCheckerDefPropag(element));
                    continue;                    
                }
                if (element.getName().equals("errorRule")) {                    
                    errorRules.add(new StaticCheckerDefError(element));
                    continue;
                }
            }  
        }           
    }
     
    /**
     * Get start state set from checker definition
     * @return start state set
     */
    protected Set<Character> startState() {
        return startState;
    } 
    
    /**
     * Is interprocedural checker definition
     * @return true/false
     */
    protected boolean isInterprocedural() {
        return interprocedural;
    }
    
    /**
     * Get set of function names for interprocedural check
     * @return set of variables
     */
    protected Set<String> interproceduralFunctions() {
        return interproceduralFunctions;
    }
    
    /**
     * Get checker definition name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get checker definition description
     * @return description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Get set of variables in this checker definition
     * @return set of variables
     */
    protected Set<StaticCheckerDefVar> getVariables() {
        return variables;
    }
    
    /**
     * Porpagates state set trought edge
     * @param defAssign assign to variables in this static checker definition
     * @param edgeElement xml element on edge
     * @param edgeConditionType type of edge
     * @param stateBefore state set on the begining of the edge (before propagation)
     * @return state set on the end of the edge (after propagation)
     */
    protected Set<Character> propagate(Map<StaticCheckerDefVar,List<String>> defAssign, Element edgeElement, Boolean edgeConditionType, Set<Character> stateBefore) {
        
        Set<Character> stateAfter = new HashSet<Character>();
        stateAfter.addAll(stateBefore);
        
        boolean ruleFound = false;
        for (StaticCheckerDefPropag rule : rules) {
            ruleFound |= rule.propagate(defAssign, edgeElement, edgeConditionType, stateAfter);            
        }
        return (ruleFound) ? stateAfter : null;
    }
    
    /**
     * Compare element in code and in definition
     * @param defAssign assign to variables in this static checker definition
     * @param edgeElement element on edge in code
     * @param template template element in chercker definition
     * @return true if they're coresponding otherwise false
     */
    protected static boolean elementCompare(Map<StaticCheckerDefVar,List<String>> defAssign, Element edgeElement, String template) {      
            String text = new String(template);
            
            Pattern findPattern = Pattern.compile("^.*\\$\\{([^\\}]*)\\}.*$"); 
            Matcher findMatcher = findPattern.matcher(text);
            
            while (findMatcher.matches()) {
                String param = findMatcher.replaceFirst("$1"); 
                //System.out.println("param:"+param);
                
                int varIndex = 1;
                String varName = null;
                if (param.contains(":")) {
                    String[] splited = param.split(":",2);
                    
                    varIndex = Integer.valueOf(splited[1]);   
                    varName = param.substring(0,param.indexOf(":"));
                }
                
                String replaceText = "";
                
                for (StaticCheckerDefVar varDef : defAssign.keySet()) {
                    if (varDef.getName().equals(varName)) {
                        replaceText = defAssign.get(varDef).get(varIndex);
                        break;
                    }
                }
                
             
                Pattern replacePattern = Pattern.compile("\\$\\{"+param+"\\}"); 
                Matcher replaceMatcher = replacePattern.matcher(text);
                text = replaceMatcher.replaceAll(replaceText);
                //System.out.println("text:"+text); 
                
                findMatcher = findPattern.matcher(text);
            }
//            DocumentWrapper wrapper = new DocumentWrapper(edgeElement.getDocument(), "", new Configuration());
            XPathEvaluator evaluator = new XPathEvaluator();
            String result = null;
        try {
            result = evaluator.evaluate(text, edgeElement);
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
            return !result.equals("");
//            return edgeElement.selectSingleNode(text) != null;            
    }
    
    /**
     * Get violated checker definition's error rules by edge
     * @param defAssign assign to variables in this static checker definition
     * @param expression xml element on edge
     * @param state state set on the begining of the edge
     * @return violated error rules
     */
    protected Set<StaticCheckerDefError> getViolatedErrorRules(Map<StaticCheckerDefVar,List<String>> defAssign, Element expression, Set<Character> state) {
        Set<StaticCheckerDefError> violatedErrorRules = new HashSet<StaticCheckerDefError>();
        
        for (StaticCheckerDefError errorRule : errorRules) {
            if (errorRule.isEndErrorRule()) continue;
            if (errorRule.isError(defAssign, expression, state)) {
                violatedErrorRules.add(errorRule);
            }
        }
        
        return violatedErrorRules;
    }
    
    /**
     * Get violated checker definition's error rules on the end of procedure
     * @param defAssign assign to variables in this static checker definition
     * @param state state set on the end of procedure
     * @return violated error rules
     */
    protected Set<StaticCheckerDefError> getViolatedErrorRules(Map<StaticCheckerDefVar,List<String>> defAssign, Set<Character> state) {
        Set<StaticCheckerDefError> violatedErrorRules = new HashSet<StaticCheckerDefError>();
        
        for (StaticCheckerDefError errorRule : errorRules) {
            if (!errorRule.isEndErrorRule()) continue;
            if (errorRule.isError(state)) {
                violatedErrorRules.add(errorRule);
            }
        }
        
        return violatedErrorRules;
    }
    
}
