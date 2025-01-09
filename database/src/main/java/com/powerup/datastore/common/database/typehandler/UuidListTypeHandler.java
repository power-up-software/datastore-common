package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * The <code>UuidListTypeHandler</code> class defines a base type handler for java UUIDs. This allows UUIDs to be stored as a single string value,
 * rather than having to do foreign keys.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class UuidListTypeHandler implements TypeHandler<List<UUID>> {

    /**
     * Default constructor.
     */
    public UuidListTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final List<UUID> parameter, final JdbcType jdbcType)
            throws SQLException {
        if (parameter != null) {
            preparedStatement.setString(parameterIndex, StringUtils.join(parameter, ','));
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.VARCHAR.TYPE_CODE);
        }
    }

    @Override
    public List<UUID> getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getString(columnName) != null) {
            return convertStringToUuidList(resultSet.getString(columnName));
        }
        return null;
    }

    @Override
    public List<UUID> getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getString(columnIndex) != null) {
            return convertStringToUuidList(resultSet.getString(columnIndex));
        }
        return null;
    }

    @Override
    public List<UUID> getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getString(columnIndex) != null) {
            return convertStringToUuidList(callableStatement.getString(columnIndex));
        }
        return null;
    }

    private List<UUID> convertStringToUuidList(final String string) {
        List<UUID> result = new ArrayList<>();
        if (StringUtils.isNoneBlank(string)) {
            for (String uuidAsString : string.split(",")) {
                result.add(UUID.fromString(uuidAsString));
            }
        }
        return result;
    }
}