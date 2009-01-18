package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerFactory;

import java.util.LinkedList;
import java.io.File;

final class CheckerConfiguration {

    // package-private section

    CheckerConfiguration(final String checkerClassName,
                         final File checkerArgument) {
        this.checkerClassName = checkerClassName;
        checkerArgumentsList = new LinkedList<File>();
        checkerArgumentsList.add(checkerArgument);
    }

    CheckerConfiguration(final String checkerClassName,
                         final LinkedList<File> checkerArgumentsList) {
        this.checkerClassName = checkerClassName;
        this.checkerArgumentsList = checkerArgumentsList;
    }

    cz.muni.stanse.checker.Checker getChecker() throws Exception {
        return CheckerFactory.create(getCheckerClassName(),
                                     getCheckerArgumentsList());
    }

    String getCheckerClassName() {
        return checkerClassName;
    }

    LinkedList<File> getCheckerArgumentsList() {
        return checkerArgumentsList;
    }

    // private section

    private final String checkerClassName;
    private final LinkedList<File> checkerArgumentsList;
}
