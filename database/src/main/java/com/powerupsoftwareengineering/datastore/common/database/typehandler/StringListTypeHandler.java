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
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * The <code>StringListTypeHandler</code> class defines a base type handler for a list of strings. This allows the list to be stored as a single
 * string value, rather than having to do foreign keys.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class StringListTypeHandler implements TypeHandler<List<String>> {
    private static final String COMMA_ESCAPE = "%;";
    private static final String COMMA = ",";

    /**
     * Default constructor.
     */
    public StringListTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final List<String> parameter,
            final JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            StringBuilder contentBuilder = new StringBuilder();
            for (String item : parameter) {
                if (contentBuilder.isEmpty()) {
                    contentBuilder.append(item.replace(COMMA, COMMA_ESCAPE));
                } else {
                    contentBuilder.append(COMMA);
                    contentBuilder.append(item.replace(COMMA, COMMA_ESCAPE));
                }
            }
            preparedStatement.setString(parameterIndex, contentBuilder.toString());
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.VARCHAR.TYPE_CODE);
        }
    }

    @Override
    public List<String> getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getString(columnName) != null) {
            return convertStringToStringList(resultSet.getString(columnName));
        }
        return null;
    }

    @Override
    public List<String> getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getString(columnIndex) != null) {
            return convertStringToStringList(resultSet.getString(columnIndex));
        }
        return null;
    }

    @Override
    public List<String> getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getString(columnIndex) != null) {
            return convertStringToStringList(callableStatement.getString(columnIndex));
        }
        return null;
    }

    private List<String> convertStringToStringList(final String string) {
        if (StringVerificationUtil.isNotEmpty(string)) {
            List<String> items = new ArrayList<>();
            for (String item : string.split(COMMA)) {
                items.add(item.replace(COMMA_ESCAPE, COMMA));
            }
            return items;
        } else {
            return new ArrayList<>();
        }
    }
}