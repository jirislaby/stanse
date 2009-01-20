package cz.muni.stanse.gui;

final class GuiAllOpenedFilesEnumerator extends SourceCodeFilesEnumerator {
    @Override
    java.util.List<String> getSourceCodeFiles() throws Exception {
        return GuiMainWindow.getInstance().getOpenedSourceFilesManager().
                                                                 getAllFiles();
    }
}
