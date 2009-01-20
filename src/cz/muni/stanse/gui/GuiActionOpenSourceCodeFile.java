package cz.muni.stanse.gui;

@SuppressWarnings("serial")
final class GuiActionOpenSourceCodeFile extends javax.swing.AbstractAction {

    // public section

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(getInitialPath()));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new GuiFileChooserFileFilter(
                   "C source files",cz.muni.stanse.utils.Make.linkedList("C")));
        if (chooser.showDialog(GuiMainWindow.getInstance(),"Add") ==
                    javax.swing.JFileChooser.APPROVE_OPTION) {
            GuiMainWindow.getInstance().getOpenedSourceFilesManager().
                                      showSourceFile(chooser.getSelectedFile());
            setInitialPath(chooser.getSelectedFile().toString());
        }
    }

    // package-private section

    GuiActionOpenSourceCodeFile() {
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
