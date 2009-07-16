package cz.muni.stanse.gui;

import cz.muni.stanse.configuration.Configuration;

@SuppressWarnings("serial")
final class ActionConfigure extends javax.swing.AbstractAction {

    // public section

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final ConfigurationDialog cfgDialog = new ConfigurationDialog();
        cfgDialog.pack();
        cfgDialog.setVisible(true);

        MainWindow.getInstance().setConfiguration(new Configuration(
            cfgDialog.getSourceConfigurationManager().getSourceConfiguration(),
            cfgDialog.getCheckersConfurationManager().getCheckersConfiguration()
        ));
    }

    // package-private section

    ActionConfigure() {
        super();
    }
}
