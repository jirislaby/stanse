/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.pointeranalyzer;

/**
 *
 * @author Michal
 */
public class InvalidPointerOperationException extends Exception {
    public InvalidPointerOperationException(String message)
    {
        super(message);
    }
}
