package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isNotEmpty(result)) {
            return ZonedDateTime.parse(result);
        }
        return null;
    }
}