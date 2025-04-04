package io.github.jdbc2odbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    @org.junit.jupiter.api.Test
    void testtheclass() throws SQLException {
        Driver driver = new Driver();
        assertTrue(driver instanceof Driver);
    }
    @org.junit.jupiter.api.Test
    void connect() throws SQLException {
        Driver driver = new Driver();
        assertNotNull(driver);
        Connection cursor = driver.connect("jdbc:odbc://abc", new Properties());
        assertNotNull(cursor);
    }
    @org.junit.jupiter.api.Test
    void query() throws SQLException {
        Driver driver = new Driver();
        assertNotNull(driver);
        Connection cursor = driver.connect("jdbc:odbc://abc", new Properties());
        assertNotNull(cursor);
        java.sql.Statement statement = cursor.createStatement();
        assertNotNull(statement);
        ResultSet results = statement.executeQuery("select * from test");
        assertNotNull(results);
        results.next();

        System.out.println("Column count = "+results.getMetaData().getColumnCount());
        //System.out.println("Column 1 = "+results.getString(1));
        //System.out.println("Column 1 = "+results.getString(1));
        System.out.println("Column key = "+results.getString("key"));
        System.out.println("Column 2 = "+results.getString(2));
    }

    @org.junit.jupiter.api.Test
    void getTables() throws SQLException {
        Driver driver = new Driver();
        assertNotNull(driver);
        Connection cursor = driver.connect("jdbc:odbc://abc", new Properties());
        assertNotNull(cursor);
        DatabaseMetaData metadata = cursor.getMetaData();
        assertNotNull(metadata);


        ResultSet rs = metadata.getTableTypes();

        System.out.println("Table types");
        while (rs.next()) {
            System.out.print("Table type:");
            System.out.println(rs.getString(1));
        }


        rs = metadata.getTables(null, null, null, new String[]{"TABLE"});

        System.out.println("Tables");
        while (rs.next()) {
            System.out.print("Table:");
            System.out.println(rs.getString(3));
        }



    }

    @org.junit.jupiter.api.Test
    void getCatalogs() throws SQLException {
        Driver driver = new Driver();
        assertNotNull(driver);
        Connection cursor = driver.connect("jdbc:odbc://abc", new Properties());
        assertNotNull(cursor);
        DatabaseMetaData metadata = cursor.getMetaData();
        assertNotNull(metadata);
        ResultSet rs = metadata.getCatalogs();

        System.out.println("Catalogs");
        while (rs.next()) {
            System.out.print("Catalog:");
            System.out.println(rs.getString(1));
        }



    }

}