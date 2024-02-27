package command;

import java.io.Serializable;

public class Message implements Serializable {
    private String command;
    private Object param;

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
