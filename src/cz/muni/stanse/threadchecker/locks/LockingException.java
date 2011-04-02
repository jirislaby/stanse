/*
 * Licensed under GPLv2
 */

package cz.muni.stanse.threadchecker.locks;

/**
 * May be thrown when lock state is or will become illegal (e.g. negative)
 * @author Jiri Slaby
 */
public class LockingException extends Exception {
	public LockingException() {
		super();
	}

	public LockingException(final String cause) {
		super(cause);
	}
}
