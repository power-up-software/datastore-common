package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * The <code>LocalDateTypeHandler</code> class defines a type handler for java LocalDates. Since Mybatis does not natively support the java
 * LocalDate this class will allow for the time to be read and stored. This type handler does have to be referenced in the xml file but will allow
 * the duration value to be null unlike the base type handler.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class LocalDateTypeHandler implements TypeHandler<LocalDate> {

    /**
     * Default constructor.
     */
    public LocalDateTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final LocalDate parameter,
            final JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            preparedStatement.setTimestamp(parameterIndex, Timestamp.valueOf(parameter.atTime(LocalTime.MIDNIGHT)));
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.TIMESTAMP.TYPE_CODE);
        }
    }

    @Override
    public LocalDate getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getTimestamp(columnName) != null) {
            return resultSet.getTimestamp(columnName).toLocalDateTime().toLocalDate();
        }
        return null;
    }

    @Override
    public LocalDate getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getTimestamp(columnIndex) != null) {
            return resultSet.getTimestamp(columnIndex).toLocalDateTime().toLocalDate();
        }
        return null;
    }

    @Override
    public LocalDate getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getTimestamp(columnIndex) != null) {
            return callableStatement.getTimestamp(columnIndex).toLocalDateTime().toLocalDate();
        }
        return null;
    }
}