package cz.muni.stanse.threadchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.locks.Lock;
import cz.muni.stanse.threadchecker.locks.SpinLock;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Kučera
 */
public class CodeAnalyzerTest {
    private Element parameterA;
    private Element parameterB;
    private Element parameterC;
    private Element parameterD;
    private Lock lockA;

    public CodeAnalyzerTest() {
    }

    @Before
    public void setUp() {
        parameterA = new DefaultElement("id");
        parameterA.setText("A");
        parameterB = new DefaultElement("id");
        parameterB.setText("B");
        parameterC = new DefaultElement("id");
        parameterC.setText("C");
        parameterD = new DefaultElement("id");
        parameterD.setText("D");
        lockA = new SpinLock("A");
    }

    /**
     * Test of analyzeLockingFunction method, of class CodeAnalyzer.
     */
    @Test
    public void testAnalyzeLockingFunction() {
        System.out.println("analyzeLockingFunction");
        CFGNode node = new CFGNode();
        Function state = new Function("function");
        FunctionState data = state.getFunctionStates().get(0);
        
        CodeAnalyzer.analyzeLockingFunction(node, state, parameterA);
        System.out.println("Function state:"+state);
        CodeAnalyzer.analyzeLockingFunction(node, state, parameterA);
        System.out.println("Function state:"+state);
        assertEquals("Function after recursive locking showing wrong"
                        +" locked lock",data.getLockStack().getLock("A"),lockA);
        assertEquals("Function after recursive locking showing wrong"
                        +" join node",
                data.getJoins().iterator().next().getVertex().getName(),"A");
        CodeAnalyzer.analyzeLockingFunction(node, state, parameterB);
        assertEquals("Method didn't create dependencyRule",
                                   data.getRules().get(0).toString(), "A <- B");
        CodeAnalyzer.analyzeLockingFunction(node, state, parameterA);
        assertEquals("Method create dependencyRule with recursivelock",
                                   data.getRules().size(), 1);
    }

    /**
     * Test of analyzeUnlockingFunction method, of class CodeAnalyzer.
     */
    @Test
    public void testAnalyzeUnlockingFunction() {
        System.out.println("analyzeUnlockingFunction");
        CFGNode node = new CFGNode();
        Function state = new Function("function");
        FunctionState data = state.getFunctionStates().get(0);

        //Simulate locking
        lockA = data.getLock("A");
        data.lockUp("A",node);

        CodeAnalyzer.analyzeUnlockingFunction(node, state, parameterA);
        assertEquals("Lock A isn't unlocked", lockA.getState(), 0);
        assertEquals("Lock isn't removed from LockSet",
                                                  data.getLockStack().size(),0);
        CodeAnalyzer.analyzeUnlockingFunction(node, state, parameterA);
        lockA = data.getLock("A");
        assertEquals("Lock A isn't unlocked for second time",
                                                        lockA.getState(), -1);
        assertEquals("Unlocked lock isn't at unlockSet",
                                                data.getUnlockSet().size(),1);
    }
}