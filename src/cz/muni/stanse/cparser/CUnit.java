/*
 * Copyright (c) 2008 Jan Obdrzalek
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.cparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.cparser.CFGEmitter;

/**
 * Holds all the code-related data for C compilation units (files).
 *
 * Currently based on the ANTLR parser, working in three steps.
 * 
 *  1. parse C and create an AST (GNUCa.g)
 *  2. parse AST and generate an XML representation of the AST (XMLEmitter.g)
 *  3. parse AST and generate its CFG, with pointers to the relevant XML
 *     nodes (CFGEmitter.g)
 */
public final class CUnit extends Unit {

    public CUnit(File file) throws IOException, RecognitionException {
	this(new FileInputStream(file), file.toString());
    }
	
    public CUnit(InputStream stream, String name) throws IOException,
				RecognitionException {
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
}
