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
class ZonedDateTimeBaseTypeHandlerTest {
    private static final ZonedDateTime EXPECTED_DATE_TIME = ZonedDateTime.now();
    private static final String COLUMN_NAME = "column";

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private ZonedDateTimeBaseTypeHandler instance;

    @Test
    public void testSetNonNullParameter() throws Exception {
        instance.setNonNullParameter(mockPreparedStatement, 1, EXPECTED_DATE_TIME, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setString(1, EXPECTED_DATE_TIME.toString());
    }

    @Test
    public void testGetNullableResult_String() throws Exception {
        when(mockResultSet.getString(COLUMN_NAME)).thenReturn(EXPECTED_DATE_TIME.toString());

        ZonedDateTime result = instance.getNullableResult(mockResultSet, COLUMN_NAME);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetNullableResult_String_NullResult() throws Exception {
        when(mockResultSet.getString(COLUMN_NAME)).thenReturn(null);

        ZonedDateTime result = instance.getNullableResult(mockResultSet, COLUMN_NAME);

        assertNull(result);
    }

    @Test
    public void testGetNullableResult_Int() throws Exception {
        int columnId = 1;
        when(mockResultSet.getString(columnId)).thenReturn(EXPECTED_DATE_TIME.toString());

        ZonedDateTime result = instance.getNullableResult(mockResultSet, columnId);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetNullableResult_Int_NullResult() throws Exception {
        int columnId = 1;
        when(mockResultSet.getString(columnId)).thenReturn(null);

        ZonedDateTime result = instance.getNullableResult(mockResultSet, columnId);

        assertNull(result);
    }

    @Test
    public void testGetNullableResult_Callable_Statement() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getString(columnId)).thenReturn(EXPECTED_DATE_TIME.toString());

        ZonedDateTime result = instance.getNullableResult(mockCallableStatement, columnId);

        assertEquals(EXPECTED_DATE_TIME, result);
    }

    @Test
    public void testGetNullableResult_Callable_Statement_NullResult() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getString(columnId)).thenReturn(null);

        ZonedDateTime result = instance.getNullableResult(mockCallableStatement, columnId);

        assertNull(result);
    }
}