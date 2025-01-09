package com.powerup.datastore.common.database.service;

import com.powerup.datastore.common.api.exception.DatastoreSaveException;
import com.powerup.java.immutable.model.ModelObjectAbs;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>DatabaseExecutor</code> class defines the common portions needed by database executors. Rather than having the database service perform
 * operations which leads to extremely long and complicated classed, the database operations are broken out into executors.
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
public abstract class DatabaseExecutor {
    /**
     * Log statement format to use in generating successful retrieve debug log messages.
     */
    protected static final String SUCCESSFUL_RETRIEVE_DEBUG_MESSAGE_FORMAT = "Retrieved {}";
    /**
     * Log statement format to use in generating not found retrieve debug log messages.
     */
    protected static final String NOT_FOUND_RETRIEVE_DEBUG_MESSAGE_FORMAT = "No {} with ID {} found";
    /**
     * Log statement format to use in generating a value null when retrieving all log messages.
     */
    protected static final String NULL_RETRIEVE_ALL_MESSAGE_FORMAT = "Received null {} in request all request";
    /**
     * Log statement format to use in generating a deleting from database debug log messages.
     */
    protected static final String DELETING_DEBUG_MESSAGE_FORMAT = "Deleting {} from the database.";
    /**
     * Defines the SLF4J logger for the class.
     */
    @SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static Logger logger = LoggerFactory.getLogger(DatabaseExecutor.class);
    /**
     * The common database executor to use for common calls.
     */
    private final CommonDatabaseExecutor commonDatabaseExecutor;

    /**
     * Base constructor that takes services required by the executor.
     *
     * @param commonDatabaseExecutor Instance of the parent database service.
     */
    public DatabaseExecutor(final CommonDatabaseExecutor commonDatabaseExecutor) {
        this.commonDatabaseExecutor = commonDatabaseExecutor;
    }

    /**
     * Utility method that provides a common save object operation.
     *
     * @param modelObjectParameterGroup The group of model object parameters for the object to be saved.
     * @param databaseOperationGroup The group of database operations that can be performed on the model object.
     * @param sqlSession The sql session that will be used to perform the database operations.
     * @param <T> The type of model object the save is for.
     *
     * @return True if the save was performed, false if it was not
     *
     * @throws DatastoreSaveException An error occurred when trying to save the model object
     */
    protected <T extends ModelObjectAbs> boolean saveObject(final ModelObjectParameterGroup<T> modelObjectParameterGroup,
            final DatabaseOperationGroup<T> databaseOperationGroup, final SqlSession sqlSession) throws DatastoreSaveException {
        boolean result = commonDatabaseExecutor.saveObject(modelObjectParameterGroup, databaseOperationGroup, sqlSession);
        if (logger.isDebugEnabled()) {
            logger.debug("Result of {} when saving {}", result, modelObjectParameterGroup.modelObject());
        }
        return result;
    }
}