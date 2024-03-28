package command;

import data.Account;
import data.Park;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateParkDetails implements ServerCommand {
    @Override
    public Message execute(Object param, Account account) throws SQLException {
        /**
         * Executes the command to update park details in the database.
         *
         * @param param   The Park object containing the updated park details.
         * @param account The account associated with the update request.
         * @return A message indicating the result of the update operation.
         */
        if(!(param instanceof Park)){
            return new Message("FailMessage", "Invalid parameter type ");
        }
        DatabaseController DB = new DatabaseController();
        Park parkUpdateDetails = (Park) param;
        String query = "UPDATE gonature.park " +
                "SET capacity = ?, " +
                "    average_visit_time = ?, " +
                "    capacity_offset = ? " +
                "WHERE park_name = ?";
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);
            pstmt.setInt(1, parkUpdateDetails.getCapacity());
            pstmt.setInt(2, parkUpdateDetails.getAverage_visit_time());
            pstmt.setInt(3, parkUpdateDetails.getCapacity_offset());
            pstmt.setString(4, parkUpdateDetails.getPark_name());
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated + "park rows updated");
            DeleteParkChanges(DB,parkUpdateDetails.getPark_name());
        }catch (SQLException e){
            e.printStackTrace();
            return new Message("FailMessage", "An error occurred while trying to update park.");
        }

        return new Message("FailMessage", "Order list has been successfully updated.");
    }
    private void DeleteParkChanges(DatabaseController db, String parkName) throws SQLException {
        String deleteQuery = "DELETE FROM gonature.park_changes WHERE park_name = ?";
        PreparedStatement pstmt = db.getConnection().prepareStatement(deleteQuery);
        pstmt.setString(1,parkName);
        int rowsUpdated = pstmt.executeUpdate();
        System.out.println(rowsUpdated + "park changes deleted");

    }
}
