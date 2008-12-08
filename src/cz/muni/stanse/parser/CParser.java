package cz.muni.stanse.parser;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import org.dom4j.Document;

public final class CParser {
    private InputStream stream;

    private CParser() { }

    public CParser(InputStream stream) {
	this.stream = stream;
    }

    public Document run() throws IOException, RecognitionException {
	GNUCaLexer lex = new GNUCaLexer(new ANTLRInputStream(stream));
	CommonTokenStream tokens = new CommonTokenStream(lex);
	GNUCaParser parser = new GNUCaParser(tokens);

	GNUCaParser.translationUnit_return r = parser.translationUnit();
	CommonTree t = (CommonTree)r.getTree();

	CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
	nodes.setTokenStream(tokens);
	XMLEmitter emitter = new XMLEmitter(nodes);
	return emitter.translationUnit();
    }
}
