package com.powerup.datastore.common.database.service;

import com.powerup.java.immutable.model.ModelObject;
import com.powerup.lambda.functions.api.TwoParameterFunction;
import java.util.UUID;
import org.apache.ibatis.session.SqlSession;

/**
 * The <code>DatabaseOperationGroup</code> class is a group of database operations that can be performed on a model object.
 *
 * @param <T> The type of model object the database options are for.
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
public class DatabaseOperationGroup<T extends ModelObject> {
    /**
     * The store function that is for inserting new model objects into the database.
     */
    private final StoreFunction<T, SqlSession> storeFunction;
    /**
     * The update function that is for updating existing model objects in the database.
     */
    private final UpdateFunction<T, SqlSession> updateFunction;
    /**
     * The cascade update function that will perform updates to items contained in the model object. This is only needed for objects that have such
     * elements.
     */
    private final CascadeUpdateFunction<T, SqlSession> cascadeUpdateFunction;
    /**
     * The retrieve function that will retrieve a model object from the database.
     */
    private final TwoParameterFunction<UUID, SqlSession, T> retrieveFunction;

    /**
     * Base Constructor taking all functions except the cascade update function as parameters. Use this version if there is no need for
     * the cascade update function.
     *
     * @param storeFunction Value of {@link #storeFunction}.
     * @param updateFunction Value of {@link #updateFunction}.
     * @param retrieveFunction Value of {@link #retrieveFunction}.
     */
    public DatabaseOperationGroup(final StoreFunction<T, SqlSession> storeFunction, final UpdateFunction<T, SqlSession> updateFunction,
            final TwoParameterFunction<UUID, SqlSession, T> retrieveFunction) {
        this(storeFunction, updateFunction, null, retrieveFunction);
    }

    /**
     * Base Constructor taking all functions as parameters.
     *
     * @param storeFunction Value of {@link #storeFunction}
     * @param updateFunction Value of {@link #updateFunction}
     * @param cascadeUpdateFunction value of {@link #cascadeUpdateFunction}
     * @param retrieveFunction value of {@link #retrieveFunction}
     */
    public DatabaseOperationGroup(final StoreFunction<T, SqlSession> storeFunction, final UpdateFunction<T, SqlSession> updateFunction,
            final CascadeUpdateFunction<T, SqlSession> cascadeUpdateFunction, final TwoParameterFunction<UUID, SqlSession, T> retrieveFunction) {
        this.storeFunction = storeFunction;
        this.updateFunction = updateFunction;
        this.cascadeUpdateFunction = cascadeUpdateFunction;
        this.retrieveFunction = retrieveFunction;
    }

    /**
     * Accessor for the {@link #storeFunction} member variable.
     *
     * @return Current value of the {@link #storeFunction} member variable
     */
    public StoreFunction<T, SqlSession> getStoreFunction() {
        return storeFunction;
    }

    /**
     * Accessor for the {@link #updateFunction} member variable.
     *
     * @return Current value of the {@link #updateFunction} member variable
     */
    public UpdateFunction<T, SqlSession> getUpdateFunction() {
        return updateFunction;
    }

    /**
     * Accessor for the {@link #cascadeUpdateFunction} member variable.
     *
     * @return Current value of the {@link #cascadeUpdateFunction} member variable
     */
    public CascadeUpdateFunction<T, SqlSession> getCascadeUpdateFunction() {
        return cascadeUpdateFunction;
    }

    /**
     * Accessor for the {@link #retrieveFunction} member variable.
     *
     * @return Current value of the {@link #retrieveFunction} member variable
     */
    public TwoParameterFunction<UUID, SqlSession, T> getRetrieveFunction() {
        return retrieveFunction;
    }
}