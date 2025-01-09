package com.powerup.datastore.common.database.service;

import com.powerup.java.immutable.model.ModelObjectAbs;
import com.powerup.lambda.functions.api.TwoParameterFunction;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@edu.umd.cs.findbugs.annotations.SuppressWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT", "SQL_INJECTION_JDBC"})
@ExtendWith(MockitoExtension.class)
class CommonExecutorTest {
    private static final String ROLE = "Role";
    @Mock
    private SqlSessionFactory mockSqlSessionFactory;
    @Mock
    private ModelObjectParameterGroup<ModelObjectAbs> mockModelObjectParameterGroup;
    @Mock
    private DatabaseOperationGroup<ModelObjectAbs> mockDatabaseOperationGroup;
    @Mock
    private TwoParameterFunction<UUID, SqlSession, ModelObjectAbs> mockTwoParameterFunction;
    @Mock
    private StoreFunction<ModelObjectAbs, SqlSession> mockStoreFunction;
    @Mock
    private UpdateFunction<ModelObjectAbs, SqlSession> mockUpdateFunction;
    @Mock
    private SqlSession mockSqlSession;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @InjectMocks
    private CommonDatabaseExecutor instance;

    @Test
    public void testOpenSqlSession_NominalCase() throws Exception {
        when(mockSqlSessionFactory.openSession()).thenReturn(mockSqlSession);
        when(mockSqlSession.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        SqlSession result = instance.getOpenSqlSession(ROLE);

        assertEquals(mockSqlSession, result);
        verify(mockStatement, times(1)).execute(CommonDatabaseExecutor.ROLE_DEFINITION_SQL + ROLE);
    }

    @Test
    public void testOpenSqlSession_SpecialCharactersInRole() throws Exception {
        String role = "Role$%2&*";
        when(mockSqlSessionFactory.openSession()).thenReturn(mockSqlSession);
        when(mockSqlSession.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        SqlSession result = instance.getOpenSqlSession(role);

        assertEquals(mockSqlSession, result);
        verify(mockStatement, times(1)).execute(CommonDatabaseExecutor.ROLE_DEFINITION_SQL + "Role2");
    }

    @Test
    public void testOpenSqlSession_NoRole() throws Exception {
        when(mockSqlSessionFactory.openSession()).thenReturn(mockSqlSession);
        when(mockSqlSession.getConnection()).thenReturn(mockConnection);

        SqlSession result = instance.getOpenSqlSession(null);

        assertEquals(mockSqlSession, result);
        verify(mockStatement, times(0)).execute(anyString());
    }

    @Test
    public void testOpenSqlSession_Error() throws Exception {
        when(mockSqlSessionFactory.openSession()).thenReturn(mockSqlSession);
        when(mockSqlSession.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenThrow(new SQLException("test"));

        assertThrows(SqlSessionException.class, () -> instance.getOpenSqlSession(ROLE));

        verify(mockSqlSession, times(1)).close();
    }

    @Test
    public void testSaveObject_UpdateCase() throws Exception {
        FakeModelObjectAbs.Builder builder = new FakeModelObjectAbs.Builder();
        builder.setId(UUID.randomUUID());
        FakeModelObjectAbs originalObject = builder.build();
        builder.setId(UUID.randomUUID());
        FakeModelObjectAbs newModelObject = builder.build();
        when(mockDatabaseOperationGroup.getRetrieveFunction()).thenReturn(mockTwoParameterFunction);
        when(mockDatabaseOperationGroup.getUpdateFunction()).thenReturn(mockUpdateFunction);
        when(mockTwoParameterFunction.apply(any(UUID.class), any(SqlSession.class))).thenReturn(originalObject);
        when(mockModelObjectParameterGroup.modelObject()).thenReturn(newModelObject);

        boolean result = instance.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession);

        assertTrue(result);
        verify(mockModelObjectParameterGroup, times(4)).modelObject();
        verify(mockUpdateFunction, times(1)).apply(newModelObject, originalObject, mockSqlSession);
    }

    @Test
    public void testSaveObject_SameObject() throws Exception {
        FakeModelObjectAbs.Builder builder = new FakeModelObjectAbs.Builder();
        builder.setId(UUID.randomUUID());
        FakeModelObjectAbs originalObject = builder.build();
        when(mockDatabaseOperationGroup.getRetrieveFunction()).thenReturn(mockTwoParameterFunction);
        when(mockTwoParameterFunction.apply(any(UUID.class), any(SqlSession.class))).thenReturn(originalObject);
        when(mockModelObjectParameterGroup.modelObject()).thenReturn(originalObject);

        boolean result = instance.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession);

        assertTrue(result);
        verify(mockModelObjectParameterGroup, times(3)).modelObject();
        verify(mockDatabaseOperationGroup, times(0)).getUpdateFunction();
    }

    @Test
    public void testSaveObject_StoreCase() throws Exception {
        FakeModelObjectAbs.Builder builder = new FakeModelObjectAbs.Builder();
        builder.setId(UUID.randomUUID());
        FakeModelObjectAbs newModelObject = builder.build();
        when(mockDatabaseOperationGroup.getRetrieveFunction()).thenReturn(mockTwoParameterFunction);
        when(mockDatabaseOperationGroup.getStoreFunction()).thenReturn(mockStoreFunction);
        when(mockTwoParameterFunction.apply(any(UUID.class), any(SqlSession.class))).thenReturn(null);
        when(mockModelObjectParameterGroup.modelObject()).thenReturn(newModelObject);

        boolean result = instance.saveObject(mockModelObjectParameterGroup, mockDatabaseOperationGroup, mockSqlSession);
        assertTrue(result);
        verify(mockModelObjectParameterGroup, times(3)).modelObject();
        verify(mockStoreFunction, times(1)).apply(newModelObject, mockSqlSession);
    }
}