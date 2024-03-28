package command;

import data.Account;
import data.DataParkChange;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadChangesData implements ServerCommand{
    /**
     * Executes the command to upload changes data to the database.
     *
     * @param param   The DataParkChange object containing the changes data to upload.
     * @param account The account associated with the upload request.
     * @return A message indicating the result of the upload operation.
     */
    @Override
    public Message execute(Object param, Account account){
        if (!(param instanceof DataParkChange)){
            return new Message("DataParkChange","Parameter is not valid");
        }

        DataParkChange dataParkChange = (DataParkChange) param;
        DatabaseController DB = new DatabaseController();
        String queryToLoad = "INSERT INTO park_changes (park_name,new_capacity,old_capacity,new_average_visit_time,old_average_visit_time,new_capacity_offset,old_capacity_offset) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToLoad);
            pstmt.setString(1,dataParkChange.parkName);
            pstmt.setInt(2,dataParkChange.newCapacity);
            pstmt.setInt(3,dataParkChange.oldCapacity);
            pstmt.setInt(4,dataParkChange.newStayingTime);
            pstmt.setInt(5,dataParkChange.oldStayingTime);
            pstmt.setInt(6,dataParkChange.newOffset);
            pstmt.setInt(7,dataParkChange.oldOffset);

            pstmt.execute();
            return new Message("UploadSucceed");

        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("UploadFailed");
        }


    }
}
