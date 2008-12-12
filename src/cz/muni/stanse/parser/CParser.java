package cz.muni.stanse.parser;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Set;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import org.dom4j.Document;

public final class CParser {
    private InputStream stream;
    private Document xmlDocument;
    private Set<CFG> CFGs;

    private CParser() { }

    public CParser(InputStream stream) {
	this.stream = stream;
    }

    public Document getXMLDocument() {
	return xmlDocument;
    }

    public Set<CFG> getCFGs() {
	return Collections.unmodifiableSet(CFGs);
    }

    public void run() throws IOException, RecognitionException {
	StanseTreeAdaptor adaptor = new StanseTreeAdaptor();

	GNUCaLexer lex = new GNUCaLexer(new ANTLRInputStream(stream));
	CommonTokenStream tokens = new CommonTokenStream(lex);

	GNUCaParser parser = new GNUCaParser(tokens);
	parser.setTreeAdaptor(adaptor);
	GNUCaParser.translationUnit_return parserRet = parser.translationUnit();
	StanseTree parserTree = (StanseTree)parserRet.getTree();

	CommonTreeNodeStream nodes = new CommonTreeNodeStream(parserTree);
	nodes.setTokenStream(tokens);

	XMLEmitter xmlEmitter = new XMLEmitter(nodes);
	xmlDocument = xmlEmitter.translationUnit();

	nodes.reset();

	CFGEmitter cfgEmitter = new CFGEmitter(nodes);
	CFGs = cfgEmitter.translationUnit();
    }
}
