package command;

import data.Account;
import data.FilesData;
import database.DatabaseController;

import java.io.FileWriter;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DownloadFileFromDB implements ServerCommand {
    /**
     * Executes the command to download a file from the database.
     *
     * @param param   The name of the file to download.
     * @param account The account associated with the download request.
     * @return A message indicating the result of the download operation.
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof String)) {
            return new Message("FileNameNotString", "check parameter");
        }

        String fileName = (String) param;
        //String directory = "C:\\Desktop\\OfficeManager\\" + fileName;
        String queryToDownload = "SELECT file_data FROM files WHERE file_name = ?";
        String queryToDeleteFile = "DELETE FROM files WHERE file_name = ?";
        DatabaseController db = new DatabaseController();

        FilesData filesData = new FilesData();
        filesData.setFileName(fileName);
        try {
            PreparedStatement pstmt = db.getConnection().prepareStatement(queryToDownload);
            pstmt.setString(1, fileName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                filesData.setFile_data(rs.getBytes("file_data"));
            }

            pstmt = db.getConnection().prepareStatement(queryToDeleteFile);
            pstmt.setString(1, fileName);
            pstmt.execute();
            return new Message("FileDownloaded",filesData);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}