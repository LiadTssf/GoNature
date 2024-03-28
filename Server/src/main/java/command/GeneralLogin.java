package command;

import data.Account;
import data.RegisteredAccount;
import data.WorkerAccount;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneralLogin implements ServerCommand{
    /**
     * Receives an authentication attempt using only account_id and either accepts it and tells the client to
     * authenticate or denies it and sends appropriate denial message
     * @param param ArrayList contains (username,password)
     * @param account client authorized account
     * @return message to client authenticating with new account or denying the login
     */

    @Override
    public Message execute(Object param, Account account){
        if (!(param instanceof ArrayList)) {
            return new Message("InvalidParam", "The parameter should be an ArrayList containing username and password.");
        }

        DatabaseController DB = new DatabaseController();
        ArrayList<String> LoginInfo = (ArrayList<String>) param;

        String userName = LoginInfo.get(0);
        String PassWord = LoginInfo.get(1);
        String accountID;
        String accountType;
        String query = ("SELECT * FROM gonature.account WHERE username = ? AND password = ?");
        try{
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);
            pstmt.setString(1,userName);
            pstmt.setString(2,PassWord);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                LoginInfo.add(rs.getString("account_type"));
                accountType = rs.getString("account_type");
                int account_id_pk = rs.getInt("account_id_pk");
                if (accountType.equals("Worker")){
                    return workerDBSearch(pstmt,rs,account_id_pk,accountType,DB);
                }
                if (ServerHandler.getClientFromAccount(account_id_pk) == -1) {
                    RegisteredAccount accountToReturn = new RegisteredAccount(account_id_pk, LoginInfo.get(2));
                    rs.first();
                    accountToReturn.email= rs.getString("email");
                    accountToReturn.phone = rs.getString("phone");
                    accountToReturn.password = rs.getString("password");
                    accountToReturn.username = rs.getString("username");
                    return new Message("AuthenticateUser", accountToReturn);
                }
                return new Message("LoginSession", "Username already connected from another session.");
            }
            return new Message("LoginFailed", "User not found.");


        }catch (SQLException e){
            e.printStackTrace();
            return new Message("Error","An error occurred while trying to log in.");
        }
    }

    private Message workerDBSearch(PreparedStatement pstmt,ResultSet rs,int accountID,String accountType,DatabaseController DB) throws SQLException {
        String QueryToSearchInWorkerDB=("SELECT * FROM gonature.account_extra_info_worker WHERE account_id = ?");
        String searchParkNameQuery = ("SELECT park_name FROM gonature.park WHERE park_id_pk = ?");
        DatabaseController databaseController = new DatabaseController();
        pstmt = DB.getConnection().prepareStatement(QueryToSearchInWorkerDB);
        pstmt.setInt(1,accountID);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            if (ServerHandler.getClientFromAccount(accountID) == -1) {
                WorkerAccount workerAccount = new WorkerAccount(accountID, accountType);
                rs.first();
                workerAccount.account_type = rs.getString("worker_role");
                int park_id = rs.getInt("park_id_fk");
                PreparedStatement preparedStatement = databaseController.getConnection().prepareStatement(searchParkNameQuery);
                preparedStatement.setInt(1,park_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    workerAccount.park_id_fk = resultSet.getString("park_name");
                }
                workerAccount.park_id="" + park_id;
                workerAccount.firstname = rs.getString("firstname");
                workerAccount.lastname = rs.getString("lastname");
                workerAccount.worker_id = rs.getInt("worker_id");

                return new Message("AuthenticateUser", workerAccount);
            }
            return new Message("LoginSession", "Username already connected from another session.");
        }
        return new Message("LoginFailed", "User not found.");
    }
}
