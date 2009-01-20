package cz.muni.stanse.gui;

import cz.muni.stanse.codestructures.Unit;

import java.util.LinkedList;

final class SourceConfiguration {

    // package-private section

    SourceConfiguration(final SourceCodeFilesEnumerator sourceEnumerator) {
        this.sourceEnumerator = sourceEnumerator;
    }

    java.util.List<Unit> getUnits() throws Exception {
        final LinkedList<Unit> result = new LinkedList<Unit>();
        for (String pathName : getSourceEnumerator().getSourceCodeFiles())
            result.add(new cz.muni.stanse.cparser.CUnit(
                                                   new java.io.File(pathName)));
        return java.util.Collections.unmodifiableList(result);
    }

    SourceCodeFilesEnumerator getSourceEnumerator() {
        return sourceEnumerator;
    }

    // private section

    private final SourceCodeFilesEnumerator sourceEnumerator;
}
