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
class LocalDateTypeHandlerTest {
    private static final LocalDate EXPECTED_DATE = LocalDate.now();
    private static final String COLUMN_NAME = "column";

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @InjectMocks
    private LocalDateTypeHandler instance;

    @Test
    public void testSetParameter() throws Exception {
        instance.setParameter(mockPreparedStatement, 1, EXPECTED_DATE, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setTimestamp(1, Timestamp.valueOf(EXPECTED_DATE
                .atTime(LocalTime.MIDNIGHT)));
    }

    @Test
    public void testSetParameter_Null() throws Exception {
        instance.setParameter(mockPreparedStatement, 1, null, JdbcType.VARCHAR);

        verify(mockPreparedStatement, times(1)).setNull(1, JdbcType.TIMESTAMP.TYPE_CODE);

    }

    @Test
    public void testGetResult_String() throws Exception {
        when(mockResultSet.getTimestamp(COLUMN_NAME)).thenReturn(Timestamp.valueOf(EXPECTED_DATE.atTime(LocalTime.MIDNIGHT)));

        LocalDate result = instance.getResult(mockResultSet, COLUMN_NAME);

        assertEquals(EXPECTED_DATE, result);
    }

    @Test
    public void testGetResult_String_NullResult() throws Exception {
        when(mockResultSet.getTimestamp(COLUMN_NAME)).thenReturn(null);

        LocalDate result = instance.getResult(mockResultSet, COLUMN_NAME);

        assertNull(result);
    }

    @Test
    public void testGetResult_Int() throws Exception {
        int columnId = 1;
        when(mockResultSet.getTimestamp(columnId)).thenReturn(Timestamp.valueOf(EXPECTED_DATE.atTime(LocalTime.MIDNIGHT)));

        LocalDate result = instance.getResult(mockResultSet, columnId);

        assertEquals(EXPECTED_DATE, result);
    }

    @Test
    public void testGetResult_Int_NullResult() throws Exception {
        int columnId = 1;
        when(mockResultSet.getTimestamp(columnId)).thenReturn(null);

        LocalDate result = instance.getResult(mockResultSet, columnId);

        assertNull(result);
    }

    @Test
    public void testGetResult_Callable_Statement() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getTimestamp(columnId)).thenReturn(Timestamp.valueOf(EXPECTED_DATE.atTime(LocalTime.MIDNIGHT)));

        LocalDate result = instance.getResult(mockCallableStatement, columnId);

        assertEquals(EXPECTED_DATE, result);
    }

    @Test
    public void testGetResult_Callable_Statement_NullResult() throws Exception {
        int columnId = 1;
        when(mockCallableStatement.getTimestamp(columnId)).thenReturn(null);

        LocalDate result = instance.getResult(mockCallableStatement, columnId);

        assertNull(result);
    }
}