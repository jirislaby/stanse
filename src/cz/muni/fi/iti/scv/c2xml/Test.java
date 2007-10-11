package cz.muni.fi.iti.scv.c2xml;
import java.io.*;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import antlr.*;
import antlr.debug.misc.ASTFrame;

public class Test {
    public static void main(String[] args) {
        try {
            String programName = args[0];
            System.out.println("\nWorking on " + programName);
            System.out.flush();
            ExtentLexerSharedInputState dis = new ExtentLexerSharedInputState (programName);
            
            // Lexer
            GnuCLexer lexer = new GnuCLexer( dis );
            lexer.setTokenObjectClass(CToken.class.getName());
            lexer.initialize();  // ?????
            
            // Parser
            GnuCParser parser = new GnuCParser( lexer );
            
            // set AST node type to TNode or get nasty cast class errors
//            parser.setASTNodeType("TNode");
            parser.setASTNodeType(TNode.class.getName());
            TNode.setTokenVocabulary(GNUCTokenTypes.class.getName());
            
	        System.out.println("\nC Parser START");
	        System.out.flush();
	        
            // invoke parser
            try {
                parser.translationUnit();
            } catch (RecognitionException e) {
                System.err.println("Fatal IO error:\n"+e);
                System.exit(1);
            } catch (TokenStreamException e) {
                System.err.println("Fatal IO error:\n"+e);
                System.exit(1);
            }
            
//            System.out.println(lexer.getPreprocessorInfoChannel());
            TNode strom = (TNode)parser.getAST();
            strom.doubleLink(); //nejprve pospojujeme strom, aby sel prochazet i zpetne
            
	        System.out.println("\nC Parser STOP");
	        System.out.flush();


	        System.out.println("\nXML Emitter START");
	        System.out.flush();

	        XmlEmitter e = new XmlEmitter();	        
            // set AST node type to TNode or get nasty cast class errors
            e.setASTNodeType(TNode.class.getName());

            e.translationUnit(strom);
            Document xmlDocument = e.getXmlDocument();
            
	        System.out.println("\nXML Emitter STOP");
	        System.out.flush();

            // Pretty print the document to System.out
            //OutputFormat format = OutputFormat.createPrettyPrint();
            //XMLWriter writer = new XMLWriter( System.out, format );
            //writer.write( xmlDocument );            
            
//            TNode.printTree(strom);          
            
//            ASTFrame af = new ASTFrame("jmeno", strom);
//            af.setVisible(true);
            
            System.out.println("Rozsah zdrojaku:");
            
            CToken tokenStrom = new CToken();
            tokenStrom.setFromTNode(strom);
            System.out.println("Zacatek: " + tokenStrom.getColumn() + ":" + tokenStrom.getLine());
            System.out.println("Konec: " + tokenStrom.getEndColumn() + ":" + tokenStrom.getEndLine());
            
        } catch ( Exception e ) {
            System.err.println( "exception: " + e);
            e.printStackTrace();
        }
    }
}

