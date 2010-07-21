/* Distributed under GPLv2 */

package cz.muni.stanse.cppparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.sun.org.apache.bcel.internal.generic.ASTORE;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGBranchNode;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.CFGPart;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;

/**
 * Holds all the code-related data for C++ compilation units (files).
 */
public final class CppUnit extends Unit {
    private List<String> typedefs = null;

    /**
     * Constructor with flags parameter
     * 
     * @param jobEntry
     *            string in format TODO
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
	this.fileName = new File(file);
    }

    public void parse() throws ParserException {
	this.CFGHs = new ArrayList<CFGHandle>();
	this.CFGs = new ArrayList<CFG>();

	org.dom4j.Document cfgDocument;

	String command = Stanse.getInstance().getRootDirectory()
		+ File.separator + "bin" + File.separator + "clang-pokus";
	ProcessBuilder builder = new ProcessBuilder(command, "-A", "-c",
		this.fileName.getAbsolutePath());
	try {
	    Process p = builder.start();
	    java.io.InputStreamReader sr = new java.io.InputStreamReader(
		    p.getInputStream());
	    java.io.BufferedReader br = new java.io.BufferedReader(sr);
	    String astString = br.readLine();
	    String cfgString = br.readLine();
	    br.close();
	    xmlDocument = (new org.dom4j.io.SAXReader()).read(new StringReader(
		    astString));
	    cfgDocument = (new org.dom4j.io.SAXReader()).read(new StringReader(
		    cfgString));
	} catch (IOException e) {
	    throw new ParserException("parser: " + e.getLocalizedMessage(), e);
	} catch (DocumentException e) {
	    throw new ParserException("invalid output from the parser: "
		    + e.getLocalizedMessage(), e);
	}

	{
	    try {
		java.io.FileWriter writer = new java.io.FileWriter("x:\\dump_cpp.xml");
		xmlDocument.write(writer);
		writer.close();
	    }
	    catch (java.io.IOException e)
	    {
	    }
	}

	List<Element> list = cfgDocument.selectNodes("//cfg");
	for (Element cfgElem : list) {
	    String cfgname = cfgElem.attribute("name").getValue();
	    java.util.Map<Integer, CFGNode> nodes = new java.util.HashMap<Integer, CFGNode>();
	    for (Element node : (List<Element>) cfgElem.elements()) {
		CFGNode newnode = null;
		if (node.getName().equals("node") || node.getName().equals("exitnode")) {
		    if (node.elements().size() > 0) {
			Element content = (Element)node.elements().get(0);
			newnode = new CFGNode(content);
		    } else {
			newnode = new CFGNode();
		    }
		} else if (node.getName().equals("branchnode")) {
		    newnode = new CFGBranchNode((Element)node.selectSingleNode("cond/*[1]"));
		}
		String nodeid = node.attributeValue("id");
		nodes.put(Integer.parseInt(nodeid), newnode);
	    }
	    for (Element node : (List<Element>) cfgElem.elements()) {
		CFGNode newnode = nodes.get(Integer.parseInt(node
			.attributeValue("id")));
		if (node.getName().equals("node")) {
		    newnode.addEdge(nodes.get(Integer.parseInt(node
			    .attributeValue("next"))));
		} else if (node.getName().equals("branchnode")) {
		    CFGBranchNode branchnode = (CFGBranchNode) newnode;
		    for (Element nextnode : (List<Element>) node
			    .elements("next")) {
			String nextNodeId = nextnode.attributeValue("nodeid");
			branchnode.addEdge(
				nodes.get(Integer.parseInt(nextNodeId)),
				(Element) nextnode.elements().get(0));
		    }
		}
	    }

	    CFGPart cfgpart = new CFGPart();
	    cfgpart.setStartNode(nodes.get(Integer.parseInt(cfgElem
		    .attributeValue("startnode"))));
	    cfgpart.setEndNode(nodes.get(Integer.parseInt(cfgElem
		    .attributeValue("endnode"))));
	    String xpath = "//functionDefinition[@name='" + cfgname + "']";
	    Element fnDef = (Element)xmlDocument.selectSingleNode(xpath);
	    CFG cfg = CFG.createFromCFGPart(cfgpart, fnDef);
	    CFGs.add(cfg);
	}

	for (CFG cfg : CFGs)
	    CFGHs.add(new CFGHandle(this, cfg));

	{
	    try {
		java.io.FileWriter writer = new java.io.FileWriter(
			"x:\\dump_cfg_cpp.txt");
		for (CFG cfg : CFGs) {
		    writer.write(cfg.toStringGraph());
		}
		writer.close();
	    } catch (java.io.IOException e) {
	    }
	}
    }

    @Override
    public synchronized void drop() {
	super.drop();
	assert (typedefs != null);
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
