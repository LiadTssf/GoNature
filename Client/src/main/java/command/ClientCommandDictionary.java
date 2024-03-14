package command;

import java.util.HashMap;
import java.util.Map;

public class ClientCommandDictionary {
    private final Map<String, ClientCommand> commandDictionary;

    /**
     * Constructs a new commands dictionary and adds client commandss
     */
    public ClientCommandDictionary() {
        commandDictionary = new HashMap<>();
        this.addCommand("AuthenticateClient", new AuthenticateClient());
        this.addCommand("DeauthenticateClient", new DeauthenticateClient());
        this.addCommand("AuthenticateUser",new AuthenticateUser());
        this.addCommand("DeauthenticateClient",new DeauthenticateUser());
        this.addCommand("OrderCreated",new OrderCreated());
    }

    /**
     * Gets a command instance from the dictionary for execution
     * @param key the name of the command as a string
     * @return A command instance to be executed
     */
    public ClientCommand getCommand(String key) {
        return commandDictionary.get(key);
    }

    /**
     * Adds a new command to the dictionary
     * @param key the name of the command as a string
     * @param command an instance of the command to save in the dictionary
     */
    public void addCommand(String key, ClientCommand command) {
        commandDictionary.put(key, command);
    }
}
