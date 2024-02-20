package account.DB;

import java.sql.*;

public class SqlConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/GoNeture"; // Changed to Nir's DB
    private static final String USER = "root";
    private static final String PASSWORD = "n8040197";



    public static Connection getConnection (){

        try{
            Connection con;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL,USER,PASSWORD);  //return new Connection to db
            System.out.println("success");
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
