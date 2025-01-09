package com.powerup.datastore.common.api.exception;

import java.io.Serial;

/**
 * The <code>DatastoreRetrieveException</code> class represents an exception that occurred when attempting to retrieve an object from a data storage
 * implementation.
 *
 * @author Chris Picard
 */
public class DatastoreRetrieveException extends Exception {
    /**
     * Serial Version ID implements the {@link java.io.Serializable} contract.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Base constructor taking an error message.
     *
     * @param message Error message describing what caused the exception.
     */
    public DatastoreRetrieveException(final String message) {
        super(message);
    }

    /**
     * Base constructor taking an error message.
     *
     * @param message Error message describing what caused the exception.
     * @param cause Exception that caused this exception to be generated.
     */
    public DatastoreRetrieveException(final String message, final Throwable cause) {
        super(message, cause);
    }
}