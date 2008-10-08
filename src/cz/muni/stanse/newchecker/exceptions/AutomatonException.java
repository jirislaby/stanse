/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.newchecker.exceptions;

/**
 *
 * @author xstastn
 */
public class AutomatonException extends Exception {

    public AutomatonException(Throwable cause) {
        super(cause);
    }

    public AutomatonException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutomatonException(String message) {
        super(message);
    }

    public AutomatonException() {
    }
    

}
