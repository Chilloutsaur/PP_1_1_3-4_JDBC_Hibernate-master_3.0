package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    static private final String URL = "jdbc:mysql://localhost:3306/test";
    static private final String USERNAME = "root";
    static private final String PASSWORD = "admin";

    public static Connection getConnection () {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
