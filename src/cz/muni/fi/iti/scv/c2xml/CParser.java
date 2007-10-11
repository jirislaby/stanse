package cz.muni.fi.iti.scv.c2xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import antlr.*;


public class CParser {
	
	protected InputStream inputStream;
	
	public CParser(InputStream inputStream) {
		this.inputStream = inputStream;	
	}

	public CParser() {
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Document runXmlParser() throws NullPointerException, RecognitionException, TokenStreamException {
		//check wether inputstream is set
		if (inputStream == null) throw new NullPointerException();

		//ExtentLexerSharedInputState - it is used to determine position of a token in source
        ExtentLexerSharedInputState eis = new ExtentLexerSharedInputState(inputStream);
        
        // Lexer
        GnuCLexer lexer = new GnuCLexer(eis);
        lexer.setTokenObjectClass(CToken.class.getName());
        lexer.initialize();
        
        // Parser
        GnuCParser parser = new GnuCParser(lexer);
        
        // set AST node type to TNode or get nasty cast class errors
        parser.setASTNodeType(TNode.class.getName());
        TNode.setTokenVocabulary("GNUCTokenTypes");
        
        // invoke parser
       	parser.translationUnit();
        TNode astTree = (TNode)parser.getAST();

        XmlEmitter e = new XmlEmitter();
        // set AST node type to TNode or get nasty cast class errors
        e.setASTNodeType(TNode.class.getName());

        // walk that tree
        astTree.doubleLink(); //nejprve pospojujeme strom, aby sel prochazet i zpetne
		e.translationUnit(astTree);
        Document xmlDocument = e.getXmlDocument();

        return xmlDocument;
	}
	
}
