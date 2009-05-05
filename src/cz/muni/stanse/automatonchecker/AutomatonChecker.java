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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Collections;
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
final class AutomatonChecker extends cz.muni.stanse.checker.Checker {

    // public section

    /**
     * @brief Parses accepted XML automata definition and initializes internal
     *        structures related to automata definition. 
     *
     * @param XMLdefinition XML representation of AST
     * @throws XMLAutomatonSyntaxErrorException 
     */
    public AutomatonChecker(final LinkedList<File> xmlFiles) {
        super();
        this.xmlFiles = xmlFiles;
    }

    /**
     * @brief Uniquelly identifies the checker by the string identifier. 
     *
     * @return String which uniquelly identifies the checker.
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return "Automaton checker " + getXmlFiles().toString();
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
        final LinkedList<cz.muni.stanse.checker.CheckerError> result =
                          new LinkedList<cz.muni.stanse.checker.CheckerError>();
        for (File file : getXmlFiles()) {
            final Document XMLdefinition = readXMLdefinition(file);
            if (XMLdefinition != null)
                result.addAll(new AutomatonCheckerImpl(XMLdefinition).
                                        check(units));
        }
        return result;
    }

    // private section

    private final List<File> getXmlFiles() {
        return Collections.unmodifiableList(xmlFiles);
    }

    private static final Document readXMLdefinition(final File file) {
            try {
                return (new SAXReader()).read(file);
            } catch (final DocumentException e) {
		System.err.println("Cannot open '" + file.getAbsolutePath() +
			"': " + e.getLocalizedMessage());
                return null;
            }
    }

    private final LinkedList<File> xmlFiles;
}
