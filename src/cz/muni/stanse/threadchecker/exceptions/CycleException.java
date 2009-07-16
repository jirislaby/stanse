package cz.muni.stanse.threadchecker.exceptions;

/**
 *
 * @author Jan Kučera
 */
public class CycleException extends Exception {

    public CycleException(Throwable cause) {
        super(cause);
    }

    public CycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CycleException(String message) {
        super(message);
    }

    public CycleException() {
    }

}
