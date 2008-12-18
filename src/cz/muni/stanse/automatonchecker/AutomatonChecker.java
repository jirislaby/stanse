/**
 * @brief Defines public final class AutomatonChecker which provides static
 *        program verification specialized to locking problems, interrupts
 *        enabling/disabling problems, unnecessary check optimizations and
 *        points-to problems like null pointer dereference and memory leaks.
 */
package cz.muni.stanse.automatonchecker;

import java.io.File;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import cz.muni.stanse.codestructures.CFG;

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
public final class AutomatonChecker extends cz.muni.stanse.checker.Checker {

    // public section

    /**
     * @brief
     *
     * @param XMLdefinition XML representation of AST
     * @throws 
     * @see
     */
    public AutomatonChecker(final org.dom4j.Document XMLdefinition)
                                       throws XMLAutomatonSyntaxErrorException {
        super();
        XMLAutomatonDefinition = new XMLAutomatonDefinition(
                                                XMLdefinition.getRootElement());
    }

    
    public AutomatonChecker(String[] args) throws XMLAutomatonSyntaxErrorException, Exception {
    	this(getDocument(args));
//    	OptionParser parser = new OptionParser();
//    	OptionSpec<File> automaton = parser.accepts("Xautomaton" , "Checking automaton.")
//		.withRequiredArg()
//		.describedAs("file")
//		.ofType(File.class);
//    	
//    	final OptionSet options = parser.parse(args);
//    	if(options.has(automaton)) {
//    		this((new SAXReader()).read(options.valueOf(automaton)));
//    	}else {
//    		// TODO spadni!
//    	}
    		
    }
    
    private static Document getDocument(String[] args) throws Exception {
    	OptionParser parser = new OptionParser();
    	OptionSpec<File> automaton = parser.accepts("Xautomaton" , "Checking automaton.")
		.withRequiredArg()
		.describedAs("file")
		.ofType(File.class);
    	
    	final OptionSet options = parser.parse(args);
    	// if(options.has(automaton)) {
    		
				return ((new SAXReader()).read(options.valueOf(automaton)));
			
//    	}else {
//    		// TODO spadni!
//    	}
    }
    
    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see cz.muni.stanse.checker.Checker#getName()
     */
    @Override
    public String getName() {
        return "Automaton checker [" +
               getXMLAutomatonDefinition().getAutomatonName() +
               "]";
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see cz.muni.stanse.checker.Checker#check(java.util.Set)
     */
    @Override
    public List<cz.muni.stanse.checker.CheckerError>
    check(final Set<CFG> CFGs) throws XMLAutomatonSyntaxErrorException {
        final HashMap<cz.muni.stanse.codestructures.CFGNode,PatternLocation>
            nodeLocationDictionary = PatternLocationBuilder.
                   buildPatternLocations(CFGs,getXMLAutomatonDefinition());

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
        return CheckerErrorBuilder.buildErrorList(nodeLocationDictionary);
    }

    // private section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    private XMLAutomatonDefinition getXMLAutomatonDefinition() {
        return XMLAutomatonDefinition;
    }

    /**
     * @brief
     */
    private final XMLAutomatonDefinition XMLAutomatonDefinition;
}
