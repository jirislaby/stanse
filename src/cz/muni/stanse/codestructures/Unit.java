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

public abstract class Unit {
    protected String name;
    protected Document xmlDocument;
    protected List<CFG> CFGs;

    // constructors
    public Unit() {}
    
    public Unit(InputStream stream, String name) throws IOException, RecognitionException {}

    public Unit(File file) throws IOException, RecognitionException {
    this(new FileInputStream(file), file.getName());
    }
    
    // getters
    public String getName() {
    return name;
    }
    
    public Document getXMLDocument() {
	return xmlDocument;
    }

    public List<CFG> getCFGs() {
	return Collections.unmodifiableList(CFGs);
    }
}
