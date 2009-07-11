package cz.muni.stanse;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.cparser.CUnit;
import cz.muni.stanse.gui.AllOpenedFilesEnumerator;
import cz.muni.stanse.utils.Make;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public final class Configuration {

    // public section

    public Configuration() {
        sourceConfiguration = createDefaultSourceConfiguration();
        checkerConfigurations = createDefaultCheckerConfiguration();
    }

    public Configuration(final SourceConfiguration sourceConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
        checkerConfigurations = createDefaultCheckerConfiguration();
    }

    public Configuration(final SourceConfiguration sourceConfiguration,
                  final List<CheckerConfiguration> checkerConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
        this.checkerConfigurations = checkerConfiguration;
    }

    public void visit(final ConfigurationVisitor visitor,
           final ConfigurationProgressHandler progressHandler) throws Exception{
        final List<Unit> units = getSourceConfiguration().
                                                      getUnits(progressHandler);
        progressHandler.onCheckingBegin();
        final Map<CFG,Unit> cfgToUnitMapping = buildCfgToUnitMapping(units);
        for (CheckerConfiguration checkerCfg : getCheckerConfigurations())
            if (!visitor.visit(units,checkerCfg.getChecker(),cfgToUnitMapping))
                break;
        progressHandler.onCheckingEnd();
    }

    @Deprecated
    public void visitIntraprocedutral(final ConfigurationVisitor visitor,
            final ConfigurationProgressHandler progressHandler)throws Exception{
        final List<Checker> checkers = new LinkedList<Checker>();

        for (CheckerConfiguration checkerCfg: getCheckerConfigurations())
            checkers.add(checkerCfg.getChecker());

        final SourceCodeFilesEnumerator sourceEnumerator =
                                 getSourceConfiguration().getSourceEnumerator();
        final List<Unit> processedUnits = new LinkedList<Unit>();

        progressHandler.onParsingBegin();
        progressHandler.onCheckingBegin();

        for (String filePathName: sourceEnumerator.getSourceCodeFiles()) {
	    final List<Unit> units;
            progressHandler.onFileBegin(filePathName);
	    try {
		units = Make.<Unit>linkedList(new CUnit(filePathName));
	    } catch (ParserException e) {
		System.err.println("Failed to parse '" + filePathName + "':");
		e.printStackTrace();
		progressHandler.onFileEnd();
		continue;
	    }
            progressHandler.onFileEnd();
            for (Checker checker: checkers)
                if (!visitor.visit(units,checker,buildCfgToUnitMapping(units)))
                    break;

            processedUnits.addAll(units);
        }
        progressHandler.onCheckingEnd();
        progressHandler.onParsingEnd();
        getSourceConfiguration().setProcessedUnits(processedUnits);
    }

    public SourceConfiguration getSourceConfiguration() {
        return sourceConfiguration;
    }

    public List<CheckerConfiguration> getCheckerConfigurations() {
        return checkerConfigurations;
    }

    // private section

    private static Map<CFG,Unit> buildCfgToUnitMapping(final List<Unit> units)
				throws Exception {
        final Map<CFG,Unit> result = new HashMap<CFG,Unit>();
        for (final Unit unit : units)   
            for (final CFG cfg : unit.getCFGs())
                result.put(cfg, unit);
        return result;
    }

    private static SourceConfiguration createDefaultSourceConfiguration() {
        return new SourceConfiguration(new AllOpenedFilesEnumerator());
    }

    private static LinkedList<CheckerConfiguration>
	    createDefaultCheckerConfiguration() {
        return Make.<CheckerConfiguration>linkedList(
            new CheckerConfiguration("AutomatonChecker", Make.<File>linkedList(
                        new File(Stanse.getRootDirectory() +
                              "/data/checkers/AutomatonChecker/memory.xml"),
                        new File(Stanse.getRootDirectory() +
                              "/data/checkers/AutomatonChecker/interrupts.xml"),
                        new File(Stanse.getRootDirectory() +
                              "/data/checkers/AutomatonChecker/locking.xml")
                ),true));
    }

    private final SourceConfiguration sourceConfiguration;
    private final List<CheckerConfiguration> checkerConfigurations;
}
