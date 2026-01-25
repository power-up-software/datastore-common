/*
 * Copyright (c) Power Up Software Engineering LLC 2026.
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