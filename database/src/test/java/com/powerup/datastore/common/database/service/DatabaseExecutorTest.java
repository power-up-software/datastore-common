package com.powerup.datastore.common.database.service;

import com.powerup.datastore.common.api.exception.DatastoreSaveException;
import com.powerup.java.immutable.model.ModelObjectAbs;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseExecutorTest {
    @Mock
    private ModelObjectParameterGroup<ModelObjectAbs> mockModelObjectParameterGroup;
    @Mock
    private DatabaseOperationGroup<ModelObjectAbs> mockDatabaseOperationGroup;
    @Mock
    private SqlSession mockSqlSession;
    @Mock
    private Logger mockLogger;
    @Mock
    private CommonDatabaseExecutor mockCommonDatabaseExecutor;
    @InjectMocks
    private FakeDatabaseExecutor instance;

    @Test
    public void testSaveObject_NominalCase() throws Exception {
        when(mockCommonDatabaseExecutor.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession)).thenReturn(true);
        when(mockLogger.isDebugEnabled()).thenReturn(true);
        boolean result = instance.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession);
        assertTrue(result);
        verify(mockLogger, times(1)).debug("Result of {} when saving {}", true, null);
    }

    @Test
    public void testSaveObject_SaveError() throws Exception {
        when(mockCommonDatabaseExecutor.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession))
                .thenThrow(new DatastoreSaveException("test"));

        assertThrows(DatastoreSaveException.class,
                () -> instance.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession));
    }
}