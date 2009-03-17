package cz.muni.stanse;

import cz.muni.stanse.checker.CheckerFactory;

import java.util.LinkedList;
import java.io.File;

public final class CheckerConfiguration {

    // public section

    public CheckerConfiguration(final String checkerClassName,
                         final File checkerArgument) {
        this.checkerClassName = checkerClassName;
        checkerArgumentsList = new LinkedList<File>();
        checkerArgumentsList.add(checkerArgument);
    }

    public CheckerConfiguration(final String checkerClassName,
                         final LinkedList<File> checkerArgumentsList) {
        this.checkerClassName = checkerClassName;
        this.checkerArgumentsList = checkerArgumentsList;
    }

    public cz.muni.stanse.checker.Checker getChecker() throws Exception {
        return CheckerFactory.create(getCheckerClassName(),
                                     getCheckerArgumentsList());
    }

    public String getCheckerClassName() {
        return checkerClassName;
    }

    public LinkedList<File> getCheckerArgumentsList() {
        return checkerArgumentsList;
    }

    // private section

    private final String checkerClassName;
    private final LinkedList<File> checkerArgumentsList;
}
