package cz.muni.stanse;

import cz.muni.stanse.configuration.Configuration;
import cz.muni.stanse.configuration.CheckerConfiguration;
import cz.muni.stanse.configuration.SourceConfiguration;
import cz.muni.stanse.configuration.source_enumeration.MakefileSourceEnumerator;
import cz.muni.stanse.configuration.source_enumeration.FileListEnumerator;
import cz.muni.stanse.configuration.source_enumeration.BatchFileEnumerator;
import cz.muni.stanse.configuration.source_enumeration
                                   .DirectorySourceEnumerator;
import cz.muni.stanse.utils.Pair;

import java.util.List;
import java.util.Vector;
import java.io.File;
import static java.util.Arrays.asList;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;
import joptsimple.OptionSet;
import org.apache.log4j.Level;

final class CmdLineManager {

    // package-private section

    CmdLineManager(final String[] args) {
        parser = new OptionParser();

        help =
              parser.acceptsAll(asList("h", "?", "help"),
                                "Shows this help message.");
        version =
              parser.accepts("version","Prints the program version.");

        // TODO: following option 'useIntraproceduralAnalysis' switches between
        //       (iter/intra)procedural analyses. But it does it globaly per all
        //       the checkers. This should be rewriten to enable swiching these
        //       options per checker.
        useIntraproceduralAnalysis =
              parser.accepts("intraprocedural",
                             "Use simpler intraprocedural analysis instead " +
                             "of much more complex interprocedural analysis. " +
                             "Affects all the checkers.");
        checkers =
              parser.acceptsAll(asList("c", "checker"),
                                "Checker name and (possibly) configuration. " +
                                "Can be used multiple times.")
                    .withRequiredArg()
                    .describedAs("name[[:configuration_file1]:" +
                                 "configuration_file2:name ...]")
                    .ofType(String.class);

        makefile =
              parser.accepts("makefile","Makefile specifying input files.")
                    .withRequiredArg()
                    .describedAs("file")
                    .ofType(String.class);
        makeParams =
              parser.accepts("make-params",
                             "Parameters passed to the make tool.")
                    .withRequiredArg()
                    .describedAs("parameters")
                    .ofType(String.class);
        jobfile =
              parser.accepts("jobfile","Jobfile specifying input files.")
                    .withRequiredArg()
                    .describedAs("file")
                    .ofType(String.class);
        dir =
              parser.accepts("dir","Directory to be (non-recursively) " +
                                   "searched for input files.")
                    .withRequiredArg()
                    .describedAs("directory")
                    .ofType(String.class);
    	rdir =
              parser.accepts("rdir","Directory to be recursively searched " +
                                    "for input files.")
                    .withRequiredArg()
                    .describedAs("directory")
                    .ofType(String.class);

        dumpCFG =
              parser.accepts("dump-cfg",
                             "Dump control flow graphs in Dot format");
    	dumpXML =
              parser.accepts("dump-xml",
                             "Dump XML representation of AST");
        dumpCallGraph =
              parser.accepts("dump-callgraph",
                             "Dump callgraph in Dot format");

        outputDir =
              parser.accepts("output-dir",
                             "Sets the output directory for generated files")
                    .withRequiredArg()
                    .describedAs("directory")
                    .ofType(File.class);

        debugLevel =
              parser.acceptsAll(asList("d","debug-level"),
                                "Sets the debug level")
                    .withRequiredArg()
                    .describedAs("n")
                    .ofType(Integer.class);

        gui =
              parser.acceptsAll(asList("g", "gui"),
                                "Starts GUI. GUI may be run in these styles: " +
                                "DEFAULT (equal to METAL style), SYSTEM (" +
                                "system dependant widgets), METAL - (system " +
                                "independet widgets)")
                    .withOptionalArg()
                    .describedAs("GUI style name (DEFAULT, SYSTEM, METAL)")
                    .ofType(String.class);

        statsBuild =
              parser.accepts("stats-build","Builds statistical data in XML " +
                                           "format of processed checking. " +
                                           "Output file for statistical data " +
                                           "must be provided as an argument")
                    .withRequiredArg()
                    .describedAs("file")
                    .ofType(String.class);
        statsGuiTracing =
              parser.accepts("stats-err-guitracing",
                             "Loads statistical database file and parses all " +
                             "its error messages and deliveres them into " +
                             "Stanse's GUI to enable easy error-tracing. It " +
                             "is also possible to specify relocation of " +
                             "source code files by specifying original and " +
                             "current location (directory) of souces. "+
                             "Original reports are updated by user-defined "+
                             "resolution state and are written to the " +
                             "output file.")
                    .withRequiredArg()
                    .describedAs("XMLdatabaseFile:outputFile" +
                                 "[:Original-sources-dir:Current-sources-dir]")
                    .ofType(String.class);
        statsSort =
              parser.accepts("stats-err-sort",
                             "Loads statistical database file and then it " +
                             "will sort error messages by lexicografical " +
                             "order defined by these keywords: " +
                             "checker_name (checker name), " +
                             "description (short description of report), " +
                             "unit (unit of error report), " +
                             "importance (importance). Output consists of " +
                             "directory tree with information files (.txt) " +
                             "and data files (.xml). Root of this directory " +
                             "structure is passed as the second argument here.")
                    .withRequiredArg()
                    .describedAs("XMLdatabaseFile:" +
                                 "outputDirectory:SortKeyword1:" +
                                 "SortKeyword2 ...")
                    .ofType(String.class);
        statsMerge =
              parser.accepts("stats-err-merge",
                             "Loads statistical database file and all XML " +
                             "file in specified directory. All these files " +
                             "combined (merged) to form one output XML " +
                             "database file. Merge is realized this way. " +
                             "Content of sections 'files','internals', " +
                             "'checkers' and 'checkfails' are simply copied" +
                             "from original database file. The last section " +
                             "'errors' is computed by simple concatenation " +
                             "of contents of 'errors' sections in XML files " +
                             "inside specified directory structure. Content " +
                             "of 'errors' section in original database file " +
                             "does not participate in this merge of 'errors' " +
                             "sections.")
                    .withRequiredArg()
                    .describedAs("XMLdatabaseFile:" +
                                 "outputDatabaseFile:RootOfDirectoryStructure")
                    .ofType(String.class);
        statsPerformance =
              parser.accepts("stats-performance",
                             "Loads statistical database file and then it " +
                             "searched in the data to collect all performace " +
                             "information. Collected performance data are " +
                             "then written into specified output file, in " +
                             "specified output format. Valid formats are: " +
                             "simple-text (human-readable simple text file)")
                    .withRequiredArg()
                    .describedAs("XMLdatabaseFile:" +
                                 "outputFile:OutputFormat")
                    .ofType(String.class);
        statsReports =
              parser.accepts("stats-reports",
                             "Loads statistical database file and then it " +
                             "searched in the data to collect all reports " +
                             "related information. Collected data are " +
                             "then written into specified output file, in " +
                             "specified output format. Valid formats are: " +
                             "simple-text (human-readable simple text file)")
                    .withRequiredArg()
                    .describedAs("XMLdatabaseFile:" +
                                 "outputFile:OutputFormat")
                    .ofType(String.class);

        options = parser.parse(args);

        numArgs = args.length;
    }

    SourceConfiguration getSourceConfiguration() {
        if (getOptions().has(makefile))
            return new SourceConfiguration(new MakefileSourceEnumerator(
                            getOptions().valueOf(makefile),
                            getOptions().has(makeParams) ?
                                getOptions().valueOf(makeParams) : ""));
        if (getOptions().has(jobfile))
            return new SourceConfiguration(new BatchFileEnumerator(
                            getOptions().valueOf(jobfile)));
        if (getOptions().has(dir))
            return new SourceConfiguration(new DirectorySourceEnumerator(
                            getOptions().valueOf(dir),"c",false));
        if (getOptions().has(rdir))
            return new SourceConfiguration(new DirectorySourceEnumerator(
                            getOptions().valueOf(dir),"c",true));
        if (!getOptions().nonOptionArguments().isEmpty())
            return new SourceConfiguration(new FileListEnumerator(
                            getOptions().nonOptionArguments()));
        return Configuration.createDefaultSourceConfiguration();
    }

    List<CheckerConfiguration> getCheckerConfiguration() {
        if (!getOptions().has(checkers))
            return Configuration.createDefaultCheckerConfiguration();

        Vector<CheckerConfiguration> checkerConfiguration =
            new Vector<CheckerConfiguration>();
        for (final String s: getOptions().valuesOf(checkers)) {
            String[] cc = s.split(":");
            final String checkerName = cc[0];
            final Vector<File> checkerDataFiles = new Vector<File>();
            for (int i = 1; i < cc.length; i++)
                checkerDataFiles.add(new File(cc[i]));
            checkerConfiguration.add(
                new CheckerConfiguration(checkerName,checkerDataFiles,
                                !getOptions().has(useIntraproceduralAnalysis)));
        }
        return checkerConfiguration;
    }

    String getOutputDir() {
        return getOptions().has(outputDir) ? getOptions().valueOf(outputDir)
                                                         .toString() :
                                             null;
    }

	Level getVerbosityLevel() {
		if (!getOptions().has(debugLevel))
			return Level.INFO;
		switch (getOptions().valueOf(debugLevel)) {
		case 0:
			return Level.ERROR;
		case 1:
			return Level.WARN;
		default:
			System.err.println("Illegal verbosity level. Falling "
				+ "back to the default - 2");
		case 2:
			return Level.INFO;
		case 3:
			return Level.DEBUG;
		}
	}

    boolean infoMode() {
        return numArgs == 0 || getOptions().has(help)
                            || getOptions().has(version);
    }

    void printInfo(final java.io.OutputStream sink) {
        if (numArgs == 0 || getOptions().has(help))
            try {
		getParser().printHelpOn(sink);
	    } catch (final java.io.IOException e) {
		System.err.println("Can't print help");
	    }
        if (getOptions().has(version))
            try {
		new java.io.DataOutputStream(sink).writeChars(
                          Stanse.class.getPackage().getImplementationVersion());
            } catch (final java.io.IOException e) {
		System.err.println("Can't print version");
	    }
    }

    boolean dumpAST() {
        return getOptions().has(dumpXML);
    }

    boolean dumpCFG() {
        return getOptions().has(dumpCFG);
    }

    boolean dumpCallGraph() {
        return getOptions().has(dumpCallGraph);
    }

    boolean statsMode() {
        return getOptions().has(statsBuild)         ||
               getOptions().has(statsSort)          ||
               getOptions().has(statsGuiTracing)    ||
               getOptions().has(statsMerge)         ||
               getOptions().has(statsPerformance)   ||
               getOptions().has(statsReports)       ;
    }

    String statsBuildFile() {
        return (getOptions().has(statsBuild)) ?
                    getOptions().valueOf(statsBuild) : null;
    }

    String getStatsDatabase() {
        assert(statsMode());
        final OptionSpec<String> option = getStatsDatabaseOption();
        assert(option != null);
        String[] cc = getOptions().valueOf(option).split(":");
        final String database = cc[0];
        return database;
    }

    boolean doStatsGuiTracing() {
        return getOptions().has(statsGuiTracing);
    }

    String statsGuiTracingOutputFile() {
        assert(getOptions().has(statsGuiTracing));
        String[] cc = getOptions().valueOf(statsGuiTracing).split(":");
        if (cc.length < 2)
	    return null;
        return cc[1];
    }

    String statsGuiTracingOrigSrcDir() {
        assert(getOptions().has(statsGuiTracing));
        String[] cc = getOptions().valueOf(statsGuiTracing).split(":");
        if (cc.length != 4)
            return null;
        return cc[2];
    }

    String statsGuiTracingCurrSrcDir() {
        assert(getOptions().has(statsGuiTracing));
        String[] cc = getOptions().valueOf(statsGuiTracing).split(":");
        if (cc.length != 4)
            return null;
        return cc[3];
    }

    String statsOrderingRootDir() {
        if (!getOptions().has(statsSort))
            return null;
        String[] cc = getOptions().valueOf(statsSort).split(":");
	if (cc.length < 2)
	    return null;
        final String orderingFile = cc[1];
        return orderingFile;
    }

    Vector<String> getStatsOrdering() {
        if (!getOptions().has(statsSort))
            return null;
        final Vector<String> ordering = new Vector<String>();
        String[] cc = getOptions().valueOf(statsSort).split(":");
        for (int i = 2; i < cc.length; i++)
            ordering.add(cc[i]);
        return ordering;
    }

    boolean doStatsMerge() {
        return getOptions().has(statsMerge);
    }

    String getStatsMergeOutputFile() {
        assert(getOptions().has(statsMerge));
        String[] cc = getOptions().valueOf(statsMerge).split(":");
        if (cc.length != 3)
	    return null;
        return cc[1];
    }

    String getStatsMergeDirsRoot() {
        assert(getOptions().has(statsMerge));
        String[] cc = getOptions().valueOf(statsMerge).split(":");
        if (cc.length != 3)
	    return null;
        return cc[2];
    }

    boolean doStatsPerformance() {
        return getOptions().has(statsPerformance);
    }

    String getStatsPerformanceOutputFile() {
        assert(getOptions().has(statsPerformance));
        String[] cc = getOptions().valueOf(statsPerformance).split(":");
        if (cc.length != 3)
	    return null;
        return cc[1];
    }

    String getStatsPerformanceOutputFormat() {
        assert(getOptions().has(statsPerformance));
        String[] cc = getOptions().valueOf(statsPerformance).split(":");
        if (cc.length != 3)
	    return null;
        return cc[2];
    }

    boolean doStatsReports() {
        return getOptions().has(statsReports);
    }

    String getStatsReportsOutputFile() {
        assert(getOptions().has(statsReports));
        String[] cc = getOptions().valueOf(statsReports).split(":");
        if (cc.length != 3)
	    return null;
        return cc[1];
    }

    String getStatsReportsOutputFormat() {
        assert(getOptions().has(statsReports));
        String[] cc = getOptions().valueOf(statsReports).split(":");
        if (cc.length != 3)
	    return null;
        return cc[2];
    }

    Pair<String,String> getUIdesc() {
	if (!getOptions().has(gui))
	    return Pair.make("TUI","");
	String value = getOptions().valueOf(gui);
	if (value == null)
	    value = "default";
        return Pair.make("GUI", value);
    }

    // private section

    private OptionSet getOptions() {
        return options;
    }

    private OptionParser getParser() {
        return parser;
    }

    private OptionSpec<String> getStatsDatabaseOption() {
        if (getOptions().has(statsSort)) return statsSort;
        if (getOptions().has(statsGuiTracing)) return statsGuiTracing;
        if (getOptions().has(statsMerge)) return statsMerge;
        if (getOptions().has(statsPerformance)) return statsPerformance;
        if (getOptions().has(statsReports)) return statsReports;
        return null;
    }

    private final OptionParser parser;
    private final OptionSpec<Void> help;
    private final OptionSpec<Void> version;
    private final OptionSpec<Void> useIntraproceduralAnalysis;
    private final OptionSpec<String> checkers;
    private final OptionSpec<String> makefile;
    private final OptionSpec<String> makeParams;
    private final OptionSpec<String> jobfile;
    private final OptionSpec<String> dir;
    private final OptionSpec<String> rdir;
    private final OptionSpec<Void> dumpCFG;
    private final OptionSpec<Void> dumpXML;
    private final OptionSpec<Void> dumpCallGraph;
    private final OptionSpec<File> outputDir;
    private final OptionSpec<Integer> debugLevel;
    private final OptionSpec<String> gui;
    private final OptionSpec<String> statsBuild;
    private final OptionSpec<String> statsGuiTracing;
    private final OptionSpec<String> statsSort;
    private final OptionSpec<String> statsMerge;
    private final OptionSpec<String> statsPerformance;
    private final OptionSpec<String> statsReports;
    private final OptionSet options;
    private final int numArgs;
}
