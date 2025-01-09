package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * The <code>DurationBaseTypeHandler</code> class defines a base type handler for java Duration. Since Mybatis does not natively support the java
 * Duration this class will allow for the duration to be read and stored. Since it is a base type handler, it does not have to be referenced in the
 * Mybatis XML file.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class DurationBaseTypeHandler extends BaseTypeHandler<Duration> {

    /**
     * Default constructor.
     */
    public DurationBaseTypeHandler() {
    }

    @Override
    public void setNonNullParameter(final PreparedStatement preparedStatement, final int parameterIndex, final Duration parameter,
            final JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(parameterIndex, parameter.toString());
    }

    @Override
    public Duration getNullableResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getString(columnName) != null) {
            return Duration.parse(resultSet.getString(columnName));
        }
        return null;
    }

    @Override
    public Duration getNullableResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getString(columnIndex) != null) {
            return Duration.parse(resultSet.getString(columnIndex));
        }
        return null;
    }

    @Override
    public Duration getNullableResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getString(columnIndex) != null) {
            return Duration.parse(callableStatement.getString(columnIndex));
        }
        return null;
    }
}