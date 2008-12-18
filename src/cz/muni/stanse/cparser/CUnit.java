package cz.muni.stanse.cparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Set;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import org.dom4j.Document;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.cparser.CFGEmitter;

public final class CUnit {
    private String name;
    private Document xmlDocument;
    private Set<CFG> CFGs;

    // constructors
    
    public CUnit(File file) throws IOException, RecognitionException {
    this(new FileInputStream(file), file.getName());
    }
    
    public CUnit(InputStream stream, String name) throws IOException, RecognitionException {
	this.name = name;
	
	StanseTreeAdaptor adaptor = new StanseTreeAdaptor();

	GNUCaLexer lex = new GNUCaLexer(new ANTLRInputStream(stream));
	CommonTokenStream tokens = new CommonTokenStream(lex);

	GNUCaParser parser = new GNUCaParser(tokens);
	parser.setTreeAdaptor(adaptor);
	GNUCaParser.translationUnit_return parserRet = parser.translationUnit();
	StanseTree parserTree = (StanseTree)parserRet.getTree();

	CommonTreeNodeStream nodes = new CommonTreeNodeStream(adaptor, parserTree);
	nodes.setTokenStream(tokens);

	XMLEmitter xmlEmitter = new XMLEmitter(nodes);
	xmlDocument = xmlEmitter.translationUnit();

	nodes.reset();

	CFGEmitter cfgEmitter = new CFGEmitter(nodes);
	CFGs = cfgEmitter.translationUnit();
   }

    // getters
    
    public String getName() {
    return name;
    }
    
    public Document getXMLDocument() {
	return xmlDocument;
    }

    public Set<CFG> getCFGs() {
	return Collections.unmodifiableSet(CFGs);
    }

    // remaining members    
}
