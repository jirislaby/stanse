package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.codestructures.
                                    LazyInternalStructuresInter;
import cz.muni.stanse.utils.Pair;

import java.util.Vector;
import java.util.List;
import java.util.Map;

class FalsePositivesDetectorFactory {

    static List<FalsePositivesDetector>
    getDetectors(final XMLAutomatonDefinition definition,
                 final LazyInternalStructures internals,
                 final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                        nodeLocationDictionary) {
        final Vector<FalsePositivesDetector> result =
                new Vector<FalsePositivesDetector>();

        final boolean isIterprocedural =
               internals instanceof LazyInternalStructuresInter;
        for (FalsePositivesDetectorCreator creator : creators)
            if (creator.isApplicable(definition,isIterprocedural))
                result.add(creator.create(definition,internals,
                                          isIterprocedural,
                                          nodeLocationDictionary));

        return result;
    }

    // private section

    private FalsePositivesDetectorFactory() {
    }

    private static void register(final FalsePositivesDetectorCreator creator){
        creators.add(creator);
    }

    private static final Vector<FalsePositivesDetectorCreator> creators =
                                    new Vector<FalsePositivesDetectorCreator>();
    static {
        register(new FPDMemoryAssignedFilterCreator());
        register(new FPDMemoryReassignedFilterCreator());
	register(new FPDMemoryNestedCheckFilterCreator());
        /* should be last */
        register(new FPDSameLocationReportFilterCreator());
    }
}
