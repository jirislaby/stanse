/**
 * @brief defines public class, which is base class for all the
 *        exceptions which can be thrown in SourceCodeEnumerators.
 *
 * Copyright (c) 2008-2009 Marek Trtik
 * Copyright (c) 2009 Jiri Slaby <jirislaby@gmail.com>
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.configuration.source_enumeration;

/**
 * @brief Defines base class for all the exceptions which can be thrown
 *        in SourceCodeEnumerators.
 * @see java.lang.Exception
 */
@SuppressWarnings("serial")
public class SourceCodeFilesException extends Exception {

    // public section

    /**
     * @brief Initializes the class by exception description message.
     *
     * Exception description message is directly passed to the base class.
     *
     * @param errorMessage Description of a error which occurs in the checker.
     */
    public SourceCodeFilesException(final String errorMessage) {
        super(errorMessage);
    }

    /**
     * @brief Initializes the class by exception cause object.
     *
     * Exception cause object is directly passed to the base class.
     *
     * @param cause Cause of the exception which occurs in the checker.
     */
    public SourceCodeFilesException(final Throwable cause) {
        super(cause);
    }

    /**
     * @brief Initializes the class by exception description message and
     *        exception cause object.
     *
     * Exception description message is directly passed to the base class.
     * Exception cause object is directly passed to the base class as well.
     *
     * @param errorMessage Description of a error which occurs in the checker.
     * @param cause Cause of the exception which occurs in the checker.
     */
    public SourceCodeFilesException(final String errorMessage,
                                    final Throwable cause) {
        super(errorMessage,cause);
    }
}

