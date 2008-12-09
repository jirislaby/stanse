/**
 * @brief
 * 
 *  
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.parser.ControlFlowGraph;

import java.util.Set;
import java.util.List;

/**
 * @brief
 *
 * @see
 */
public abstract class Checker {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @see
     */
    public Checker() {
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @see
     */
    public abstract String getName();

    public abstract List<CheckerError> check(final Set<ControlFlowGraph> CFGs)
			throws Exception;

    /**
     * 
     * @brief
     *
     * @param
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Checker: " + getName();
    }
}
