/**
 * @brief ReachabilityChecker factory
 *
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.reachabilitychecker;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerCreator;
import cz.muni.stanse.checker.CheckerException;

import java.io.File;

import java.util.List;

public final class ReachabilityCheckerCreator extends CheckerCreator {

    // public section

    @Override
    public String getCheckerName() {
        return getNameForCheckerFactory();
    }

    @Override
    public String getCheckerCreationInfo() {
        return "   ReachabilityChecker is a static checker which is able to " +
               "detect unreachable code.";
    }

    @Override
    public Checker createIntraprocedural(final List<File> args)
	    throws CheckerException {
        if (!checkArgumentList(args))
            throw new CheckerException("Bad number of data arguments. " +
			"ReachabilityChecker accepts no definition files.");
        return new ReachabilityChecker();
    }

    @Override
    public Checker createInterprocedural(List<File> args)
	    throws CheckerException {
	return createIntraprocedural(args);
    }

    @Override
    public List<String> getDataFilesExtensions() {
	throw new UnsupportedOperationException("Reachability Checker needs " +
		"no data");
    }

    @Override
    public boolean checkArgumentList(final List<File> args) {
        return args.size() == 0;
    }

    // package-private section

    static String getNameForCheckerFactory() {
        return "ReachabilityChecker";
    }
}
