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
public class XMLAutomatonSyntaxErrorException extends Exception {

    // public section 

    /**
     * @brief Creates exception class with string message, which describes
     *        a syntax error in XML file with automaton definition. 
     *
     * The passed error message gets prefix, which identifies a location
     * in the code where the exception was thrown.
     *
     * @param errorMessage Raw description of a syntax error in XML automaton
     *        definition file.
     */
    public XMLAutomatonSyntaxErrorException(final String errorMessage) {
        super(errorMessage); 
    }

    public XMLAutomatonSyntaxErrorException(final Throwable cause) {
        super(cause); 
    }

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
