package command;

import data.Account;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWithID implements ServerCommand {
    /**
     * Receives an authentication attempt using only account_id and either accepts it and tells the client to
     * authenticate or denies it and sends appropriate denial message
     * @param param int account_id
     * @param account client authorized account
     * @return message to client authenticating with new account or denying the login
     */
    @Override
    public Message execute(Object param, Account account) {
        if(!(param instanceof Integer)) {
            return new Message("LoginDenied", "Invalid parameter type sent");
        }

//        DatabaseController DB = new DatabaseController();
//        String queryToSearch = "SELECT * FROM account WHERE account_id = ?";
        int account_id = (int) param;
//        try {
//            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToSearch);
//            pstmt.setInt(1,account_id);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()){
//                if (rs.getString("account_type").equals("TourGuide") || rs.getString("account_type").equals("Client")) {
//                    return new Message("AlreadyRegistered", account_id);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }



            if (account_id >= 100000000 && account_id <= 999999999) {
                if (ServerHandler.getClientFromAccount(account_id) == -1) {
                    return new Message("AuthenticateClient", new Account(account_id, "Unregistered"));
                } else {
                    return new Message("LoginDenied", "Already logged in another session");
                }
            }
            return new Message("LoginDenied", "Invalid ID entered");
        }
}
