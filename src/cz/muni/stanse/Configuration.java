package cz.muni.stanse;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.gui.AllOpenedFilesEnumerator;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
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
                  final LinkedList<CheckerConfiguration> checkerConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
        this.checkerConfigurations = checkerConfiguration;
    }

    public void visit(final ConfigurationVisitor visitor,
           final ConfigurationProgressHandler progressHandler) throws Exception{
        final List<Unit> units = getSourceConfiguration().
                                                      getUnits(progressHandler);
        progressHandler.onCheckingBegin();
        final HashMap<CFG,Unit> cfgToUnitMapping = buildCfgToUnitMapping(units);
        for (CheckerConfiguration checkerCfg : getCheckerConfigurations())
            if (!visitor.visit(units,checkerCfg.getChecker(),cfgToUnitMapping))
                break;
        progressHandler.onCheckingEnd();
    }

    @Deprecated
    public void visitIntraprocedutral(final ConfigurationVisitor visitor,
            final ConfigurationProgressHandler progressHandler)throws Exception{
        final LinkedList<cz.muni.stanse.checker.Checker> checkers =
                               new LinkedList<cz.muni.stanse.checker.Checker>();
        for (CheckerConfiguration checkerCfg : getCheckerConfigurations())
            checkers.add(checkerCfg.getChecker());
        final SourceCodeFilesEnumerator sourceEnumerator =
                                 getSourceConfiguration().getSourceEnumerator();
        progressHandler.onParsingBegin();
        progressHandler.onCheckingBegin();
        for (String filePathName : sourceEnumerator.getSourceCodeFiles()) {
            progressHandler.onFileBegin(filePathName);
            final LinkedList<Unit> units = cz.muni.stanse.utils.Make.<Unit>
                                    linkedList(new cz.muni.stanse.cparser.CUnit(
                                               filePathName));
            progressHandler.onFileEnd();
            for (cz.muni.stanse.checker.Checker checker : checkers) {
                if (!visitor.visit(units,checker,buildCfgToUnitMapping(units)))
                    break;
            }
        }
        progressHandler.onCheckingEnd();
        progressHandler.onParsingEnd();
    }

    public SourceConfiguration getSourceConfiguration() {
        return sourceConfiguration;
    }

    public LinkedList<CheckerConfiguration> getCheckerConfigurations() {
        return checkerConfigurations;
    }

    // private section

    private static HashMap<CFG,Unit>
    buildCfgToUnitMapping(final List<Unit> units) throws Exception {
        final HashMap<CFG,Unit> result = new HashMap<CFG,Unit>();
        for (final Unit unit : units)   
            for (final CFG cfg : unit.getCFGs())
                result.put(cfg,unit);
        return result;
    }

    private static SourceConfiguration createDefaultSourceConfiguration() {
        return new SourceConfiguration(new AllOpenedFilesEnumerator());
    }

    private static LinkedList<CheckerConfiguration>
    createDefaultCheckerConfiguration() {
        return cz.muni.stanse.utils.Make.<CheckerConfiguration>linkedList(
            new CheckerConfiguration("AutomatonChecker",
                cz.muni.stanse.utils.Make.<java.io.File>linkedList(
                        new File(cz.muni.stanse.Stanse.getRootDirectory() +
                              "/data/checkers/AutomatonChecker/memory.xml"),
                        new File(cz.muni.stanse.Stanse.getRootDirectory() +
                              "/data/checkers/AutomatonChecker/interrupts.xml"),
                        new File(cz.muni.stanse.Stanse.getRootDirectory() +
                              "/data/checkers/AutomatonChecker/locking.xml")
           )));
    }

    private final SourceConfiguration sourceConfiguration;
    private final LinkedList<CheckerConfiguration> checkerConfigurations;
}
