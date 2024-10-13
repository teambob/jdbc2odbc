package io.github.jdbc2odbc;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.lwjgl.odbc.SQL.*;

public class Connection implements java.sql.Connection {
    Long connHandle = null;
    Properties properties = new Properties();

    public Connection(long envHandle, String s, Properties properties) throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        short ret = SQLAllocHandle(SQL_HANDLE_DBC, envHandle, outHandle);

        if (ret != SQL_SUCCESS) {
            throw new SQLException("SQLAllocHandle(SQL_HANDLE_DBC,...) failed");
        }

        connHandle = outHandle.get();

        ret = SQLConnect(connHandle, "abc", "", "");
        if (ret != SQL_SUCCESS) {
            throw new SQLException("SQLConnect() failed");
        }

    }

    @Override
    public Statement createStatement() throws SQLException {
        return new Statement(connHandle);
    }

    @Override
    public PreparedStatement prepareStatement(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not supported");
    }

    @Override
    public CallableStatement prepareCall(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareCall() not supported");
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
        return new DatabaseMetaData(this, connHandle);
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
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        valuePtr.putInt(i);
        SQLSetConnectAttr(connHandle, SQL_ATTR_TXN_ISOLATION, valuePtr);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetConnectAttr(connHandle, SQL_ATTR_TXN_ISOLATION, valuePtr, strlenOut);
        return valuePtr.getInt();
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
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY){
            throw new SQLFeatureNotSupportedException("Only ResultSet.TYPE_FORWARD_ONLY is supported");
        }
        if (resultSetConcurrency != ResultSet.CONCUR_READ_ONLY){
            throw new SQLFeatureNotSupportedException("Only ResultSet.CONCUR_READ_ONLY is supported");
        }

        return createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not supported");
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareCall() not supported");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLFeatureNotSupportedException("getTypeMap() not supported");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException("setTypeMap() not supported");
    }

    @Override
    public void setHoldability(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("setHoldability() not supported");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException("getHoldability() not supported");
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
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLFeatureNotSupportedException("createStatement() not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareCall() not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new SQLFeatureNotSupportedException("createClob() not supported");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new SQLFeatureNotSupportedException("createBlob() not supported");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new SQLFeatureNotSupportedException("createNClob() not supported");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new SQLFeatureNotSupportedException("createSQLXML() not supported");
    }

    @Override
    public boolean isValid(int i) throws SQLException {
        // TODO: implement or throw exception
        return false;
    }

    @Override
    public void setClientInfo(String s, String s1) throws SQLClientInfoException {
        this.properties.setProperty(s, s1);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.properties = properties;
    }

    @Override
    public String getClientInfo(String s) throws SQLException {
        return (String)properties.get(s);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return properties;
    }

    @Override
    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        throw new SQLFeatureNotSupportedException("createArrayOf() not supported");
    }

    @Override
    public Struct createStruct(String s, Object[] objects) throws SQLException {
        throw new SQLFeatureNotSupportedException("createStruct() not supported");
    }

    @Override
    public void setSchema(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("setSchema() not supported");
    }

    @Override
    public String getSchema() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSchema() not supported");
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        // TODO: implement or throw exception
    }

    @Override
    public void setNetworkTimeout(Executor executor, int i) throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        valuePtr.putInt(i);
        SQLSetConnectAttr(connHandle, SQL_ATTR_CONNECTION_TIMEOUT, valuePtr);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetConnectAttr(connHandle, SQL_ATTR_CONNECTION_TIMEOUT, valuePtr, strlenOut);
        return valuePtr.getInt();
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap() not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor() not supported");
    }
}
