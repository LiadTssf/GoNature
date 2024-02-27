package command;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommandDictionary {
    private final Map<String, Command> commandDictionary;

    public AbstractCommandDictionary() { commandDictionary = new HashMap<>(); }

    public Command getCommand(String key) { return commandDictionary.get(key); }

    public void addCommand(String key, Command command) { commandDictionary.put(key, command); }
}
