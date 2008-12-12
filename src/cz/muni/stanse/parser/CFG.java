/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.parser;

import org.dom4j.Element;

/**
 * Represents a control-flow graph of a procedure
 */
public class CFG extends CFGPart {

    private String functionName; // name of the corespondenting function
    private Element functionDefinition; // function definition in xml

    static CFG createFromCFGPart(CFGPart cfgPart, Element functionDefinition) {
	CFG cfg = new CFG(functionDefinition);
	cfg.setStartNode(cfgPart.getStartNode());
	cfg.setEndNode(cfgPart.getEndNode());
	return cfg;
    }

    /**
     * Creates a new instance of FunctionCFG
     * @param functionDefinition function definition of procedure representated
     * as org.dom4j.Element with name "functionDefinition"
     */
    public CFG(Element functionDefinition) {
	super();
	this.functionDefinition = functionDefinition;
	functionName = functionDefinition.selectSingleNode("./declarator/id").
		getText();
    }

    /**
     * Returns function name of procedure
     * @return function name
     */
    public String getFunctionName() {
	return functionName;
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
