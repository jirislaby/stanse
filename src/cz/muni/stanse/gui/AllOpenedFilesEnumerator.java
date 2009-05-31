package cz.muni.stanse.gui;

import cz.muni.stanse.SourceCodeFilesEnumerator;
import cz.muni.stanse.SourceCodeFilesException;

import java.util.List;

public final class AllOpenedFilesEnumerator extends SourceCodeFilesEnumerator {
    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        return MainWindow.getInstance().getOpenedSourceFilesManager().
                                                                 getAllFiles();
    }
}
