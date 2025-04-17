package by.aston.myexpencetracker.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/my_db", "postgres", "postgres");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}