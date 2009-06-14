package cz.muni.stanse;

import cz.muni.stanse.checker.CheckerFactory;

import java.util.LinkedList;
import java.io.File;

public final class CheckerConfiguration {

    // public section

    public CheckerConfiguration(final String checkerClassName,
                                final File checkerArgument,
                                final boolean interprocedural) {
        this.checkerClassName = checkerClassName;
        checkerArgumentsList = new LinkedList<File>();
        checkerArgumentsList.add(checkerArgument);
        this.interprocedural = interprocedural;
    }

    public CheckerConfiguration(final String checkerClassName,
                                final LinkedList<File> checkerArgumentsList,
                                final boolean interprocedural) {
        this.checkerClassName = checkerClassName;
        this.checkerArgumentsList = checkerArgumentsList;
        this.interprocedural = interprocedural;
    }

    public cz.muni.stanse.checker.Checker getChecker() throws Exception {
        return (interprocedural) ?
                    CheckerFactory.createInterprocedural(getCheckerClassName(),
                                                    getCheckerArgumentsList()) :
                    CheckerFactory.createIntraprocedural(getCheckerClassName(),
                                                    getCheckerArgumentsList());
    }

    public String getCheckerClassName() {
        return checkerClassName;
    }

    public LinkedList<File> getCheckerArgumentsList() {
        return checkerArgumentsList;
    }

    public boolean isInterprocedural() {
        return interprocedural;
    }

    // private section

    private final String checkerClassName;
    private final LinkedList<File> checkerArgumentsList;
    private final boolean interprocedural;
}
