package command;

import data.Account;
import database.DatabaseController;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DownloadFileFromDB implements ServerCommand{
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof String)) {
            return new Message("FileNameNotString", "check parameter");
        }

        String fileName = (String) param;
        //String directory = "C:\\Desktop\\OfficeManager\\" + fileName;
        File dir = new File(fileName);
        String queryToDownload = "SELECT file_data FROM files WHERE file_name = ?";
        String queryToDeleteFile = "DELETE FROM files WHERE file_name = ?";
        DatabaseController db = new DatabaseController();

        if (!dir.exists()) {
            try {
                if (dir.createNewFile()) {
                    try {
                        PreparedStatement pstmt = db.getConnection().prepareStatement(queryToDownload);
                        pstmt.setString(1, fileName);
                        ResultSet rs = pstmt.executeQuery();
                        FileOutputStream fos = new FileOutputStream(dir);
                        Writer writer = new FileWriter(dir);
                        if (rs.next()) {
                            writer.write(rs.getString("file_data"));
                        }

                        pstmt = db.getConnection().prepareStatement(queryToDeleteFile);
                        pstmt.setString(1,fileName);
                        pstmt.execute();
                        return new Message("FileDownloaded");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return new Message("DownloadFailed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Message("FileExist");
    }
}
