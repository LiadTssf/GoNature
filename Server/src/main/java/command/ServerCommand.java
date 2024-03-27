package command;

import data.Account;

import java.sql.SQLException;

public interface ServerCommand {
    Message execute(Object param, Account account) throws SQLException;
}
