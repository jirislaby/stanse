/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.newchecker.exceptions;

/**
 *
 * @author xstastn
 */
public class AutomatonRunException extends AutomatonException {

    public AutomatonRunException() {
    }

    public AutomatonRunException(String message) {
        super(message);
    }

    public AutomatonRunException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutomatonRunException(Throwable cause) {
        super(cause);
    }

}
