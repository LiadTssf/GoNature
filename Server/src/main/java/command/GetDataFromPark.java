package command;

import data.Account;
import data.DataParkChange;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetDataFromPark implements ServerCommand{
    /**
     * Executes the command to retrieve data from a park in the database.
     *
     * @param param   The DataParkChange object containing the park data to retrieve.
     * @param account The account associated with the data retrieval request.
     * @return A message containing the retrieved park data.
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof DataParkChange)){
            return new Message("ParamError","The param is not DataParkChange");
        }

        int parkID = 0;
        DataParkChange dataParkChange = (DataParkChange) param;
        if (dataParkChange.newCapacity< dataParkChange.newOffset){
            return new Message("capacity_VS_offset");
        }

        if (dataParkChange.newStayingTime >= 24){
            return new Message("IllegalTime");
        }

        DatabaseController DB= new DatabaseController();

        String getParkID = "SELECT park_id_pk FROM park WHERE park_name = ?";
        String getValues = "SELECT capacity,average_visit_time,capacity_offset FROM park WHERE park_id_pk = ?";

        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(getParkID);
            pstmt.setString(1, dataParkChange.parkName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()){
                parkID = resultSet.getInt("park_id_pk");
            }
            pstmt = DB.getConnection().prepareStatement(getValues);
            pstmt.setInt(1,parkID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()){
                dataParkChange.oldCapacity = resultSet.getInt("capacity");
                dataParkChange.oldOffset = resultSet.getInt("capacity_offset");
                dataParkChange.oldStayingTime = resultSet.getInt("average_visit_time");

                return new Message("DataAchieved",dataParkChange);
            }
            return new Message("DataNotAchieved");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
