package command;

public class CommandDictionary extends AbstractCommandDictionary {
    public CommandDictionary() {
        super();
        this.addCommand("ReturnParam", new ReturnParam());
    }
}
