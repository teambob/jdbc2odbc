package io.github.jdbc2odbc;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.lwjgl.odbc.SQL.*;


public class Statement implements java.sql.Statement{
    Long statementHandle = null;
    public Statement(Long connHandle){
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);
        SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);
        statementHandle = outHandle.get();
    }
    @Override
    public ResultSet executeQuery(String s) throws SQLException {
        SQLExecDirect(statementHandle, s);
        PointerBuffer rowCountOut = PointerBuffer.allocateDirect(8);
        SQLRowCount(statementHandle, rowCountOut);
        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public int executeUpdate(String s) throws SQLException {
        return 0;
    }

    @Override
    public void close() throws SQLException {
        SQLFreeHandle(SQL_HANDLE_STMT, statementHandle);
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int i) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int i) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean b) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetStmtAttr(statementHandle, SQL_ATTR_QUERY_TIMEOUT, valueOut, strlenOut);
        return valueOut.getInt();
    }

    @Override
    public void setQueryTimeout(int i) throws SQLException {
        nSQLSetStmtAttr(statementHandle, SQL_ATTR_QUERY_TIMEOUT, i, SQL_IS_INTEGER);
    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String s) throws SQLException {

    }

    @Override
    public boolean execute(String s) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return new io.github.jdbc2odbc.ResultSet(statementHandle);
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchDirection() not supported");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new SQLFeatureNotSupportedException("getFetchDirection() not supported");
    }

    @Override
    public void setFetchSize(int i) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String s) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int i) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String s, int i) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String s, int[] ints) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String s, String[] strings) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String s, int i) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String s, int[] ints) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String s, String[] strings) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean b) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
