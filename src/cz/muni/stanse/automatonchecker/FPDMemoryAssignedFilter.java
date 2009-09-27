package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;

import cz.muni.stanse.utils.xmlpatterns.XMLAlgo;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Element;

final class FPDMemoryAssignedFilter extends FalsePositivesDetector {
    @Override
    boolean isFalsePositive(final java.util.List<CFGNode> path,
                            final java.util.Stack<CFGNode> cfgContext,
                            final ErrorRule rule) {
        if (!rule.getErrorDescription().equals(
                "memory leak - leaving function without releasing memory"))
            return false;
        Element e = path.get(0).getElement();
        if (!e.getName().equals("assignExpression"))
            return false;
        Element left = (Element)e.elements().get(0); // leftside
        for (CFGNode node: path)
            for (Object fno: node.getElement().selectNodes("..//functionCall")) {
                Iterator<Element> params = ((Element)fno).nodeIterator();
                params.next(); // skip name
                while (params.hasNext())
                    if (XMLAlgo.equalElements(left, params.next()))
                        return true;
            }
        return false;
    }
}
final class FPDMemoryAssignedFilterCreator
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
        return new FPDMemoryAssignedFilter();
    }
}
