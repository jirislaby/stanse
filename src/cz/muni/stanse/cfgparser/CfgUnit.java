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
import java.util.HashMap;

/**
 * Holds all the code-related data for JSON-encoded CFG files.
 */
public class CfgUnit extends Unit {
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
            parseUnit(this.fileName.getParentFile(), jsonUnit);
        } catch (FileNotFoundException e) {
            throw new ParserException("Failed to open the input file.", e);
        } catch (JSONException e) {
            throw new ParserException("Failed to parse the input file.", e);
        } catch (IOException e) {
            throw new ParserException("Failed to read the input file.", e);
        }
    }

    public void parseUnit(File basePath, JSONObject jsonUnit) throws JSONException, ParserException {
        List<CFG> cfgs = new ArrayList<CFG>();

        aliases = new HashMap<String, String>();
        JSONObject jsonAliases = jsonUnit.optJSONObject("aliases");
        if (jsonAliases != null)
        {
            Iterator aliasIterator = jsonAliases.keys();
            while (aliasIterator.hasNext())
            {
                String aliasName = (String)aliasIterator.next();
                JSONArray aliasList = jsonAliases.getJSONArray(aliasName);
                if (aliasList.length() > 0)
                    aliases.put(aliasName, aliasList.getString(0));
            }
        }

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

        if (jsonUnit.has("_vfn_map") && jsonUnit.has("_vfn_param_counts")) {
            JSONObject vfn_param_counts = jsonUnit.getJSONObject("_vfn_param_counts");
            JSONObject vfn_map = jsonUnit.getJSONObject("_vfn_map");

            Iterator vfnMapIter = vfn_map.keys();
            while (vfnMapIter.hasNext()) {
                String key = (String)vfnMapIter.next();
                JSONArray overrides = vfn_map.getJSONArray(key);
                int paramCount = vfn_param_counts.getInt(key);

                if (overrides.length() != 0)
                    cfgs.add(lateBind(key, overrides, paramCount));
            }
        }

        CFGs = cfgs;
        CFGHs = new ArrayList<CFGHandle>();
        for (CFG cfg : CFGs)
            CFGHs.add(new CFGHandle(this, cfg));
    }

    private static CFG lateBind(String cfgName, JSONArray overrides, int paramCount) throws JSONException {
        assert overrides.length() != 0;

        List<String> params = new ArrayList<String>();
        Set<String> symbols = new HashSet<String>();
        for (int j = 0; j < paramCount; ++j) {
            String paramName = "p:" + Integer.toString(j);
            params.add(paramName);
            symbols.add(paramName);
        }

        Set<CFGNode> overrideCalls = new HashSet<CFGNode>();
        for (int i = 0; i < overrides.length(); ++i) {
            String overrideName = overrides.getString(i);

            CFGNode overrideCall = new CFGNode();
            overrideCall.setNodeType(CFGNode.NodeType.call);
            overrideCall.addOperand(CFGNode.OperandType.function, overrideName);

            for (int j = 0; j < paramCount; ++j)
                overrideCall.addOperand(CFGNode.OperandType.varval, params.get(j));

            overrideCalls.add(overrideCall);
        }

        CFGNode exit = new CFGNode();
        exit.addOperand(CFGNode.OperandType.constant, "0");

        CFG cfg;
        if (overrides.length() == 1) {
            CFGNode oc = overrideCalls.iterator().next();
            exit.addOperand(CFGNode.OperandType.nodeval, oc);
            oc.addEdge(exit);
            cfg = new CFG(oc, exit, cfgName);
        } else {
            CFGNode entry = new CFGNode();
            CFGNode phi = new CFGNode();
            phi.setNodeType(CFGNode.NodeType.phi);
            phi.addEdge(exit);

            for (CFGNode oc : overrideCalls) {
                entry.addEdge(oc);
                oc.addEdge(phi);
                phi.addOperand(CFGNode.OperandType.nodeval, oc);
            }

            cfg = new CFG(entry, exit, cfgName);
        }

        cfg.setParams(params);
        cfg.setSymbols(symbols);

        return cfg;
    }

    private static CFG parseCfg(String cfgName, JSONObject jsonCfg, File[] filenames) throws JSONException, ParserException {
        // TODO: Teach automaton checker about exit indexes
        // and remove dummy edges between exit nodes.

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
        List<Integer> exitNodes = new ArrayList<Integer>();

        for (int i = 0; i < jsonNodes.length(); ++i)
            nodes[i] = new CFGNode();

        for (int i = 0; i < jsonNodes.length(); ++i) {
            JSONArray jsonNode = jsonNodes.getJSONArray(i);
            CFGNode node = nodes[i];

            node.setNodeType(jsonNode.getString(0));

            JSONArray jsonSuccs = jsonNode.getJSONArray(1);
            for (int j = 0; j < jsonSuccs.length(); ++j) {
                JSONArray jsonSucc = jsonSuccs.getJSONArray(j);
                if (jsonSucc.getInt(1) != 2)
                    node.addEdge(nodes[jsonSucc.getInt(0)], jsonSucc.getInt(1), jsonSucc.get(2));
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
                if (node.getOperands().isEmpty()
                        || node.getOperands().get(0).type != CFGNode.OperandType.constant)
                    throw new ParserException("Invalid exit node in the JSON-encoded CFG.");
                int exitId = Integer.parseInt((String)node.getOperands().get(0).id);
                if (exitId == 0)
                    exitNode = i;
                else
                    exitNodes.add(i);
            }
        }

        // TODO: Maybe we should create a dummy exit node for now?
        if (exitNode < 0)
            throw new ParserException("No appropriate exit node found.");

        // This adds a dummy edge from all non-zero exit nodes to exit node 0.
        // This makes sure the automaton checker will correctly walk exception paths,
        // even though it currently ignores path indexes.
        for (Integer i : exitNodes) {
            nodes[i].addEdge(nodes[exitNode]);

            // Clear the node's source location
            // so that the right brace does not show up twice.
            nodes[i].setLocation(null, -1, -1);
        }

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
