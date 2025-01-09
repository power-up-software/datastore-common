package com.powerup.datastore.common.database.service;

import com.powerup.datastore.common.api.exception.DatastoreSaveException;
import com.powerup.java.immutable.model.ModelObject;
import org.apache.ibatis.session.SqlSession;

/**
 * The <code>StoreFunction</code> class defines a lambda expression function for a database store operation.
 *
 * @param <OneT> The type of model object being stored
 * @param <TwoT> The sql session to use to perform the store operation
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface StoreFunction<OneT extends ModelObject, TwoT extends SqlSession> {

    /**
     * Apply invokes the function this lambda function represents.
     *
     * @param one The model object to be stored
     * @param two The sql session to use to perform the store operation
     *
     * @throws DatastoreSaveException Unable to save model object
     */
    @SuppressWarnings("RedundantThrows")
    void apply(OneT one, TwoT two) throws DatastoreSaveException;
}