/*
 * Main class of the project.
 */
package cz.muni.stanse.main;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import cz.muni.stanse.c2xml.CParser;
import cz.muni.stanse.callgraph.CallGraph;
import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.props.LoggerConfigurator;
import cz.muni.stanse.props.Properties;
import cz.muni.stanse.scvgui.GraphViz;
import cz.muni.stanse.scvgui.GuiMain;
import cz.muni.stanse.scvgui.SourceAndXMLWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import joptsimple.OptionException;
import org.apache.log4j.Logger;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;


/**
 * Main class of the project. Includes the static main method, which is started from the command line.
 * This class takes care of command line arguments.
 * 
 * @author xstastn
 * 
 * @version $Id$
 */
public class SCV {

    private static final String OPT_HELP = "help";
    private static final String OPT_USAGE = "usage";
    private static final String OPT_VERBOSITY = "v";
    private static final String OPT_SILENT = "s";
    private static final String OPT_CHECKERS = "ch";
    private static final String OPT_NOGUI = "nogui";
    private static final String OPT_CALLGRAPH = "callgraph";
    private static final String OPT_STRONGLYCONNECTED = "scc";
    private static final String OPT_DEBUG = "debug";
    private static final String OPT_REPORT = "report";
    private static final String OPT_VERSION = "version";
    private static final String OPT_CALLGRAPHMULTI = "cgm";
    
    
    private static Logger logger = Logger.getLogger(SCV.class);
    
    private static Properties.VerbosityLevel VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;
    
    private static boolean DEBUG = false;

    
    /**
     * Filenames of the source files.
     */
    private List<String> sourceFiles = new ArrayList<String>();
    /**
     * Parsed source files. This is a cache created by method getXMLDocumentByFilename
     */
    private Map<String, Document> sourceFilesParsed = new HashMap<String, Document>();
    /**
     * Filenames of checker definitions
     */
    private List<String> checkerDefinitions = new ArrayList<String>();
    
    /**
     * Cache for parsed static checked definitions. Key is the filename, parsed Document is the value
     */
    private Map<String, Document> definitionsParsed = new HashMap<String, Document>();
    

    
    
    
    /**
     * @param args the command line arguments
     * Command line argument parsed using JOpt simple. Homepage: http://jopt-simple.sourceforge.net/
     */
    public static void main(String[] args) {
        
        OptionParser optionsParser = new OptionParser() {

            {
                accepts(OPT_HELP, "prints help");
                accepts(OPT_USAGE, "shows usage");
                accepts(OPT_VERBOSITY, "Sets the level of verbosity")
                        .withOptionalArg()
                        .describedAs("0 to 3")
                        .ofType(Integer.class);
                accepts(OPT_SILENT, "Turns silent mode on. Same as -v 0");
                accepts(OPT_CHECKERS, "Checker definition file to be run. Can occur more than once.")
                            .withOptionalArg()
                            .describedAs("Checker def file")
                            .ofType(String.class);
                accepts(OPT_NOGUI, "Don't start the GUI");
                accepts(OPT_CALLGRAPH, "Generate call graph and store result to the file (--nogui is implied)").withRequiredArg().describedAs("Output file filename");
                accepts(OPT_STRONGLYCONNECTED, "Highlight strongly connected subsets in the call graph");
                accepts(OPT_CALLGRAPHMULTI, "Represent callgraph as multigraph");
                accepts(OPT_DEBUG, "Debug mode - all debug info to stderr implies -v3");
                accepts(OPT_REPORT, "Target of the HTML checker report")
                            .withRequiredArg()
                            .describedAs("report file")
                            .ofType(String.class);
                accepts(OPT_VERSION, "Prints the program version (build) and exits");
                
            }
        };

        try {
            final OptionSet options = optionsParser.parse(args);

            if (options.wasDetected(OPT_HELP) || options.wasDetected(OPT_USAGE)) {
                try {
                    optionsParser.printHelpOn(System.out);
                } catch (IOException ex) {
                    Logger.getLogger(SCV.class.getName()).log(Level.FATAL, null, ex);
                } finally {
                    System.exit(0);
                }
            }
            
            if(options.wasDetected(OPT_VERSION)) {
                System.out.println(SCV.class.getPackage().getImplementationVersion());
                System.exit(0);
            }

            if (options.wasDetected(OPT_VERBOSITY)) {
                switch ((Integer) options.valueOf(OPT_VERBOSITY)) {
                    case 1:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;
                        break;
                    case 2:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.MIDDLE;
                        break;
                    case 3:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.HIGH;
                        break;
                    case 0:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.SILENT;
                        break;
                    default:
                        System.err.println("Illegal verbosity level. Falling back to default - 1");
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;
                        break;
                    
                    }
            } 

            if (options.wasDetected(OPT_SILENT)) {
                VERBOSITY_LEVEL = Properties.VerbosityLevel.SILENT;
            }
            
            if(options.wasDetected(OPT_DEBUG)) {
                VERBOSITY_LEVEL = Properties.VerbosityLevel.HIGH;
                
            }
            
            // Sets up the logging facility (reads the logging options from log4j.properties file.
            // This needs to be done only once
            // This is done after the verbosity level is set to use it as default
            LoggerConfigurator.doConfigure();

            
            boolean startGui = !options.wasDetected(OPT_NOGUI);

            List<String> sources = new ArrayList<String>();
            for (Object nonOptionArgument : options.nonOptionArguments()) {
                sources.add((String) nonOptionArgument);
            }
            SCV scv = new SCV(sources);
            
            if (options.wasDetected(OPT_CALLGRAPH)) {
                String filename = options.argumentOf(OPT_CALLGRAPH);
                if (!filename.isEmpty()) {
                    scv.generateCallGraphToFile(filename, options.wasDetected(OPT_STRONGLYCONNECTED), options.wasDetected(OPT_CALLGRAPHMULTI));
                    startGui = false;

                }

            }
            
            List<String> checkers = new ArrayList<String>();
            if(options.wasDetected(OPT_CHECKERS)) {
                for(Object checker: options.argumentsOf(OPT_CHECKERS)) {
                    checkers.add((String) checker);
                }
            }
            
            scv.setCheckerDefinitions(checkers);
            
            
            if(options.wasDetected(OPT_REPORT)) {
                FileAppender reportAppender = new FileAppender();
                reportAppender.setLayout(new HTMLLayout());
                reportAppender.setFile(options.argumentOf(OPT_REPORT));
                reportAppender.setThreshold(Level.INFO);
                reportAppender.activateOptions();
/*                Logger.getLogger(StaticChecker.class).addAppender(reportAppender);
                Logger.getLogger(StaticChecker.class).setLevel(Level.INFO);*/
                
                
            }

            // Start GUI?
            if (startGui) {
                scv.startGui();
            } else {
                // Dont start gui, but run checkers
                if(!checkers.isEmpty()) {
                    scv.runCheckers();
                }
            }


        // If an unknown argument is found
        } catch (OptionException ex) {
            try {
                optionsParser.printHelpOn(System.out);
            } catch (IOException ex1) {
                logger.log(Level.FATAL, null, ex1);
            }
        }

    }

    /**
     * Constructor
     * @param filenames List of filenames to be worked with
     */
    public SCV(List<String> filenames) {
        this.sourceFiles = filenames;
    }

    public void setCheckerDefinitions(List<String> checkerDefinitions) {
        this.checkerDefinitions = checkerDefinitions;
    }

    /**
     * Generate call graph either with or without strongly connected subsets highlighted
     * @param outfile Name of the file to write the image to.
     * @param withStronglyConnected Whether to highlight the strongly connected subsets or not
     * @param isMultigraph Multigraph is generated if true
     */
    public void generateCallGraphToFile(String outfile, boolean withStronglyConnected, boolean isMultigraph) throws NullPointerException {
        List<Element> rootElements = new ArrayList<Element>();
        for (String filename : sourceFiles) {
            try {
                rootElements.addAll(getXMLDocumentByFilename(filename).getRootElement().selectNodes("//functionDefinition"));
            } catch (FileNotFoundException ex) {
                logger.log(Level.FATAL, filename, ex);
            } catch (IllegalArgumentException ex) {
                logger.log(Level.FATAL, filename, ex);
            }
        }
        if (rootElements.isEmpty()) {
            throw new NullPointerException("No source files");
        }

        CallGraph callGraph = new CallGraph(rootElements, isMultigraph);
        String dotSource = null;
        if (withStronglyConnected) {
            List<DirectedSubgraph<String, DefaultEdge>> stronglyConnectedSubgraphs = callGraph.stronglyConnected();
            dotSource = CallGraph.directedSubgraphsToDot(callGraph.generateDirectedGraph(), stronglyConnectedSubgraphs);
        } else {
            dotSource = callGraph.toDot();
        }
        GraphViz gv = new GraphViz();
        gv.graph.append(dotSource);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), outfile);
        logger.info("Written the callgraph to "+outfile);
    }

    /**
     * Generate Callgraph to outfile without strongly connected regions
     * @param outfile Name of the file to write the image to.
     * @param isMultigraph Multigraph is generated if true
     */
    public void generateCallGraphToFile(String outfile, boolean isMultigraph) {
        this.generateCallGraphToFile(outfile, false, isMultigraph);
    }

    /**
     * Generate callgraph to outfile with strongly connected regions highlighted
     * @param outfile Name of the file to write the image to.
     * @param isMultigraph Multigraph is generated if true
     */
    public void generateCallGraphToFileStronglyConnected(String outfile, boolean isMultigraph) {
        this.generateCallGraphToFile(outfile, true, isMultigraph);
    }

    public void startGui() {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                GuiMain gui = new GuiMain();
                gui.setVisible(true);
                if (!sourceFiles.isEmpty()) {
                    for (String filename : sourceFiles) {
                        File file = new File(filename);
                        gui.openSourceFile(file);
                    }

                    Set<File> files = new HashSet<File>();
                    for (String checkerDefinition : checkerDefinitions) {
                        files.add(new File(checkerDefinition));
                    }

                    if (!files.isEmpty()) {
                        
                        ((SourceAndXMLWindow) gui.getJTabbedPane1().getSelectedComponent()).runStaticChecker(files);
                    }
                }

            }
        });

    }

    /**
     * Gets the transformed XML document by the name of the source code
     * @param filename Name of the document to be transfered to XML and returned
     * @return Transfered XML document
     * @throws java.lang.IllegalArgumentException If the source file is not in the xmlDocuments array (source files open by this instance)
     * @throws java.io.FileNotFoundException If the file is not found or readable
     */
    public Document getXMLDocumentByFilename(String filename) throws IllegalArgumentException, FileNotFoundException {
        if (!sourceFiles.contains(filename)) {
            throw new IllegalArgumentException("Source code by this name doesn't exist");
        }
        if(sourceFilesParsed.containsKey(filename)) {
            return sourceFilesParsed.get(filename);
        } else {

            Document returnDocument = null;

            CParser parser = new CParser(new FileInputStream(filename));
            try {
                returnDocument = parser.runXmlParser();
                File file = new File(filename);
                returnDocument.setName(file.getName());
                sourceFilesParsed.put(filename, returnDocument);
            } catch (NullPointerException e) {
                logger.log(Level.FATAL, null, e);
            } catch (RecognitionException e) {
                logger.log(Level.FATAL, null, e);
            } catch (TokenStreamException e) {
                logger.log(Level.FATAL, null, e);
            }
        
            return returnDocument;
        }
        
        
    }
    
    /**
     * Returns the parsed static checked definition Document. Takes care of caching.
     * @param filename Filename of the checked definition to be parsed
     * @return Parsed document
     * @throws IllegalArgumentException If the checker definition has not been loaded.
     */
    public Document getCheckerDefinitionByFilename(String filename) throws IllegalArgumentException {
        if (!this.checkerDefinitions.contains(filename)) {
            throw new IllegalArgumentException("Checked definition by this name doesn't exist");
        }
        if(definitionsParsed.containsKey(filename)) {
            return definitionsParsed.get(filename);
        } else {

            Document returnDocument = null;
            SAXReader reader = new SAXReader();
            try {
                returnDocument = reader.read(filename);
            } catch (DocumentException ex) {
                logger.error(null, ex);
            }
            definitionsParsed.put(filename, returnDocument);
            
            return returnDocument;
        }
            
    }
        
    public void runCheckers() {
    Set<Document> definitions = new HashSet<Document>();
    for(String checker: checkerDefinitions) {
        definitions.add(getCheckerDefinitionByFilename(checker));
    }

    for(String filename: sourceFiles) {
        try {
            Document compiledSource = getXMLDocumentByFilename(filename);
/*            StaticChecker checker = new StaticChecker(compiledSource);
            for(Document definition: definitions) {
                checker.addDefinition(definition);
            }
            checker.check();
*/
            
        } catch (IllegalArgumentException ex) {
           logger.error(null, ex);
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }

    }

    }
    
    
    /**
     * This should be the setting, all the other classes should use.
     * Log4j uses this as default for the level of debuging
     * SILENT - no log4j output given by default
     * LOW - FATAL, ERROR and WARN levels are dumped by default
     * MIDDLE - FATAL, ERROR, WARN and INFO levels are dumped by default
     * HIGH - all messages dumped by default
     * This can be changed by the settings in the log4j.properties file.
     * @return Verbosity level
     */
    public static Properties.VerbosityLevel getVerbosityLevel() {
        return VERBOSITY_LEVEL;
    }
    
    /**
     * Whether the program is running in the debug mode or not
     * @return True - the program is running in the debug mode, false otherwise
     */
    public static boolean getDebug() {
        return DEBUG;
    }
    
}
