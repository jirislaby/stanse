/**
 * @brief Implements exception class which represents any syntax error in XML
 *        files, which contains automaton definitions.
 */
package cz.muni.stanse.automatonchecker;

/**
 * @brief Describes a syntax error in XML file with automaton definition. 
 *
 * Raw error description string passed to the constructor is automatically
 * extedned by additional information, which describes location in the source
 * code, where the exception was thrown.
 *
 * @see java.lang.Exception 
 */
public class XMLAutomatonSyntaxErrorException extends
                                       cz.muni.stanse.checker.CheckerException {

    // public section 

    /**
     * @brief Initializes the class by message, which describes syntax error
     *        in XML file with automaton definition.
     *
     * Exception description message is directly passed to the base class. 
     * 
     * @param errorMessage Description of a error which occurs in the checker. 
     */
    public XMLAutomatonSyntaxErrorException(final String errorMessage) {
        super(errorMessage); 
    }

    /**
     * @brief Initializes the class by cause object, which describes syntax
     *        error in XML file with automaton definition.
     *
     * Exception cause object is directly passed to the base class. 
     * 
     * @param cause Cause of the exception which occurs in the checker. 
     */
    public XMLAutomatonSyntaxErrorException(final Throwable cause) {
        super(cause); 
    }

    /**
     * @brief Initializes the class by message and cause, which together
     *        describes syntax error in XML file with automaton definition.
     *
     * Exception description message is directly passed to the base class. 
     * Exception cause object is directly passed to the base class as well. 
     * 
     * @param errorMessage Description of a error which occurs in the checker. 
     * @param cause Cause of the exception which occurs in the checker. 
     */
    public XMLAutomatonSyntaxErrorException(final String errorMessage,
                                            final Throwable cause) {
        super(errorMessage,cause); 
    }

    // private section

    /**
     * @brief Mandatory field necessitated by Serializable interface.
     *
     * This necessity was derived from Exception class.
     * 
     * @see java.io.Serializable 
     */
    private static final long serialVersionUID = new String("cz.muni.stanse." +
                "automatonchecker.XMLAutomatonSyntaxErrorException").hashCode();
}
