package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalProgramStructuresCollection;
import cz.muni.stanse.utils.Pair;

import java.util.Map;

abstract class FalsePositivesDetectorCreator {
    abstract boolean isApplicable(XMLAutomatonDefinition definition,
                                  boolean isInterprocediral);
    abstract FalsePositivesDetector
    create(XMLAutomatonDefinition definition,
           LazyInternalProgramStructuresCollection internals,
           boolean isInterprocediral,
           final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                        nodeLocationDictionary);
}
