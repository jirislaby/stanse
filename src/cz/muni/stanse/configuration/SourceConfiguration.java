package cz.muni.stanse.configuration;

import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesException;
import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesEnumerator;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.codestructures.LazyInternalStructuresInter;
import cz.muni.stanse.codestructures.LazyInternalStructuresIntra;
import cz.muni.stanse.cparser.CUnit;
import cz.muni.stanse.utils.ClassLogger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

public final class SourceConfiguration {

    // public section

    public SourceConfiguration(final SourceCodeFilesEnumerator
                               sourceEnumerator) {
        this.sourceEnumerator = sourceEnumerator;
	units = new LinkedList<Unit>();
	List<String> files = null;
	try {
	    files = sourceEnumerator.getSourceCodeFiles();
	} catch (SourceCodeFilesException e) {
	    ClassLogger.error(SourceConfiguration.class,
			      "Failed to get source files.", e);
	}

	if (files != null)
	    for (String pathName: files)
		units.add(new CUnit(pathName));

        cfgToUnitDictionary = null;
        internals = null;
	intraproceduralInternals = null;
    }

    public LazyInternalStructures getLazySourceInternals() {
        if (internals == null)
            setLazySourceInternals();
        return internals;
    }

    public LazyInternalStructures
		getLazySourceIntraproceduralInternals() {
	if (intraproceduralInternals == null)
            setLazySourceInternalsIntra();
	return intraproceduralInternals;
    }

    public SourceCodeFilesEnumerator getSourceEnumerator() {
        return sourceEnumerator;
    }

    // private section

    private List<Unit> getUnits() {
	return Collections.unmodifiableList(units);
    }

    private List<CFGHandle> getCFGHandles() {
        List<CFGHandle> cfghs = new LinkedList<CFGHandle>();
        for (Unit unit: getUnits())
            for (CFG cfg: unit.getCFGs())
                cfghs.add(new CFGHandle(unit, cfg));
        return cfghs;
    }

    private synchronized void setLazySourceInternals() {
	if (internals != null)
	    return;

	internals = new LazyInternalStructuresInter(getUnits(), getCFGHandles());
    }

    private synchronized void setLazySourceInternalsIntra() {
	if (intraproceduralInternals != null)
	    return;

	intraproceduralInternals =
	    new LazyInternalStructuresIntra(getUnits(), getCFGHandles());
    }

    private final SourceCodeFilesEnumerator sourceEnumerator;
    private List<Unit> units;
    private HashMap<CFG,Unit> cfgToUnitDictionary;
    private LazyInternalStructures internals;
    private LazyInternalStructures intraproceduralInternals;
}
