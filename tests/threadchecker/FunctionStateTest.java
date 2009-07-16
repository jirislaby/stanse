
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.graph.DependencyRule;
import cz.muni.stanse.threadchecker.graph.ResourceVertex;
import cz.muni.stanse.threadchecker.locks.LockStack;
import cz.muni.stanse.threadchecker.locks.SpinLock;
import cz.muni.stanse.threadchecker.locks.UnlockSet;
import java.util.List;
import java.util.Vector;
import static org.junit.Assert.*;

/**
 *
 * @author Jan Kučera
 */
public class FunctionStateTest {

    private final ResourceVertex vertexA = new ResourceVertex("A", null);
    private final ResourceVertex vertexB = new ResourceVertex("B", null);
    private final ResourceVertex vertexC = new ResourceVertex("C", null);
    private final ResourceVertex vertexD = new ResourceVertex("D", null);
    private final SpinLock spinLockA = new SpinLock("A");
    private final SpinLock spinLockB = new SpinLock("B");
    private final SpinLock spinLockC = new SpinLock("C");
    private final SpinLock spinLockD = new SpinLock("D");
    public FunctionStateTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    /**
     * Test of clone method, of class FunctionState.
     */
    @org.junit.Test
    public void testClone() {
        System.out.println("Testing proper cloninig");
        FunctionState instance = new FunctionState();
        CFGNode node = new CFGNode();
        FunctionState expResult = null;
        FunctionState result;
        LockStack alreadyLocked = new LockStack();

        alreadyLocked.add(spinLockB);

        instance.lockUp("B", node);
        instance.lockUp("C", node);


        result = instance.clone();
        instance.getJoins().clear();
        assertFalse("Clone has same joins", 
                                result.getJoins().equals(instance.getJoins()));
        instance.getRules().clear();
        assertFalse("Clone has same rules", result.getRules().equals(
                                                instance.getRules()));
        instance.getLockStack().add(spinLockD);
        assertFalse("Clone has same lockSet", result.getLockStack().equals(
                                                instance.getLockStack()));

    }

    /**
     * Test of isSubset method, of class FunctionState.
     */
    @org.junit.Test
    public void testIsSubset() {
        System.out.println("Testing isSubset ");
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack alreadyLocked = new LockStack();
        boolean expResult = true;
        boolean result;

        alreadyLocked.add(spinLockA);
        
        instance.addJoin(vertexA, new LockStack(),new UnlockSet());
        others.addJoin(vertexA, new LockStack(),new UnlockSet());

        instance.getLockStack().add(spinLockA);
        others.getLockStack().add(spinLockA);

        instance.addRule(new DependencyRule(vertexC, vertexB, alreadyLocked, new UnlockSet()));
        others.addRule(new DependencyRule(vertexC, vertexB, alreadyLocked, new UnlockSet()));

        result = instance.isSubset(others);
        assertEquals("Object are same!", expResult, result);
        instance.getJoins().clear();
        result = instance.isSubset(others);
        assertEquals("Instance doesn't have same joins", false, result);
        result = others.isSubset(instance);
        assertEquals("Others is subset", true, result);

    }

    /**
     * Test of stitchFunctions method, of class FunctionState.
     */
    @org.junit.Test
    public void testStitchData() {
        System.out.println("Testing normal stitching");
        System.out.println("Test for recursive locks");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(2);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                                   expectedLockStack.clone(), new UnlockSet()));
        spinLockB.setState(1);
        expectedLockStack.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexC, vertexB,
                                   expectedLockStack.clone(), new UnlockSet()));

        expectedLockStack.remove(spinLockB);
        
        //J:A
        instance.lockUp("A", node);

        //B <- C, J:B
        others.lockUp("B", node);
        others.lockUp("C", node);
        others.lockDown("C");
        others.lockDown("B");
        
        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());

    }

    @org.junit.Test
    public void testRecursiveLockInRule() {
        System.out.println("Test for recursive locks");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                                    expectedLockStack.clone(),new UnlockSet()));
        spinLockB.setState(1);
        expectedLockStack.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexC, vertexB,
                                    expectedLockStack.clone(),new UnlockSet()));
        spinLockC.setState(1);
        spinLockA.setState(2);
        expectedLockStack.add(spinLockC);
        expectedRules.add(new DependencyRule(vertexD, vertexC,
                                    expectedLockStack.clone(),new UnlockSet()));
        spinLockD.setState(1);
        expectedLockStack.add(spinLockD);
        instance.lockUp("A", node);
        instance.lockUp("B", node);

        others.lockUp("C", node);
        others.lockUp("A", node);
        others.lockUp("D", node);

        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Wrong number of arguments",instance.getRules().size(),3);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
    }

        @org.junit.Test
    public void testRecursiveLockInAloneJoin() {
        System.out.println("Test for recursive lock in alone join");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);
    }

    @org.junit.Test
    public void testRecursiveLockInJoin() {
        System.out.println("Test for recursive lock in joins");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        
        instance.lockUp("A", node);

        others.lockUp("A", node);
        others.lockDown("A");

        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
    }

    @org.junit.Test
    public void testUnlockingStitch() {
        System.out.println("Test for recursive lock in target");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                                    expectedLockStack.clone(),new UnlockSet()));
        spinLockB.setState(1);
        spinLockA.setState(2);
        expectedLockStack.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexC, vertexB,
                                    expectedLockStack.clone(),new UnlockSet()));
        spinLockC.setState(1);
        expectedLockStack.add(spinLockC);

        instance.lockUp("A", node);
        instance.lockUp("B", node);

        others.lockUp("C", node);
        others.lockUp("A", node);

        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
    }

    @org.junit.Test
    public void testRecursiveUnlocking() {
        System.out.println("Test for recursive lock in target");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                                    expectedLockStack.clone(),new UnlockSet()));

        expectedRules.add(new DependencyRule(vertexC, vertexA,
                                    expectedLockStack.clone(),new UnlockSet()));
        spinLockC.setState(1);
        expectedLockStack.add(spinLockC);

        instance.lockUp("A", node);
        instance.lockUp("B", node);

        others.lockDown("B");
        others.lockUp("C", node);
        
        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
    }

     @org.junit.Test
    public void testRecursiveUnlockingWithNoLock() {
        System.out.println("Test for recursive lock in target");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));

        expectedLockStack.clear();
        spinLockC.setState(1);
        expectedLockStack.add(spinLockC);
        expectedRules.add(new DependencyRule(vertexD, vertexC,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        spinLockD.setState(1);
        expectedLockStack.add(spinLockD);

        instance.lockUp("A", node);
        instance.lockUp("B", node);

        others.lockDown("A");
        others.lockDown("B");
        others.lockUp("C", node);
        others.lockUp("D", node);

        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
    }

      @org.junit.Test
    public void testRecursiveJoinUnlock() {
        System.out.println("Test recursive join");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockB.setState(1);
        expectedLockStack.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexA, vertexB,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));

        expectedLockStack.clear();
        spinLockB.setState(1);
        expectedLockStack.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexC, vertexB,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        expectedLockStack.clear();
        spinLockB.setState(1);
        spinLockA.setState(2);
        expectedLockStack.add(spinLockB);
        expectedLockStack.add(spinLockA);

        instance.lockUp("B", node);
        instance.lockUp("A", node);
        instance.lockUp("A", node);

        others.lockDown("A");
        others.lockDown("A");
        others.lockUp("C", node);
        others.lockUp("A", node);
        others.lockUp("A", node);
        others.lockDown("C");
        others.lockUp("A", node);
        instance.stitchFunctions(others);
        System.out.println("Expected rules"+expectedRules);
        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Stitching generate wrong rules",expectedRules,
                                                        instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
    }


      @org.junit.Test
    public void testAllRecursiveSameFunction() {
        System.out.println("Test recursive same function");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        instance.lockUp("A", node);
        instance.lockUp("B", node);
        instance.lockDown("C");

        others.lockUp("A", node);
        others.lockUp("B", node);
        others.lockDown("C");
        spinLockA.setState(2);
        spinLockB.setState(2);
        expectedLockStack.add(spinLockA);
        expectedLockStack.add(spinLockB);
        spinLockC.setState(-2);
        expectedUnlockSet.add(spinLockC);
        instance.stitchFunctions(others);

        assertEquals("Wrong number of arguments",instance.getRules().size(),1);
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }

     @org.junit.Test
    public void test01() {
        System.out.println("Test recursive join");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        instance.lockDown("B");
        instance.lockUp("A", node);


        others.lockUp("B", node);
        instance.stitchFunctions(others);
        
        assertEquals("Wrong number of arguments",instance.getRules().size(),1);
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }

  @org.junit.Test
    public void test02() {
        System.out.println("Test 02");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(3);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        spinLockB.setState(-1);
        expectedUnlockSet.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexC, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        
        instance.lockDown("B");
        instance.lockUp("A", node);
        instance.lockUp("C", node);
        spinLockA.setState(2);
        expectedUnlockSet.clear();
        expectedRules.add(new DependencyRule(vertexC, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        others.lockDown("C");
        others.lockUp("A", node);
        others.lockUp("B", node);
        instance.stitchFunctions(others);

        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }

   @org.junit.Test
    public void test03() {
        System.out.println("Test 03");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(1);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        spinLockB.setState(-1);
        expectedUnlockSet.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        expectedUnlockSet.clear();
        instance.lockDown("B");
        instance.lockUp("A", node);

        others.lockUp("B", node);
        instance.stitchFunctions(others);

        assertEquals("Wrong number of arguments",instance.getRules().size(),1);
        assertEquals("Wrong rule",
                                    expectedRules, instance.getRules());
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }

      @org.junit.Test
    public void test04() {
        System.out.println("Test 04");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(1);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        expectedRules.add(new DependencyRule(vertexC, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        
        spinLockA.setState(2);
        spinLockB.setState(-1);
        expectedUnlockSet.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        expectedUnlockSet.clear();
        
        instance.lockUp("A", node);
        instance.lockUp("C", node);
        instance.lockDown("B");
        
        others.lockUp("A", node);
        others.lockDown("C");
        others.lockUp("B", node);
        instance.stitchFunctions(others);

        assertEquals("Wrong number of arguments",instance.getRules().size(),2);
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }

    @org.junit.Test
    public void test05() {
        System.out.println("Test 05");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(1);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);
        spinLockB.setState(-1);
        expectedUnlockSet.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        spinLockB.setState(1);
        expectedLockStack.add(spinLockB.clone());
        spinLockB.setState(-1);
        expectedRules.add(new DependencyRule(vertexC, vertexB,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        spinLockC.setState(1);
        expectedLockStack.add(spinLockC);
        expectedRules.add(new DependencyRule(vertexD, vertexC,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));

        expectedUnlockSet.add(spinLockB);
        expectedRules.add(new DependencyRule(vertexB, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        spinLockD.setState(1);
        expectedLockStack.add(spinLockD);
        expectedLockStack.remove(spinLockB);
        expectedUnlockSet.clear();

        instance.lockUp("A", node);
        instance.lockDown("B");
        

        others.lockUp("B", node);
        others.lockUp("C", node);
        others.lockUp("D", node);
        
        instance.stitchFunctions(others);

        assertEquals("Wrong number of arguments",instance.getRules().size(),3);
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }

        @org.junit.Test
    public void test06() {
        System.out.println("Test 06");
        CFGNode node = new CFGNode();
        FunctionState others = new FunctionState();
        FunctionState instance = new FunctionState();
        LockStack expectedLockStack = new LockStack();
        UnlockSet expectedUnlockSet = new UnlockSet();
        List<DependencyRule> expectedRules = new Vector<DependencyRule>(1);

        spinLockA.setState(1);
        expectedLockStack.add(spinLockA);

        expectedRules.add(new DependencyRule(vertexB, vertexA,
                        expectedLockStack.clone(),expectedUnlockSet.clone()));
        spinLockB.setState(1);
        expectedLockStack.add(spinLockB);

        instance.lockUp("A", node);


        others.lockUp("B", node);

        instance.stitchFunctions(others);

        assertEquals("Wrong number of arguments",instance.getRules().size(),1);
        assertEquals("Caller has wrong lockStack",
                                    expectedLockStack, instance.getLockStack());
        assertEquals("Caller has wrong unlockSet",
                                    expectedUnlockSet, instance.getUnlockSet());
     }
}