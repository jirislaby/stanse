package cz.muni.stanse.pointeranalyzer;

import cz.muni.stanse.checker.*;
import cz.muni.stanse.codestructures.*;
import cz.muni.stanse.pointeranalyzer.steensgaard.*;
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

        SteensgaardAnalyzer analyzer = new SteensgaardAnalyzer();
        analyzer.analyze(internals.getCFGHandles());

        return result;
    }

    public static void main(String[] args)
    {
        TypeTable table = new TypeTable();
        EquivalenceClass.addJoinListener(table);

        EquivalenceClass t1;
        EquivalenceClass t2;

        // a = &x
        System.out.println("a = &x");
        t1 = ((LocationPointerType)table.getTypeOf("a").getType()).getTau();
        t2 = table.getTypeOf("x");
        if (t1 != t2)
            t1.joinWith(t2);

        // y = &z
        System.out.println("y = &z");
        t1 = ((LocationPointerType)table.getTypeOf("y").getType()).getTau();
        t2 = table.getTypeOf("z");
        if (t1 != t2)
            t1.joinWith(t2);

        // y = &x
        System.out.println("y = &x");
        t1 = ((LocationPointerType)table.getTypeOf("y").getType()).getTau();
        t2 = table.getTypeOf("x");
        if (t1 != t2)
            t1.joinWith(t2);

        System.out.printf("Z: %s X: %s\n", table.getTypeOf("y").toString(),
                table.getTypeOf("x").toString());
    }

}
