package io.github;


import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import static org.lwjgl.odbc.SQL.*;
import org.lwjgl.*;


public class Driver implements java.sql.Driver {
    Long envHandle = null;
    public Driver(){
        PointerBuffer outHandle = PointerBuffer.allocateDirect(8);
        SQLAllocHandle(
                SQL_HANDLE_ENV, SQL_NULL_HANDLE, outHandle);
        envHandle = outHandle.get();

        nSQLSetEnvAttr(envHandle, SQL_ATTR_ODBC_VERSION, SQL_OV_ODBC3, 0);

    }
    @Override
    public Connection connect(String s, Properties properties) throws SQLException {

        return new io.github.Connection(envHandle, s, properties);
    }

    @Override
    public boolean acceptsURL(String s) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String s, Properties properties) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
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
