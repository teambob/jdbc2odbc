package io.github.jdbc2odbc;

import java.sql.Connection;
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
        assertTrue(driver != null);
        Connection cursor = driver.connect("abc", new Properties());
        assertTrue(cursor != null);
    }
    @org.junit.jupiter.api.Test
    void query() throws SQLException {
        Driver driver = new Driver();
        assertTrue(driver != null);
        Connection cursor = driver.connect("abc", new Properties());
        assertTrue(cursor != null);
        java.sql.Statement statement = cursor.createStatement();
        assertTrue(statement != null);
        ResultSet results = statement.executeQuery("select * from test");
        assertTrue(results != null);
        System.out.println(results.getString(1));
        System.out.println(results.getString(2));
        System.out.println(results.getMetaData().getColumnCount());


    }
}