package com.powerup.datastore.common.database.service;

import com.powerup.datastore.common.api.exception.DatastoreSaveException;
import com.powerup.java.immutable.model.ModelObjectAbs;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The <code>CommonDatabaseExecutor</code> class defines the common behavior used by all database services.
 *
 * @author Chris Picard
 */
@SuppressWarnings("unused")
@Component("CommonDatabaseExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommonDatabaseExecutor {
    /**
     * String that defines the common format of save error messages.
     */
    public static final String FAILED_TO_SAVE_ERROR_MESSAGE_FORMAT = "Failed to save %s with id %s";
    /**
     * String that defines the common format of update error messages.
     */
    public static final String FAILED_TO_UPDATE_ERROR_MESSAGE_FORMAT = "Failed to update %s with id %s";
    /**
     * String that defines the common format of delete error messages.
     */
    public static final String FAILED_TO_DELETE_ERROR_MESSAGE_FORMAT = "Failed to delete %s with id %s";
    /**
     * SQL command that will set the role of a database call.
     */
    public static final String ROLE_DEFINITION_SQL = "SET ROLE ";
    private final SqlSessionFactory sqlSessionFactory;

    /**
     * Base constructor.
     *
     * @param sqlSessionFactory SQL session generation factory for the executor to use.
     */
    @Autowired
    public CommonDatabaseExecutor(final SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Utility method that will establish an SQL session with the database.
     *
     * @param role The role that the user should have in the database connection
     *
     * @return The open session
     *
     * @throws SqlSessionException Unable to create the sql session
     */
    public SqlSession getOpenSqlSession(final String role) throws SqlSessionException {
        SqlSession sqlSession = null;
        boolean validSession = false;
        try {
            sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            if (StringUtils.isNotEmpty(role)) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(ROLE_DEFINITION_SQL + role.replaceAll("[^a-zA-Z\\d]", ""));
                }
            }
            validSession = true;
            return sqlSession;
        } catch (SQLException e) {
            throw new SqlSessionException(e);
        } finally {
            if (!validSession && sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    /**
     * Utility method that will save an object to the database. It will determine if it needs to be inserted or if the value should be updated.
     *
     * @param modelObjectParameterGroup The group of model object parameters for the object to be saved
     * @param databaseOperationGroup The group of database operations that can be performed on the model object
     * @param sqlSession The sql session that will be used to perform the database operations
     * @param <T> The type of model object the save is for
     *
     * @return True if the save was performed, false if it was not
     *
     * @throws DatastoreSaveException An error occurred when trying to save the model object
     */
    @SuppressWarnings("SameReturnValue")
    public <T extends ModelObjectAbs> boolean saveObject(final ModelObjectParameterGroup<T> modelObjectParameterGroup,
            final DatabaseOperationGroup<T> databaseOperationGroup, final SqlSession sqlSession) throws DatastoreSaveException {
        boolean result = true;
        if (modelObjectParameterGroup.modelObject() == null) {
            result = false;
        } else {
            T existingModelObject = databaseOperationGroup.getRetrieveFunction().apply(modelObjectParameterGroup.modelObject().getId(),
                    sqlSession);
            if (existingModelObject == null) {
                databaseOperationGroup.getStoreFunction().apply(modelObjectParameterGroup.modelObject(), sqlSession);
            } else if (!modelObjectParameterGroup.modelObject().equals(existingModelObject)) {
                T currentModelObject = modelObjectParameterGroup.modelObject();
                databaseOperationGroup.getUpdateFunction().apply(currentModelObject, existingModelObject, sqlSession);
            }
        }
        return result;
    }
}