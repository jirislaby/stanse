package cz.muni.stanse.gui;

import cz.muni.stanse.SourceCodeFilesEnumerator;

final class GuiActiveOpenedFileEnumerator extends SourceCodeFilesEnumerator {
    @Override
    public java.util.List<String> getSourceCodeFiles() throws Exception {
        return cz.muni.stanse.utils.Make.linkedList(GuiMainWindow.getInstance().
                                 getOpenedSourceFilesManager().getActiveFile());
    }
}
