/**
 * @brief defines public abstract class Checker. 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.codestructures.Unit;

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
     * @brief Initializes checker by supplied command-line arguments.
     * 
     * The options must all start with -X/--X. 
     */
    public Checker(String[] args){
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
     * @brief Performs checking itself. Accepts set of compilation units and runs the
     *        checking on them to produce list of errors found in the units.   
     *
     * Derived checker can implement checking procedure as it likes. It is
     * assumed, that verification is interprocedural on the set of accepted
     * set of units.
     *
     * @param  units Set of compilation units, which should be checked for
     *         errors.
     * @return List of errors found.
     * @throws CheckerException All the exceptions thrown by child checker.
     *         Children should derive their own exception classes from
     *         CheckerException class. 
     *         
     * @see cz.muni.stanse.checker#CheckerError
     * @see cz.muni.stanse.checker#CheckerException
     */
    public abstract List<CheckerError> check(final List<Unit> units)
                        throws CheckerException;

    /**
     * @brief Implements standard string conversion method.
     * @return String description of the checker.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Checker: " + getName();
    }
}
