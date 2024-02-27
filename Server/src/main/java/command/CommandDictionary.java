package command;

public class CommandDictionary extends AbstractCommandDictionary {
    public CommandDictionary() {
        super();
        this.addCommand("GetOrderByNumber", new GetOrderByNumber());
        this.addCommand("UpdateOrderByNumber", new UpdateOrderByNumber());
    }
}
