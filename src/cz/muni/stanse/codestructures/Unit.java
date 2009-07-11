/*
 * Copyright (c) 2008 Jan Obdrzalek
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.codestructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.dom4j.Document;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.utils.ClassLogger;

/**
 * @brief Holds all the relevant data about the code in one compilation unit (usually a file).
 * 
 * This is intended to be a language-independent superclass.
 * For a concrete language create an appropriate subclass.
 * The constructor calls the appropriate parser and fills the CFG and AST structures.
 */
public abstract class Unit {
    /**
     * Name of the unit
     */
    protected File fileName;
    /**
     * Stream to read from
     */
    protected InputStream stream;
    /**
     * XML representation of the unit's AST
     */
    protected Document xmlDocument;
    /**
     * List of units control flow graphs.
     */
    protected List<CFG> CFGs;

    /**
     * Already available/parsed?
     */
    protected boolean available = false;

    // constructors
    /**
     * Intentionally empty, called by derived class constructors.
     */
    protected Unit() {}
    
    /**
     * Calls the appropriate parser(s) to fill in the data members.
     *
     * @param file Name of the unit. Needs to be supplied explicitly, because
     * 		it is not derivable from a stream.
     * @param stream Stream to read the compilation unit from.
     * @throws IOException If there any problems with IO.
     * @throws ParserException In case of parsing problems not related to IO.
     */
    public Unit(File file, InputStream stream) {
	this.fileName = file;
	this.stream = stream;
    }

    /**
     * Calls the appropriate parser(s) to fill in the data members.
     * 
     * @param file Name of the file containing the compilation unit.
     * @throws IOException If there any problems with IO.
     * @throws ParserException In case of parsing problems not related to IO.
     */
    public Unit(File file) throws IOException {
	this.fileName = file;
	stream = new FileInputStream(file);
    }
    
    public String getName() {
	return fileName.getAbsolutePath();
    }
    
    public Document getXMLDocument() {
	makeAvailable();
	return xmlDocument;
    }

    public void drop() {
	available = false;
	for (CFG cfg: CFGs)
	    cfg.drop();
    }

    public abstract void parse() throws ParserException;

    private void makeAvailable() {
	if (!available)
	    try {
		parse();
		available = true;
	    } catch (ParserException e) {
		ClassLogger.error(this, "can't parse '" + fileName.getPath() +
			"'!", e);
	    }
    }

    /**
     * @return Unmodifiable list of CFGs in this compilation unit.
     */
    public List<CFG> getCFGs() {
	makeAvailable();
	return Collections.unmodifiableList(CFGs);
    }
}
