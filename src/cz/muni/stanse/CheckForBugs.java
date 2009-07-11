package cz.muni.stanse;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerError;

import cz.muni.stanse.utils.Pair;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public final class CheckForBugs {

    // package-private section

    public static Pair<LinkedList<CheckerError>,LinkedList<PresentableError> >
    run(final Configuration configuration) throws Exception {
        return run(configuration,new ConfigurationProgressHandler());
    }

    public static Pair<LinkedList<CheckerError>,LinkedList<PresentableError> >
    run(final Configuration configuration,
        final ConfigurationProgressHandler progressHandler) throws Exception {
        final LinkedList<CheckerError> checkerErrors =
                new LinkedList<CheckerError>();
        final LinkedList<PresentableError> presentableErrors =
                new LinkedList<PresentableError>();
        assert(configuration != null);
        assert(progressHandler != null);
        progressHandler.onProcessBegin();
        // TODO: here should be called method 'visit()' instead of method
        //       'visitIntraprocedutral()'. But I must wait till Unit streaming
        //       is available.
        //configuration.visit(
        configuration.visitIntraprocedutral(
            new ConfigurationVisitor() {
                @Override
                public boolean visit(final List<Unit> units,
                                     final Checker checker,
                                     final Map<CFG,Unit> cfgToUnitMapping)
                                     throws Exception {
                    progressHandler.onCheckerBegin(checker.getName());
                    final List<CheckerError> errors = checker.check(units);
                    progressHandler.onCheckerEnd(errors.size());
                    checkerErrors.addAll(errors);
                    for (final CheckerError error : errors)
                        presentableErrors.add(
                                  new PresentableError(error,cfgToUnitMapping));
                    return true;
                }
            },
            progressHandler
        );
        progressHandler.onProcessEnd(checkerErrors.size());
        return new Pair<LinkedList<CheckerError>,LinkedList<PresentableError> >
                                              (checkerErrors,presentableErrors);
    }

    // private section

    private CheckForBugs() {
    }
}
