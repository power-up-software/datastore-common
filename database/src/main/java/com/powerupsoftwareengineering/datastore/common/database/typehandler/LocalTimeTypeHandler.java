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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * The <code>LocalTimeTypeHandler</code> class defines a type handler for java LocalTimes. Since Mybatis does not natively support the java
 * LocalTime this class will allow for the time to be read and stored. This type handler does have to be referenced in the xml file but will allow
 * the duration value to be null unlike the base type handler.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class LocalTimeTypeHandler implements TypeHandler<LocalTime> {

    /**
     * Default constructor.
     */
    public LocalTimeTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final LocalTime parameter,
            final JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            preparedStatement.setTime(parameterIndex, Time.valueOf(parameter));
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.TIME.TYPE_CODE);
        }
    }

    @Override
    public LocalTime getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getTime(columnName) != null) {
            return resultSet.getTime(columnName).toLocalTime();
        }
        return null;
    }

    @Override
    public LocalTime getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getTime(columnIndex) != null) {
            return resultSet.getTime(columnIndex).toLocalTime();
        }
        return null;
    }

    @Override
    public LocalTime getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getTime(columnIndex) != null) {
            return callableStatement.getTime(columnIndex).toLocalTime();
        }
        return null;
    }
}