package cz.muni.stanse;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.cparser.CUnit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class SourceConfiguration {

    // public section

    public SourceConfiguration(final SourceCodeFilesEnumerator sourceEnumerator)
    { this.sourceEnumerator = sourceEnumerator; }

    public List<Unit> getUnits(final ConfigurationProgressHandler
                                             progressHandler) throws Exception {
        progressHandler.onParsingBegin();
        final List<Unit> result = new LinkedList<Unit>();
        for (String pathName : getSourceEnumerator().getSourceCodeFiles()) {
            progressHandler.onFileBegin(pathName);
            result.add(new CUnit(pathName));
            progressHandler.onFileEnd();
        }
        progressHandler.onParsingEnd();
        return Collections.unmodifiableList(result);
    }

    public SourceCodeFilesEnumerator getSourceEnumerator() {
        return sourceEnumerator;
    }

    // private section

    private final SourceCodeFilesEnumerator sourceEnumerator;
}
