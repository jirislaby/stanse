/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.builders.XMLLinearizeASTElement;
import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;

/**
 * Represents a control-flow graph of a function
 */
public class CFG extends CFGPart {
    private List<String> params;
    private Set<String> symbols; // locals
    private String functionName;
    private Element functionDefinition;

    /**
     * Creates a new instance of CFG
     *
     * @param cfgPart CFGPart to create CFG from
     * @param functionDefinition XML representation of a function definition
     * @return CFG created from CFGPart
     */
    public static CFG createFromCFGPart(CFGPart cfgPart,
		Element functionDefinition) {

	List<Element> linear = XMLLinearizeASTElement.functionDeclaration(functionDefinition);
	assert linear.size() > 0;
	assert linear.get(0).getName().equals("id");

	String functionName = linear.get(0).getText();

	List<String> params = new ArrayList<String>();
	for (int i = 1; i < linear.size(); ++i) {
	    assert linear.get(i).getName().equals("id");
	    params.add(linear.get(i).getText());
	}

	CFG cfg = new CFG(cfgPart.getStartNode(), cfgPart.getEndNode(), functionName);
	cfg.setParams(params);
	cfg.functionDefinition = functionDefinition;
	return cfg;
    }

    public CFG(CFGNode startNode, CFGNode endNode, String functionName) {
	super();
	this.functionName = functionName;
	this.setStartNode(startNode);
	this.setEndNode(endNode);
    }

    /**
     * Returns function name assigned to this CFG
     *
     * @return function name
     */
    protected String getFunctionName() {
	return functionName;
    }

    protected Element getElement() {
        if (functionDefinition != null && !functionDefinition.getName().equals("functionDefinition"))
	    throw new UnsupportedOperationException(
		    "wrong element in functionDefinition");
        return functionDefinition;
    }

    public void setParams(List<String> params) {
	this.params = params;
    }

    public void setSymbols(Set<String> symbols) {
	this.symbols = symbols;
    }

    public List<String> getParams() {
	assert this.params != null;
	return Collections.unmodifiableList(this.params);
    }

    public Set<String> getSymbols() {
	return Collections.unmodifiableSet(this.symbols);
    }

    public boolean isSymbolLocal(String symbol) {
	return symbols.contains(symbol);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final CFG other = (CFG)obj;
	if (this.functionDefinition != other.functionDefinition &&
		(this.functionDefinition == null ||
		 !this.functionDefinition.equals(other.functionDefinition))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = super.hashCode();
	hash = 23 * hash + functionDefinition.hashCode();
	return hash;
    }

    @Override
    public String toString() {
	return functionName + "(): " + super.toString();
    }
}
