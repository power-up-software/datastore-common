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

import com.powerupsoftwareengineering.value.verification.util.StringVerificationUtil;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>StringListTypeHandler</code> class defines a base type handler for a list of strings. This allows the list to be stored as a single
 * string value, rather than having to do foreign keys.
 *
 * @author Chris Picard
 */
//Mybatis uses this definition, but is referenced in xml so compiler does not realize it is used
@SuppressWarnings("unused")
public class IntegerListTypeHandler implements TypeHandler<List<Integer>> {
    private static final Logger logger = LoggerFactory.getLogger(IntegerListTypeHandler.class);

    /**
     * Default constructor.
     */
    public IntegerListTypeHandler() {
    }

    @Override
    public void setParameter(final PreparedStatement preparedStatement, final int parameterIndex, final List<Integer> parameter,
            final JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            preparedStatement.setString(parameterIndex, String.join(",", parameter.stream().map(String::valueOf).toArray(String[]::new)));
        } else {
            preparedStatement.setNull(parameterIndex, JdbcType.VARCHAR.TYPE_CODE);
        }
    }

    @Override
    public List<Integer> getResult(final ResultSet resultSet, final String columnName) throws SQLException {
        if (resultSet.getString(columnName) != null) {
            return convertStringToStringList(resultSet.getString(columnName));
        }
        return null;
    }

    @Override
    public List<Integer> getResult(final ResultSet resultSet, final int columnIndex) throws SQLException {
        if (resultSet.getString(columnIndex) != null) {
            return convertStringToStringList(resultSet.getString(columnIndex));
        }
        return null;
    }

    @Override
    public List<Integer> getResult(final CallableStatement callableStatement, final int columnIndex) throws SQLException {
        if (callableStatement.getString(columnIndex) != null) {
            return convertStringToStringList(callableStatement.getString(columnIndex));
        }
        return null;
    }

    private List<Integer> convertStringToStringList(final String string) {
        List<Integer> values = new ArrayList<>();
        if (StringVerificationUtil.isNotEmpty(string)) {
            for (String value : string.split(",")) {
                try {
                    values.add(Integer.parseInt(value));
                } catch (NumberFormatException ex) {
                    logger.error("Unable to parse integer value", ex);
                }
            }
        }
        return values;
    }
}