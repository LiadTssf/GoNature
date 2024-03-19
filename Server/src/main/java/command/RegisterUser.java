package command;

import data.Account;
import data.RegisteredAccount;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterUser implements ServerCommand{

    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof RegisteredAccount)){
            return new Message("RegistrationFailed","param is not registerUser type");
        }

        RegisteredAccount registerUser = (RegisteredAccount) param;

        if (!registerUser.email.contains("@")) {
            return new Message("RegistrationFailed", "The Email you entered is incorrect");
        }

        if (registerUser.password.length() != 6){
            return new Message("RegistrationFailed", "The password you entered is not 6 characters");
        }

        DatabaseController DB = new DatabaseController();

        String CheckQuery = ("SELECT username FROM account WHERE username = ? ");
        String ExQuery = ("INSERT INTO account (account_id_pk,account_type,username,password,email,phone) VALUES (?,?,?,?,?,?)");
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(CheckQuery);
            pstmt.setString(1,registerUser.username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                String userName = rs.getString("username");
                if (userName.equals(registerUser.username)){
                    return new Message("RegistrationFailed","Username already exist");
                }
            }

            pstmt = DB.getConnection().prepareStatement(ExQuery);
            pstmt.setInt(1,registerUser.account_id_pk);
            pstmt.setString(2,registerUser.account_type);
            pstmt.setString(3,registerUser.username);
            pstmt.setString(4, registerUser.password);
            pstmt.setString(5,registerUser.email);
            pstmt.setString(6,registerUser.phone);
            pstmt.execute();
            return new Message("AuthenticateUser",registerUser);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("RegistrationFailed","loading to DB failed");
        }

    }
}
