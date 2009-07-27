package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.utils.Pair;

import java.util.Map;

abstract class FalsePositivesDetectorCreator {
    abstract boolean isApplicable(XMLAutomatonDefinition definition,
                                  boolean isInterprocediral);
    abstract FalsePositivesDetector
    create(XMLAutomatonDefinition definition,
           LazyInternalStructures internals,
           boolean isInterprocediral,
           final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                        nodeLocationDictionary);
}
