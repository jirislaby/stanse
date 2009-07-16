package cz.muni.stanse.gui;

import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesException;

import java.util.List;

public class OpenedFilesAccessor {

    public static List<String>
    getFiles() throws SourceCodeFilesException {
        return MainWindow.getInstance().getOpenedSourceFilesManager().
                                                                 getAllFiles();
    }

    // private section

    private OpenedFilesAccessor() {
    }
}
