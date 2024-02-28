package database;

import java.sql.*;

public class DatabaseController {
    Connection connection;

    /**
     * Constructs a new database controller and establishes connection to db
     */
    public DatabaseController() {
        connection = SqlConnection.getConnection();
    }

    /**
     * Returns the current connection instance
     * @return connection instance
     */
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

    /**
     * Executes an update query through PreparedStatement
     * @param statement PreparedStatement constructed ahead of calling this method
     */
    public void executeUpdate(PreparedStatement statement) {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection, allowing the controller to destruct itself
     * Call this method any time you finish using a database controller
     * Not implemented because of a faulty SQLException throw when using Java 11+
     */
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
