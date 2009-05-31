package cz.muni.stanse;

import cz.muni.stanse.SourceCodeFilesException;
import cz.muni.stanse.codestructures.ParserException;
import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.cparser.CUnit;

import java.io.IOException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class SourceConfiguration {

    // public section

    public SourceConfiguration(final SourceCodeFilesEnumerator
			sourceEnumerator) {
	this.sourceEnumerator = sourceEnumerator;
    }

    public List<Unit> getUnits(final ConfigurationProgressHandler
			progressHandler) {
        progressHandler.onParsingBegin();
        final List<Unit> result = new LinkedList<Unit>();
	List <String> files;
	try {
	    files = getSourceEnumerator().getSourceCodeFiles();
	} catch (SourceCodeFilesException e) {
	    System.err.println("Failed to get source files:");
	    e.printStackTrace();
	    return Collections.unmodifiableList(result);
	}
        for (String pathName: files) {
            progressHandler.onFileBegin(pathName);
	    try {
		result.add(new CUnit(pathName));
	    } catch (ParserException e) {
		System.err.println("Failed to parse '" + pathName + "':");
		e.printStackTrace();
	    } catch (IOException e) {
		System.err.println("Failed to parse '" + pathName + "':");
		e.printStackTrace();
	    }
            progressHandler.onFileEnd();
        }
        progressHandler.onParsingEnd();
        return Collections.unmodifiableList(result);
    }

    public SourceCodeFilesEnumerator getSourceEnumerator() {
        return sourceEnumerator;
    }

    @Deprecated
    public void setProcessedUnits(final List<Unit> units) {
        processedUnitList = Collections.unmodifiableList(units);
    }

    @Deprecated
    public List<Unit> getProcessedUnits(final ConfigurationProgressHandler
                                             progressHandler) {
        return processedUnitList = (processedUnitList != null) ?
                                  processedUnitList : getUnits(progressHandler);
    }

    // private section

    private List<Unit> processedUnitList = null;
    private final SourceCodeFilesEnumerator sourceEnumerator;
}
