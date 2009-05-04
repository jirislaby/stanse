package cz.muni.stanse;

import cz.muni.stanse.ConfigurationProgressHandler;

final class ConsoleProgressHandler extends ConfigurationProgressHandler {

    // package-private section

    @Override
    public void onProcessBegin() {
        System.out.println("Checking procedure started.\n");
    }

    @Override
    public void onParsingBegin() {
        System.out.println("   Parsing source file(s)...\n");
    }

    @Override
    public void onFileBegin(final String filePathName) {
        System.out.println("      " + filePathName + "...");
    }

    @Override
    public void onFileEnd() {
        System.out.println("Done.\n");
    }

    @Override
    public void onParsingEnd() {
        System.out.println("   Done.\n");
    }

    @Override
    public void onCheckingBegin() {
        System.out.println("   Checking parsed file(s)....\n");
    }

    @Override
    public void onCheckerBegin(final String checkerName) {
        System.out.println("      " + checkerName + " in progress...");
    }

    @Override
    public void onCheckerEnd(final int numErrorsFound) {
        System.out.println("Done. Errors found: " + numErrorsFound + "\n");
    }

    @Override
    public void onCheckingEnd() {
        System.out.println("   Done.\n");
    }

    @Override
    public void onProcessEnd(final int numErrorsFound) {
        System.out.println("Checking procedure finished. Errors found: " +
                                numErrorsFound + "\n");
    }

}
