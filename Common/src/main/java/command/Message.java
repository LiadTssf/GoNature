package command;

import data.Account;

import java.io.Serializable;

public class Message implements Serializable {
    private String command;
    private Object param;
    private Account account;

    public Message(String command) {
        this.command = command;
        this.param = "No parameters";
    }

    public Message(String command, Object param) {
        this.command = command;
        this.param = param;
    }

    public String getCommand() {
        return command;
    }

    public Object getParam() {
        return param;
    }

    public Account getAccount() {
        return account;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toString() {
        if (account == null) {
            return command + "( " + param.toString() + " ) ";
        } else {
            return command + "(" + param.toString() + " ) " + "Account: " + account;
        }
    }
}