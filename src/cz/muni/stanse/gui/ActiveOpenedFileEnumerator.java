package cz.muni.stanse.gui;

import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesEnumerator;
import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesException;
import cz.muni.stanse.utils.Make;

import java.util.List;

final class ActiveOpenedFileEnumerator extends SourceCodeFilesEnumerator {
    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
	String f = MainWindow.getInstance().getOpenedSourceFilesManager().
		getActiveFile();
        return f == null ? Make.<String>linkedList() : Make.linkedList(f);
    }
}
