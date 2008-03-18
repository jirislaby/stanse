/*
 * Main class of the project.
 */
package cz.muni.fi.iti.scv.main;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import cz.muni.fi.iti.scv.c2xml.CParser;
import cz.muni.fi.iti.scv.callgraph.CallGraph;
import cz.muni.fi.iti.scv.props.LoggerConfigurator;
import cz.muni.fi.iti.scv.props.Properties;
import cz.muni.fi.iti.scv.scvgui.GraphViz;
import cz.muni.fi.iti.scv.scvgui.GuiMain;
import cz.muni.fi.iti.scv.scvgui.SourceAndXMLWindow;
import cz.muni.fi.iti.scv.staticchecker.StaticChecker;
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
import org.dom4j.Document;
import org.dom4j.Element;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private static final String OPT_STRONGLYCONNECTED = "sc";
    private static final String OPT_DEBUG = "debug";
    private static final String OPT_REPORT = "report";
    
    private static Logger logger = Logger.getLogger(SCV.class);
    
    private static Properties.VerbosityLevel VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;
    
    private static boolean DEBUG = false;

    /**
     * Filenames of the source files.
     */
    private List<String> sourceFiles = new ArrayList<String>();
    /**
     * Filenames of checker definitions
     */
    private List<String> checkerDefinitions = new ArrayList<String>();
    /**
     * Cache of XML parsed source codes. Key is the filename, value is the xml
     */
    private Map<String, Document> xmlDocuments = new HashMap<String, Document>();

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
                accepts(OPT_DEBUG, "Debug mode - all debug info to stderr implies -v3");
                accepts(OPT_REPORT, "Target of the HTML checker report")
                            .withRequiredArg()
                            .describedAs("report file")
                            .ofType(String.class);
                
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
            new LoggerConfigurator();

            
            boolean startGui = !options.wasDetected(OPT_NOGUI);

            List<String> sources = new ArrayList<String>();
            for (Object nonOptionArgument : options.nonOptionArguments()) {
                sources.add((String) nonOptionArgument);
            }
            SCV scv = new SCV(sources);
            
            if (options.wasDetected(OPT_CALLGRAPH)) {
                String filename = options.argumentOf(OPT_CALLGRAPH);
                if (!filename.isEmpty()) {
                    scv.generateCallGraphToFile(filename, options.wasDetected(OPT_STRONGLYCONNECTED));
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
                FileAppender debugAppender = new FileAppender();
                debugAppender.setLayout(new HTMLLayout());
                debugAppender.setFile(options.argumentOf(OPT_REPORT));
                debugAppender.setThreshold(Level.INFO);
                Logger.getLogger(StaticChecker.class).addAppender(debugAppender);
                debugAppender.activateOptions();
            }

            // Start GUI?
            if (startGui) {
                scv.startGui();
            } else {
                // Dont start gui, but run checkers
                throw new NotImplementedException();
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
        /**
         * Needs to be run just once at program startup
         */
        this.sourceFiles = filenames;
    }

    public void setCheckerDefinitions(List<String> checkerDefinitions) {
        this.checkerDefinitions = checkerDefinitions;
    }

    /**
     * Generate call graph either with or without strongly connected subsets highlighted
     * @param outfile Name of the file to write the image to.
     * @param withStronglyConnected Whether to highlight the strongly connected subsets or not
     */
    public void generateCallGraphToFile(String outfile, boolean withStronglyConnected) throws NullPointerException {
        List<Element> rootElements = new ArrayList<Element>();
        for (String filename : sourceFiles) {
            try {
                rootElements.addAll(getXMDocumentLByFilename(filename).getRootElement().selectNodes("//functionDefinition"));
            } catch (FileNotFoundException ex) {
                logger.log(Level.FATAL, filename, ex);
            } catch (IllegalArgumentException ex) {
                logger.log(Level.FATAL, filename, ex);
            }
        }
        if (rootElements.isEmpty()) {
            throw new NullPointerException("No source files");
        }

        CallGraph callGraph = new CallGraph(rootElements);
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
     */
    public void generateCallGraphToFile(String outfile) {
        this.generateCallGraphToFile(outfile, false);
    }

    /**
     * Generate callgraph to outfile with strongly connected regions highlighted
     * @param outfile Name of the file to write the image to.
     */
    public void generateCallGraphToFileStronglyConnected(String outfile) {
        this.generateCallGraphToFile(outfile, true);
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
    public Document getXMDocumentLByFilename(String filename) throws IllegalArgumentException, FileNotFoundException {
        if (!sourceFiles.contains(filename)) {
            throw new IllegalArgumentException("Source code by this name doesn't exist");
        }

        Document returnDocument = null;
        if (!xmlDocuments.containsKey(filename)) {
            CParser parser = new CParser(new FileInputStream(filename));
            try {
                returnDocument = parser.runXmlParser();
            } catch (NullPointerException e) {
                logger.log(Level.FATAL, null, e);
            } catch (RecognitionException e) {
                logger.log(Level.FATAL, null, e);
            } catch (TokenStreamException e) {
                logger.log(Level.FATAL, null, e);
            }

        }
        return returnDocument;
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
