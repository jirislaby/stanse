
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerCreator;
import java.io.File;
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
    public Checker create(LinkedList<File> args) throws Exception {
        if(args.size()!=1) {
            throw new Exception("Wrong number of arguments");
        }
        CheckerSettings.getInstance().setConfigFile(args.getFirst());
        Checker checker = new ThreadChecker();
        return checker;
    }

}
