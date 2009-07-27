/**
 * @brief defines public abstract class Checker. 
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.codestructures.LazyInternalStructures;

public abstract class Checker {

    // public section

    /**
     * @brief Forces all the children to define name of the checker.
     * 
     * The name should be unique.
     *
     * @return Unique name of the checker. 
     */
    public abstract String getName();

    public abstract void
    check(final LazyInternalStructures internals,
          final CheckerErrorReceiver errReciver,
          final CheckerProgressMonitor monitor) throws CheckerException;

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
