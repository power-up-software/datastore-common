/*
 * Copyright (c) Power Up Software Engineering LLC 2025.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.powerupsoftwareengineering.datastore.common.database.typehandler;

import com.powerup.value.verification.util.StringVerificationUtil;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
            preparedStatement.setString(parameterIndex, String.join(",", parameter.stream().map(String::valueOf).toArray(String[]::new)));
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
        if (StringVerificationUtil.isNotEmpty(string)) {
            for (String uuidAsString : string.split(",")) {
                result.add(UUID.fromString(uuidAsString));
            }
        }
        return result;
    }
}