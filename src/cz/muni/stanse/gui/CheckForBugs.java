package cz.muni.stanse.gui;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerError;

import cz.muni.stanse.utils.Pair;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

final class CheckForBugs {

    // package-private section

    static Pair<LinkedList<CheckerError>,LinkedList<PresentableError> >
    run(final Configuration configuration) throws Exception {
        final LinkedList<CheckerError> checkerErrors =
                new LinkedList<CheckerError>();
        final LinkedList<PresentableError> presentableErrors =
                new LinkedList<PresentableError>();
        // TODO: here should be called method 'visit()' instead of method
        //       'visitIntraprocedutral()'. But I must wait till
        configuration.visitIntraprocedutral(
            new ConfigurationVisitor() {
                @Override
                public boolean visit(final List<Unit> units,
                                     final Checker checker,
                                     final HashMap<CFG,Unit> cfgToUnitMapping)
                                     throws Exception {
                    final List<CheckerError> errors = checker.check(units);
                    checkerErrors.addAll(errors);
                    for (final CheckerError error : errors)
                        presentableErrors.add(
                                  new PresentableError(error,cfgToUnitMapping));
                    return true;
                }
            }
        );
        return new Pair<LinkedList<CheckerError>,LinkedList<PresentableError> >
                                              (checkerErrors,presentableErrors);
    }

    // private section

    private CheckForBugs() {
    }
}
