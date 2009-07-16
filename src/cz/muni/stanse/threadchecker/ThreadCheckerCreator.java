
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerCreator;
import cz.muni.stanse.checker.CheckerException;
import java.io.File;
import java.util.List;
import java.util.LinkedList;

public final class ThreadCheckerCreator extends CheckerCreator {

    @Override
    public String getCheckerName() {
        return "ThreadChecker";
    }

    @Override
    public String getCheckerCreationInfo() {
        return "PThreadChecker is a static checker which detects deadlock"
                +" problems between multiple threads\n"
                +"Checker demands that source code can't contains double "
                +"lock/unlock and corrupted sequence of locking.";
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
        throw new CheckerException("ThreadChecker is interprocedural only.");
    }

    @Override
    public Checker createInterprocedural(final List<File> args)
                                                       throws CheckerException {
        if(args.size()!=1) {
            throw new CheckerException("Wrong number of arguments");
        }
        try {
            CheckerSettings.getInstance().setConfigFile(args.get(0));
        }
        catch(final Exception e) {
            throw new CheckerException("Parsing config file '" +
                                       args.get(0).toString() + "' has FAILED");
        }
        Checker checker = new ThreadChecker();
        return checker;
    }

}
