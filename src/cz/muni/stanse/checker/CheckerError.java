/**
 *  @brief Defines public final class CheckerError. It represents the error
 *         found in source program (reprezented by set of CFGs).
 * 
 */
package cz.muni.stanse.checker;

import java.util.List;

/**
 * @brief Represent output from checkers, which is the error found in the
 *        source file (reprezented by set of CFGs). 
 *
 * Found error is described by short and full string description, level of
 * error importance and by list of error-traces. Each checker is under
 * obligation to report all its errors via instances of this class.  
 *
 * @see cz.muni.stanse.checker#Checker
 *      cz.muni.stanse.checker#ErrorTrace
 */
public final class CheckerError {

    // public section

    /**
     * @brief Accepts all the data necessary to construct instance of this
     *        class (wich are short and full string description, level of
     *        error importance and by list of error-traces), and stores them
     *        in created instance.
     * @param shortDesc Short description of error, like null-dereference,
     *                  memory-leak, and so on
     * @param fullDesc Complete descriprion of error. Checker can put here
     *                 the message as much detailed as it likes.
     * @param level Level of importance of the error message.
     * @param traces List of error traces. (See ErrorTrace class for more info)
     * @see cz.muni.stanse.checker#ErrorTrace 
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
        StringBuilder result = new StringBuilder("CheckerError:\n").
	    append("    - shortDesc: ").append(getShortDescription()).
		    append('\n').
	    append("    - fullDesc: ").append(getFullDescription()).
		    append('\n').
	    append("    - errorLevel: ").append(getErrorLevel()).append('\n');
	for (ErrorTrace trace : getErrorTraces())
            result.append(trace.toString().replaceAll("\n","\n    "));

        return result.toString();
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
