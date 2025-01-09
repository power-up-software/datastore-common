package com.powerup.datastore.common.database.service;

import com.powerup.datastore.common.api.exception.DatastoreSaveException;
import com.powerup.java.immutable.model.ModelObject;
import org.apache.ibatis.session.SqlSession;

/**
 * The <code>SubElementUpdateFunction</code> class defines a lambda expression function for the cascade of an update operation to an objects contained
 * elements.
 *
 * @param <OneT> The type of model object being updated.
 * @param <TwoT> The sql session to use to perform the store operation.
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface CascadeUpdateFunction<OneT extends ModelObject, TwoT extends SqlSession> {

    /**
     * Apply invokes the function this lambda function represents.
     *
     * @param one The updated model object.
     * @param two The existing model object.
     * @param three The sql session to use to perform the update operation.
     *
     * @return Updated model object with cascaded changes added.
     *
     * @throws DatastoreSaveException Unable to update model object.
     */
    @SuppressWarnings("RedundantThrows")
    OneT apply(OneT one, OneT two, TwoT three) throws DatastoreSaveException;
}