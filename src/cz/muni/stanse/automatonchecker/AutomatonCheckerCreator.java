package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerCreator;
import cz.muni.stanse.checker.CheckerException;

import java.io.File;

import java.util.LinkedList;
import java.util.List;

public final class AutomatonCheckerCreator extends CheckerCreator {

    // public section

    @Override
    public String getCheckerName() {
        return "AutomatonChecker";//AutomatonChecker.class.getName();
    }

    @Override
    public String getCheckerCreationInfo() {
        return "   AutomatoChecker is a static checker which is able to " +
               "detect locking problems, interrupts enabling/disabling " +
               "problems, unnecessary check optimizations and points-to " +
               "problems like null pointer dereference and memory leaks. \n" +
               "   The checker is based on execution of finite automata " +
               "specified in XML file whose states are propagated through " +
               "program locations. Some automata states are considered as " +
               "erroneous, so when automaton's transition to such erroneous " +
               "state is invoked in some program location, then appropriate " +
               "error message is reported. Program locations, which are " +
               "considered for automata state propagation are found by " +
               "pattern matching. These pattern are specified in the XML " +
               "file defining automata as well. \n" +
               "   For more info see Stanse documentation. You can also " +
               "have a look to AutomatonChecker XML data files, which are " +
               "distributed with Stanse as well.";
    }

    @Override
    public LinkedList<String> getDataFilesExtensions() {
        final LinkedList<String> result = new LinkedList<String>();
        result.add("xml");
        return result;
    }

    @Override
    public Checker createIntraprocedural(final List<File> args)
		throws CheckerException {
        checkArgumentList(args);
        return new AutomatonChecker(args,false);
    }

    @Override
    public Checker createInterprocedural(final List<File> args)
		throws CheckerException {
	checkArgumentList(args);
	return new AutomatonChecker(args,true);
    }

    // private section

    private static void checkArgumentList(final List<File> args)
		throws CheckerException {
        if (args.size() < 1)
            throw new CheckerException("Bad number of data arguments. " +
			"Accepts one or more XML definition files.");
    }
}
