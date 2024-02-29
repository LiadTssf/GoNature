package command;

import data.Account;

public interface ServerCommand {
    Message execute(Object param, Account account);
}
