package cz.muni.stanse.pointeranalyzer;

import cz.muni.stanse.checker.*;
import cz.muni.stanse.codestructures.*;
import cz.muni.stanse.pointeranalyzer.shapirohorwitz.*;
import org.dom4j.*;
import java.util.Collection;

/**
 *
 * @author Michal Strehovsky
 */
public class PointerAnalyzer extends Checker {
    /**
     * @brief Uniquely identifies the checker by the string identifier.
     *
     * @return String which uniquely identifies the checker.
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return PointerAnalyzerCreator.getNameForCheckerFactory();
    }

    /**
     * @brief Does the source code checking itself.
     *
     * Method searches through source code to find nodes (except start node)
     * with no predecessors.
     *
     * @return List of errors found in the source code.
     * @see cz.muni.stanse.checker.Checker#check(java.util.List)
     */
    @Override
    public CheckingResult check(
            final LazyInternalStructures internals,
            final CheckerErrorReceiver errReceiver,
            final CheckerProgressMonitor monitor)
    {
	CheckingResult result = new CheckingSuccess();

        monitor.write("Entering PointerAnalyzer");

        ShapiroHorwitzAnalyzer analyzer = new ShapiroHorwitzAnalyzer(
                //new AndersenCategorizationProvider(VariableCounter.countVariables(internals.getCFGHandles())));
                new SteensgaardCategorizationProvider());
        
        analyzer.analyze(internals.getCFGHandles());

        return result;
    }
}
