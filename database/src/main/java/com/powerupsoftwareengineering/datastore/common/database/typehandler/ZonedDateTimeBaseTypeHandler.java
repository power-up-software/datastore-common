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

import com.powerupsoftwareengineering.value.verification.util.StringVerificationUtil;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * The <code>ZonedDateTimeBaseTypeHandler</code> class defines a base type handler for java ZonedDateTimes. Since Mybatis does not natively support
 * the java ZonedDateTime this class will allow for the time to be read and stored. Since it is a base type handler, it does not have to be referenced
 * in the Mybatis XML file.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class ZonedDateTimeBaseTypeHandler extends BaseTypeHandler<ZonedDateTime> {

    /**
     * Default constructor.
     */
    public ZonedDateTimeBaseTypeHandler() {
    }

    @Override
    public void setNonNullParameter(final PreparedStatement preparedStatement, final int parameterIndex, final ZonedDateTime parameter,
            final JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(parameterIndex, parameter.toString());
    }

    @Override
    public ZonedDateTime getNullableResult(final ResultSet resultSet, final String columnName) throws SQLException {
        return parseDateTime(resultSet.getString(columnName));
    }

    @Override
    public ZonedDateTime getNullableResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        return parseDateTime(resultSet.getString(columnIndex));
    }

    @Override
    public ZonedDateTime getNullableResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        return parseDateTime(callableStatement.getString(columnIndex));
    }

    private ZonedDateTime parseDateTime(final String result) {
        if (StringVerificationUtil.isNotEmpty(result)) {
            return ZonedDateTime.parse(result);
        }
        return null;
    }
}