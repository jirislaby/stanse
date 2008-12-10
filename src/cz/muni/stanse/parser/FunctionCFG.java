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
public class FunctionCFG extends CFG {

    private String functionName; // name of the corespondenting function
    private Element functionDefinition; // function definition in xml

    static FunctionCFG createFromCFG(CFG cfg, Element functionDefinition) {
	FunctionCFG fcfg = new FunctionCFG(functionDefinition);
	fcfg.setStartNode(cfg.getStartNode());
	fcfg.setEndNode(cfg.getEndNode());
	return fcfg;
    }

    /**
     * Creates a new instance of FunctionCFG
     * @param functionDefinition function definition of procedure representated
     * as org.dom4j.Element with name "functionDefinition"
     */
    public FunctionCFG(Element functionDefinition) {
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
	final FunctionCFG other = (FunctionCFG)obj;
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
