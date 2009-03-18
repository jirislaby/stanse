package cz.muni.stanse.gui;

import cz.muni.stanse.SourceCodeFilesEnumerator;

final class ActiveOpenedFileEnumerator extends SourceCodeFilesEnumerator {
    @Override
    public java.util.List<String> getSourceCodeFiles() throws Exception {
        return cz.muni.stanse.utils.Make.linkedList(MainWindow.getInstance().
                                 getOpenedSourceFilesManager().getActiveFile());
    }
}
