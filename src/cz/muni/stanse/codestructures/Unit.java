package cz.muni.stanse.codestructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;

import org.antlr.runtime.RecognitionException;

import org.dom4j.Document;

import cz.muni.stanse.codestructures.CFG;

/**
 * @brief Holds all the relevant data about the code in one compilation unit (usually a file).
 * 
 * This is intended to be a language-independent superclass.
 * For a concrete language create an appropriate subclass.
 * The constructor calls the appropriate parser and fills the CFG and AST structures.
 */
public abstract class Unit {
    /**
     * Name of the unit (usually a filename).
     */
    protected String name;
    /**
     * XML representation of the unit's AST
     */
    protected Document xmlDocument;
    /**
     * List of units control flow graphs.
     */
    protected List<CFG> CFGs;

    // constructors
    /**
     * Intentionally empty, called by derived class constructors.
     */
    public Unit() {}
    
    /**
     * Calls the appropriate parser(s) to fill in the data members.
     * 
     * @param stream Stream to read the compilation unit from.
     * @param name Name of the unit. Needs to be supplied explicitly, because
     * 		it is not derivable from a stream.
     * @throws IOException If there any problems with IO.
     * @throws RecognitionException In case of parsing problems not related to IO.
     */
    // TODO Should have an own exception class, instead of ANTLR's RecognitionException.
    public Unit(InputStream stream, String name) throws IOException, RecognitionException {}

    /**
     * Calls the appropriate parser(s) to fill in the data members.
     * 
     * @param file Name of the file containing the compilation unit.
     * @throws IOException If there any problems with IO.
     * @throws RecognitionException In case of parsing problems not related to IO.
     */
    public Unit(File file) throws IOException, RecognitionException {
    this(new FileInputStream(file), file.getName());
    }
    
    public String getName() {
    return name;
    }
    
    public Document getXMLDocument() {
	return xmlDocument;
    }

    /**
     * @return Unmodifiable list of CFGs in this compilation unit.
     */
    public List<CFG> getCFGs() {
	return Collections.unmodifiableList(CFGs);
    }
}
