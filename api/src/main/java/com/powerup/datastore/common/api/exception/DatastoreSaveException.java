package com.powerup.datastore.common.api.exception;

import java.io.Serial;

/**
 * The <code>DatastoreSaveException</code> class represents an exception that occurred when attempting to save an object to a data storage
 * implementation.
 *
 * @author Chris Picard
 */
public class DatastoreSaveException extends Exception {
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
    public DatastoreSaveException(final String message) {
        super(message);
    }

    /**
     * Base constructor taking an error message.
     *
     * @param message Error message describing what caused the exception.
     * @param cause Exception that caused this exception to be generated.
     */
    public DatastoreSaveException(final String message, final Throwable cause) {
        super(message, cause);
    }
}