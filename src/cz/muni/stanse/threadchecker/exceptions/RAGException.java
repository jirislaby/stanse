/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.threadchecker.exceptions;

/**
 *
 * @author Jan Kučera
 */
public class RAGException extends Exception {

    public RAGException(Throwable cause) {
        super(cause);
    }

    public RAGException(String message, Throwable cause) {
        super(message, cause);
    }

    public RAGException(String message) {
        super(message);
    }

    public RAGException() {
    }
    
}
