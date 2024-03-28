package command;

import data.Account;
import data.FilesData;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetFilesFromDB implements ServerCommand{

    @Override
    public Message execute(Object param, Account account){
        /**
         * Executes the command to retrieve files from the database.
         *
         * @param param   The parameter required for the command (not used in this case).
         * @param account The account associated with the command.
         * @return A message containing the files retrieved from the database.
         */
        ArrayList<FilesData> arrayListFiles = new ArrayList<>();
        DatabaseController DB = new DatabaseController();
        String queryGetFiles = "SELECT file_name FROM files";

        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryGetFiles);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                FilesData filesData = new FilesData();
                filesData.setFileName(rs.getString("file_name"));
                arrayListFiles.add(filesData);
            }
            return new Message("LoadSucceed",arrayListFiles);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("LoadFailed",null);
        }
    }
}
