package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * The <code>UuidBaseTypeHandler</code> class defines a base type handler for java UUIDs. Since Mybatis does not natively support UUID this class will
 * allow for UUID to be read and stored. Since it is a base type handler, it does not have to be referenced in the Mybatis XML file.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class UuidBaseTypeHandler extends BaseTypeHandler<UUID> {

    /**
     * Default constructor.
     */
    public UuidBaseTypeHandler() {
    }

    @Override
    public void setNonNullParameter(final PreparedStatement preparedStatement, final int parameterIndex, final UUID parameter,
            final JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(parameterIndex, parameter.toString());
    }

    @Override
    public UUID getNullableResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getString(columnName) != null) {
            return UUID.fromString(resultSet.getString(columnName));
        }
        return null;
    }

    @Override
    public UUID getNullableResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getString(columnIndex) != null) {
            return UUID.fromString(resultSet.getString(columnIndex));
        }
        return null;
    }

    @Override
    public UUID getNullableResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getString(columnIndex) != null) {
            return UUID.fromString(callableStatement.getString(columnIndex));
        }
        return null;
    }
}