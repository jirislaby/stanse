package cz.muni.stanse.threadchecker.debug;

import cz.muni.stanse.threadchecker.*;
import cz.muni.stanse.callgraph.CallGraph;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.threadchecker.graph.DependencyGraph;
import cz.muni.stanse.threadchecker.graph.DependencyRule;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author Jan Kučera
 */
public class Utils {
    private final static Logger logger =
                                Logger.getLogger(ThreadChecker.class.getName());
    private static GraphViz gv;
    private static GraphView view;
    private static boolean debug = false;
    private static CallGraph callGraph;

    private Utils() { }

    public static void writeToDot(CFG cfg, String filename) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(filename));
            out.write(cfg.toDot());
            out.close();
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }

    /**
     * Write XML Element with indent to file
     * @param Element
     * @param outputName
     * @deprecated
     */
    public static void writeElement(Element Element, String outputName) {

        XMLWriter writer;
        try {

        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(new FileWriter(outputName),format);
        writer.write(Element);
        writer.close();

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public static void writeDocument(Document document, String outputName) {
        XMLWriter writer;
        try {

        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(new FileWriter(outputName),format);
        writer.write(document);
        writer.close();

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public static void showGraph(CFG cfg) {
        if(!debug)
            return;
        if(gv == null) {
            gv = new GraphViz();
            view = new GraphView();
        }

        String toDot = cfg.toDot();
        gv.graph = new StringBuffer(toDot);

        try {
            final File out = File.createTempFile("SCVGraph",".png");
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
            view.addImage(new ImageIcon(out.getAbsolutePath()),
                                                        cfg.getFunctionName());
            view.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function only picks all CFGs and generate their dot representation and
     * show all cfgs in temporary GUI window
     * @param unit Unit object representing C file with functions
     * @deprecated
     */
    public static void showGraph(Unit unit) {
        if(!debug)
            return;
        if(gv == null) {
            gv = new GraphViz();
            view = new GraphView();
        }

        List<Element> functionDefinitions = new Vector<Element>();
        String toDot = "digraph CFG {";
        String temporary = "";

        for (CFG cfg : unit.getCFGs()) {
             temporary = cfg.toDot();
             temporary = temporary.replaceFirst("digraph CFG", "subgraph CFG");
             toDot += temporary;
             functionDefinitions.add(cfg.getFunctionDefinition());
        }

        toDot +="}";

        callGraph = new CallGraph(functionDefinitions);

        gv.graph = new StringBuffer(toDot);

        try {
            final File out = File.createTempFile("SCVGraph",".png");
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
            view.addImage(new ImageIcon(out.getAbsolutePath()),"Unit");
            view.setVisible(true);

            gv.graph = new StringBuffer(callGraph.toDot());

            final File callFile = File.createTempFile("CALLGraph",".png");
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource()),callFile);
            view.addImage(new ImageIcon(callFile.getAbsolutePath()),
                                                                   "Callgraph");
            view.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //=======END OF ERASE=========
    }
    
    /**
     * Method accepts graphs, generate their's toDot representation and display
     * image of those graphs to GraphView.
     * @param graphs
     */
    public static void showDependencyGraphs(Set<DependencyGraph> graphs) {
        String toDot = "digraph CFG {\n";
        String sourceName;
        String sourceLabel;
        String targetName;
        String targetLabel;

        if(!debug)
            return;
        if(gv == null) {
            gv = new GraphViz();
            view = new GraphView();
        }


        for(DependencyGraph graph : graphs) {
            toDot += "subgraph CFG {\n";
            for(DependencyRule rule : graph.getRules()) {

                sourceName = rule.getSource().getName();
                targetName = rule.getTarget().getName();
                sourceLabel = sourceName;
                targetLabel = targetName;
                sourceName = sourceName.replace("->", "_");
                targetName = targetName.replace("->", "_");

                toDot += sourceName+" [label = \""+ sourceLabel+"\"];\n";
                toDot += targetName+" [label = \""+ targetLabel+"\"];\n";
                toDot += sourceName+" -> "+targetName
                        +" [label = \""+rule.getThread().getId()+"\"];\n";
            }
            toDot+="}\n";
        }

        toDot +="}\n";

        gv.graph = new StringBuffer(toDot);

        try {
            final File out = File.createTempFile("SCVGraph",".png");
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
            view.addImage(new ImageIcon(out.getAbsolutePath()),
                                                            "Dependency Graph");
            view.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void writeToDot(String todot, String filename) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(filename));
            out.write(todot);
            out.close();
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }

    public static void setDebug(boolean debugMode) {
        debug = debugMode;
    }
}
