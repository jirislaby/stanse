/*
 * Main class of the project.
 */
package cz.muni.stanse;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.cparser.CUnit;

// import cz.muni.stanse.gui.GuiMain;
// import cz.muni.stanse.gui.SourceAndXMLWindow;

import cz.muni.stanse.props.LoggerConfigurator;
import cz.muni.stanse.props.Properties;
import cz.muni.stanse.utils.XMLAlgo;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.util.Arrays.*;
import java.util.LinkedList;
import java.util.List;


import joptsimple.OptionParser;
import joptsimple.OptionSpec;
import joptsimple.OptionSet;
import joptsimple.OptionException;

import org.apache.log4j.Logger;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

import org.dom4j.Document;
import org.antlr.runtime.RecognitionException;

/**
 * Main class of the project. Includes the static main method, which is started
 * from the command line. This class takes care of command line arguments.
 * 
 * @author xstastn
 * 
 * @version $Id$
 */
public final class Stanse {
	private static Logger logger = Logger.getLogger(Stanse.class);
	private static Properties.VerbosityLevel VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;

	/**
	 * Output directory for all the "dump" files. By default it is the current
	 * directory.
	 */
	private static File outputDirectory = new File(".");

	private static List<File> sources = new LinkedList<File>();	
	
	private static List<Unit> units = new LinkedList<Unit>();
	
	/**
	 * @param args
	 *            the command line arguments Command line argument parsed using
	 *            JOpt simple. Homepage: http://jopt-simple.sourceforge.net/
	 */
	public static void main(String[] args) {
		OptionParser parser = new OptionParser();
		OptionSpec<Void> help = parser.acceptsAll( asList( "h", "?", "help" ), 
		"Shows this help message and exits." );	
//		OptionSpec<Void> gui = parser.acceptsAll( asList( "g", "gui" ), "Starts GUI" );
		OptionSpec<Void> version = parser.accepts( "version", "Prints the program version and exits" );
		OptionSpec<String> checkerClass = parser.acceptsAll( asList( "c", "checker"), 
		"Checker to be run. Can occur more than once.")
		.withRequiredArg()
		.describedAs("className")
		.ofType(String.class);
		// TODO: argumenty checkeru	
		// TODO: change to gcc style?
//		OptionSpec<Integer> warnLevel = parser.acceptsAll( asList("w", "warn-level"),
//		"Sets the reported warning level")
//		.withRequiredArg()
//		.describedAs("n")
//		.ofType(Integer.class);
		OptionSpec<Integer> debugLevel = parser.acceptsAll( asList("d", "debug-level"),
		"Sets the debug level")
		.withRequiredArg()
		.describedAs("n")
		.ofType(Integer.class);
		OptionSpec<File> outputFile = parser.accepts("o" , "Output file")
		.withRequiredArg()
		.describedAs("file")
		.ofType(File.class);
		OptionSpec<Void> dumpCFG = parser.accepts("dump-cfg", "Dump control flow graphs in Dot format");
		OptionSpec<Void> dumpXML = parser.accepts("dump-xml", "Dump XML representation of AST");
		// OptionSpec<Void> dumpCallGraph = parser.accepts("dump-callgraph", "Dump callgraph in Dot format");
		OptionSpec<File> outputDir = parser.accepts("output-dir", "Sets the output directory for generated files")
		.withRequiredArg()
		.describedAs("file")
		.ofType(File.class);
		// TODO: how to print "command-line usage", including the sources?
		OptionSpec<File> inputFile = parser.accepts("input-file",
		"File containing names of the source files to be processed.")
		.withRequiredArg()
		.describedAs("file")
		.ofType(File.class);		

		// split arguments for the checker and for the tool
		List<String> argsStanse = new LinkedList<String>();
		List<String> argsChecker = new LinkedList<String>();

		for (String option : args) {
			if (option.startsWith("-X") || option.startsWith("--X")) {
				argsChecker.add(option);
			} else {
				argsStanse.add(option);
			}
		}
		
		
		try {
			final OptionSet options = parser.parse(argsStanse.toArray(new String[0]));

			// HELP - called explicitly or no options given
			// -> exit
			if (options.has(help) || (args.length==0)) {
				try {
					parser.printHelpOn(System.out);
				} catch (IOException ex) {
					Logger.getLogger(Stanse.class.getName()).log(Level.FATAL, null, ex);
				} finally {
					System.exit(0);
				}
			}

			// VERSION
			// -> exit
			if(options.has(version)) {
				System.out.println(Stanse.class.getPackage().getImplementationVersion());
				System.exit(0);
			}

			// OUTPUT FILE
			// redirect output
			if (options.has(outputFile)) {
			}

			// OUTPUT DIRECTORY
			if (options.has(outputDir)) {
				File dir = options.valueOf(outputDir);
				if(!dir.exists()) {
					// TODO throw exception
				}
				outputDirectory = dir; 
			}

			// WARN LEVELS
			// now by severity of bug

			// DEBUG LEVELS
			if (options.has(debugLevel)) {
				Integer level = options.valueOf(debugLevel);
				switch (level) {
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

			// INPUT FILES
			// 1: read file names from input file
			if (options.has(inputFile)) {
				try {
					BufferedReader in = new BufferedReader(new FileReader(options.valueOf(inputFile)));
					String s;
					while ((s = in.readLine()) != null) {
						sources.add(new File(s));
					}
					in.close();
				} catch (IOException e) {
				}
			}
			// 2: read file names from the command line
			for (Object s : options.nonOptionArguments()) {
				sources.add(new File((String) s));
			}
			// 3: check, whether the source files really exist
			for (File f : sources ) {
				if (!f.exists()) {
					// TODO "File " + f + " does not exist! Exiting."
				}				
			}
			// TODO: single hyphen is a non-option argument, and as such should be present only if no file names are present

			// PARSING, CONVERSION to XML and CFG
			Unit unit;
			Document unitAST;
			List<CFG> unitCFG;
			File xmlFile;
			File cfgFile;
			String fileName;
			for (File unitFile : sources)			
			{		
				try {
					fileName = unitFile.getName();
					// we know we are dealing with C
					// TODO make this universal
					unit = new CUnit(unitFile);
					units.add(unit);
					
					// DUMP-XML
					if (options.has(dumpXML)) {
						unitAST = unit.getXMLDocument();
						xmlFile = new File(outputDirectory, fileName + ".xml");
						try {
							// BufferedWriter out = new BufferedWriter(new
							// FileWriter(xmlFile));
							XMLAlgo.outputXML(unitAST,	new PrintStream(xmlFile));
							// out.close();
						} catch (IOException e) {
							// TODO
							e.printStackTrace();
						}
					}
					// DUMP-CFG
					if (options.has(dumpCFG)) {
						unitCFG = unit.getCFGs();
						for (CFG cfg : unitCFG) {
							cfgFile = new File(outputDirectory, fileName + "."
									+ cfg.getFunctionName() + ".dot");
							try {
								BufferedWriter out = new BufferedWriter(
										new FileWriter(cfgFile));
								out.write(cfg.toDot());
								out.close();
							} catch (IOException e) {
								// TODO
							}
						}
					}
				} catch (NullPointerException e) {
					logger.log(Level.FATAL, null, e);
				} catch (RecognitionException e) {
					logger.log(Level.FATAL, null, e);
				} catch (IOException e) {
					logger.log(Level.FATAL, null, e);
				}

			}

			// TODO: create callgraph
			// DUMP-CALL GRAPH

			// GUI
			// if checkers are specified, they (together with their parameters) should be passed to GUI
			// or run?
			// TODO: GUI has to know about checkers somehow! (properties file?)
			// GUI has to be able to start any checker! (allow clasSpec in gui)
			// in GUI give a checkbox/radiobutton chooser between checkers
//			if (options.has(gui)) {
//			// TODO:	stanse.startGui();
//				return;
//			}

			// CHECKERS (only if --gui was not specified)
			// here we should parse checker related arguments. For now we allow only a single checker
			// by convention, checker related arguments start with "X"
			// we will pass the options set

			// If no checker was specified, exit.
			// TODO - check for multiple checkers
			if(!options.has(checkerClass)){
				// TODO
				System.err.println("No checkers specified, exiting.");
				System.exit(0);
			}
			// some checker was specified
			try {
				// this works only for no arguments:
				// Class c = Class.forName(options.valueOf(checkerClass));
				// Checker checker = (Checker)c.newInstance();

				// pass arguments
				Class cl = Class.forName(options.valueOf(checkerClass));
				Constructor c = cl.getConstructor(new Class[] { String[].class });
				Checker checker = (Checker)c.newInstance((Object) argsChecker.toArray(new String[0]));
								
				for (CheckerError e : checker.check(units)){
					// TODO: better output
					System.out.println(e.toString());
				}
			}
			catch ( ClassNotFoundException ex ){
				System.err.println( ex + " Interpreter class must be in class path.");
			}
			catch( InstantiationException ex ){
				System.err.println( ex + " Interpreter class must be concrete.");
			}
			catch( IllegalAccessException ex ){
				System.err.println( ex + " Interpreter class must have a no-arg constructor.");
			} catch (CheckerException e) {
				// TODO fix this
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			// UNKNOWN ARGUMENT
		} catch (OptionException ex) {
			try {
				System.out.println("Invalid option.");
				parser.printHelpOn(System.out);
			} catch (IOException ex1) {
				logger.log(Level.FATAL, null, ex1);
			}
		}
		}


		//		// Sets up the logging facility (reads the logging options from log4j.properties file.
		//		// This needs to be done only once
		//		// This is done after the verbosity level is set to use it as default
		//		LoggerConfigurator.doConfigure();
		//
		//		if(options.has(OPT_REPORT)) {
		//			FileAppender reportAppender = new FileAppender();
		//			reportAppender.setLayout(new HTMLLayout());
		//			reportAppender.setFile(options.argumentOf(OPT_REPORT));
		//			reportAppender.setThreshold(Level.INFO);
		//			reportAppender.activateOptions();
		//			/*                Logger.getLogger(StaticChecker.class).addAppender(reportAppender);
		//                Logger.getLogger(StaticChecker.class).setLevel(Level.INFO);*/
		//		}
		//
		//	}

//		public static void startGui() {
//
//			java.awt.EventQueue.invokeLater(new Runnable() {
//
//				public void run() {
//					GuiMain gui = new GuiMain();
//					gui.setVisible(true);
//					if (!sourceFiles.isEmpty()) {
//						for (String filename : sourceFiles) {
//							File file = new File(filename);
//							gui.openSourceFile(file);
//						}
//
//						Set<File> files = new HashSet<File>();
//						for (String checkerDefinition : checkerDefinitions) {
//							files.add(new File(checkerDefinition));
//						}
//
//						if (!files.isEmpty()) {
//
//							((SourceAndXMLWindow) gui.getJTabbedPane1()
//									.getSelectedComponent())
//									.runStaticChecker(files);
//						}
//					}
//
//				}
//			});
//
//		}


		/**
		 * This should be the setting, all the other classes should use. Log4j uses
		 * this as default for the level of debuging SILENT - no log4j output given
		 * by default LOW - FATAL, ERROR and WARN levels are dumped by default
		 * MIDDLE - FATAL, ERROR, WARN and INFO levels are dumped by default HIGH -
		 * all messages dumped by default This can be changed by the settings in the
		 * log4j.properties file.
		 * 
		 * @return Verbosity level
		 */
		public static Properties.VerbosityLevel getVerbosityLevel() {
			return VERBOSITY_LEVEL;
		}


	}
