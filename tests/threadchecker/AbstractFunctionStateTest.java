package cz.muni.stanse.threadchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.locks.Lock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Kučera
 */
public class AbstractFunctionStateTest {

    public AbstractFunctionStateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    @Test
    public void testLockDown() {
        System.out.println("lockDown");
        String lockNameA = "A";
        String lockNameB = "B";
        Lock lockA;
        Lock lockB;
        CFGNode node = new CFGNode();
        AbstractFunctionState instance = new AbstractFunctionState();
        instance.lockUp(lockNameA, node);
        instance.lockUp(lockNameB, node);
        instance.lockDown(lockNameA);
        lockA = instance.getLock(lockNameA);
        lockB = instance.getLock(lockNameB);
        assertEquals("Wrong number of locks in lockSet",
                                            instance.getLockStack().size(),1);
        assertEquals("Wrong lock in lockset",
                                    instance.getLockStack().getLock("B"),lockB);
        assertEquals("Lock B is unlocked", lockB.getState(),1);
        assertEquals("Lock A stayed locked", lockA.getState(),0);

        instance.lockUp(lockNameA, node);
        instance.lockUp(lockNameA, node);
        lockA = instance.getLock(lockNameA);
        assertEquals("After two locking A has wrong state", lockA.getState(),2);
        assertEquals("Data didn't create proper rules",
                                                  instance.getRules().size(),2);
    }

    @Test
    public void testLockUp() {
        System.out.println("lockUp");
        String lockNameA = "A";
        String lockNameB = "B";
        CFGNode node = new CFGNode();
        AbstractFunctionState instance = new AbstractFunctionState();
        instance.lockUp(lockNameA, node);
        Lock lockA = instance.getLock(lockNameA);
        assertEquals("Lock A hasn't state set to 1",
                                    instance.getLock(lockNameA).getState(),1);
        assertEquals("Lock A isn't in lockSet",
                                instance.getLockStack().contains(lockA),true);
        assertEquals("Lock A isn't in joins",
                                instance.getJoins().size(),1);
        instance.lockUp(lockNameB, node);
        Lock lockB = instance.getLock(lockNameB);
        assertEquals("Lock B hasn't state set to 1",
                                    instance.getLock(lockNameB).getState(),1);
        assertEquals("Lock B isn't in lockSet",
                                instance.getLockStack().contains(lockB),true);
        assertEquals("Joins have more locks",
                                instance.getJoins().size(),1);
        assertEquals("Data didn't create rule",instance.getRules().size(),1);
        assertEquals("Wrong rule was created",
                                instance.getRules().get(0).toString(),"A <- B");
    }

}