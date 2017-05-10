package sample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    // JDBC driver name and database URL
    private String DB_URL = "jdbc:h2:./src/main/java/db/test";
    //  Database credentials
    private String USER = "sa";
    private String PASSWORD = "";
    private Connection connection = null;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch(SQLException e) {
            throw e;
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e) {
            throw e;
        }
    }
}
