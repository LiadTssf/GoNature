package command;

import data.Account;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourGuideLogin implements ServerCommand{
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

        String query = ("SELECT * FROM gonature.account WHERE username = ? AND password = ?");

        try{
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);

            pstmt.setString(1,userName);
            pstmt.setString(1,PassWord);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                LoginInfo.add(rs.getString("account_type"));
                int account_id_pk = rs.getInt("account_id_pk");
                if (ServerHandler.getClientFromAccount(account_id_pk) != -1){
                    return new Message("AuthenticateUser",new Account(account_id_pk,LoginInfo.get(2)));
                }
                return new Message("LoginFailed","Username or password is incorrect.");
            }else{
                return new Message("LoginFailed","Username or password is incorrect.");
            }


        }catch (SQLException e){
            e.printStackTrace();
            return new Message("Error","An error occurred while trying to log in.");
        }
    }
}
