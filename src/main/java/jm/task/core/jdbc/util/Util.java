package jm.task.core.jdbc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static String dbUrl = "jdbc:mysql://192.168.89.21:3306/firstdb";
    public static String dbUsername = "root";
    public static String dbPassword = "root";
    // реализуйте настройку соеденения с БД
    private static Connection connection;
    public static Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("\nConnection established\n");
        } catch (SQLException e) {
            throw new RuntimeException("Database properties file not available or no connection ", e);
        }
        return connection;
    }
}
