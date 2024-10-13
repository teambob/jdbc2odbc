package io.github.jdbc2odbc;


import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import org.lwjgl.odbc.SQL;
import org.lwjgl.*;
import static org.lwjgl.system.Configuration.*;

public class Driver implements java.sql.Driver {
    Long envHandle = null;
    public Driver() throws SQLException {
        ODBC_LIBRARY_NAME.set("libodbc.so.2");
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);

        //TODO: close driver handle
        short ret = SQL.SQLAllocHandle(
                SQL.SQL_HANDLE_ENV, SQL.SQL_NULL_HANDLE, outHandle);

        if (ret != SQL.SQL_SUCCESS) {
            throw new SQLException("SQLAllocHandle(SQL_HANDLE_ENV,...) failed");
        }
        envHandle = outHandle.get();

        ret = SQL.nSQLSetEnvAttr(envHandle, SQL.SQL_ATTR_ODBC_VERSION, SQL.SQL_OV_ODBC3, 0);
        if (ret != SQL.SQL_SUCCESS) {
            throw new SQLException("SQLSetEnvAttr() failed");
        }


    }
    @Override
    public Connection connect(String s, Properties properties) throws SQLException {

        return new io.github.jdbc2odbc.Connection(envHandle, s, properties);
    }

    @Override
    public boolean acceptsURL(String s) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String s, Properties properties) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    // Version 0.1 - alpha
    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 1;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
