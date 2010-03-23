/**
 * @brief PointsToAnalyzer factory
 *
 * Copyright (c) 2010 Michal Strehovsky
 *
 */
package cz.muni.stanse.pointeranalyzer;


/*
 *  Symbol defintion: Pair<CFGHandle, string>
 * CFGHandle==null: global variable
 * string==null: function
 *
 * AbstractLocation
 * BackingSymbol
 * - dynamically allocated
 * - variable {global, local}
 * - function
 * PointsToSet (najviac 1 prvok pre Steensgaarda, null = T)
 *
 * PointerAbstraction
 * GetAbstractLocationOf(Symbol)
 *
 *
 */

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerCreator;
import cz.muni.stanse.checker.CheckerException;

import java.io.File;

import java.util.List;

public final class PointerAnalyzerCreator extends CheckerCreator {

    @Override
    public String getCheckerName() {
        return getNameForCheckerFactory();
    }

    @Override
    public String getCheckerCreationInfo() {
        return "Provides points-to analysis for checkers.";
    }

    @Override
    public Checker createIntraprocedural(final List<File> args)
	    throws CheckerException {
        if (!checkArgumentList(args))
            throw new CheckerException("Bad number of data arguments. " +
			"PointsToAnalyzer accepts no definition files.");
        return new PointerAnalyzer();
    }

    @Override
    public Checker createInterprocedural(List<File> args)
	    throws CheckerException {
	return createIntraprocedural(args);
    }

    @Override
    public List<String> getDataFilesExtensions() {
	throw new UnsupportedOperationException("No data needed");
    }

    @Override
    public boolean checkArgumentList(final List<File> args) {
        return args.size() == 0;
    }

    // package-private section

    static String getNameForCheckerFactory() {
        return "PointerAnalyzer";
    }
}
