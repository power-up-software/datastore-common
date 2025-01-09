package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * The <code>ZonedDateTimeTypeHandler</code> class defines a type handler for java ZonedDateTimes. Since Mybatis does not natively support the java
 * ZonedDateTime this class will allow for the time to be read and stored. his type handler does have to be referenced in the xml file but will allow
 * the duration value to be null unlike the base type handler.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class ZonedDateTimeTypeHandler implements TypeHandler<ZonedDateTime> {

    /**
     * Default constructor.
     */
    public ZonedDateTimeTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final ZonedDateTime parameter,
            final JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            preparedStatement.setString(parameterIndex, parameter.toString());
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.VARCHAR.TYPE_CODE);
        }
    }

    @Override
    public ZonedDateTime getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getString(columnName) != null) {
            return ZonedDateTime.parse(resultSet.getString(columnName));
        }
        return null;
    }

    @Override
    public ZonedDateTime getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getString(columnIndex) != null) {
            return ZonedDateTime.parse(resultSet.getString(columnIndex));
        }
        return null;
    }

    @Override
    public ZonedDateTime getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getString(columnIndex) != null) {
            return ZonedDateTime.parse(callableStatement.getString(columnIndex));
        }
        return null;
    }
}