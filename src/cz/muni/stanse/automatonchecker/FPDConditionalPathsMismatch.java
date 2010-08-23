package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;

import java.util.Map;
import java.util.List;
import org.dom4j.Element;


/*
    void xyz(void)
    {
     if (X)
      __st_spin_lock_st__(XX);
     xyz();
     if (X)
      __st_spin_unlock_st__(XX);
    }
 */
final class FPDConditionalPathsMismatch extends FalsePositivesDetector {
    @Override
    int getTraceImportance(final java.util.List<CFGNode> path,
                            final java.util.Stack<CFGNode> cfgContext,
                            final ErrorRule rule) {
        final Element assertElem = getStartCondAssert(path.get(0));
        if (assertElem == null)
            return getBugImportance(0);
        if (existsNegatedElementInPath(path,assertElem))
            return getFalsePositiveImportance();
        if (rule.getErrorDescription()
                .equals("leaving function in unlocked state"))
            return getFalsePositiveImportance();
        return getBugImportance(0);
    }

    private static Element getStartCondAssert(final CFGNode node) {
        if (node.getPredecessors().size() != 1)
            return null;
        final Element assertElem = node.getPredecessors().get(0).getElement();
        if (assertElem == null || !assertElem.getName().equals("assert"))
            return null;
        return (Element)assertElem.elements().get(0);
    }

    private static boolean
    existsNegatedElementInPath(final List<CFGNode> path,
                               final Element assertElem) {
        for (final CFGNode node : path)
            if (isNegatedPathElement(node.getElement(),assertElem))
                return true;
        return false;
    }

    private static boolean
    isNegatedPathElement(final Element pathElem, final Element assertElem) {
        if (!pathElem.getName().equals("assert"))
            return false;
        final Element pathBodyElem = (Element)pathElem.elements().get(0);
        if (compareNegationOfFirstAndSecond(pathBodyElem, assertElem))
            return true;
        if (compareNegationOfFirstAndSecond(assertElem,pathBodyElem))
            return true;
        return false;
    }

    private static boolean
    compareNegationOfFirstAndSecond(final Element elem1, final Element elem2) {
        if (!elem1.getName().equals("prefixExpression") ||
            !elem1.attributeValue("op").equals("!"))
            return false;
        return XMLAlgo.equalElements((Element)elem1.elements().get(0),elem2);
    }
}

final class FPDConditionalPathsMismatchCreator
        extends FalsePositivesDetectorCreator {

    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return true;
    }

    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
        LazyInternalStructures internals,boolean isInterprocediral,
        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
        return new FPDConditionalPathsMismatch();
    }
}
