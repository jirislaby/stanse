package cz.muni.stanse.configuration.source_enumeration;

import cz.muni.stanse.gui.OpenedFilesAccessor;

import java.util.List;

public final class AllOpenedFilesEnumerator extends SourceCodeFilesEnumerator {
    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        return OpenedFilesAccessor.getFiles();
    }
}
