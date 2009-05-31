/*
 * Copyright (c) 2008 Jan Obdrzalek
 *
 * Distributed under GPLv2
 */

package cz.muni.stanse.cparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.RewriteCardinalityException;
;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.cparser.CFGEmitter;
import cz.muni.stanse.utils.Triple;
import cz.muni.stanse.Stanse;

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

    public CUnit(File file) throws IOException, ParserException {
	this(new FileInputStream(file), file.getAbsolutePath());
    }

    /**
     * Constructor with flags parameter
     *
     * @param jobEntry string in format TODO
     */
    public CUnit(String jobEntry) throws IOException, ParserException {
	this(preprocess(jobEntry));
    }

    public CUnit(InputStream stream, String name) throws IOException,
				ParserException {
	this(new Triple<InputStream, String, List<String>>(stream, name, null));
    }

    private CUnit(Triple<InputStream, String, List<String>> triple)
				throws IOException, ParserException {
	super(triple.getFirst(), triple.getSecond());

	GNUCaParser.translationUnit_return parserRet;
	
	StanseTreeAdaptor adaptor = new StanseTreeAdaptor();

	GNUCaLexer lex = new GNUCaLexer(
			new ANTLRInputStream(triple.getFirst()));
	CommonTokenStream tokens = new CommonTokenStream(lex);

	GNUCaParser parser = new GNUCaParser(tokens);
	parser.setTreeAdaptor(adaptor);
	parser.setTypedefs(triple.getThird());
	try {
	    parserRet = parser.translationUnit();
	} catch (RecognitionException e) {
	    throw new ParserException("parser", e);
	} catch (RewriteCardinalityException e) {
	    throw new ParserException("parser", e);
	}
	StanseTree parserTree = (StanseTree)parserRet.getTree();

	CommonTreeNodeStream nodes = new CommonTreeNodeStream(adaptor,
	    parserTree);
	nodes.setTokenStream(tokens);

	XMLEmitter xmlEmitter = new XMLEmitter(nodes);
	try {
	    xmlDocument = xmlEmitter.translationUnit();
	} catch (RecognitionException e) {
	    throw new ParserException("XMLEmitter", e);
	}

	nodes.reset();

	CFGEmitter cfgEmitter = new CFGEmitter(nodes);
	try {
	    CFGs = cfgEmitter.translationUnit();
	} catch (RecognitionException e) {
	    throw new ParserException("CFGEmitter", e);
	}
    }

    private static Triple<InputStream, String, List<String>> preprocess(
		String jobEntry) throws IOException, ParserException {
	List<String> typedefs = new LinkedList<String>();
	String file;
	int outputIdx;

	jobEntry = jobEntry.trim();
	outputIdx = jobEntry.indexOf("},{");
	if (jobEntry.charAt(0) != '{' || outputIdx < 0) {
	    file = jobEntry;
	    jobEntry = "{" + file + "},{" + file + "},{},{}";
	} else {
	    String workDir, output;
	    File f;
	    int dirIdx;

	    outputIdx += 3;
	    dirIdx = jobEntry.indexOf("},{", outputIdx) + 3;
	    output = jobEntry.substring(outputIdx, dirIdx - 3);

	    f = new File(output);
	    if (!f.isAbsolute()) {
		workDir = jobEntry.substring(dirIdx,
			jobEntry.indexOf("},{", dirIdx));
		f = new File(workDir, output);
	    }
	    file = f.getAbsolutePath();
	}
	file += ".preproc";


	
	try {
		// this is necessary
		// the environment is modified only AFTER the command is executed!
		String command = Stanse.getRootDirectory()+ File.separator + "bin" + File.separator + "stpreproc"; 
		final ProcessBuilder builder = new ProcessBuilder(command, jobEntry);
		java.util.Map<String, String> env = builder.environment();
		env.put("PATH", env.get("PATH") + File.pathSeparator + Stanse.getRootDirectory() + File.separator + "bin");
		Process p = builder.start();

	    BufferedReader reader = new BufferedReader(
			new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
		typedefs.add(line);
            reader.close();

	    try {
		int retval = p.waitFor();
		if (retval != 0)
			throw new ParserException("preprocessor failed: " +
					Integer.toString(retval));
		p.destroy();
	    } catch (InterruptedException e) {
		System.err.println("BUBUBU");
	    }
	} catch (IOException e) {
	    System.err.println("Can't exec cpp");
	    e.printStackTrace();
	}
	File preprocFile = new File(file);
	preprocFile.deleteOnExit();
	return new Triple<InputStream, String, List<String>>(
			new FileInputStream(preprocFile), file, typedefs);
    }
}
