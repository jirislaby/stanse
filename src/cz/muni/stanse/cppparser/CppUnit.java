/* Distributed under GPLv2 */

package cz.muni.stanse.cppparser;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.cfgparser.CfgUnit;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;

/**
 * Holds all the code-related data for C++ compilation units (files).
 */
public final class CppUnit extends Unit {
    private List<String> args = null;

    public CppUnit(List<String> args) {
	this.args = args;
	this.fileName = new File(args.get(0));
    }

    public void parse() throws ParserException {
	String command = Stanse.getInstance().getRootDirectory()
		+ File.separator + "bin" + File.separator + "cpp2sir";
	List<String> parserArgs = new ArrayList<String>();
	parserArgs.add(command);
	parserArgs.add("-J");
	parserArgs.addAll(args);
	ProcessBuilder builder = new ProcessBuilder(parserArgs);
	try {
	    final Process p = builder.start();
	    new Thread() {
		public void run() {
		    java.io.InputStreamReader sr = new java.io.InputStreamReader(
			    p.getErrorStream());
		    java.io.BufferedReader br = new java.io.BufferedReader(sr);
		    try {
			String line = br.readLine();
			while (line != null) {
			    System.err.println(line);
			    line = br.readLine();
			}
			br.close();
		    } catch (IOException ex) {
		    }
		}
	    }.start();

	    java.io.InputStreamReader sr = new java.io.InputStreamReader(
		    p.getInputStream());
	    JSONTokener jsonTokener = new JSONTokener(sr);
	    JSONObject jsonUnit = new JSONObject(jsonTokener);

	    CFGs = CfgUnit.parseUnit(this.fileName.getParentFile(), jsonUnit);
	} catch (IOException e) {
	    throw new ParserException("parser: " + e.getLocalizedMessage(), e);
	} catch (JSONException e) {
	    throw new ParserException("parser: " + e.getLocalizedMessage(), e);
	}

	this.CFGHs = new ArrayList<CFGHandle>();
	for (CFG cfg : CFGs)
	    CFGHs.add(new CFGHandle(this, cfg));
    }
}
