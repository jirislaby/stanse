/**
 * @file AutomatonChecker.java
 * @brief Defines public final class AutomatonChecker which provides static
 *        program verification specialized to locking problems, interrupts
 *        enabling/disabling problems, unnecessary check optimizations and
 *        points-to problems like null pointer dereference and memory leaks.
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.CFGInstrumentationEraser;
import cz.muni.stanse.utils.CFGInstrumentationBuilder;
import cz.muni.stanse.utils.LazyInternalProgramStructuresCollection;
import cz.muni.stanse.utils.UnitsToCFGs;
import cz.muni.stanse.utils.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/**
 * @brief Static checker which is able to detect locking problems, interrupts
 *        enabling/disabling problems, unnecessary check optimizations and
 *        points-to problems like null pointer dereference and memory leaks.
 *
 * The checker is based on execution of finite automata specified in XML file
 * whose states are propagated through program locations. Some automata states
 * are considered as erroneous, so when automaton's transition to such erroneous
 * state is invoked in some program location, then appropriate error message is
 * reported. Program locations, which are considered for automata state
 * propagation are found by pattern matching. These pattern are specified in
 * the XML file defining automata as well.
 *
 * @see cz.muni.stanse.checker.Checker
 */
final class AutomatonCheckerImpl {

    // package-private section

    /**
     * @brief Parses accepted XML automata definition and initializes internal
     *        structures related to automata definition.
     *
     * @param XMLdefinition XML representation of AST
     * @throws XMLAutomatonSyntaxErrorException
     */
    AutomatonCheckerImpl(final org.dom4j.Document XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        XMLAutomatonDefinition = new XMLAutomatonDefinition(
                                                XMLdefinition.getRootElement());
    }

    /**
     * @brief Does the source code checking itself.
     *
     * Method searches through source code to find matching patterns defined
     * in XML automata definition file. Each such matched location in the source
     * code is assigned an instance of PatternLocation class. Initial location
     * in the program is always introduced and is initialized with initial
     * states of all autoamta to be run on the source code.
     *
     * The computation itself is simple distribution of automata states between
     * instances of PatternLocation class (locations are linked together with
     * respect to control-flow of source code). Automata states are transformed
     * with respect to transition rules assigned to each PatternLocation and
     * then distributed to linked locations. This procedure is finished, when
     * no location was delivered automaton state, which was not processed in
     * that location.
     *
     * Error detection is the final phase of the procedure. All PatternLocations
     * are crossed and checked for error states. Eeach PatternLocation has
     * assigned set of error transition rules which are applied to all processed
     * states in the location. If some error transition rule can be applied to
     * processed states, then it means the source code contains error. Each
     * error rule contains description of an error it checks for. Those error
     * transition rules are defined in XML automaton definition file.
     *
     * @param units List of translations units (described in internal structures
     *              like CFGs and ASTs)
     * @return List of errors found in the source code.
     * @throws XMLAutomatonSyntaxErrorException Is thrown, when some semantic
     *              error is detected in XML automata definition.
     * @see cz.muni.stanse.checker.Checker#check(java.util.List)
     */
    List<cz.muni.stanse.checker.CheckerError>
    check(final List<Unit> units, final boolean interprocedural)
                                       throws XMLAutomatonSyntaxErrorException {
        final Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> > CFGbackMapping =
            CFGInstrumentationBuilder.run(UnitsToCFGs.run(units));

        final LazyInternalProgramStructuresCollection internals =
            new LazyInternalProgramStructuresCollection(
                    CFGbackMapping.getFirst().keySet(),interprocedural);

        final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
            nodeLocationDictionary = PatternLocationBuilder
                   .buildPatternLocations(internals.getCFGs(),
                                          getXMLAutomatonDefinition(),
                                          internals.getArgumentPassingManager(),
                                          internals.getNavigator(),
                                          internals.getStartFunctions());

        final LinkedList<PatternLocation> progressQueue =
                new LinkedList<PatternLocation>();
        for (final CFG cfg : internals.getCFGs()) {
            final PatternLocation location =
                nodeLocationDictionary.get(cfg.getStartNode()).getSecond();
            if (location.isIsStartLocation())
                progressQueue.add(location);
        }
        while (!progressQueue.isEmpty()) {
            final PatternLocation currentLocation = progressQueue.remove();
            if (!currentLocation.hasUnprocessedAutomataStates())
                continue;
            final boolean successorsWereAffected =
                currentLocation.processUnprocessedAutomataStates();
            if (successorsWereAffected) {
                progressQueue.addAll(
                        currentLocation.getSuccessorPatternLocations());
                if (currentLocation.getLocationForCallNotPassedStates() != null)
                    progressQueue.add(
                           currentLocation.getLocationForCallNotPassedStates());
            }
        }

        return CFGInstrumentationEraser.run(CFGbackMapping,
                    CheckerErrorBuilder.buildErrorList(nodeLocationDictionary,
                                          internals.getArgumentPassingManager(),
                                          internals.getNavigator(),
                                          internals.getNodeToCFGdictionary()));
    }

    // private section

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return XMLAutomatonDefinition;
    }

    private final XMLAutomatonDefinition XMLAutomatonDefinition;
}
