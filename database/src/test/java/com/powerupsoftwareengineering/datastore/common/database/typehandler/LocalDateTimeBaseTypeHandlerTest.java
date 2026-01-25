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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalDateTimeBaseTypeHandlerTest {
    private static final LocalDateTime EXPECTED_DATE_TIME = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private static final String COLUMN_NAME = "column";

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private LocalDateTimeBaseTypeHandler instance;

    @Test
    public void testSetNonNullParameter() throws Exception {
        instance.setNonNullParameter(mockPreparedStatement, 1, EXPECTED_DATE_TIME, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setTimestamp(1, Timestamp.valueOf(EXPECTED_DATE_TIME));
    }

    @Test
    public void testGetNullableResult_String() throws Exception {
        when(mockResultSet.getTimestamp(COLUMN_NAME)).thenReturn(Timestamp.valueOf(EXPECTED_DATE_TIME));

        LocalDateTime result = instance.getNullableResult(mockResultSet, COLUMN_NAME);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetNullableResult_String_NullResult() throws Exception {
        when(mockResultSet.getTimestamp(COLUMN_NAME)).thenReturn(null);

        LocalDateTime result = instance.getNullableResult(mockResultSet, COLUMN_NAME);

        assertNull(result);
    }

    @Test
    public void testGetNullableResult_Int() throws Exception {
        int columnId = 1;
        when(mockResultSet.getTimestamp(columnId)).thenReturn(Timestamp.valueOf(EXPECTED_DATE_TIME));

        LocalDateTime result = instance.getNullableResult(mockResultSet, columnId);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetNullableResult_Int_NullResult() throws Exception {
        int columnId = 1;
        when(mockResultSet.getTimestamp(columnId)).thenReturn(null);

        LocalDateTime result = instance.getNullableResult(mockResultSet, columnId);

        assertNull(result);
    }

    @Test
    public void testGetNullableResult_Callable_Statement() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getTimestamp(columnId)).thenReturn(Timestamp.valueOf(EXPECTED_DATE_TIME));

        LocalDateTime result = instance.getNullableResult(mockCallableStatement, columnId);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetNullableResult_Callable_Statement_NullResult() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getTimestamp(columnId)).thenReturn(null);

        LocalDateTime result = instance.getNullableResult(mockCallableStatement, columnId);

        assertNull(result);
    }
}