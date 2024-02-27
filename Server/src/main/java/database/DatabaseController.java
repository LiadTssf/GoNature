package database;

import java.sql.*;

public class DatabaseController {
    Connection connection;

    public DatabaseController() {
        connection = SqlConnection.getConnection();
    }

    public Connection getConnection() { return connection; }

    public ResultSet executeQuery(PreparedStatement statement) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void executeUpdate(PreparedStatement statement) {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
//        mysql-connector-java-8.0.13 throws a faulty SQLException when
//        connection is closed in Java 11+, keep this commented away
//        not closing connections after using controller  can cause memory leak

//        try {
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
