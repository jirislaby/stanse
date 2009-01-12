/*
 * Copyright (c) 2008 Jiri Slaby <jirislaby@gmail.com>
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import cz.muni.stanse.utils.XMLAlgo;

import org.dom4j.Element;

/**
 * Represents a control-flow graph of a function
 */
public class CFG extends CFGPart {
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
	CFG cfg = new CFG(functionDefinition);
	cfg.setStartNode(cfgPart.getStartNode());
	cfg.setEndNode(cfgPart.getEndNode());
	return cfg;
    }

    /**
     * Creates a new instance of CFG
     *
     * @param functionDefinition XML representation of a function definition
     */
    public CFG(Element functionDefinition) {
	super();
	this.functionDefinition = functionDefinition;
	Element declarator = (Element)functionDefinition.
		selectSingleNode("./declarator");
	while (true) {
	    String node0 = declarator.node(0).getName();
	    if (node0.equals("declarator"))
		declarator = (Element)declarator.node(0);
	    else if (node0.equals("pointer") &&
			declarator.node(1).getName().equals("declarator"))
		declarator = (Element)declarator.node(1);
	    else
		break;
	}
	Element nameElem = (Element)declarator.selectSingleNode("./id");
	if (nameElem == null) {
	    functionName = "UNKNOWN";
	    System.err.println("Unknown function definition:");
	    XMLAlgo.outputXML(functionDefinition);
	    System.err.println("\n============");
	} else
	    functionName = nameElem.getText();
    }

    /**
     * @brief Copy constructor. It creates shallow copy of original one.
     *
     * Nothing is duplicated! All CFGNodes are shared with original CFG. This
     * sharing holds for function name and XML representation of AST as well.
     *
     * @param cfg Original CFG to be copied
     */
    public CFG(final CFG cfg) {
	super();
	this.functionDefinition = cfg.functionDefinition;
	this.functionName = cfg.getFunctionName();
	setStartNode(cfg.getStartNode());
	setEndNode(cfg.getEndNode());
    }

    /**
     * Returns function name assigned to this CFG
     *
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
