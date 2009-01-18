package cz.muni.stanse.gui;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.File;

final class Configuration {

    // package-private section

    Configuration() {
        sourceConfiguration = createDefaultSourceConfiguration();
        checkerConfigurations = createDefaultCheckerConfiguration();
    }

    Configuration(final SourceConfiguration sourceConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
        checkerConfigurations = createDefaultCheckerConfiguration();
    }

    Configuration(final SourceConfiguration sourceConfiguration,
                  final LinkedList<CheckerConfiguration> checkerConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
        this.checkerConfigurations = checkerConfiguration;
    }

    void visit(final ConfigurationVisitor visitor) throws Exception {
        final List<Unit> units = getSourceConfiguration().getUnits();
        final HashMap<CFG,Unit> cfgToUnitMapping = buildCfgToUnitMapping(units);
        for (CheckerConfiguration checkerCfg : getCheckerConfigurations())
            if (!visitor.visit(units,checkerCfg.getChecker(),cfgToUnitMapping))
                break;
    }

    SourceConfiguration getSourceConfiguration() {
        return sourceConfiguration;
    }

    LinkedList<CheckerConfiguration> getCheckerConfigurations() {
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
        return new SourceConfiguration(
                     new File("../examples/AutomatonChecker/pointers/memory.c"),
                     SourceConfiguration.SourceType.SingleSourceFile);
    }

    private static LinkedList<CheckerConfiguration>
    createDefaultCheckerConfiguration() {
        final LinkedList<CheckerConfiguration> cfg =
                                         new LinkedList<CheckerConfiguration>();
        cfg.add(new CheckerConfiguration(
                  "cz.muni.stanse.automatonchecker.AutomatonChecker",
                  new File("./data/checkers/AutomatonChecker/memory.xml")));
        cfg.add(new CheckerConfiguration(
                  "cz.muni.stanse.automatonchecker.AutomatonChecker",
                  new File("./data/checkers/AutomatonChecker/interrupts.xml")));
        cfg.add(new CheckerConfiguration(
                  "cz.muni.stanse.automatonchecker.AutomatonChecker",
                  new File("./data/checkers/AutomatonChecker/locking.xml")));
        return cfg;
    }

    private final SourceConfiguration sourceConfiguration;
    private final LinkedList<CheckerConfiguration> checkerConfigurations;
}
