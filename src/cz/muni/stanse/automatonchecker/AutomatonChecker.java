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
import cz.muni.stanse.utils.Pair;

import java.util.Set;
import java.util.HashSet;
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
final public class AutomatonChecker extends cz.muni.stanse.checker.Checker {

    // public section

    /**
     * @brief Parses accepted XML automata definition and initializes internal
     *        structures related to automata definition. 
     *
     * @param XMLdefinition XML representation of AST
     * @throws XMLAutomatonSyntaxErrorException 
     */
    public AutomatonChecker(final org.dom4j.Document XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        super();
        XMLAutomatonDefinition = new XMLAutomatonDefinition(
                                                XMLdefinition.getRootElement());
    }

    /**
     * @brief Parses accepted arguments which corresponds to arguments specified
     *        in the command-line of Stanse. The only purpose of this construc-
     *        tor is to find and load XML file which defines automata, and then
     *        calls constructor of this class, which accepts loaded XML automata
     *        definition. 
     *
     * @param args Stanse's command line arguments related to this checker.
     * @throws XMLAutomatonSyntaxErrorException This may occur when calling
     *              other constructor, which really constructs instance. 
     * @throws Exception This may occur when parsing command-line arguments
     * @see cz.muni.stanse.automatonchecker#AutomatonChecker(org.dom4j.Document)
     */
    public AutomatonChecker(final String[] args)
                            throws XMLAutomatonSyntaxErrorException, Exception {
        this(getDocument(args));    		
    }
    
    /**
     * @brief Uniquelly identifies the checker by the string identifier. 
     *
     * @return String which uniquelly identifies the checker.
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return "Automaton checker [" +
               getXMLAutomatonDefinition().getAutomatonName() +
               "]";
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
    @Override
    public List<cz.muni.stanse.checker.CheckerError>
    check(final List<Unit> units) throws XMLAutomatonSyntaxErrorException {
        final Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> > CFGbackMapping =
            CFGInstrumentationBuilder.run(buildSetOfCFGs(units));
        final Set<CFG> CFGs = CFGbackMapping.getFirst().keySet();

        final HashMap<CFGNode,PatternLocation> nodeLocationDictionary =
            PatternLocationBuilder.buildPatternLocations(CFGs,
                                                   getXMLAutomatonDefinition());

        final LinkedList<PatternLocation> progressQueue =
                new LinkedList<PatternLocation>();
        for (final CFG cfg : CFGs)
            progressQueue.add(nodeLocationDictionary.get(cfg.getStartNode()));

        while (!progressQueue.isEmpty()) {
            final PatternLocation currentLocation = progressQueue.remove();
            if (!currentLocation.hasUnprocessedAutomataStates())
                continue;
            final boolean successorsWereAffected =
                currentLocation.processUnprocessedAutomataStates();
            if (successorsWereAffected)
                progressQueue.addAll(
                        currentLocation.getSuccessorPatternLocations());
        }
        return CFGInstrumentationEraser.run(CFGbackMapping,
                    CheckerErrorBuilder.buildErrorList(nodeLocationDictionary));
    }

    // private section

    private static org.dom4j.Document
    getDocument(String[] args) throws Exception {
        joptsimple.OptionParser parser = new joptsimple.OptionParser();
        joptsimple.OptionSpec<java.io.File> automaton =
                            parser.accepts("Xautomaton" , "Checking automaton.")
                                  .withRequiredArg()
                                  .describedAs("file")
                                  .ofType(java.io.File.class);
        final joptsimple.OptionSet options = parser.parse(args);
//       if(options.has(automaton)) {
        return ((new org.dom4j.io.SAXReader()).
                        read(options.valueOf(automaton)));            
//      }else {
//          // TODO spadni!
//      }
    }

    private static HashSet<CFG> buildSetOfCFGs(final List<Unit> units) {
        final HashSet<CFG> CFGs = new HashSet<CFG>();
        for (final Unit unit : units)
            CFGs.addAll(unit.getCFGs());
        return CFGs;
    }

    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return XMLAutomatonDefinition;
    }

    private final XMLAutomatonDefinition XMLAutomatonDefinition;
}
