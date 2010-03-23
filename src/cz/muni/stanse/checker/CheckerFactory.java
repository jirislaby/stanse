package cz.muni.stanse.checker;

import java.io.File;

import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class CheckerFactory {

    // public section

    public static Checker createInterprocedural(final String checkerName,
		final List<File> args)
		throws UnsupportedOperationException, CheckerException {
        return getCheckerCreator(checkerName).createInterprocedural(args);
    }

    public static Checker createIntraprocedural(final String checkerName,
		final List<File> args)
		throws UnsupportedOperationException, CheckerException {
        return getCheckerCreator(checkerName).createIntraprocedural(args);
    }

    public static String getCheckerCreationInfo(final String checkerName)
		throws UnsupportedOperationException {
        return getCheckerCreator(checkerName).getCheckerCreationInfo();
    }

    public static List<String> getDataFilesExtensions(
		final String checkerName) throws UnsupportedOperationException {
        return getCheckerCreator(checkerName).getDataFilesExtensions();
    }

    public static boolean checkArgumentList(final String checkerName,
                  final List<File> args) throws UnsupportedOperationException {
        return getCheckerCreator(checkerName).checkArgumentList(args);
    }

    public static Set<String> getRegisteredCheckers() {
        return Collections.unmodifiableSet(creatorsDictionary.keySet());
    }

    // private section

    private CheckerFactory() {
    }

    private static CheckerCreator getCheckerCreator(final String checkerName)
		throws UnsupportedOperationException {
        final CheckerCreator creator = creatorsDictionary.get(checkerName);
        if (creator == null)
            throw new UnsupportedOperationException("Checker '" + checkerName +
			"' is not registered to the factory.");
        return creator;
    }

    private static void registerCheckerCreator(final CheckerCreator creator) {
        creatorsDictionary.put(creator.getCheckerName(),creator);
    }

    private static final HashMap<String,CheckerCreator> creatorsDictionary =
                                           new HashMap<String,CheckerCreator>();
    static {
        registerCheckerCreator(
            new cz.muni.stanse.automatonchecker.AutomatonCheckerCreator());
        registerCheckerCreator(
            new cz.muni.stanse.threadchecker.ThreadCheckerCreator());
        registerCheckerCreator(new cz.muni.stanse.reachabilitychecker.
		ReachabilityCheckerCreator());
        registerCheckerCreator(
                new cz.muni.stanse.pointeranalyzer.PointerAnalyzerCreator());
    }
}
