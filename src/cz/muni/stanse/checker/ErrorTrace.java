/**
 * @brief
 *  
 */
package cz.muni.stanse.checker;

import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.utils.Pair;
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
                      List< Pair<CFGNode,String> > trace) {
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
    public List< Pair<CFGNode,String> > getErrorTrace() {
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
        String result =
            "- ErrorTrace:\n" +
            "    - shortDesc: " + getShortDescription() + '\n'+
            "    - fullDesc: " + getFullDescription() + '\n' +
            "    - CFGNodes:\n";
        for (Pair<CFGNode,String> node : getErrorTrace())
            result += "        - <" + node.getFirst().toString() + "," +
                                      node.getSecond() + ">\n";

        return result;
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
    private final List< Pair<CFGNode,String> > trace;
}
