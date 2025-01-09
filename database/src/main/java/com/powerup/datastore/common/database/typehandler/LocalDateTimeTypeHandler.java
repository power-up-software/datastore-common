package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * The <code>LocalDateTimeTypeHandler</code> class defines a type handler for java LocalDateTimes. Since Mybatis does not natively support the java
 * LocalDateTime this class will allow for the time to be read and stored. This type handler does have to be referenced in the xml file but will allow
 * the duration value to be null unlike the base type handler.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {

    /**
     * Default constructor.
     */
    public LocalDateTimeTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final LocalDateTime parameter,
            final JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            preparedStatement.setTimestamp(parameterIndex, Timestamp.valueOf(parameter));
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.TIMESTAMP.TYPE_CODE);
        }
    }

    @Override
    public LocalDateTime getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getTimestamp(columnName) != null) {
            return resultSet.getTimestamp(columnName).toLocalDateTime();
        }
        return null;
    }

    @Override
    public LocalDateTime getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getTimestamp(columnIndex) != null) {
            return resultSet.getTimestamp(columnIndex).toLocalDateTime();
        }
        return null;
    }

    @Override
    public LocalDateTime getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getTimestamp(columnIndex) != null) {
            return callableStatement.getTimestamp(columnIndex).toLocalDateTime();
        }
        return null;
    }
}