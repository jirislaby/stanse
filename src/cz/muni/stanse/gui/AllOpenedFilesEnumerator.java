package cz.muni.stanse.gui;

import cz.muni.stanse.SourceCodeFilesEnumerator;

public final class AllOpenedFilesEnumerator extends SourceCodeFilesEnumerator {
    @Override
    public java.util.List<String> getSourceCodeFiles() throws Exception {
        return MainWindow.getInstance().getOpenedSourceFilesManager().
                                                                 getAllFiles();
    }
}
