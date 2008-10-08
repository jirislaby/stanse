/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.automatonchecker.exceptions;

/**
 *
 * @author xstastn
 */
public class AutomatonSyntaxException extends AutomatonException {

    public AutomatonSyntaxException() {
    }

    public AutomatonSyntaxException(String message) {
        super(message);
    }

    public AutomatonSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutomatonSyntaxException(Throwable cause) {
        super(cause);
    }

}
