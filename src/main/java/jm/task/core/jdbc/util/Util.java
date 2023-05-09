package jm.task.core.jdbc.util;

import java.sql.*;

public class Util implements AutoCloseable {
    static private final String URL = "jdbc:mysql://localhost:3306/test";
    static private final String USERNAME = "root";
    static private final String PASSWORD = "admin";
    private static Connection connection = null;
    public static Connection getConnection () {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
