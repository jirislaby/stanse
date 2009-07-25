package cz.muni.stanse.configuration;

import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesException;
import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesEnumerator;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.LazyInternalProgramStructuresCollection;
import cz.muni.stanse.codestructures.LazyInternalProgramStructuresCollectionImpl;
import cz.muni.stanse.codestructures.LazyInternalProgramStructuresCollectionIntra;
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

    public LazyInternalProgramStructuresCollection getLazySourceInternals() {
        if (internals == null)
            setLazySourceInternals();
        return internals;
    }

    public LazyInternalProgramStructuresCollection
		getLazySourceIntraproceduralInternals() {
	if (intraproceduralInternals == null)
            setLazySourceIntraproceduralInternals();
	return intraproceduralInternals;
    }

    public SourceCodeFilesEnumerator getSourceEnumerator() {
        return sourceEnumerator;
    }

    // private section

    private List<Unit> getUnits() {
	return Collections.unmodifiableList(units);
    }

    private HashMap<CFG,Unit> getCFGtoUnitDictionary() {
        if (cfgToUnitDictionary == null)
            setCFGtoUnitDictionary();
        return cfgToUnitDictionary;
    }

    private synchronized void setLazySourceInternals() {
        if (internals == null)
            internals = new LazyInternalProgramStructuresCollectionImpl(
		    getUnits(), getCFGtoUnitDictionary());
    }

    private synchronized void setLazySourceIntraproceduralInternals() {
	if (intraproceduralInternals == null)
	    intraproceduralInternals =
		new LazyInternalProgramStructuresCollectionIntra(getUnits(),
			getCFGtoUnitDictionary());
    }

    private synchronized void setCFGtoUnitDictionary() {
        if (cfgToUnitDictionary != null)
            return;

        cfgToUnitDictionary = new HashMap<CFG,Unit>();
        for (final Unit unit : units)
            for (final CFG cfg : unit.getCFGs())
                cfgToUnitDictionary.put(cfg, unit);
    }

    private final SourceCodeFilesEnumerator sourceEnumerator;
    private List<Unit> units;
    private HashMap<CFG,Unit> cfgToUnitDictionary;
    private LazyInternalProgramStructuresCollection internals;
    private LazyInternalProgramStructuresCollection intraproceduralInternals;
}
