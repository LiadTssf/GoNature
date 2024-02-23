package account.DB;


import java.sql.*;

/**
this class responsible to working with the db
it will be called from different classes that need to write / delete / update data from the db
*/
public class CRUD  {

    Connection connection;
    Statement stmt;
    ResultSet rs;

    public CRUD () throws SQLException {
        connection = SqlConnection.getConnection();
        stmt = connection.createStatement();
        rs = stmt.getResultSet();
    }
    public void insertData( String queryToRun) {
         try {
             stmt.executeUpdate(queryToRun);
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

    public boolean next() throws SQLException {
        return rs.next();
    }

    public String getString(String stringToSearch) throws SQLException {
        if (stringToSearch == "TelephoneNumber") {
            return rs.getString(stringToSearch);
        }
        return rs.getString(stringToSearch);
    }

    public int getInt(String columm) throws SQLException {
        if (columm == "OrderNumber") {
            return rs.getInt(columm);
        }
        return rs.getInt(columm);
    }

    public Time getTime(String timeOfVisit) throws SQLException {
       return rs.getTime(timeOfVisit);
    }


    public void close() throws SQLException {
        rs.close();
        stmt.close();
        connection.close();
    }
}
