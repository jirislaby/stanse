package cz.muni.stanse.gui;

@SuppressWarnings("serial")
final class GuiActionConfigure extends javax.swing.AbstractAction {

    // public section

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final GuiConfigurationDialog cfgDialog = new GuiConfigurationDialog();
        cfgDialog.pack();
        cfgDialog.setVisible(true);

        GuiMainWindow.getInstance().setConfiguration(new Configuration(
            cfgDialog.getSourceConfigurationManager().getSourceConfiguration(),
            cfgDialog.getCheckersConfurationManager().getCheckersConfiguration()
        ));
    }

    // package-private section

    GuiActionConfigure() {
        super();
    }
}
