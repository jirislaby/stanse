package cz.muni.stanse.gui;

final class GuiSpecifySourceFilePathNameManager {

    // package-private section

    GuiSpecifySourceFilePathNameManager(
                        final javax.swing.JTextField sourceCodeFileTextField,
                        final javax.swing.JButton chooseFileOnDiscButton,
                        final javax.swing.JTextField makefileArgumentsTextField,
                        final String initialFile,
                        final String initialArguments) {
        this.sourceCodeFileTextField = sourceCodeFileTextField;
        this.chooseFileOnDiscButton = chooseFileOnDiscButton;
        this.makefileArgumentsTextField = makefileArgumentsTextField;

        getSourceCodeFileTextField().setText(initialFile);
        getMakefileArgumentsTextField().setText(initialArguments);

        getChooseFileOnDiscButton().addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                onChooseFileOnDisc();
            }
        });
    }

    String getSourceFile() {
        return getSourceCodeFileTextField().getText();
    }

    String getArguments() {
        return getMakefileArgumentsTextField().getText();
    }

    // private section

    private void onChooseFileOnDisc() {
        final javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(new java.io.File(
                                                   getSourceFile()).getPath()));
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

    private javax.swing.JTextField getMakefileArgumentsTextField() {
        return makefileArgumentsTextField;
    }

    private final javax.swing.JTextField sourceCodeFileTextField;
    private final javax.swing.JButton chooseFileOnDiscButton;
    private final javax.swing.JTextField makefileArgumentsTextField;
}
