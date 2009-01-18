package cz.muni.stanse.utils;

import org.apache.log4j.Logger;

public final class ClassLogger {

    // public section

    public static <TClass,TObject> void error(final TClass forWhichClass,
                                              final TObject object) {
        Logger.getLogger(forWhichClass.getClass()).error(object);
    }

    public static <TClass,TObject> void info(final TClass forWhichClass,
                                             final TObject object) {
        Logger.getLogger(forWhichClass.getClass()).info(object);
    }

    // private section

    private ClassLogger() {
    }
}
