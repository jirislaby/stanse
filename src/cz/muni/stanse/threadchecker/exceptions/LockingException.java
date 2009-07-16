
package cz.muni.stanse.threadchecker.exceptions;

/**
 *
 * @author Jan Kučera
 */
public class LockingException extends Exception {

    public LockingException(Throwable cause) {
        super(cause);
    }

    public LockingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockingException(String message) {
        super(message);
    }

    public LockingException() {
    }

}
