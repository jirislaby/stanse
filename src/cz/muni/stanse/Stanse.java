/*
 * Main class of the project.
 *
 * Copyright (c) 2009 Jan Obdrzalek
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.XMLAlgo;
import cz.muni.stanse.gui.MainWindow;
import cz.muni.stanse.props.Properties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import static java.util.Arrays.*;
import java.util.LinkedList;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;
import joptsimple.OptionSet;
import joptsimple.OptionException;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.dom4j.Document;

/**
 * Class containing the main() method. Not supposed to be instantiated.
 * The main functionality is command-line parsing.
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
	private static File outputDirectory = new File("");

	/**
	 * List of sourcefiles.
	 */
	private static List<String> sources = new LinkedList<String>();	
	
	/**
	 * List of compilation units.
	 */
	 static List<Unit> units = new LinkedList<Unit>();
	
 	/**
	 * @brief Reads command line and invokes the relevant methods. 
	 * 
	 * Calls parser for compilation units specified on the command line.
	 * Calls the given checker (not if --gui was specified)
	 * Currently depends on the --long-option=xxx syntax for the -X/--X options
	 *  
	 * Parsing done using JOpt simple. 
	 * Homepage: http://jopt-simple.sourceforge.net/
	 * 
	 * @param args Command-line options
	 */
	 // TODO Should return an exitcode.
	 public static void main(String[] args) {
		 System.out.println("Stanse version \"0.9alpha\"");
		 System.out.println("Copyright (c) 2009 Masaryk University, Brno\n");
		 System.out.println("WARNING: This is an alpha-release. The false-positive rate is not indicative of the false-positive rate for the full release.\n");
		 setRootDirectory();
		 
		 // parse input options
		OptionParser parser = new OptionParser();
		OptionSpec<Void> help = parser.acceptsAll( asList( "h", "?", "help" ), 
		"Shows this help message and exits." );	

        final OptionSpec<String> gui =
            parser.acceptsAll(asList("g","gui"),"Starts GUI" )
                  .withOptionalArg()
                  .describedAs("name")
                  .ofType(String.class);

        OptionSpec<Void> version = parser.accepts( "version", "Prints the program version and exits" );
		// *** Checkers and their configurations
		OptionSpec<String> checkers = parser.acceptsAll( asList( "c", "checker"),
		"Checker name and (possibly) configuration. Can be used multiple times.")
		.withRequiredArg()
		.describedAs("name[[:configuration_file1]:configuration_file2 ...]")
		.ofType(String.class);		
//		OptionSpec<String> checkerName = parser.acceptsAll( asList( "c", "checker"),
//			"Checker to be run.")
//		.withRequiredArg()
//		.describedAs("name")
//		.ofType(String.class);
//		OptionSpec<String> checkerData = parser.acceptsAll( asList( "cd", "checker-data"),
//				"Checker configuration file. Can occur more than once.") // TODO multiple checkers
//		.withRequiredArg()
//		.describedAs("file")
//		.ofType(String.class);
		// *** Different ways of specifying input files.
		OptionSpec<String> makefile = parser.accepts("makefile", "Makefile specifying input files.")
		.withRequiredArg()
		.describedAs("file")
		.ofType(String.class);
		OptionSpec<String> makeParams = parser.accepts("make-params", "Parameters passed to the make tool.")
		.withRequiredArg()
		.describedAs("parameters")
		.ofType(String.class);  
		OptionSpec<String> jobfile = parser.accepts("jobfile", "Jobfile specifying input files.")
		.withRequiredArg()
		.describedAs("file")
		.ofType(String.class);  
		OptionSpec<String> dir = parser.accepts("dir", "Directory to be (non-recursively) searched for input files.")
		.withRequiredArg()
		.describedAs("directory")
		.ofType(String.class);  
		OptionSpec<String> rdir = parser.accepts("rdir", "Directory to be recursively searched for input files.")
		.withRequiredArg()
		.describedAs("directory")
		.ofType(String.class);  
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
		.describedAs("directory")
		.ofType(File.class);

		final OptionSpec<String> singleSourceFile =
            parser.accepts("single-file",
                           "Single source file to be checked for bugs.")
                  .withRequiredArg()
                  .describedAs("file")
                  .ofType(String.class);

        // TODO: following option 'useIntraproceduralAnalysis' switches between
        //       (iter/intra)procedural analyses. But it does it globaly per all
        //       the checkers. This should be rewriten to enable swiching these
        //       options per checker.
        final OptionSpec<Void> useIntraproceduralAnalysis =
            parser.accepts("intraprocedural",
                           "Use simpler intraprocedural analysis instead of " +
                           "much more complex interprocedural analysis. " +
                           "Affects all the checkers.");

        // TODO: how to print "command-line usage", including the sources?

        Configuration config = null;
        final OptionSet options = parser.parse(args);
		try {

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
				File d = options.valueOf(outputDir);
				if(!d.exists()) {
                    System.out.print("Output directory: '" + d.toString() +
                                       "' does not exist.\n\tCreating it...");
                    d.mkdir();
                    System.out.println(d.exists() ? "Done." :
                                            "Failed! Output won't be written.");
				}
				outputDirectory = d; 
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

			// INPUT FILES SPECIFICATION
			SourceCodeFilesEnumerator e;
			// 1: read file names from the command line
			for (Object s : options.nonOptionArguments()) {
				sources.add((String) s);
			}
			    // check, whether the source files really exist
			for (String s : sources ) {
				File f = new File(s);
				if (!f.exists()) {
					System.err.println("File " + f + " does not exist! Exiting.");
					return;
				}				
			}
			e = new FileListEnumerator(sources);
			// X: check that only one of the possible inputs is applicable
			{	int i=0;
				if (!sources.isEmpty()) i++;
				if (options.has(makefile)) i++;
				if (options.has(jobfile)) i++;
				if (options.has(dir)) i++;
				if (options.has(rdir)) i++;
                if (options.has(singleSourceFile)) i++;
				if (i>1) {
					// TODO "Only one way of specifying source files can be used".
				}
			}
			// 2: Makefile & make paremeters
			if (options.has(makefile)) {
				File f = new File(options.valueOf(makefile));
				if (! f.exists() ) {
					System.err.println("Makefile " + f + " does not exist! Exiting.");
					return;
				}
				String s;
				if (options.has(makeParams)) { 
					s = options.valueOf(makeParams);
				} else s="";
				e = new MakefileSourceEnumerator(options.valueOf(makefile), s);
			}
			// 3: Jobfile
			if (options.has(jobfile)) {
				File f = new File(options.valueOf(jobfile));
				if (! f.exists() ) {
					System.err.println("Jobfile " + f + " does not exist! Exiting.");
					return;
				}
				e = new BatchFileEnumerator(options.valueOf(jobfile));
			}
			// 4: Directory
			if (options.has(dir)) {
				File d = new File(options.valueOf(dir));
				if (!(d.exists() && d.isDirectory() )) {
					System.err.println("Directory " + d + " does not exist! Exiting.");
					return;
				} 				
				e = new DirectorySourceEnumerator(options.valueOf(dir), "c", false);
			}
			// 5: Recursive directory
			if (options.has(rdir)) {
				File d = new File(options.valueOf(rdir));
				if (!(d.exists() && d.isDirectory() )) {
					System.err.println("Directory " + d + " does not exist! Exiting.");
					return;
				} 				
				e = new DirectorySourceEnumerator(options.valueOf(rdir), "c", true);
			}

            // 6: Single source file
            if (options.has(singleSourceFile)) {
                final File file = new File(options.valueOf(singleSourceFile));
                if (!file.exists()) {
                    System.err.println(
                        "Processing option : '" + singleSourceFile.toString() +
                        "' failed. Reason is:\nFile " + file +
                        " does not exist!\nExiting.");
                    return;
                }
                e = new SingleFileEnumerator(file.toString());
            }

			// 7: TODO single hyphen - read from standard input
			// "-" is a non-option argument, and as such should be present only if no file names are present
			// LAST:

            SourceConfiguration sourceConfig=new SourceConfiguration(e);

			// CHECKERS
			if(options.has(checkers)){ // at least one checker was specified
				// TODO - short names instead of classes
				String checkerName;
				LinkedList<File> CheckerDataFiles;
				LinkedList<CheckerConfiguration> checkerConfig = new LinkedList<CheckerConfiguration>();
				for (String s : options.valuesOf(checkers)) {
					CheckerDataFiles = new LinkedList<File>(); // empty by default
					String[] cc = s.split(":");
					// checker name
					checkerName=cc[0];
					// checker data, multiple permitted
					for (int i=1; i<cc.length; i++) {
						CheckerDataFiles.add(new File(cc[i]));
					}
					checkerConfig.add(
                        new CheckerConfiguration(checkerName,CheckerDataFiles,
                                     !options.has(useIntraproceduralAnalysis)));
				}
				config = new Configuration(sourceConfig, checkerConfig);
			} else { // use default configuration
				// TODO - output handling
				System.err.println("No checkers specified, AutomatonChecker used as default.");
				config = new Configuration(sourceConfig); 
			}
			
			//  *** RUN
			if (options.has(gui)) { 	// GUI
                final Configuration configFinal = config;
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
                        if (options.valueOf(gui) != null)
                            MainWindow.setLookAndFeel(options.valueOf(gui));
						MainWindow gui = MainWindow.getInstance();
						if (sources.isEmpty()) { 
							gui.setConfiguration(configFinal);
						} else {
							// open sources specified on the command line and set config
							gui.openSourceFiles(sources, configFinal);
						}				
						gui.setVisible(true);
					}
				});
				//return;
			} else { 					// CLI
				Pair<LinkedList<CheckerError>, LinkedList<PresentableError> > errors;
				try {
					errors= CheckForBugs.run(config, new ConsoleProgressHandler());
					for (PresentableError error: errors.getSecond()) {
						System.out.println(error.toString());
					}
				} catch (Exception ex) {
					System.err.println("Fatal error when " +
						"executing the checker:");
					ex.printStackTrace();
					logger.log(Level.FATAL, null, ex);
				}
			}

	        // UNKNOWN ARGUMENT
		} catch (OptionException ex) {
            System.out.println("Command-line arguments parsing has failed:\n" +
                               ex.getMessage() + '\n' + ex.getStackTrace() +
                               "\n\nSee Stanse command-line help:\n");
	             try {
	                     //System.out.println("Invalid option.");
	                     parser.printHelpOn(System.out);
	             } catch (IOException ex1) {
	                     logger.log(Level.FATAL, null, ex1);
	             }
             return;
	     }
	     

			
			// TODO: dumpXML, dumpAST
			// PARSING, CONVERSION to XML and CFG
            
			//Unit unit;
			Document unitAST;
			List<CFG> unitCFG;
			File xmlFile;
			File cfgFile;
			String fileName;

            List<Unit> unitsList = new LinkedList<Unit>();
            try {
                if (config == null)
                    throw new Exception("Configuration not set.");
                unitsList = config.getSourceConfiguration()
                               .getProcessedUnits(new ConsoleProgressHandler());
            }
            catch (final Exception e) {
                System.err.println("Dump error: ");
            }

            for (Unit unit : unitsList)
			{		
				try {
					fileName = unit.getName();
					// we know we are dealing with C
					// TODO make this universal
					//unit = new CUnit(unitFile);
					//units.add(unit);
					
					// DUMP-XML
					if (options.has(dumpXML)) {
						unitAST = unit.getXMLDocument();
                        final String name =
                                outputDirectory.toString().isEmpty() ?
                                    fileName : (new File(fileName)).getName();
						xmlFile = new File(outputDirectory,name + ".xml");
						try {
							 //BufferedWriter out = new BufferedWriter(new
							 //FileWriter(xmlFile));
							XMLAlgo.outputXML(unitAST,	new PrintStream(xmlFile));
							 //out.close();
						} catch (IOException e) {
							// TODO
							e.printStackTrace();
						}
					}
					// DUMP-CFG
					if (options.has(dumpCFG)) {
						unitCFG = unit.getCFGs();
						for (CFG cfg : unitCFG) {
                            final String name =
                                    outputDirectory.toString().isEmpty() ?
                                        fileName : (new File(fileName)).getName();
							cfgFile = new File(outputDirectory, name + "." +
                                                cfg.getFunctionName() + ".dot");
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
//				} catch (ParserException e) {
//					logger.log(Level.FATAL, null, e);
//				} catch (NullPointerException e) {
//					logger.log(Level.FATAL, null, e);
//				} catch (RecognitionException e) {
//					logger.log(Level.FATAL, null, e);
//				} catch (IOException e) {
				} catch (Exception e) {
					logger.log(Level.FATAL, null, e);
				}


			// TODO:  DUMP-CALL GRAPH

			
			

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
			}
            
     }

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


    public static String getRootDirectory() {
        return rootDirectory;
    }

    // private section

    private Stanse() {
    }

    private static String rootDirectory;

    public static void setRootDirectory() {
        try {
            rootDirectory = System.getenv("STANSE_HOME");
            if (rootDirectory == null) {
            	System.err.println("STANSE_HOME not specified. Using the location of stanse.jar.");            	
            	rootDirectory = new java.io.File(cz.muni.stanse.utils.ClassLocation.
                        get("cz.muni.stanse.Stanse")).getParent();
            }
        }
        catch (final Exception e) {
            System.out.println(e.getStackTrace());
            rootDirectory = new java.io.File(".").getAbsolutePath();
        }
    }
}
