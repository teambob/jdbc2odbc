package io.github.jdbc2odbc;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;

import com.jcabi.aspects.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.lwjgl.odbc.SQL.*;

public class Connection implements java.sql.Connection {
    private static Logger LOGGER = LoggerFactory.getLogger(Connection.class);
    Long connHandle = null;
    Properties properties = new Properties();

    public Connection(long envHandle, String s, Properties properties) throws SQLException {
        PointerBuffer outHandle = BufferUtils.createPointerBuffer(8);
        short ret = SQLAllocHandle(SQL_HANDLE_DBC, envHandle, outHandle);

        if (ret != SQL_SUCCESS) {
            throw new SQLException("SQLAllocHandle(SQL_HANDLE_DBC,...) failed");
        }

        connHandle = outHandle.get();

        URI uri = URI.create(s.substring("jdbc:".length()));
        String user = properties.getProperty("user", "");
        String password = properties.getProperty("password", "");

        if (uri.getUserInfo() != null) {
            String[] list = uri.getUserInfo().split(":");
            user = list[0];
            if (list.length > 1) {
                password = list[1];
            }
        }

        if (properties.containsKey("LOG_LEVEL")){
            System.setProperty("org.slf4j.simpleLogger.log.io.github.jdbc2odbc", properties.getProperty("LOG_LEVEL"));
        }

        LOGGER.debug("uri.host={}", uri.getHost());
        ret = SQLConnect(connHandle, uri.getHost(), user, password);
        if (ret != SQL_SUCCESS) {
            throw new SQLException("SQLConnect() failed");
        }

    }

    @Override
    @Loggable(Loggable.TRACE)
    public Statement createStatement() throws SQLException {
        return new Statement(connHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public PreparedStatement prepareStatement(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public CallableStatement prepareCall(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareCall() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String nativeSQL(String s) throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(1024); // TODO: dynamic sizing
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLNativeSql(connHandle, StandardCharsets.UTF_16LE.encode(s), valueOut, strlenOut);
        valueOut.limit(strlenOut.get());
        return StandardCharsets.UTF_16LE.decode(valueOut).toString();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setAutoCommit(boolean b) throws SQLException {
        nSQLSetConnectAttr(connHandle, SQL_ATTR_AUTOCOMMIT, b ? SQL_AUTOCOMMIT_ON : SQL_AUTOCOMMIT_OFF, 0);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean getAutoCommit() throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);

        SQLGetConnectAttr(connHandle, SQL_ATTR_AUTOCOMMIT, valueOut, strlenOut);
        return (valueOut.get() == SQL_AUTOCOMMIT_ON);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void commit() throws SQLException {
        SQLEndTran(SQL_HANDLE_DBC, connHandle, SQL_COMMIT);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void rollback() throws SQLException {
        SQLEndTran(SQL_HANDLE_DBC, connHandle, SQL_ROLLBACK);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void close() throws SQLException {
        SQLFreeHandle(SQL_HANDLE_DBC, connHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isClosed() throws SQLException {
        // TODO: implement or throw exception
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public DatabaseMetaData getMetaData() throws SQLException {
        return new DatabaseMetaData(this, connHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setReadOnly(boolean b) throws SQLException {
        throw new SQLFeatureNotSupportedException("setReadOnly() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setCatalog(String s) throws SQLException {
        SQLSetConnectAttr(connHandle, SQL_ATTR_CURRENT_CATALOG, StandardCharsets.UTF_16LE.encode(s));
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getCatalog() throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetConnectAttr(connHandle, SQL_ATTR_CURRENT_CATALOG, valueOut, strlenOut);
        return StandardCharsets.UTF_16LE.decode(valueOut).toString();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setTransactionIsolation(int i) throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        valuePtr.putInt(i);
        SQLSetConnectAttr(connHandle, SQL_ATTR_TXN_ISOLATION, valuePtr);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getTransactionIsolation() throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetConnectAttr(connHandle, SQL_ATTR_TXN_ISOLATION, valuePtr, strlenOut);
        return valuePtr.getInt();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public SQLWarning getWarnings() throws SQLException {
        // TODO: implement or throw exception
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void clearWarnings() throws SQLException {
        // TODO: implement or throw exception

    }

    @Override
    @Loggable(Loggable.TRACE)
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
    @Loggable(Loggable.TRACE)
    public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareCall() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLFeatureNotSupportedException("getTypeMap() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException("setTypeMap() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setHoldability(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("setHoldability() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException("getHoldability() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLFeatureNotSupportedException("setSavepoint() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Savepoint setSavepoint(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("setSavepoint() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException("rollback - savepoints not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException("releaseSavepoint() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLFeatureNotSupportedException("createStatement() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareCall() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public PreparedStatement prepareStatement(String s, int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
        throw new SQLFeatureNotSupportedException("prepareStatement() not implemented");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Clob createClob() throws SQLException {
        throw new SQLFeatureNotSupportedException("createClob() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Blob createBlob() throws SQLException {
        throw new SQLFeatureNotSupportedException("createBlob() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public NClob createNClob() throws SQLException {
        throw new SQLFeatureNotSupportedException("createNClob() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public SQLXML createSQLXML() throws SQLException {
        throw new SQLFeatureNotSupportedException("createSQLXML() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isValid(int i) throws SQLException {
        // TODO: implement or throw exception
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setClientInfo(String s, String s1) throws SQLClientInfoException {
        this.properties.setProperty(s, s1);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.properties = properties;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getClientInfo(String s) throws SQLException {
        return (String)properties.get(s);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Properties getClientInfo() throws SQLException {
        return properties;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        throw new SQLFeatureNotSupportedException("createArrayOf() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Struct createStruct(String s, Object[] objects) throws SQLException {
        throw new SQLFeatureNotSupportedException("createStruct() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setSchema(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("setSchema() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public String getSchema() throws SQLException {
        throw new SQLFeatureNotSupportedException("getSchema() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void abort(Executor executor) throws SQLException {
        // TODO: implement or throw exception
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setNetworkTimeout(Executor executor, int i) throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        valuePtr.putInt(i);
        SQLSetConnectAttr(connHandle, SQL_ATTR_CONNECTION_TIMEOUT, valuePtr);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getNetworkTimeout() throws SQLException {
        ByteBuffer valuePtr = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetConnectAttr(connHandle, SQL_ATTR_CONNECTION_TIMEOUT, valuePtr, strlenOut);
        return valuePtr.getInt();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor() not supported");
    }
}
