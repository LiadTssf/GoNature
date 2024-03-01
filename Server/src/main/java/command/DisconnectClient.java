package command;

import data.Account;

public class DisconnectClient implements ServerCommand {
    @Override
    public Message execute(Object param, Account account) {
        return null;
    }
}
