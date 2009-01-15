/**
 * @brief
 *
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Triple;
import java.util.List;

/**
 * @brief
 *
 * @see
 */
public final class ErrorTrace {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public ErrorTrace(String shortDesc, String fullDesc,
                      List< Triple<CFGNode,String,CFG> > trace) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.trace = trace;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public String getShortDescription() {
        return shortDesc;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public String getFullDescription() {
        return fullDesc;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public List< Triple<CFGNode,String,CFG> > getErrorTrace() {
        return trace;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("- ErrorTrace:\n").
	    append("    - shortDesc: ").
		    append(getShortDescription()).append('\n').
	    append("    - fullDesc: ").
		    append(getFullDescription()).append('\n').
	    append("    - CFGNodes:\n");
        for (Triple<CFGNode,String,CFG> node : getErrorTrace())
            result.append("        - <").append(node.getFirst().toString()).
		    append(',').append(node.getSecond()).append(',').
		    append(node.getThird().toString()).append(">\n");

        return result.toString();
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((fullDesc == null) ? 0 : fullDesc.hashCode());
        result = PRIME * result + ((shortDesc == null) ? 0 : shortDesc.hashCode());
        result = PRIME * result + ((trace == null) ? 0 : trace.hashCode());
        return result;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((ErrorTrace)obj);
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public boolean isEqualWith(final ErrorTrace other) {
        return getShortDescription().equals(other.getShortDescription()) &&
               getFullDescription().equals(other.getFullDescription()) &&
               getErrorTrace().equals(other.getErrorTrace());
    }

    // private section

    /**
     * @brief
     *
     */
    private final String shortDesc;

    /**
     * @brief
     *
     */
    private final String fullDesc;

    /**
     * @brief
     *
     */
    private final List< Triple<CFGNode,String,CFG> > trace;
}
