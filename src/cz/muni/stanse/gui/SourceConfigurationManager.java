package cz.muni.stanse.gui;

import cz.muni.stanse.configuration.source_enumeration.AllOpenedFilesEnumerator;
import cz.muni.stanse.configuration.source_enumeration.
                                             ReferencedSourceCodeFileEnumerator;
import cz.muni.stanse.configuration.SourceConfiguration;
import cz.muni.stanse.configuration.source_enumeration.BatchFileEnumerator;
import cz.muni.stanse.configuration.source_enumeration.FileListEnumerator;
import cz.muni.stanse.configuration.source_enumeration.MakefileSourceEnumerator;
import cz.muni.stanse.configuration.source_enumeration.DirectorySourceEnumerator;
import cz.muni.stanse.configuration.source_enumeration.SourceCodeFilesEnumerator;

final class SourceConfigurationManager {

    // package-private section

    SourceConfigurationManager(
                    final javax.swing.JRadioButton actualOpenedFileRadioButton,
                    final javax.swing.JRadioButton allOpenedFilesRadioButton,
                    final javax.swing.JRadioButton makefileRadioButton,
                    final javax.swing.JRadioButton allDirectoryFilesRadioButton,
                    final javax.swing.JRadioButton
                                          allDirectoryHierarchyFilesRadioButton,
                    final javax.swing.JRadioButton batchFileRadioButton,
                    final javax.swing.JTextField sourceCodeFileTextField,
                    final javax.swing.JButton chooseFileOnDiscButton,
                    final javax.swing.JTextField makefileArgumentsTextField) {
        final SourceCodeFilesEnumerator oldEnumerator =
                                 MainWindow.getInstance().getConfiguration().
                                 getSourceConfiguration().getSourceEnumerator();
        this.sourceType = sourceTypeFromEnumerator(oldEnumerator);
        this.actualOpenedFileRadioButton = actualOpenedFileRadioButton;
        this.allOpenedFilesRadioButton = allOpenedFilesRadioButton;
        this.makefileRadioButton = makefileRadioButton;
        this.allDirectoryFilesRadioButton = allDirectoryFilesRadioButton;
        this.allDirectoryHierarchyFilesRadioButton =
                                          allDirectoryHierarchyFilesRadioButton;
        this.batchFileRadioButton = batchFileRadioButton;
        this.specifySourceFilePathNameManager =
            new SpecifySourceFilePathNameManager(sourceCodeFileTextField,
                              chooseFileOnDiscButton,makefileArgumentsTextField,
                              getEnumeratorReferenceFile(oldEnumerator),
                              getEnumeratorArguments(oldEnumerator));

        addActionListeners();
        buttonForSourceType().setSelected(true);
    }

    SourceConfiguration getSourceConfiguration() {
        return new SourceConfiguration(enumeratorFromSourceType());
    }

    // private section

    public enum SourceType {
        ActualOpenedFile(0),
        AllOpenedFiles(1),
        MakefileProject(2),
        DirectoryFiles(3),
        DirectoryHierarchyFiles(4),
        FilesListFile(5);

        SourceType(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        private final int value;
    }

    private void addActionListeners() {
        getActualOpenedFileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.ActualOpenedFile);
            }
        });
        getAllOpenedFilesRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.AllOpenedFiles);
            }
        });
        getMakefileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                             final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.MakefileProject);
            }
        });
        getAllDirectoryFilesRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                             final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.DirectoryFiles);
            }
        });
        getAllDirectoryHierarchyFilesRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                             final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.DirectoryHierarchyFiles);
            }
        });
        getBatchFileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                             final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.FilesListFile);
            }
        });
    }

    private javax.swing.JRadioButton buttonForSourceType() {
        switch (getSourceType()) {
            case ActualOpenedFile: return getActualOpenedFileRadioButton();
            case AllOpenedFiles: return getAllOpenedFilesRadioButton();
            case MakefileProject: return getMakefileRadioButton();
            case DirectoryFiles: return getAllDirectoryFilesRadioButton();
            case DirectoryHierarchyFiles:
                              return getAllDirectoryHierarchyFilesRadioButton();
            case FilesListFile: return getBatchFileRadioButton();
            default: assert(false); return null;
        }
    }

    private SourceCodeFilesEnumerator enumeratorFromSourceType() {
        switch (getSourceType()) {
            case ActualOpenedFile:
                return new ActiveOpenedFileEnumerator();
            case AllOpenedFiles:
                return new AllOpenedFilesEnumerator();
            case MakefileProject:
                return new MakefileSourceEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile(),
                        getSpecifySourceFilePathNameManager().getArguments());
            case DirectoryFiles:
                return new DirectorySourceEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile(),
                        "C",false);
            case DirectoryHierarchyFiles:
                return new DirectorySourceEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile(),
                        "C",true);
            case FilesListFile:
                return new BatchFileEnumerator
                    (getSpecifySourceFilePathNameManager().getSourceFile());
            default: assert(false); return null;
        }
    }

    private static SourceType sourceTypeFromEnumerator(
                                   final SourceCodeFilesEnumerator enumerator) {
        if (enumerator instanceof ActiveOpenedFileEnumerator)
            return SourceType.ActualOpenedFile;
        if (enumerator instanceof AllOpenedFilesEnumerator ||
            enumerator instanceof FileListEnumerator)
            return SourceType.AllOpenedFiles;
        if (enumerator instanceof MakefileSourceEnumerator)
            return SourceType.MakefileProject;
        if (enumerator instanceof DirectorySourceEnumerator)
            return (!((DirectorySourceEnumerator)enumerator).
                      getSearchSubdirectories()) ? SourceType.DirectoryFiles :
                          SourceType.DirectoryHierarchyFiles;
        if (enumerator instanceof BatchFileEnumerator)
            return SourceType.FilesListFile;
        assert(false);
        return null;
    }

    private String getEnumeratorReferenceFile(
                                   final SourceCodeFilesEnumerator enumerator) {
        if (enumerator instanceof  ReferencedSourceCodeFileEnumerator)
            return ((ReferencedSourceCodeFileEnumerator)enumerator).
                                                             getReferenceFile();
        if (enumerator instanceof ActiveOpenedFileEnumerator ||
            enumerator instanceof AllOpenedFilesEnumerator) {
            final String file = MainWindow.getInstance().
                                                getOpenedSourceFilesManager().
                                                                getActiveFile();
            if (file != null)
                return file;
        }
        return ".";
    }

    private String getEnumeratorArguments(
                                   final SourceCodeFilesEnumerator enumerator) {
        if (enumerator instanceof  MakefileSourceEnumerator)
            return ((MakefileSourceEnumerator)enumerator).getArguments();
        return "";
    }

    private SourceType getSourceType() {
        return sourceType;
    }

    private void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    private javax.swing.JRadioButton getActualOpenedFileRadioButton() {
        return actualOpenedFileRadioButton;
    }

    private javax.swing.JRadioButton getAllOpenedFilesRadioButton() {
        return allOpenedFilesRadioButton;
    }

    private javax.swing.JRadioButton getMakefileRadioButton() {
        return makefileRadioButton;
    }

    private javax.swing.JRadioButton getAllDirectoryFilesRadioButton() {
        return allDirectoryFilesRadioButton;
    }

    private javax.swing.JRadioButton getAllDirectoryHierarchyFilesRadioButton(){
        return allDirectoryHierarchyFilesRadioButton;
    }

    private javax.swing.JRadioButton getBatchFileRadioButton() {
        return batchFileRadioButton;
    }

    SpecifySourceFilePathNameManager getSpecifySourceFilePathNameManager() {
        return specifySourceFilePathNameManager;
    }

    private SourceType sourceType;
    private final javax.swing.JRadioButton actualOpenedFileRadioButton;
    private final javax.swing.JRadioButton allOpenedFilesRadioButton;
    private final javax.swing.JRadioButton makefileRadioButton;
    private final javax.swing.JRadioButton allDirectoryFilesRadioButton;
    private final javax.swing.JRadioButton
                                          allDirectoryHierarchyFilesRadioButton;
    private final javax.swing.JRadioButton batchFileRadioButton;
    private final SpecifySourceFilePathNameManager
                                               specifySourceFilePathNameManager;
}
