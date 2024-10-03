package io.github.jdbc2odbc;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.lwjgl.odbc.SQL.*;

public class Connection implements java.sql.Connection {
    Long connHandle = null;
    public Connection(long envHandle, String s, Properties properties) {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        SQLAllocHandle(SQL_HANDLE_DBC, envHandle, outHandle);

        connHandle = outHandle.get();

        SQLConnect(connHandle, s, "", "");
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new Statement(connHandle);
    }

    @Override
    public PreparedStatement prepareStatement(String s) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public CallableStatement prepareCall(String s) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public String nativeSQL(String s) throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(1024); // TODO: dynamic sizing
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLNativeSql(connHandle, StandardCharsets.UTF_16LE.encode(s), valueOut, strlenOut);
        valueOut.limit(strlenOut.get());
        return StandardCharsets.UTF_16LE.decode(valueOut).toString();
    }

    @Override
    public void setAutoCommit(boolean b) throws SQLException {
        nSQLSetConnectAttr(connHandle, SQL_ATTR_AUTOCOMMIT, b ? SQL_AUTOCOMMIT_ON : SQL_AUTOCOMMIT_OFF, 0);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);

        SQLGetConnectAttr(connHandle, SQL_ATTR_AUTOCOMMIT, valueOut, strlenOut);
        return (valueOut.get() == SQL_AUTOCOMMIT_ON);
    }

    @Override
    public void commit() throws SQLException {
        SQLEndTran(SQL_HANDLE_DBC, connHandle, SQL_COMMIT);
    }

    @Override
    public void rollback() throws SQLException {
        SQLEndTran(SQL_HANDLE_DBC, connHandle, SQL_ROLLBACK);
    }

    @Override
    public void close() throws SQLException {
        SQLFreeHandle(SQL_HANDLE_DBC, connHandle);
    }

    @Override
    public boolean isClosed() throws SQLException {
        // TODO: implement or throw exception
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return new DatabaseMetaData(connHandle);
    }

    @Override
    public void setReadOnly(boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException("setReadOnly() not supported");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String s) throws SQLException {
        SQLSetConnectAttr(connHandle, SQL_ATTR_CURRENT_CATALOG, StandardCharsets.UTF_16LE.encode(s));
    }

    @Override
    public String getCatalog() throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetConnectAttr(connHandle, SQL_ATTR_CURRENT_CATALOG, valueOut, strlenOut);
        return StandardCharsets.UTF_16LE.decode(valueOut).toString();
    }

    @Override
    public void setTransactionIsolation(int i) throws SQLException {
        // TODO: implement or throw exception
        //SQLSetConnectAttr(connHandle, SQL_ATTR_TXN_ISOLATION, )
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        // TODO: implement or throw exception
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
        // TODO: implement or throw exception

    }

    @Override
    public Statement createStatement(int i, int i1) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        // TODO: implement or throw exception
        return Map.of();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        // TODO: implement or throw exception

    }

    @Override
    public void setHoldability(int i) throws SQLException {
        // TODO: implement or throw exception
    }

    @Override
    public int getHoldability() throws SQLException {
        // TODO: implement or throw exception
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLFeatureNotSupportedException("setSavepoint() not supported");
    }

    @Override
    public Savepoint setSavepoint(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("setSavepoint() not supported");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException("rollback - savepoints not supported");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException("releaseSavepoint() not supported");
    }

    @Override
    public Statement createStatement(int i, int i1, int i2) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public boolean isValid(int i) throws SQLException {
        // TODO: implement or throw exception
        return false;
    }

    @Override
    public void setClientInfo(String s, String s1) throws SQLClientInfoException {
        // TODO: implement or throw exception
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        // TODO: implement or throw exception
    }

    @Override
    public String getClientInfo(String s) throws SQLException {
        // TODO: implement or throw exception
        return "";
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public Struct createStruct(String s, Object[] objects) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public void setSchema(String s) throws SQLException {
        // TODO: implement or throw exception
    }

    @Override
    public String getSchema() throws SQLException {
        // TODO: implement or throw exception
        return "";
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        // TODO: implement or throw exception
    }

    @Override
    public void setNetworkTimeout(Executor executor, int i) throws SQLException {
        // TODO: implement or throw exception
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        // TODO: implement or throw exception
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        // TODO: implement or throw exception
        return false;
    }
}
