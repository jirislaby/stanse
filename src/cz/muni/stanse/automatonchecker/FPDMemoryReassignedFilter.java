package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;

import java.util.Iterator;
import java.util.Map;

import org.dom4j.Element;

/**
 * Checks whether already freed pointer was not assigned again before
 * dereference by a chance.
 *
 * It is usually
 * a = alloc(); if (!a) { a = b; a->x; }
 * a = alloc(); free(a); a = b; a->x;
 *
 * @author xslaby
 */
final class FPDMemoryReassignedFilter extends FalsePositivesDetector {
    @Override
    boolean isFalsePositive(final java.util.List<CFGNode> path,
                            final java.util.Stack<CFGNode> cfgContext,
                            final ErrorRule rule) {
        String desc = rule.getErrorDescription();
        if (!desc.equals("dereferencing NULL pointer") &&
                !desc.equals("dereferencing dangling pointer"))
            return false;
        Iterator<CFGNode> nodeI = path.listIterator();
        Element e = nodeI.next().getElement();
        boolean call = e.getName().equals("functionCall");
        if (!e.getName().equals("assignExpression") && !call)
            return false;
        if (call && e.nodeCount() != 2) /* name and id */
            return false;
        Element left = (Element)e.elements().get(call ? 1 : 0); // leftside
        while (nodeI.hasNext())
            for (Object fno: nodeI.next().getElement().
                    selectNodes("..//assignExpression")) {
                Element left1 = (Element)((Element)fno).elements().get(0);
                if (XMLAlgo.equalElements(left, left1))
                    return true;
            }
        return false;
    }
}
final class FPDMemoryReassignedFilterCreator
        extends FalsePositivesDetectorCreator {

    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return definition.getAutomatonName().equals(
                "pointer analysis automaton checker");
    }

    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
        LazyInternalStructures internals,boolean isInterprocediral,
        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                                                       nodeLocationDictionary) {
        return new FPDMemoryReassignedFilter();
    }
}
