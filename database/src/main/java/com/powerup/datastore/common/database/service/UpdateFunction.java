package com.powerup.datastore.common.database.service;

import com.powerup.datastore.common.api.exception.DatastoreSaveException;
import com.powerup.java.immutable.model.ModelObject;
import org.apache.ibatis.session.SqlSession;

/**
 * The <code>UpdateFunction</code> class defines a lambda expression function for a database update operation.
 *
 * @param <OneT> The type of model object being updated
 * @param <TwoT> The sql session to use to perform the update operation
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface UpdateFunction<OneT extends ModelObject, TwoT extends SqlSession> {

    /**
     * Apply invokes the function this lambda function represents.
     *
     * @param one The updated model object
     * @param two The existing model object
     * @param three The sql session to use to perform the update operation
     *
     * @throws DatastoreSaveException Unable to update model object
     */
    @SuppressWarnings("RedundantThrows")
    void apply(OneT one, OneT two, TwoT three) throws DatastoreSaveException;
}