/* Distributed under GPLv2 */

package cz.muni.stanse.cppparser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGBranchNode;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.CFGPart;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.utils.Pair;

/**
 * Holds all the code-related data for C++ compilation units (files).
 */
public final class CppUnit extends Unit {
    private List<String> typedefs = null;
    private List<String> args = null;

    public CppUnit(List<String> args) {
	this.args = args;
	this.fileName = new File(args.get(0));
    }

    public void parse() throws ParserException {
	this.CFGHs = new ArrayList<CFGHandle>();
	this.CFGs = new ArrayList<CFG>();

	org.dom4j.Document cfgDocument;

	String command = Stanse.getInstance().getRootDirectory()
		+ File.separator + "bin" + File.separator + "cppparser";
	List<String> parserArgs = new ArrayList<String>();
	parserArgs.add(command);
	parserArgs.add("-A");
	parserArgs.add("-r");
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
	    java.io.BufferedReader br = new java.io.BufferedReader(sr);
	    List<String> output_lines = new ArrayList<String>();
	    {
		String line = br.readLine();
		while (line != null) {
		    output_lines.add(line);
		    line = br.readLine();
		}
	    }

	    try {
		if (p.waitFor() != 0 || output_lines.size() != 2)
		    throw new ParserException("parser failed");
	    } catch (InterruptedException e) {
	    }

	    String astString = output_lines.get(0);
	    String cfgString = output_lines.get(1);
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

	List<String> names = new ArrayList<String>();
	HashMap<String, Integer> namemap = new HashMap<String, Integer>();
	for (Object obj : cfgDocument.getRootElement().elements()) {
	    Element elem = (Element) obj;
	    if (!elem.getName().equals("rcfg"))
		continue;

	    String cfgname = elem.attribute("name").getValue();
	    java.util.Map<Integer, CFGNode> nodes = new java.util.HashMap<Integer, CFGNode>();
	    for (Object nodeobj : elem.elements()) {
		Element node = (Element) nodeobj;

		List<CFGNode.Operand> operands = new ArrayList<CFGNode.Operand>();
		Element ast = null;
		List<Pair<Integer, Element>> succs = new ArrayList<Pair<Integer, Element>>();

		for (Object nodeElemObj : node.elements()) {
		    Element nodeElem = (Element) nodeElemObj;

		    if (nodeElem.getName().equals("op")) {
			String nodeType = nodeElem.attributeValue("type");
			String valStr = nodeElem.attributeValue("val");
			int value;

			if (nodeType.equals("nodeval"))
			    value = Integer.parseInt(valStr);
			else {
			    if (namemap.containsKey(valStr))
				value = namemap.get(valStr);
			    else {
				value = names.size();
				namemap.put(valStr, value);
			    }
			}
			operands.add(new CFGNode.Operand(nodeType, value));
		    } else if (nodeElem.getName().equals("ast"))
			ast = (Element) nodeElem.elements().get(0);
		    else if (nodeElem.getName().equals("next"))
			succs.add(new Pair<Integer, Element>(Integer
				.parseInt(nodeElem.attributeValue("nodeid")),
				(Element) nodeElem.elements().get(0)));
		}
		
		assert ast != null;

		CFGNode newnode;
		if (succs.size() == 0
			|| (succs.size() == 1 && (succs.get(0).getSecond()
				.getName().equals("default"))))
		    newnode = new CFGNode(ast);
		else
		    newnode = new CFGBranchNode(ast);
		String nodeType = node.attributeValue("type");
		if (nodeType != null)
		    newnode.setNodeType(nodeType);
		newnode.setOperands(operands);

		String nodeid = node.attributeValue("id");
		nodes.put(Integer.parseInt(nodeid), newnode);
	    }

	    for (Object nodeobj : elem.elements()) {
		Element node = (Element) nodeobj;
		CFGNode newnode = nodes.get(Integer.parseInt(node
			.attributeValue("id")));
		for (Object nextnodeobj : node.elements("next")) {
		    Element nextnode = (Element) nextnodeobj;
		    String nextNodeId = nextnode.attributeValue("nodeid");
		    if (newnode instanceof CFGBranchNode) {
			((CFGBranchNode) newnode).addEdge(
				nodes.get(Integer.parseInt(nextNodeId)),
				(Element) nextnode.elements().get(0));
		    } else {
			newnode.addEdge(nodes.get(Integer.parseInt(nextNodeId)));
		    }
		}
	    }

	    CFGPart cfgpart = new CFGPart();
	    cfgpart.setStartNode(nodes.get(Integer.parseInt(elem
		    .attributeValue("startnode"))));
	    cfgpart.setEndNode(nodes.get(Integer.parseInt(elem
		    .attributeValue("endnode"))));

	    String xpath = "//functionDefinition[@name='" + cfgname + "']";
	    Element fnDef = (Element) xmlDocument.selectSingleNode(xpath);
	    CFG cfg = CFG.createFromCFGPart(cfgpart, fnDef);
	    CFGs.add(cfg);
	}

	for (CFG cfg : CFGs)
	    CFGHs.add(new CFGHandle(this, cfg));
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
