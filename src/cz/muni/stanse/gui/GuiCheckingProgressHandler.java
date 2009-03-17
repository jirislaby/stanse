package cz.muni.stanse.gui;

import cz.muni.stanse.ConfigurationProgressHandler;

final class GuiCheckingProgressHandler extends ConfigurationProgressHandler {

    // package-private section

    @Override
    public void onProcessBegin() {
        getConsole().clear();
        getConsole().appendText("Checking procedure started.\n");
    }

    @Override
    public void onParsingBegin() {
        getConsole().appendText("   Parsing source file(s)...\n");
    }

    @Override
    public void onFileBegin(final String filePathName) {
        getConsole().appendText("      " + filePathName + "...");
    }

    @Override
    public void onFileEnd() {
        getConsole().appendText("Done.\n");
    }

    @Override
    public void onParsingEnd() {
        getConsole().appendText("   Done.\n");
    }

    @Override
    public void onCheckingBegin() {
        getConsole().appendText("   Checking parsed file(s)....\n");
    }

    @Override
    public void onCheckerBegin(final String checkerName) {
        getConsole().appendText("      " + checkerName + " in progress...");
    }

    @Override
    public void onCheckerEnd(final int numErrorsFound) {
        getConsole().appendText("Done. Erros found: " + numErrorsFound + "\n");
    }

    @Override
    public void onCheckingEnd() {
        getConsole().appendText("   Done.\n");
    }

    @Override
    public void onProcessEnd(final int numErrorsFound) {
        getConsole().appendText("Checking procedure finished. Errors found: " +
                                numErrorsFound + "\n");
    }

    // private section

    private static GuiConsoleManager getConsole() {
        return GuiMainWindow.getInstance().getConsoleManager();
    }
}
