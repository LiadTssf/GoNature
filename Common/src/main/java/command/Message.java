package command;

import java.io.Serializable;

public class Message implements Serializable {
    private String command;
    private Object param;

    /**
     * Constructs a new message with a command to execute and parameters
     * @param command name of the command to execute
     * @param param any object sent as parameter, can be ArrayList if several objects need to be sent
     */
    public Message(String command, Object param) {
        this.command = command;
        this.param = param;
        if (param == null) this.param = "No parameters";
    }

    public String getCommand() { return command; }
    public Object getParam() { return param; }
    public void setCommand(String command) { this.command = command; }
    public void setParam(Object param) { this.param = param; }

    public String toString() { return command + " : " + param.toString(); }
}
