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
                                 "configuration_file2 ...]")
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
              parser.acceptsAll(asList("g", "gui"), "Starts GUI")
                    .withOptionalArg()
                    .describedAs("name")
                    .ofType(String.class);

        statsBuild =
              parser.accepts("stats-build","Builds statistical data in XML " +
                                           "format of processed checking. " +
                                           "Output file for statistical data " +
                                           "must be provided as an argument")
                    .withRequiredArg()
                    .describedAs("file")
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
        {
            System.err.println("No checkers specified, AutomatonChecker " +
                               "used as default.");
            return Configuration.createDefaultCheckerConfiguration();
        }

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

    cz.muni.stanse.props.Properties.VerbosityLevel getVerbosityLevel() {
        if (!getOptions().has(debugLevel))
            return cz.muni.stanse.props.Properties.VerbosityLevel.LOW;
        switch (getOptions().valueOf(debugLevel)) {
            case 0:return cz.muni.stanse.props.Properties.VerbosityLevel.SILENT;
            case 1:return cz.muni.stanse.props.Properties.VerbosityLevel.LOW;
            case 2:return cz.muni.stanse.props.Properties.VerbosityLevel.MIDDLE;
            case 3:return cz.muni.stanse.props.Properties.VerbosityLevel.HIGH;
            default:
                System.err.println("Illegal verbosity level. Falling " +
                                   "back to default - 1");
                return cz.muni.stanse.props.Properties.VerbosityLevel.LOW;
        }
    }

    boolean infoMode() {
        return numArgs == 0 || getOptions().has(help)
                            || getOptions().has(version);
    }

    void printInfo(final java.io.OutputStream sink) {
        if (numArgs == 0 || getOptions().has(help))
            try { getParser().printHelpOn(sink); }
            catch (final java.io.IOException e) {}
        if (getOptions().has(version))
            try { new java.io.DataOutputStream(sink).writeChars(
                          Stanse.class.getPackage().getImplementationVersion());
            } catch (final java.io.IOException e) {}
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
        return getOptions().has(statsBuild);
    }

    String statsBuildFile() {
        return (getOptions().has(statsBuild)) ?
                    getOptions().valueOf(statsBuild) : null;
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
    private final OptionSet options;
    private final int numArgs;
}
