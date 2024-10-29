package io.github.jdbc2odbc;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import com.jcabi.aspects.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.lwjgl.odbc.SQL;
import org.lwjgl.*;
import static org.lwjgl.system.Configuration.*;

import org.apache.commons.lang3.*;

/*
    URL reference: https://download.oracle.com/otn_hosted_doc/jdeveloper/904preview/jdk14doc/docs/guide/jdbc/getstart/bridge.doc.html
    The Bridge driver uses the odbc subprotocol. URLs for this subprotocol are of the form:
        jdbc:odbc:<data-source-name>[<attribute-name>=<attribute-value>]*
    For example:

        jdbc:odbc:sybase
        jdbc:odbc:mydb;UID=me;PWD=secret
        jdbc:odbc:ora123;Cachesize=300
 */

public class Driver implements java.sql.Driver {
    private static Logger LOGGER = LoggerFactory.getLogger(Driver.class);
    Long envHandle = null;
    public Driver() throws SQLException {
        if (SystemUtils.IS_OS_UNIX) {
            ODBC_LIBRARY_NAME.set("libodbc.so.2");
        }
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
    @Loggable(Loggable.TRACE)
    public Connection connect(String s, Properties properties) throws SQLException {

        return new io.github.jdbc2odbc.Connection(envHandle, s, properties);
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean acceptsURL(String s) throws SQLException {
        if (!s.startsWith("jdbc:"))
            return false;

        URI uri = null;
        try {
            uri = new URI(s.substring("jdbc:".length()));
        } catch (URISyntaxException e) {
            return false;
        }
        return (
                uri.getScheme() != null
                && uri.getHost() != null
        );
    }

    @Override
    @Loggable(Loggable.TRACE)
    public DriverPropertyInfo[] getPropertyInfo(String s, Properties properties) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    // Version 0.1 - alpha
    @Override
    @Loggable(Loggable.TRACE)
    public int getMajorVersion() {
        return 0;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public int getMinorVersion() {
        return 1;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    @Loggable(Loggable.TRACE)
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
