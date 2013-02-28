package cz.muni.stanse.gui;

@SuppressWarnings("serial")
final class ActionOpenSourceCodeFile extends javax.swing.AbstractAction {

    // public section

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(getInitialPath()));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileChooserFileFilter(
                "C source files",cz.muni.stanse.utils.Make.linkedList("c")));
        if (chooser.showDialog(MainWindow.getInstance(),"Add") ==
                    javax.swing.JFileChooser.APPROVE_OPTION) {
            MainWindow.getInstance().getOpenedSourceFilesManager().
                                      showSourceFile(chooser.getSelectedFile());
            setInitialPath(chooser.getSelectedFile().toString());
        }
    }

    // package-private section

    ActionOpenSourceCodeFile() {
        super();
        initialPath = ".";
    }

    // private section

    private String getInitialPath() {
        return initialPath;
    }

    private void setInitialPath(final String path) {
        initialPath = path;
    }

    private String initialPath;
}
