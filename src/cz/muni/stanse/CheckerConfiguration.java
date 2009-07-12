package cz.muni.stanse;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerFactory;

import java.io.File;

import java.util.LinkedList;
import java.util.List;

import cz.muni.stanse.checker.CheckerException;

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
                                final List<File> checkerArgumentsList,
                                final boolean interprocedural) {
        this.checkerClassName = checkerClassName;
        this.checkerArgumentsList = checkerArgumentsList;
        this.interprocedural = interprocedural;
    }

    public Checker getChecker() throws CheckerException {
        return (interprocedural) ?
                    CheckerFactory.createInterprocedural(getCheckerClassName(),
                                                    getCheckerArgumentsList()) :
                    CheckerFactory.createIntraprocedural(getCheckerClassName(),
                                                    getCheckerArgumentsList());
    }

    public String getCheckerClassName() {
        return checkerClassName;
    }

    public List<File> getCheckerArgumentsList() {
        return checkerArgumentsList;
    }

    public boolean isInterprocedural() {
        return interprocedural;
    }

    // private section

    private final String checkerClassName;
    private final List<File> checkerArgumentsList;
    private final boolean interprocedural;
}
