package command;

public class CommandDictionary extends AbstractCommandDictionary {
    /**
     * Constructs a new commands dictionary
     */
    public CommandDictionary() {
        super();
        this.addCommand("GetOrderByNumber", new GetOrderByNumber());
        this.addCommand("UpdateOrderByNumber", new UpdateOrderByNumber());
    }
}
