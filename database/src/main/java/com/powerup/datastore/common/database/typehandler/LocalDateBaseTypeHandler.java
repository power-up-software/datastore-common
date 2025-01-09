package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * The <code>LocalDateBaseTypeHandler</code> class defines a base type handler for java LocalDates. Since Mybatis does not natively support
 * the java LocalDate this class will allow for the time to be read and stored. Since it is a base type handler, it does not have to be referenced
 * in the Mybatis XML file.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class LocalDateBaseTypeHandler extends BaseTypeHandler<LocalDate> {

    /**
     * Default constructor.
     */
    public LocalDateBaseTypeHandler() {
    }

    @Override
    public void setNonNullParameter(final PreparedStatement preparedStatement, final int parameterIndex, final LocalDate parameter,
            final JdbcType jdbcType) throws SQLException {
        preparedStatement.setTimestamp(parameterIndex, Timestamp.valueOf(parameter.atTime(LocalTime.MIDNIGHT)));
    }

    @Override
    public LocalDate getNullableResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getTimestamp(columnName) != null) {
            return resultSet.getTimestamp(columnName).toLocalDateTime().toLocalDate();
        }
        return null;
    }

    @Override
    public LocalDate getNullableResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getTimestamp(columnIndex) != null) {
            return resultSet.getTimestamp(columnIndex).toLocalDateTime().toLocalDate();
        }
        return null;
    }

    @Override
    public LocalDate getNullableResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getTimestamp(columnIndex) != null) {
            return callableStatement.getTimestamp(columnIndex).toLocalDateTime().toLocalDate();
        }
        return null;
    }
}