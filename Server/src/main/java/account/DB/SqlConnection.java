package account.DB;

import java.sql.*;

public class SqlConnection {

    private static final String URL = "jdbc:mysql://localhost/students_db?serverTimezone=UTC"; // need to be changed later
    private static final String USER = "root";
    private static final String PASSWORD = "Aa123456";



    public static Connection getConnection (){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);  //return new Connection to db
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }








}
