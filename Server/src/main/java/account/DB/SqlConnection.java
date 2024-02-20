package account.DB;

import java.sql.*;

public class SqlConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/GoNeture"; // Changed to Nir's DB
    private static final String USER = "root";
    private static final String PASSWORD = "n8040197";



    public static Connection getConnection (){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);  //return new Connection to db
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }








}
