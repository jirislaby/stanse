package cz.muni.stanse.gui;

final class GuiSourceTypeConfigurationManager {

    // package-private section

    GuiSourceTypeConfigurationManager(
                    final javax.swing.JRadioButton actualOpenedFileRadioButton,
                    final javax.swing.JRadioButton allOpenedFilesRadioButton,
                    final javax.swing.JRadioButton singleSourceFileRadioButton,
                    final javax.swing.JRadioButton makefileRadioButton) {
        this.sourceType = GuiMainWindow.getInstance().getConfiguration()
                                      .getSourceConfiguration().getSourceType();
        this.actualOpenedFileRadioButton = actualOpenedFileRadioButton;
        this.allOpenedFilesRadioButton = allOpenedFilesRadioButton;
        this.singleSourceFileRadioButton = singleSourceFileRadioButton;
        this.makefileRadioButton = makefileRadioButton;

        addActionListeners();
        getButtonForSourceType().setSelected(true);
    }

    SourceConfiguration.SourceType getSourceType() {
        return sourceType;
    }

    // private section

    private void addActionListeners() {
        getActualOpenedFileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setSourceType(SourceConfiguration.SourceType.ActualOpenedFile);
            }
        });
        getAllOpenedFilesRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setSourceType(SourceConfiguration.SourceType.AllOpenedFiles);
            }
        });
        getSingleSourceFileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setSourceType(SourceConfiguration.SourceType.SingleSourceFile);
            }
        });
        getMakefileRadioButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                             final java.awt.event.ActionEvent e) {
                setSourceType(SourceConfiguration.SourceType.MakefileProject);
            }
        });
    }

    private javax.swing.JRadioButton getButtonForSourceType() {
        switch (getSourceType()) {
            case ActualOpenedFile: return getActualOpenedFileRadioButton();
            case AllOpenedFiles: return getAllOpenedFilesRadioButton();
            case SingleSourceFile: return getSingleSourceFileRadioButton();
            case MakefileProject: return getMakefileRadioButton();
            default: assert(false); return null;
        }
    }

    private void setSourceType(final SourceConfiguration.SourceType type) {
        sourceType = type;
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

    private SourceConfiguration.SourceType sourceType;
    private final javax.swing.JRadioButton actualOpenedFileRadioButton;
    private final javax.swing.JRadioButton allOpenedFilesRadioButton;
    private final javax.swing.JRadioButton singleSourceFileRadioButton;
    private final javax.swing.JRadioButton makefileRadioButton;
}
