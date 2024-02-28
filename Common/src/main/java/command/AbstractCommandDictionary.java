package command;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommandDictionary {
    private final Map<String, Command> commandDictionary;

    /**
     * Constructs a new commands dictionary
     */
    public AbstractCommandDictionary() { commandDictionary = new HashMap<>(); }

    /**
     * Gets a command instance from the dictionary for execution
     * @param key the name of the command as a string
     * @return A command instance to be executed
     */
    public Command getCommand(String key) { return commandDictionary.get(key); }

    /**
     * Adds a new command to the dictionary
     * @param key the name of the command as a string
     * @param command an instance of the command to save in the dictionary
     */
    public void addCommand(String key, Command command) { commandDictionary.put(key, command); }
}
