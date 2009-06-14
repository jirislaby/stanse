package cz.muni.stanse.checker;

import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;

public final class CheckerFactory {

    // public section

    public static Checker createInterprocedural(final String checkerName,
                          final LinkedList<File> args) throws Exception {
        return getCheckerCreator(checkerName).createInterprocedural(args);
    }

    public static Checker createIntraprocedural(final String checkerName,
                          final LinkedList<File> args) throws Exception {
        return getCheckerCreator(checkerName).createIntraprocedural(args);
    }

    public static String getCheckerCreationInfo(final String checkerName)
                                                              throws Exception {
        return getCheckerCreator(checkerName).getCheckerCreationInfo();
    }

    public static LinkedList<String> getDataFilesExtensions(
                                    final String checkerName) throws Exception {
        return getCheckerCreator(checkerName).getDataFilesExtensions();
    }

    public static Set<String> getRegisteredCheckers() {
        return Collections.unmodifiableSet(creatorsDictionary.keySet());
    }

    // private section

    private CheckerFactory() {
    }

    private static CheckerCreator getCheckerCreator(final String checkerName)
                                                              throws Exception {
        final CheckerCreator creator = creatorsDictionary.get(checkerName);
        if (creator == null)
            throw new Exception("Checker '" + checkerName + "' is not " +
                                "registered to the factory.");
        return creator;
    }

    private static void registerCheckerCreator(final CheckerCreator creator) {
        creatorsDictionary.put(creator.getCheckerName(),creator);
    }

    private static final HashMap<String,CheckerCreator> creatorsDictionary =
                                           new HashMap<String,CheckerCreator>();
    static {
        registerCheckerCreator(
            new cz.muni.stanse.automatonchecker.AutomatonCheckerCreator() );
    }
}
