package cz.muni.stanse.gui;

final class GuiCheckingProgressHandler extends ConfigurationProgressHandler {

    // package-private section

    @Override
    void onProcessBegin() {
        getConsole().clear();
        getConsole().appendText("Checking procedure started.\n");
    }

    @Override
    void onParsingBegin() {
        getConsole().appendText("   Parsing source file(s)...\n");
    }

    @Override
    void onFileBegin(final String filePathName) {
        getConsole().appendText("      " + filePathName + "...");
    }

    @Override
    void onFileEnd() {
        getConsole().appendText("Done.\n");
    }

    @Override
    void onParsingEnd() {
        getConsole().appendText("   Done.\n");
    }

    @Override
    void onCheckingBegin() {
        getConsole().appendText("   Checking parsed file(s)....\n");
    }

    @Override
    void onCheckerBegin(final String checkerName) {
        getConsole().appendText("      " + checkerName + " in progress...");
    }

    @Override
    void onCheckerEnd(final int numErrorsFound) {
        getConsole().appendText("Done. Erros found: " + numErrorsFound + "\n");
    }

    @Override
    void onCheckingEnd() {
        getConsole().appendText("   Done.\n");
    }

    @Override
    void onProcessEnd(final int numErrorsFound) {
        getConsole().appendText("Checking procedure finished. Errors found: " +
                                numErrorsFound + "\n");
    }

    // private section

    private static GuiConsoleManager getConsole() {
        return GuiMainWindow.getInstance().getConsoleManager();
    }
}
