package cz.muni.stanse.checker;

import java.io.File;

import java.util.List;

public abstract class CheckerCreator {

    // public section

    public abstract String getCheckerName();
    public abstract String getCheckerCreationInfo();
    public abstract List<String> getDataFilesExtensions();
    public abstract Checker createInterprocedural(final List<File> args)
		throws CheckerException;
    public abstract Checker createIntraprocedural(final List<File> args)
		throws CheckerException;
}
