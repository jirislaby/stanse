/**
 * @brief defines public abstract class Checker. 
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.parser.CFG;

import java.util.Set;
import java.util.List;

/**
 * @brief Defines mandatory interface for all the checkers. It provides
 *        easy integration of new checkers to Stanse's framework.
 *
 * All the checkers are manipulated only through this base class. There are
 * only two functions required to all the checker 1) getName() - to get unique
 * name for the checker, 2) check() - to perform verification itself
 */
public abstract class Checker {

    // public section

    /**
     * @brief Explicitly does nothing.
     */
    public Checker() {
    }

    /**
     * @brief Forces all the children to define name of the checker.
     * 
     * The name should be unique.
     *
     * @return Unique name of the checker. 
     */
    public abstract String getName();

    /**
     * @brief Performs checking itself. Accepts set of CFGs and runs the
     *        checking on them to produce list of errors found in the CFGs.   
     *
     * Derived checker can implement cheking procedure as it likes. It is
     * assumed, that verification is interprocedural on the set of accepted
     * set of CFGs.
     *
     * @param  CFGs Set of control-flow graphs, which should be checked for
     *         errors.
     * @return List of errors found in accepted CFGs.
     * @throws CheckerException All the exceptions thrown by child checker.
     *         Children should derive their own exception classes from
     *         CheckerException class. 
     *         
     * @see cz.muni.stanse.checker#CheckerError
     *      cz.muni.stanse.checker#CheckerException
     */
    public abstract List<CheckerError> check(final Set<CFG> CFGs)
                        throws CheckerException;

    /**
     * @brief Implements standard string conversion method.
     * @return String desctioption of the checker.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Checker: " + getName();
    }
}
