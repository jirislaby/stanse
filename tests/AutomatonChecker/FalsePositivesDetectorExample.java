package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalProgramStructuresCollection;
import cz.muni.stanse.codestructures.LazyInternalProgramStructuresCollectionImpl;
import cz.muni.stanse.utils.Pair;

import java.util.List;
import java.util.Map;

/**
 * @brief Sample implementation of false positive detector
 *
 * Each false-positive detector should consider just one type of false-positive;
 * It is recommended to follow this pattern and create many different
 * detectors, where each of them considers just one type of false-positive;
 */
class MyDetector extends FalsePositivesDetector {

    MyDetector(final XMLAutomatonDefinition definition,
               final LazyInternalProgramStructuresCollection internals,
               boolean isInterprocediral,
               final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                        nodeLocationDictionary) {
        // I do not need to save passed data, because I won't need then. 
    }

    /**
     * @brief decides whether passed error is false-positive or not
     *
     * This detector consideres no error as false-positive :-)
     *
     * @path list   of CFG nodes which forms error path in source program
     * @cfgContext  represents call-stack; It contains those nodes which
     *              are function calls in path; cfgContext is subset of path
     * @rule        rule which caught this error
     * @return      true when this error is considered as false-pasitive.
     */
    @Override
    boolean isFalsePositive(final java.util.List<CFGNode> path,
                            final java.util.Stack<CFGNode> cfgContext,
                            final ErrorRule rule) {
        return false; // no error is considered as false-positive :-)
    }
}

/**
 * @brief Sample creator for MyDetector false-positives detector
 *
 * All creators are registered to factory class FalsePositivesDetectorFactory
 * and provide creation strategy for them related false-positive detectors;
 * Registration is shown later.
 */
class MyDetectorCreator extends FalsePositivesDetectorCreator {
    /**
     * @brief This method decides if related detector is capable detect
     *        false-positives for passed XML configuration file
     * 
     * @definition         XML automaton definition file
     * @isInterprocediral  is true iff interprocedural analysis is on
     * @return             true iff related false-positive detector
     *                     is able to detect false-positives for current
     *                     XML configuration file and type of analysis
     */
    @Override
    boolean isApplicable(final XMLAutomatonDefinition definition,
                         boolean isInterprocediral) {
        return true;
    }

    /**
     * @brief Creates related false-positives detector; Here creates class
     *        MyDetector
     * 
     * @nodeLocationDictionary   Dictionary, which provides mapping from CFG
     *                           nodes to related PatternLocations
     * @return                   related false-positives detector; Here it is
     *                           instance of MyDetector class.
     */
    @Override
    FalsePositivesDetector create(XMLAutomatonDefinition definition,
                        LazyInternalProgramStructuresCollection internals,
                        boolean isInterprocediral,
                        final Map<CFGNode,Pair<PatternLocation,PatternLocation>>
                        nodeLocationDictionary) {
        return new MyDetector(definition,internals,isInterprocediral,
                              nodeLocationDictionary);
    }
}

/**
 * @brief When both detector and creator are set, the last step is to register
 *        creator to the creators' factory, i.e. into class FalsePositivesDetectorFactory
 *
 * Registration procedure is as follows:
 *   1. open file FalsePositivesDetectorFactory.java in Stanse's AutomatonChecker package
 *   2. scroll to the end of file
 *   3. search bottom up for first (and only) block of form:
 *          static {
 *              // here can be zero or more statement of form:
 *              //      register(new SomeCreatorName());
 *          }
 *   4. into this block add statement of form:
 *           register(new NameOfClassOfYourFalseDetectorCreator());
 *      for example registration statement for MyDetectorCreator class would be:
 *           register(new MyDetectorCreator());
 *   5. and that's all; Run Stanse and experiment with your detector
 */
 