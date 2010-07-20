/* Distributed under GPLv2 */

package cz.muni.stanse.cppparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
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
 * Holds all the code-related data for C++ compilation units (files).
 */
public final class CppUnit extends Unit {
    private String jobEntry;
    private List<String> typedefs = null;

    /**
     * Constructor with flags parameter
     *
     * @param jobEntry string in format TODO
     */
    public CppUnit(String jobEntry) {
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
	this.CFGHs = new ArrayList<CFGHandle>();
	this.CFGs = new ArrayList<CFG>();
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
