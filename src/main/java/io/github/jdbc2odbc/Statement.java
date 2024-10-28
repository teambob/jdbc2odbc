package io.github.jdbc2odbc;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;

import com.jcabi.aspects.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.lwjgl.odbc.SQL.*;


public class Statement implements java.sql.Statement{
    private static Logger LOGGER = LoggerFactory.getLogger(Statement.class);
    Long statementHandle = null;
    public Statement(Long connHandle) throws SQLException{
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);
        short ret = SQLAllocHandle(
                SQL_HANDLE_STMT, connHandle, outHandle);

        if (ret != SQL_SUCCESS){
            throw new SQLException("SQLAllocHande(SQL_HANDLE_STMT,...) failed");
        }
        statementHandle = outHandle.get();
    }
    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet executeQuery(String s) throws SQLException {
        execute(s);
        return getResultSet();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int executeUpdate(String s) throws SQLException {
        execute(s);
        return getUpdateCount();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void close() throws SQLException {
        //SQLFreeStmt(statementHandle, SQL_CLOSE);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setMaxFieldSize(int i) throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setMaxRows(int i) throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setEscapeProcessing(boolean b) throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getQueryTimeout() throws SQLException {
        ByteBuffer valueOut = BufferUtils.createByteBuffer(8);
        IntBuffer strlenOut = BufferUtils.createIntBuffer(8);
        SQLGetStmtAttr(statementHandle, SQL_ATTR_QUERY_TIMEOUT, valueOut, strlenOut);
        return valueOut.getInt();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setQueryTimeout(int i) throws SQLException {
        nSQLSetStmtAttr(statementHandle, SQL_ATTR_QUERY_TIMEOUT, i, SQL_IS_INTEGER);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void cancel() throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void clearWarnings() throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setCursorName(String s) throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean execute(String s) throws SQLException {
        short result = SQLExecDirect(statementHandle, s);
        return result==SQL_SUCCESS;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getResultSet() throws SQLException {
        return new io.github.jdbc2odbc.ResultSet(this, statementHandle);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getUpdateCount() throws SQLException {
        ShortBuffer columnCountOut = BufferUtils.createShortBuffer(8);
        SQLNumResultCols(statementHandle, columnCountOut);

        if (columnCountOut.get()>0){
            return -1;
        }
        else {
            PointerBuffer rowCountOut = PointerBuffer.allocateDirect(8);
            SQLRowCount(statementHandle, rowCountOut);

            return (int) rowCountOut.get();
        }
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setFetchDirection(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchDirection() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getFetchDirection() throws SQLException {
        throw new SQLFeatureNotSupportedException("getFetchDirection() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setFetchSize(int i) throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void addBatch(String s) throws SQLException {
        throw new SQLFeatureNotSupportedException("addBatch() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void clearBatch() throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public int[] executeBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException("executeBatch() not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean getMoreResults(int i) throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int executeUpdate(String s, int autoGeneratedKeys) throws SQLException {
        execute(s, autoGeneratedKeys);
        return getUpdateCount();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int executeUpdate(String s, int[] autoGeneratedKeyColumns) throws SQLException {
        execute(s, autoGeneratedKeyColumns);
        return getUpdateCount();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int executeUpdate(String s, String[] autoGeneratedKeyColumns) throws SQLException {
        execute(s, autoGeneratedKeyColumns);
        return getUpdateCount();
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean execute(String s, int autoGeneratedKeys) throws SQLException {
        throw new SQLFeatureNotSupportedException("execute() with autogenerate keys not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean execute(String s, int[] autoGeneratedKeyColumns) throws SQLException {
        throw new SQLFeatureNotSupportedException("execute() with autogenerate keys not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean execute(String s, String[] autoGeneratedKeyColumns) throws SQLException {
        throw new SQLFeatureNotSupportedException("execute() with autogenerate keys not supported");
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void setPoolable(boolean b) throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
