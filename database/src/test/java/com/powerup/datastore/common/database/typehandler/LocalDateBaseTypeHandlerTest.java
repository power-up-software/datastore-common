package com.powerup.datastore.common.database.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
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
class LocalDateBaseTypeHandlerTest {
    private static final LocalDate EXPECTED_DATE = LocalDate.now();
    private static final String COLUMN_NAME = "column";

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private LocalDateBaseTypeHandler instance;

    @Test
    public void testSetNonNullParameter() throws Exception {
        instance.setNonNullParameter(mockPreparedStatement, 1, EXPECTED_DATE, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setTimestamp(1, Timestamp.valueOf(EXPECTED_DATE.atTime(
                LocalTime.MIDNIGHT)));
    }

    @Test
    public void testGetNullableResult_String() throws Exception {
        when(mockResultSet.getTimestamp(COLUMN_NAME)).thenReturn(Timestamp.valueOf(EXPECTED_DATE.atTime(LocalTime.MIDNIGHT)));

        LocalDate result = instance.getNullableResult(mockResultSet, COLUMN_NAME);

        assertEquals(EXPECTED_DATE, result);
    }

    @Test
    public void testGetNullableResult_String_NullResult() throws Exception {
        when(mockResultSet.getTimestamp(COLUMN_NAME)).thenReturn(null);

        LocalDate result = instance.getNullableResult(mockResultSet, COLUMN_NAME);

        assertNull(result);
    }

    @Test
    public void testGetNullableResult_Int() throws Exception {
        int columnId = 1;
        when(mockResultSet.getTimestamp(columnId)).thenReturn(Timestamp.valueOf(EXPECTED_DATE.atTime(LocalTime.MIDNIGHT)));

        LocalDate result = instance.getNullableResult(mockResultSet, columnId);

        assertEquals(EXPECTED_DATE, result);
    }

    @Test
    public void testGetNullableResult_Int_NullResult() throws Exception {
        int columnId = 1;
        when(mockResultSet.getTimestamp(columnId)).thenReturn(null);

        LocalDate result = instance.getNullableResult(mockResultSet, columnId);

        assertNull(result);
    }

    @Test
    public void testGetNullableResult_Callable_Statement() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getTimestamp(columnId)).thenReturn(Timestamp.valueOf(EXPECTED_DATE.atTime(LocalTime.MIDNIGHT)));

        LocalDate result = instance.getNullableResult(mockCallableStatement, columnId);

        assertEquals(EXPECTED_DATE, result);
    }

    @Test
    public void testGetNullableResult_Callable_Statement_NullResult() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getTimestamp(columnId)).thenReturn(null);

        LocalDate result = instance.getNullableResult(mockCallableStatement, columnId);

        assertNull(result);
    }
}