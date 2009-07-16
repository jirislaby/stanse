package cz.muni.stanse.threadchecker;


import cz.muni.stanse.callgraph.CallGraph;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.threadchecker.config.ConfigurationCreator;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Singleton-stringType Class which holds every useful data required during
 * analysis.
 * @author Jan Kučera
 */
public class CheckerSettings {

    private static CheckerSettings instance;
    private final static Logger logger
                                    = Logger.getLogger(CheckerSettings.class);
    private Document configDocument;
    private Map<String,Function> functions = new HashMap<String,Function>(50);
    private Map<String,Function> cachedFunctions;
    private Map<CFG,Function> functionsByCFG = new HashMap<CFG,Function>();
    private Map<CFG,Unit> unitsByCFG = new HashMap<CFG,Unit>();
    private Map<String,ThreadInfo> threads = new HashMap<String,ThreadInfo>();
    private Map<String,CFG> cfgs = new HashMap<String, CFG>();
    private final List<String> startFunctions = new Vector<String>();
    private Set<CFG> cfgsOnStack = new HashSet<CFG>();
    private boolean globalAnalysis;
    private ConfigurationCreator configurationCreator;
    
    
    private CheckerSettings() {}

    public List<String> getStartFunctions() {
        if(startFunctions.size() > 0) {
            return startFunctions;
        }
        
        return this.findStartFunctions();
    }

    public void addOnStack(CFG cfg) {
        if(this.cfgsOnStack.contains(cfg))
            throw new IllegalArgumentException("Function is already analysing");
        this.cfgsOnStack.add(cfg);
    }

    public void removeFromOnStack(CFG cfg) {
        this.cfgsOnStack.remove(cfg);
    }

    public boolean isOnStack(CFG cfg) {
        return this.cfgsOnStack.contains(cfg);
    }

    public boolean isGlobalAnalysisEnabled() {
        return this.globalAnalysis;
    }


    /**
     * 
     * @param file File containg user's settings
     * @throws org.dom4j.DocumentException when document contains wrong settings
     */
    void setConfigFile(File file) throws DocumentException {
        Element rootElement;
        Node type;
        List<Element> configuration;

        //Remove functions and settings from previous analysis
        this.clearData();
        
        configDocument = (new org.dom4j.io.SAXReader()).read(file);
        rootElement = configDocument.getRootElement();
        
        type = rootElement.selectSingleNode("analyseType/@type");
        if(type.getText() != null && type.getText().equals("global")) {
            globalAnalysis = true;
            cachedFunctions = new HashMap<String, Function>(100);
        }
        configuration = rootElement.selectNodes("configurationBuilder/*");
        if(configuration.size()>0)
            this.configurationCreator = new ConfigurationCreator(configuration);
    }

    public Document getConfigDocument() {
        return configDocument;
    }

    /**
     * Method removes all threads, CFGs, units. If global analysis is enabled
     * function states are stored to cachedFunctions
     */
    public void clearData() {
        if(this.isGlobalAnalysisEnabled()) {
            this.cachedFunctions.putAll(this.functions);
        }
        this.functions.clear();
        this.functionsByCFG.clear();
        this.unitsByCFG.clear();
        this.threads.clear();
        this.cfgs.clear();
        this.cfgsOnStack.clear();
        this.startFunctions.clear();
    }
    
    /**
     * Function returns CFG related with funcname.
     * @param funcName String name of function which should be returned as CFG
     * @return CFG or null if funcName isn't in cfgs
     */
    public CFG getCFG(String funcName) {
        return this.cfgs.get(funcName);
    }

    /**
     * Function picks all CFGs from unit and stores them by addCFG function
     * @param unit Unit object representing C file with functions
     */
    public void addAllCFGs(Unit unit) {
        for(CFG cfg : unit.getCFGs()) {
            addCFG(cfg);
            unitsByCFG.put(cfg, unit);
        }

        //Creator is not null - user want generate configuration
        if(this.configurationCreator != null) {
            try {
                this.configurationCreator.findFunctions(unit);
            } catch (DocumentException ex) {
                logger.error(ex);
            }
        }
    }

    /**
     * Function check, whether this.cfgs contain this cfg, if not cfg is stored
     * @param cfg
     */
    private void addCFG(CFG cfg) {
        if(cfgs.containsKey(cfg)) {
            throw new IllegalArgumentException("CFG "+cfg.getFunctionName()
                    +"already in!");
        }
        cfgs.put(cfg.getFunctionName(), cfg);
    }
    
    public static CheckerSettings getInstance() {
        if(instance == null) {
            instance = new CheckerSettings();
        }
        return instance;
    }

    /**
     * Function insert new thread into Map threads by its function name.
     * @param thread ThreadInfo representing thread run
     */
    public void addThread(ThreadInfo thread) {
        if(threads.containsKey(thread.getFunctionName())) {
            return;
        }
        threads.put(thread.getFunctionName(), thread);
    }

    public Unit getUnitByCFG(CFG cfg) {
        return unitsByCFG.get(cfg);
    }

    /**
     * Function tries to find thread which has function equals with arg name.
     * @param name
     * @return Thread or null if thread with this function isn't already in.
     */
    public ThreadInfo getThread(String name) {
        if(threads.containsKey(name)) {
            return threads.get(name);
        }
        return null;
    }

    public Collection<ThreadInfo> getThreads() {
        return Collections.unmodifiableCollection(this.threads.values());
    }

    public Set<String> getFunctionList() {
        return Collections.unmodifiableSet(this.cfgs.keySet());
    }

    public Function getFunction(CFG cfg) {
        return this.functionsByCFG.get(cfg);
    }

    public Function getFunction(String functionName) {
        Function function = this.functions.get(functionName);
        if(function != null || !this.globalAnalysis)
            return function;
        return this.cachedFunctions.get(functionName);
    }
    
    /**
     * Procedure add function to global Map of all functions.
     * @param function
     * @param cfg 
     */
    public void addFunction(Function function, CFG cfg) {
        if(function == null) {
            throw new NullPointerException("Function is null");
        }

        Element definition = cfg.getFunctionDefinition();
        Element idNode;
        String functionName;

        
        idNode = (Element) definition.selectSingleNode(
                                        "./declarator/descendant-or-self::id");
        if(idNode == null) {
            throw new NullPointerException("idNode is null");
        } else {
            functionName = idNode.getText();
        }

        if(functions.containsKey(functionName)) {
            logger.error("Function "+functionName+" already in set!");
        }
        function.setActualNode(new CFGNode());//insert an empty node
        functions.put(functionName, function);
        functionsByCFG.put(cfg, function);
    }

    /**
     * Function creates from all CFG Call graph and then it finds all functions,
     * which aren't callee in whole Unit.
     * @return List<String> containg names of start functions.
     */
    private List<String> findStartFunctions() {
        List<String> foundedStartFunctions = new Vector<String>();
        List<Element> functionDefinitions = new Vector<Element>();
        CallGraph callGraph;
        DirectedGraph<String, DefaultEdge> graph;
        for(CFG cfg : this.cfgs.values()) {
            functionDefinitions.add(cfg.getFunctionDefinition());
        }
        
        callGraph = new CallGraph(functionDefinitions);
        graph = (DirectedGraph<String, DefaultEdge>) 
                            callGraph.generateDirectedGraph();

        for(String vertex : graph.vertexSet()) {
            if(graph.incomingEdgesOf(vertex).size() < 1) {
                foundedStartFunctions.add(vertex);
            }
        }
        return foundedStartFunctions;
    }
}
