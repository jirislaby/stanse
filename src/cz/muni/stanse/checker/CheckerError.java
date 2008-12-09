/**
 *  @brief
 * 
 */
package cz.muni.stanse.checker;

import java.util.List;

/**
 * @brief
 *
 * @see
 */
public final class CheckerError {

    // public section

    /**
     * @brief
     *
     * @param
     * @return
     * @see
     */
    public CheckerError(String shortDesc, String fullDesc, int level,
                        List<ErrorTrace> traces) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.level = level;
        this.traces = traces;
    }

    /**
     * @brief
     *
     * @param
     * @return
     * @see
     */
    public int getErrorLevel() {
        return level;
    }
    
    /**
     * @brief
     *
     * @param
     * @return
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
     * @see
     */
    public List<ErrorTrace> getErrorTraces() {
        return traces;
    }

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
        String result =
                "CheckerError:\n" +
                "    - shortDesc: " + getShortDescription() + '\n' +
                "    - fullDesc: " + getFullDescription() + '\n' +
                "    - errorLevel: " + getErrorLevel() + '\n';
        for (ErrorTrace trace : getErrorTraces())
            result += trace.toString().replaceAll("\n","\n    ");

        return result;
    }

    /**
     * 
     * @brief
     *
     * @param
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((fullDesc == null) ? 0 : fullDesc.hashCode());
        result = PRIME * result + level;
        result = PRIME * result + ((shortDesc == null) ? 0 : shortDesc.hashCode());
        result = PRIME * result + ((traces == null) ? 0 : traces.hashCode());
        return result;
    }

    /**
     * 
     * @brief
     *
     * @param
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return (obj == null || getClass() != obj.getClass()) ?
                false : isEqualWith((CheckerError)obj);
    }

    /**
     * 
     * @brief
     *
     * @param
     * @return
     * @see
     */
    public boolean isEqualWith(final CheckerError other) {
        return getShortDescription().equals(other.getShortDescription()) &&
               getFullDescription().equals(other.getFullDescription()) &&
               getErrorLevel() == other.getErrorLevel() &&
               getErrorTraces().equals(other.getErrorTraces());
    }

    // private section

    /**
     *  @brief
     * 
     */
    private final String shortDesc; 
    /**
     *  @brief
     * 
     */
    private final String fullDesc;
    /**
     *  @brief
     * 
     */
    private final int level;
    /**
     *  @brief
     * 
     */
    private final List<ErrorTrace> traces;
}
