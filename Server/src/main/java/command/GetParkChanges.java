package command;

import data.Account;
import data.Order;
import data.ParkChangesView;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetParkChanges implements ServerCommand {

    @Override
    public Message execute(Object param, Account account)  {
        try{
            ArrayList<ParkChangesView> parkChangesList = new ArrayList<>();
            String queryToRun = "SELECT * FROM `gonature`.`park_changes`";
            DatabaseController DB = new DatabaseController();
            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToRun);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                ParkChangesView parkChangesView = new ParkChangesView();
                parkChangesView.setParkName(rs.getString("park_name"));
                parkChangesView.setNewCapacity(rs.getInt("new_capacity"));
                parkChangesView.setOldCapacity(rs.getInt("old_capacity"));
                parkChangesView.setNewAverageVisitTime(rs.getInt("new_average_visit_time"));
                parkChangesView.setOldAverageVisitTime(rs.getInt("old_average_visit_time"));
                parkChangesView.setNewCapacityOffset(rs.getInt("new_capacity_offset"));
                parkChangesView.setOldCapacityOffset(rs.getInt("old_capacity_offset"));
                parkChangesList.add(parkChangesView);
            }

              return new Message("SetParkChanges",parkChangesList);
        }catch (SQLException e){
            e.printStackTrace();
            return new Message("Error","An error occurred while trying to get park changes");
        }


    }
}
