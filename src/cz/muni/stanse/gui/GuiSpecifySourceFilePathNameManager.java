package cz.muni.stanse.gui;

final class GuiSpecifySourceFilePathNameManager {

    // package-private section

    GuiSpecifySourceFilePathNameManager(
                         final javax.swing.JTextField sourceCodeFileTextField,
                         final javax.swing.JButton chooseFileOnDiscButton) {
        this.sourceCodeFileTextField = sourceCodeFileTextField;
        this.chooseFileOnDiscButton = chooseFileOnDiscButton;
        getSourceCodeFileTextField().setText(GuiMainWindow.getInstance().
                                    getConfiguration().getSourceConfiguration().
                                    getSourcePathName().toString());

        getChooseFileOnDiscButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onChooseFileOnDisc();
            }
        });
    }

    java.io.File getSourceFile() {
        return new java.io.File(getSourceCodeFileTextField().getText());
    }

    // private section

    private void onChooseFileOnDisc() {
        final javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(getSourceFile().
                                                     getPath()));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.
                                     FILES_AND_DIRECTORIES);
        chooser.addChoosableFileFilter(new GuiFileChooserFileFilter(
                "C file, TXT batch file, Makefile, Source directory",
                cz.muni.stanse.utils.Make.linkedList("C","TXT","Makefile")));
        if (chooser.showDialog(getChooseFileOnDiscButton(),"Choose") ==
                javax.swing.JFileChooser.APPROVE_OPTION)
            getSourceCodeFileTextField().setText(chooser.getSelectedFile().
                                                 toString());
    }

    private javax.swing.JTextField getSourceCodeFileTextField() {
        return sourceCodeFileTextField;
    }

    private javax.swing.JButton getChooseFileOnDiscButton() {
        return chooseFileOnDiscButton;
    }

    private final javax.swing.JTextField sourceCodeFileTextField;
    private final javax.swing.JButton chooseFileOnDiscButton;
}
