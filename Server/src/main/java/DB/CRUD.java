package DB;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
this class responsible to working with the db
it will be called from different classes that need to write / delete / update data from the db
*/
public class CRUD  {

    Connection connection;
    Statement stmt;

    public CRUD () throws SQLException {
        connection = SqlConnection.getConnection();
        stmt = connection.createStatement();
    }
    public void insertData( String queryToRun) {
         try {
             stmt.executeQuery(queryToRun);
         }
         catch (SQLException e) {e.printStackTrace();}


    }

    public Object getData(String queryToRun) { // need to impelement
        try {
            stmt.executeQuery(queryToRun);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
