/* Distributed under GPLv2 */

package cz.muni.stanse.cfgparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.Unit;

/**
 * Holds all the code-related data for JSON-encoded CFG files.
 */
public final class CfgUnit extends Unit {
    public CfgUnit(List<String> args) {
        if (args.size() != 1)
            throw new IllegalArgumentException("JSON CFG parser accepts a single argument -- the name of the file to parse.");
        this.fileName = new File(args.get(0)).getAbsoluteFile();
    }

    public void parse() throws ParserException {
        java.io.FileReader r;
        try {
            r = new java.io.FileReader(this.fileName);
            JSONTokener jsonTokener = new JSONTokener(r);
            JSONObject jsonUnit = new JSONObject(jsonTokener);
            r.close();
            CFGs = parseUnit(this.fileName.getParentFile(), jsonUnit);
        } catch (FileNotFoundException e) {
            throw new ParserException("Failed to open the input file.", e);
        } catch (JSONException e) {
            throw new ParserException("Failed to parse the input file.", e);
        } catch (IOException e) {
            throw new ParserException("Failed to read the input file.", e);
        }

        CFGHs = new ArrayList<CFGHandle>();
        for (CFG cfg : CFGs)
            CFGHs.add(new CFGHandle(this, cfg));
    }

    public static List<CFG> parseUnit(File basePath, JSONObject jsonUnit) throws JSONException, ParserException {
        List<CFG> cfgs = new ArrayList<CFG>();

        File[] filenames = null;
        JSONArray jsonFilenames = jsonUnit.optJSONArray("filenames");
        if (jsonFilenames != null) {
            filenames = new File[jsonFilenames.length()];
            for (int i = 0; i < jsonFilenames.length(); ++i) {
                File child = new File(jsonFilenames.getString(i));
                if (child.isAbsolute())
                    filenames[i] = child;
                else
                    filenames[i] = new File(basePath, child.getPath());
            }
        }

        JSONObject jsonCfgs = jsonUnit.getJSONObject("cfgs");

        @SuppressWarnings("rawtypes")
        Iterator cfgnameIterator = jsonCfgs.keys();
        while (cfgnameIterator.hasNext()) {
            String key = (String) cfgnameIterator.next();
            JSONObject cfg = jsonCfgs.getJSONObject(key);
            cfgs.add(parseCfg(key, cfg, filenames));
        }

        return cfgs;
    }

    private static CFG parseCfg(String cfgName, JSONObject jsonCfg, File[] filenames) throws JSONException, ParserException {
        // TODO: Do not ignore non-normal control flow paths
        // (i.e. succs that have succ index other than 0).

        JSONArray tags = jsonCfg.getJSONArray("tags");

        JSONArray jsonLocals = jsonCfg.getJSONArray("locals");
        Set<String> symbols = new HashSet<String>();
        for (int i = 0; i < jsonLocals.length(); ++i)
            symbols.add(jsonLocals.getString(i));

        JSONArray jsonParams = jsonCfg.getJSONArray("params");
        List<String> params = new ArrayList<String>(jsonParams.length());
        for (int i = 0; i < jsonParams.length(); ++i) {
            symbols.add(jsonParams.getString(i));
            params.add(jsonParams.getString(i));
        }

        JSONArray jsonNodes = jsonCfg.getJSONArray("nodes");
        CFGNode[] nodes = new CFGNode[jsonNodes.length()];

        int entryNode = jsonCfg.getInt("entry");
        int exitNode = -1;

        for (int i = 0; i < jsonNodes.length(); ++i)
            nodes[i] = new CFGNode();

        for (int i = 0; i < jsonNodes.length(); ++i) {
            JSONArray jsonNode = jsonNodes.getJSONArray(i);
            CFGNode node = nodes[i];

            node.setNodeType(jsonNode.getString(0));

            JSONArray jsonSuccs = jsonNode.getJSONArray(1);
            for (int j = 0; j < jsonSuccs.length(); ++j) {
                JSONArray jsonSucc = jsonSuccs.getJSONArray(j);
                if (jsonSucc.getInt(1) == 0)
                    node.addEdge(nodes[jsonSucc.getInt(0)], jsonSucc.get(2));
            }

            JSONArray jsonOps = jsonNode.getJSONArray(2);
            List<CFGNode.Operand> operands = new ArrayList<CFGNode.Operand>();
            for (int j = 0; j < jsonOps.length(); ++j) {
                JSONArray jsonOp = jsonOps.getJSONArray(j);
                String opType = jsonOp.getString(0);
                Object opId;
                if (opType.equals("node"))
                    opId = nodes[jsonOp.getInt(1)];
                else
                    opId = jsonOp.getString(1);
                operands.add(new CFGNode.Operand(opType, opId));
            }
            node.setOperands(operands);

            if (jsonNode.length() > 3) {
                JSONArray nodeTags = jsonNode.getJSONArray(3);
                for (int j = 0; j < nodeTags.length(); ++j) {
                    JSONArray tag = tags.getJSONArray(nodeTags.getInt(j));
                    if (tag.getString(0).equals("source_range")) {
                        File filename = filenames[tag.getInt(1)];
                        int start_line = tag.getInt(2);
                        int start_col = tag.getInt(3);
                        node.setLocation(filename, start_line, start_col);
                    }
                }
            }

            if (node.getNodeType() == CFGNode.NodeType.exit) {
                // Exit nodes must have at least one operand. The operand must be "const" and have an integer value.
                if (node.getOperands().isEmpty() || node.getOperands().get(0).type != CFGNode.OperandType.constant)
                    throw new ParserException("Invalid exit node in the JSON-encoded CFG.");
                int exitId = Integer.parseInt((String)node.getOperands().get(0).id);
                if (exitId == 0)
                    exitNode = i;
            }
        }

        // TODO: Maybe we should create a dummy exit node for now?
        if (exitNode < 0)
            throw new ParserException("No appropriate exit node found.");

        CFG cfg = new CFG(nodes[entryNode], nodes[exitNode], cfgName);
        cfg.setParams(params);
        cfg.setSymbols(symbols);
        return cfg;
    }

    @Override
    public synchronized void drop() {
        super.drop();
    }
}
