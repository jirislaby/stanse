package cz.muni.stanse.gui;

final class GuiActiveOpenedFileEnumerator extends SourceCodeFilesEnumerator {
    @Override
    java.util.List<String> getSourceCodeFiles() throws Exception {
        return cz.muni.stanse.utils.Make.linkedList(GuiMainWindow.getInstance().
                                 getOpenedSourceFilesManager().getActiveFile());
    }
}
