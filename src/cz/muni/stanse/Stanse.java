/*
 * Main class of the project.
 *
 * Copyright (c) 2009 Jan Obdrzalek
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse;

import cz.muni.stanse.configuration.Configuration;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.UnitManager;
import cz.muni.stanse.codestructures.UnitManagerLRU;
import cz.muni.stanse.gui.MainWindow;
import cz.muni.stanse.props.Properties.VerbosityLevel;
import cz.muni.stanse.statistics.CheckerErrorsGuiTracing;
import cz.muni.stanse.statistics.ErrorMessagesStatsBuilder;
import cz.muni.stanse.statistics.MergeDocuments;
import cz.muni.stanse.statistics.PerformanceDataBuilder;
import cz.muni.stanse.utils.ClassLocation;
import cz.muni.stanse.utils.ClassLogger;
import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;
import cz.muni.stanse.utils.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import joptsimple.OptionException;

/**
 * Class containing the main() method. Not supposed to be instantiated.
 * The main functionality is command-line parsing.
 *
 * @version $Id$
 */
public final class Stanse {

    // public section

    public static void main(String[] args) {
	printStanseInfo();

	CmdLineManager cmdLineManager;
	try {
	    cmdLineManager = new CmdLineManager(args);
	} catch (OptionException e) {
	    ClassLogger.error(Stanse.class, "can't parse commandline: " +
		    e.getLocalizedMessage());
	    return;
	}
	if (cmdLineManager.infoMode()) {
	    cmdLineManager.printInfo(System.out);
	    return;
	}

	getInstance().setVerbosityLevel(cmdLineManager.getVerbosityLevel());

	buildConfiguration(cmdLineManager);
	setOutputDirectory(cmdLineManager);
	processDumping(cmdLineManager);

	if (cmdLineManager.statsMode())
	    processStats(cmdLineManager);
	else
	    startUI(cmdLineManager);
    }

    public static Stanse getInstance() {
	return stanse != null ? stanse : createSingleInstance();
    }

    public Configuration getConfiguration() {
	return configuration;
    }

    public synchronized void setConfiguration(Configuration configuration) {
	this.configuration = configuration;
    }

    public String getRootDirectory() {
	return rootDirectory;
    }

    public String getOutputDirectory() {
	return outputDirectory;
    }

    public static UnitManager getUnitManager() {
	return unitManager;
    }

    public void setOutputDirectory(final String dir) {
	outputDirectory = dir;
    }

    public VerbosityLevel getVerbosityLevel() {
	return verbosityLevel;
    }

    public void setVerbosityLevel(final VerbosityLevel level) {
	verbosityLevel = level;
    }

    public synchronized void dumpAST() {
	assert(getConfiguration() != null);
	for (final Unit unit: getConfiguration().getSourceConfiguration()
						.getLazySourceInternals()
						.getUnits()) {
	    final String name = getOutputDirectory().toString().isEmpty() ?
	    unit.getName() : (new File(unit.getName())).getName();
	    File xmlFile = new File(getOutputDirectory(), name + ".xml");
	    try {
		XMLAlgo.outputXML(getUnitManager().getXMLDocument(unit),
		new PrintStream(xmlFile));
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    public synchronized void dumpCFG() {
	assert(getConfiguration() != null);
	for (final CFGHandle cfg: getConfiguration().getSourceConfiguration()
						      .getLazySourceInternals()
						      .getCFGHandles()) {
	    final String unitName = getUnitManager().getUnitName(cfg);
	    final String name = getOutputDirectory().toString().isEmpty() ?
				      unitName : (new File(unitName)).getName();
	    final File cfgFile = new File(getOutputDirectory(), name + "." +
				      cfg.getFunctionName() + ".dot");
	    try {
		BufferedWriter out = new BufferedWriter(
					new FileWriter(cfgFile));
		out.write(cfg.toDot());
		out.close();
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    public synchronized void dumpCallGraph() {
	assert(getConfiguration() != null);
	final File file = new File(getOutputDirectory(),"call-graph.dot");
	try {
	    BufferedWriter out = new BufferedWriter(new FileWriter(file));
	    out.write(cz.muni.stanse.utils.CallGraphToDot.run(
				    getConfiguration().getSourceConfiguration()
						      .getLazySourceInternals()
						      .getCallGraph()));
	    out.close();
	} catch (IOException exception) {
	    exception.printStackTrace();
	}
    }

    // private section

    private Stanse() {
	configuration = null;
	outputDirectory = ".";
	rootDirectory = buildRootDirectory();
	verbosityLevel = VerbosityLevel.LOW;
    }

    private static synchronized Stanse createSingleInstance() {
	return stanse != null ? stanse : (stanse = new Stanse());
    }

    private static void printStanseInfo() {
	System.out.println("Stanse version \"1.1.3\"");
	System.out.println("Copyright (c) 2008-2010 Masaryk University, Brno\n");
    }

    private static void buildConfiguration(final CmdLineManager cmdLineManager){
	getInstance().setConfiguration(
	    new Configuration(cmdLineManager.getSourceConfiguration(),
			      cmdLineManager.getCheckerConfiguration()));
    }

    private static void setOutputDirectory(final CmdLineManager cmdLineManager){
	final String outDirName = cmdLineManager.getOutputDir();
	if (outDirName == null)
	    return;
	File outDir = new File(outDirName);
	if (!outDir.exists()) {
	    System.out.print("Output directory: '" + outDirName +
			     "' does not exist.\n\tCreating it...");
	    if (outDir.mkdirs())
		System.out.println("Done.");
	    else
		System.out.println("Failed! Output won't be written.");
	}
	getInstance().setOutputDirectory(outDirName);
    }

    private static void processDumping(final CmdLineManager cmdLineManager) {
	if (getInstance().getConfiguration() == null) return;
	if (cmdLineManager.dumpAST()) getInstance().dumpAST();
	if (cmdLineManager.dumpCFG()) getInstance().dumpCFG();
	if (cmdLineManager.dumpCallGraph()) getInstance().dumpCallGraph();
    }

    private static void processStats(final CmdLineManager cmdLineManager) {
        System.out.print("Stanse is executed in statistical mode.\n\n\n");

        final String buildFile = cmdLineManager.statsBuildFile();
        if (buildFile != null) {
            System.out.print("Statistical results will be stored in output " +
                             "file:\n   " + buildFile + "\n\n\n");
            cz.muni.stanse.statistics.StatisticalDatabaseBuilder.run(buildFile);
            return;
        }

        final String databaseFile = cmdLineManager.getStatsDatabase();
        if (databaseFile == null || !new File(databaseFile).exists()) {
            System.err.print("Error: cannot find statistical database " +
                             "XML file:\n   " + databaseFile + "\n\n\n");
            return;
        }
        final org.dom4j.Document database = cz.muni.stanse.statistics.
                StatisticalDatabaseLoader.run(databaseFile);
        if (database == null) {
            System.err.print("Error: parsing of content of database " +
                             "XML file has FAILED. Parsed file was:\n   " +
                             databaseFile + "\n\n\n");
            return;
        }

        final String orderingDir = cmdLineManager.statsOrderingRootDir();
        if (orderingDir != null) {
            cz.muni.stanse.statistics.CheckerErrorsSorter.run(
                    database,cmdLineManager.getStatsOrdering(),orderingDir);
            return;
        }

        if (cmdLineManager.doStatsGuiTracing()) {
	    String out = cmdLineManager.statsGuiTracingOutputFile();
	    if (out == null) {
		ClassLogger.error(Stanse.class, "wrong gui-tracing " +
			"commandline");
		return;
	    }
	    String origDir = cmdLineManager.statsGuiTracingOrigSrcDir();
	    String newDir = cmdLineManager.statsGuiTracingCurrSrcDir();
            if (origDir == null || newDir == null)
                CheckerErrorsGuiTracing.run(database, out);
            else
                CheckerErrorsGuiTracing.run(database,
			Pair.make(origDir, newDir), out);
            return;
        }

        if (cmdLineManager.doStatsMerge()) {
	    String root = cmdLineManager.getStatsMergeDirsRoot();
	    String out = cmdLineManager.getStatsMergeOutputFile();
	    if (root == null || out == null) {
		ClassLogger.error(Stanse.class, "wrong stats-merge " +
			"commandline");
		return;
	    }
            MergeDocuments.run(database, root, out);
            return;
        }

        if (cmdLineManager.doStatsPerformance()) {
	    String outFile = cmdLineManager.getStatsPerformanceOutputFile();
	    String outFormat = cmdLineManager.getStatsPerformanceOutputFormat();
	    if (outFile == null || outFormat == null) {
		ClassLogger.error(Stanse.class, "wrong performance " +
			"commandline");
	    } else
		PerformanceDataBuilder.run(database,
			outFile, outFormat);
        }

        if (cmdLineManager.doStatsReports()) {
	    String outFile = cmdLineManager.getStatsReportsOutputFile();
	    String outFormat = cmdLineManager.getStatsReportsOutputFormat();
	    if (outFile == null || outFormat == null) {
		ClassLogger.error(Stanse.class, "wrong reports " +
			"commandline");
	    } else
                ErrorMessagesStatsBuilder.run(database, outFile, outFormat);
        }
    }

    private static void startUI(final CmdLineManager cmdLineManager) {
	final Pair<String,String> UIdesc = cmdLineManager.getUIdesc();
	if (UIdesc.getFirst().equals("GUI"))
	    startGUI(UIdesc.getSecond());
	else if (UIdesc.getFirst().equals("TUI"))
	    startTUI(UIdesc.getSecond());
	else
	    ClassLogger.error(Stanse.class, "no user interface specified.");
    }

    private static void startGUI(final String style) {
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		MainWindow.setLookAndFeel(style);
		MainWindow.getInstance().setVisible(true);
	    }
	});
    }

    private static void startTUI(final String style) {
	System.out.println("Checking for bugs:");
	final java.util.Vector<CheckerError> errors =
		new java.util.Vector<CheckerError>();
	getInstance().getConfiguration().evaluate_EachUnitSeparately(
	    new CheckerErrorReceiver() {
		@Override
		public void receive(final CheckerError error) {
		    errors.add(error);
		}
		@Override
		public void onEnd() {
		    if (!errors.isEmpty()) {
			System.out.println("Error(s) found:");
			for (final CheckerError error : errors)
			    System.out.println(error.dump());
		    }
		    System.out.println("Done.");
		}
	    },
	    new CheckerProgressMonitor() {
		@Override
		public void write(final String s) {
		    System.out.print(s);
		}
	    });
    }

    private static String buildRootDirectory() {
	String rootDirectory = System.getenv("STANSE_HOME");
	if (rootDirectory == null) {
	    System.err.println("STANSE_HOME not specified. Using the " +
				"location of stanse.jar.");
	    try {
		rootDirectory =
		    new File(ClassLocation.get("cz.muni.stanse.Stanse"))
			  .getParent();
	    } catch (final ClassNotFoundException e) {
		e.printStackTrace();
		rootDirectory = new File(".").getAbsolutePath();
	    }
	}
	return rootDirectory;
    }

    private static UnitManager unitManager = new UnitManagerLRU();
    private Configuration configuration;
    private String outputDirectory;
    private final String rootDirectory;
    private VerbosityLevel verbosityLevel;
    private static Stanse stanse = null;
}
