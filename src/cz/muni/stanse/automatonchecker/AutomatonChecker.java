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

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.LazyInternalStructures;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingSuccess;
import cz.muni.stanse.checker.CheckingFailed;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.ClassLogger;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

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
final class AutomatonChecker extends cz.muni.stanse.checker.Checker {

    // public section

    /**
     * @brief Parses accepted XML automata definition and initializes internal
     *        structures related to automata definition.
     *
     * @param XMLdefinition XML representation of AST
     * @throws XMLAutomatonSyntaxErrorException
     */
    public AutomatonChecker(final File xmlFile) {
        super();
        this.xmlFile = xmlFile;
    }

    /**
     * @brief Uniquelly identifies the checker by the string identifier.
     *
     * @return String which uniquelly identifies the checker.
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return AutomatonCheckerCreator.getNameForCheckerFactory() +
               " of " + getXmlFile().toString();
    }

    /**
     * @brief Does the source code checking itself.
     *
     * Method searches through source code to find matching patterns defined
     * in XML automata definition file. Each such matched location in the source
     * code is assigned an instance of PatternLocation class. Initial location
     * in the program is always introduced and is initialized with initial
     * states of all automata to be run on the source code.
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
     * are crossed and checked for error states. Each PatternLocation has
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
    @Override
    public CheckingResult check(final LazyInternalStructures internals,
                                final CheckerErrorReceiver errReciver,
                                final CheckerProgressMonitor monitor)
                                throws XMLAutomatonSyntaxErrorException {
        CheckingResult result = new CheckingSuccess();
        final AutomatonCheckerLogger automatonMonitor =
                new AutomatonCheckerLogger(monitor);
        automatonMonitor.note("Checker: " + getName());
        automatonMonitor.pushTab();
        automatonMonitor.phaseLog("parsing configuration XML file");
        final XMLAutomatonDefinition XMLdefinition =
            parseXMLAutomatondefinition(loadXMLdefinition());
        if (XMLdefinition != null)
            result = check(XMLdefinition,internals,errReciver,automatonMonitor);
        automatonMonitor.phaseBreak("checking done in ");
        return result;
    }

    // private section

    private Integer makePatternId(HashMap<PatternLocation, Integer> patternids, StringBuilder sb, PatternLocation pattern) {
        if (!patternids.containsKey(pattern)) {
            sb.append("n").append(patternids.size()).append("[label=\"");
            for (AutomatonState state : pattern.getUnprocessedAutomataStates()){
                sb.append("[").append(state.toString()).append("]");
            }
            for (AutomatonState state : pattern.getProcessedAutomataStates()){
                sb.append("[").append(state.toString()).append("]");
            }
            sb.append(pattern.getCFGreferenceNode());
            sb.append("\"]").append(";\n");
            patternids.put(pattern, patternids.size());
            return patternids.size() - 1;
        } else
            return patternids.get(pattern);
    }

    private void dumpNodeLocationGraph(Collection<Pair<PatternLocation, PatternLocation> > locs) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");

        HashMap<PatternLocation, Integer> patternids = new HashMap<PatternLocation, Integer>();
        for (Pair<PatternLocation, PatternLocation> loc : locs) {
            Integer firstId = makePatternId(patternids, sb, loc.getFirst());
            Integer secondId = makePatternId(patternids, sb, loc.getSecond());
            for (PatternLocation succ : loc.getFirst().getSuccessorPatternLocations()) {
                Integer succId = makePatternId(patternids, sb, succ);
                sb.append("n").append(firstId).append("->").append("n").append(succId).append(";\n");
            }
            if (firstId != secondId) {
                sb.append("n").append(firstId).append("->").append("n").append(secondId).append(";\n");
                for (PatternLocation succ : loc.getSecond().getSuccessorPatternLocations()) {
                    Integer succId = makePatternId(patternids, sb, succ);
                    sb.append("n").append(secondId).append("->").append("n").append(succId).append(";\n");
                }
            }
        }

        sb.append("}");
        System.out.print(sb);
    }

    private CheckingResult
    check(final XMLAutomatonDefinition xmlAutomatonDefinition,
          final LazyInternalStructures internals,
          final CheckerErrorReceiver errReciver,
          final AutomatonCheckerLogger monitor)
          throws XMLAutomatonSyntaxErrorException {
        monitor.phaseLog("building pattern locations");
        final HashMap<CFGNode,Pair<PatternLocation,PatternLocation>>
            nodeLocationDictionary = PatternLocationBuilder
                   .buildPatternLocations(internals.getCFGHandles(),
                                          xmlAutomatonDefinition,
                                          internals.getArgumentPassingManager(),
                                          internals.getNavigator(),
                                          internals.getStartFunctions());

        monitor.phaseLog("processing automata states");
        final LinkedList<PatternLocation> progressQueue =
                new LinkedList<PatternLocation>();
        for (final CFGHandle cfg: internals.getCFGHandles()) {
            final PatternLocation location =
                nodeLocationDictionary.get(cfg.getStartNode()).getSecond();
            if (location.hasUnprocessedAutomataStates())
                progressQueue.add(location);
        }
        final long startFixPointComputationTime = System.currentTimeMillis();
        while (!progressQueue.isEmpty()) {
            //dumpNodeLocationGraph(nodeLocationDictionary.values());
            final PatternLocation currentLocation = progressQueue.remove();
            if (!currentLocation.hasUnprocessedAutomataStates())
                continue;
            currentLocation.fireLocalAutomataStates();
            final boolean successorsWereAffected =
                currentLocation.processUnprocessedAutomataStates();
            if (successorsWereAffected) {
                progressQueue.addAll(
                        currentLocation.getSuccessorPatternLocations());
                if (currentLocation.getLocationForCallNotPassedStates() != null)
                    progressQueue.add(
                           currentLocation.getLocationForCallNotPassedStates());
            }
            final long FixPointComputationTime =
                    System.currentTimeMillis() - startFixPointComputationTime;
	    /* 60 s is hard limit, 10 s when there are many locations */
            if (FixPointComputationTime > 60000 ||
			(FixPointComputationTime > 10000 &&
				nodeLocationDictionary.size() > 500)) {
                monitor.pushTab();
                final String errMsg =
                    "*** FAILED: fix-point computation FAILED, " +
                    "because of timeout. Location set is extremely " +
                    "large: " + nodeLocationDictionary.size();
                monitor.note(errMsg);
                monitor.popTab();
                getLocationUnitName(currentLocation,internals);
                return new CheckingFailed(errMsg,
                                getLocationUnitName(currentLocation,internals));
            }
        }

        monitor.phaseLog("collecting false-positive detectors");
        final java.util.List<FalsePositivesDetector> detectors =
            FalsePositivesDetectorFactory.getDetectors(
                        xmlAutomatonDefinition,internals,
                        Collections.unmodifiableMap(nodeLocationDictionary));

        monitor.phaseLog("building error traces");
        monitor.pushTab();
        final CheckingResult result =
            CheckerErrorBuilder.buildErrorList(nodeLocationDictionary,internals,
                                               detectors,errReciver,monitor,
                                               getName());
        monitor.popTab();

        return result;
    }

    private String getNodeUnitName(final CFGNode node,
                                   final LazyInternalStructures internals) {
        return Stanse.getUnitManager().getUnitName(
                    internals.getNodeToCFGdictionary().get(node));
    }

    private String getLocationUnitName(final PatternLocation location,
                                   final LazyInternalStructures internals) {
        return getNodeUnitName(location.getCFGreferenceNode(),internals);
    }

    private File getXmlFile() {
        return xmlFile;
    }

    private Document loadXMLdefinition() {
        try {
            return (new SAXReader()).read(getXmlFile());
        } catch (final DocumentException e) {
            ClassLogger.error(AutomatonChecker.class,
                              "Cannot open '" + getXmlFile().getAbsolutePath() +
                              "': " + e.getLocalizedMessage());
            return null;
        }
    }

    private XMLAutomatonDefinition
    parseXMLAutomatondefinition(final Document XMLdefinition) {
        if (XMLdefinition == null)
            return null;
        try {
            return new XMLAutomatonDefinition(XMLdefinition.getRootElement());
        } catch (final XMLAutomatonSyntaxErrorException e) {
            ClassLogger.error(AutomatonChecker.class,
                              "Error found in XML definition file '" +
                              getXmlFile().getAbsolutePath() + "': " +
                              e.getLocalizedMessage());
            return null;
        }
    }

    private final File xmlFile;
}
