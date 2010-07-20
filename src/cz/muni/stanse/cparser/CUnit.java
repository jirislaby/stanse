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
import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.RewriteCardinalityException;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.utils.ClassLogger;
import cz.muni.stanse.utils.StreamAlgo;

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
    private String jobEntry;
    private List<String> typedefs = null;

    /**
     * Constructor with flags parameter
     *
     * @param jobEntry string in format TODO
     */
    public CUnit(String jobEntry) {
	String file;
	int outputIdx;

	jobEntry = jobEntry.trim();
	outputIdx = jobEntry.indexOf("},{");
	if (outputIdx < 0 || jobEntry.charAt(0) != '{') {
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
	this.jobEntry = jobEntry;
	this.fileName = new File(file);
    }

    public void parse() throws ParserException {
	GNUCaParser.translationUnit_return parserRet;
	StanseTreeAdaptor adaptor = new StanseTreeAdaptor();
	GNUCaLexer lex;

	/* do it here, so that we have at least empty list, not null */
	CFGHs = new LinkedList<CFGHandle>();

	preprocess();

	try {
	    /* need be after preprocess */
	    stream = new FileInputStream(fileName);
	    lex = new GNUCaLexer(new ANTLRInputStream(stream));
	} catch (IOException e) {
	    throw new ParserException("parser", e);
	}
	CommonTokenStream tokens = new CommonTokenStream(lex);

	GNUCaParser parser = new GNUCaParser(tokens);
	parser.setTreeAdaptor(adaptor);
	parser.setTypedefs(typedefs);
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
	    for (CFG cfg: CFGs)
		CFGHs.add(new CFGHandle(this, cfg));
	} catch (RecognitionException e) {
	    throw new ParserException("CFGEmitter", e);
	}
    }

    private void preprocess() throws ParserException {
	typedefs = new LinkedList<String>();
	Process p;
	String line;
	
	// this is necessary
	// the environment is modified only AFTER the command is
	// executed!
	String command = Stanse.getInstance().getRootDirectory() + File.separator +
		"bin" + File.separator + "stpreproc";
	ProcessBuilder builder = new ProcessBuilder("perl", command, jobEntry);
	Map<String, String> env = builder.environment();
	if (env.containsKey("Path")) {
	    env.put("PATH", env.get("Path"));
	    env.remove("Path");
	}
	env.put("PATH", env.get("PATH") + File.pathSeparator +
		Stanse.getInstance().getRootDirectory() + File.separator + "bin");
	try {
	    p = builder.start();

	    BufferedReader reader = new BufferedReader(
			new InputStreamReader(p.getInputStream()));
	    while ((line = reader.readLine()) != null)
		typedefs.add(line);
	    reader.close();
	} catch (IOException e) {
	    throw new ParserException("preprocessor: " +
		    e.getLocalizedMessage(), e);
	}

	try {
	    int retval = p.waitFor();
	    if (retval != 0) {
		ClassLogger.error(this, "preprocessor failed. stderr follows:");
		try {
		    StreamAlgo.copy(p.getErrorStream(), System.err);
		} catch (IOException e) {
		    ClassLogger.error(this, "Failed to dump stderr");
		}
		ClassLogger.error(this, "========== stderr end");
		throw new ParserException("preprocessor failed: " +
			Integer.toString(retval));
	    }
	} catch (InterruptedException e) {
	    throw new ParserException("preprocessor", e);
	}
	p.destroy();
    }

    @Override
    public synchronized void drop() {
	super.drop();
	assert(typedefs != null);
	typedefs.clear();
	typedefs = null;
	if (stream != null)
	    try {
		stream.close();
	    } catch (IOException e) {
	    }
	stream = null;
    }
}
