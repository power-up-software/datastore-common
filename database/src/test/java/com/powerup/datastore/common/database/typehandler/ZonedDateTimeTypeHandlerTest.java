package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZonedDateTime;
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
class ZonedDateTimeTypeHandlerTest {
    private static final ZonedDateTime EXPECTED_DATE_TIME = ZonedDateTime.now();
    private static final String COLUMN_NAME = "column";

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private ZonedDateTimeTypeHandler instance;

    @Test
    public void testSetParameter() throws Exception {
        instance.setParameter(mockPreparedStatement, 1, EXPECTED_DATE_TIME, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setString(1, EXPECTED_DATE_TIME.toString());
    }

    @Test
    public void testSetParameter_Null() throws Exception {
        instance.setParameter(mockPreparedStatement, 1, null, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setNull(1, JdbcType.VARCHAR.TYPE_CODE);
    }

    @Test
    public void testGetResult_String() throws Exception {
        when(mockResultSet.getString(COLUMN_NAME)).thenReturn(EXPECTED_DATE_TIME.toString());

        ZonedDateTime result = instance.getResult(mockResultSet, COLUMN_NAME);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetResult_String_NullResult() throws Exception {
        when(mockResultSet.getString(COLUMN_NAME)).thenReturn(null);

        ZonedDateTime result = instance.getResult(mockResultSet, COLUMN_NAME);

        assertNull(result);
    }

    @Test
    public void testGetResult_Int() throws Exception {
        int columnId = 1;
        when(mockResultSet.getString(columnId)).thenReturn(EXPECTED_DATE_TIME.toString());

        ZonedDateTime result = instance.getResult(mockResultSet, columnId);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetResult_Int_NullResult() throws Exception {
        int columnId = 1;
        when(mockResultSet.getString(columnId)).thenReturn(null);

        ZonedDateTime result = instance.getResult(mockResultSet, columnId);

        assertNull(result);
    }

    @Test
    public void testGetResult_Callable_Statement() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getString(columnId)).thenReturn(EXPECTED_DATE_TIME.toString());

        ZonedDateTime result = instance.getResult(mockCallableStatement, columnId);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetResult_Callable_Statement_NullResult() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getString(columnId)).thenReturn(null);

        ZonedDateTime result = instance.getResult(mockCallableStatement, columnId);

        assertNull(result);
    }
}