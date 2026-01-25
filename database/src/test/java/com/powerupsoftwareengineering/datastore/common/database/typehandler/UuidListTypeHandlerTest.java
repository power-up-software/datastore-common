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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
class UuidListTypeHandlerTest {
    private static final List<UUID> EXPECTED_LIST = new ArrayList<>();
    private static final String EXPECTED_STRING;
    private static final String COLUMN_NAME = "column";

    static {
        EXPECTED_LIST.add(UUID.randomUUID());
        EXPECTED_LIST.add(UUID.randomUUID());
        EXPECTED_STRING = EXPECTED_LIST.get(0).toString() + ',' + EXPECTED_LIST.get(1).toString();
    }

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private UuidListTypeHandler instance;

    @Test
    public void testSetParameter() throws Exception {
        instance.setParameter(mockPreparedStatement, 1, EXPECTED_LIST, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setString(1, EXPECTED_STRING);
    }

    @Test
    public void testSetParameter_Null() throws Exception {
        instance.setParameter(mockPreparedStatement, 1, null, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setNull(1, JdbcType.VARCHAR.TYPE_CODE);
    }

    @Test
    public void testGetResult_String() throws Exception {
        when(mockResultSet.getString(COLUMN_NAME)).thenReturn(EXPECTED_STRING);

        List<UUID> result = instance.getResult(mockResultSet, COLUMN_NAME);

        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    public void testGetResult_String_NullResult() throws Exception {
        when(mockResultSet.getString(COLUMN_NAME)).thenReturn(null);

        List<UUID> result = instance.getResult(mockResultSet, COLUMN_NAME);

        assertNull(result);
    }

    @Test
    public void testGetResult_Int() throws Exception {
        int columnId = 1;
        when(mockResultSet.getString(columnId)).thenReturn(EXPECTED_STRING);

        List<UUID> result = instance.getResult(mockResultSet, columnId);

        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    public void testGetResult_Int_NullResult() throws Exception {
        int columnId = 1;
        when(mockResultSet.getString(columnId)).thenReturn(null);

        List<UUID> result = instance.getResult(mockResultSet, columnId);

        assertNull(result);
    }

    @Test
    public void testGetResult_Callable_Statement() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getString(columnId)).thenReturn(EXPECTED_STRING);

        List<UUID> result = instance.getResult(mockCallableStatement, columnId);

        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    public void testGetResult_Callable_Statement_NullResult() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getString(columnId)).thenReturn(null);

        List<UUID> result = instance.getResult(mockCallableStatement, columnId);

        assertNull(result);
    }
}