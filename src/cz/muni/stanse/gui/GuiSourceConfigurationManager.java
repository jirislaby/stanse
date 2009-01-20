package cz.muni.stanse.gui;

final class GuiSourceConfigurationManager {

    // package-private section

    GuiSourceConfigurationManager(
                    final javax.swing.JRadioButton actualOpenedFileRadioButton,
                    final javax.swing.JRadioButton allOpenedFilesRadioButton,
                    final javax.swing.JRadioButton singleSourceFileRadioButton,
                    final javax.swing.JRadioButton makefileRadioButton,
                    final javax.swing.JRadioButton allDirectoryFilesRadioButton,
                    final javax.swing.JRadioButton
                                          allDirectoryHierarchyFilesRadioButton,
                    final javax.swing.JRadioButton batchFileRadioButton,
                    final javax.swing.JTextField sourceCodeFileTextField,
                    final javax.swing.JButton chooseFileOnDiscButton) {
        final SourceCodeFilesEnumerator oldEnumerator =
                                 GuiMainWindow.getInstance().getConfiguration().
                                 getSourceConfiguration().getSourceEnumerator();
        this.sourceType = sourceTypeFromEnumerator(oldEnumerator);
        this.actualOpenedFileRadioButton = actualOpenedFileRadioButton;
        this.allOpenedFilesRadioButton = allOpenedFilesRadioButton;
        this.singleSourceFileRadioButton = singleSourceFileRadioButton;
        this.makefileRadioButton = makefileRadioButton;
        this.allDirectoryFilesRadioButton = allDirectoryFilesRadioButton;
        this.allDirectoryHierarchyFilesRadioButton =
                                          allDirectoryHierarchyFilesRadioButton;
        this.batchFileRadioButton = batchFileRadioButton;
        this.specifySourceFilePathNameManager =
            new GuiSpecifySourceFilePathNameManager(sourceCodeFileTextField,
                                     chooseFileOnDiscButton,
                                     getEnumeratorReferenceFile(oldEnumerator));

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
        SingleSourceFile(2),
        MakefileProject(3),
        DirectoryFiles(4),
        DirectoryHierarchyFiles(5),
        FilesListFile(6);

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
        getSingleSourceFileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setSourceType(SourceType.SingleSourceFile);
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
            case SingleSourceFile: return getSingleSourceFileRadioButton();
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
                return new GuiActiveOpenedFileEnumerator();
            case AllOpenedFiles:
                return new GuiAllOpenedFilesEnumerator();
            case SingleSourceFile:
                return new SingleFileEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile());
            case MakefileProject:
                return new MakefileSourceEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile());
            case DirectoryFiles:
                return new DirectorySourceEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile(),
                        "C",false);
            case DirectoryHierarchyFiles:
                return new DirectorySourceEnumerator(
                        getSpecifySourceFilePathNameManager().getSourceFile(),
                        "C",true);
            case FilesListFile:
                return new SourceFilesListEnumerator
                    (getSpecifySourceFilePathNameManager().getSourceFile());
            default: assert(false); return null;
        }
    }

    private static SourceType sourceTypeFromEnumerator(
                                   final SourceCodeFilesEnumerator enumerator) {
        if (enumerator instanceof GuiActiveOpenedFileEnumerator)
            return SourceType.ActualOpenedFile;
        if (enumerator instanceof GuiAllOpenedFilesEnumerator)
            return SourceType.AllOpenedFiles;
        if (enumerator instanceof SingleFileEnumerator)
            return SourceType.SingleSourceFile;
        if (enumerator instanceof MakefileSourceEnumerator)
            return SourceType.MakefileProject;
        if (enumerator instanceof DirectorySourceEnumerator)
            return (!((DirectorySourceEnumerator)enumerator).
                      getSearchSubdirectories()) ? SourceType.DirectoryFiles :
                          SourceType.DirectoryHierarchyFiles;
        if (enumerator instanceof SourceFilesListEnumerator)
            return SourceType.FilesListFile;
        assert(false);
        return null;
    }

    private String getEnumeratorReferenceFile(
                                   final SourceCodeFilesEnumerator enumerator) {
        if (enumerator instanceof  ReferencedSourceCodeFilesEnumerator)
            return ((ReferencedSourceCodeFilesEnumerator)enumerator).
                                                             getReferenceFile();
        if (enumerator instanceof GuiActiveOpenedFileEnumerator ||
            enumerator instanceof GuiAllOpenedFilesEnumerator) {
            final String file = GuiMainWindow.getInstance().
                                                getOpenedSourceFilesManager().
                                                                getActiveFile();
            if (file != null)
                return file;
        }
        return ".";
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

    private javax.swing.JRadioButton getSingleSourceFileRadioButton() {
        return singleSourceFileRadioButton;
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

    GuiSpecifySourceFilePathNameManager getSpecifySourceFilePathNameManager() {
        return specifySourceFilePathNameManager;
    }

    private SourceType sourceType;
    private final javax.swing.JRadioButton actualOpenedFileRadioButton;
    private final javax.swing.JRadioButton allOpenedFilesRadioButton;
    private final javax.swing.JRadioButton singleSourceFileRadioButton;
    private final javax.swing.JRadioButton makefileRadioButton;
    private final javax.swing.JRadioButton allDirectoryFilesRadioButton;
    private final javax.swing.JRadioButton
                                          allDirectoryHierarchyFilesRadioButton;
    private final javax.swing.JRadioButton batchFileRadioButton;
    private final GuiSpecifySourceFilePathNameManager
                                               specifySourceFilePathNameManager;
}
