package command;

import java.util.HashMap;
import java.util.Map;

public class ServerCommandDictionary {
    private final Map<String, ServerCommand> commandDictionary;

    /**
     * Constructs a new commands dictionary and adds server specific commands to it
     */
    public ServerCommandDictionary() {
        commandDictionary = new HashMap<>();
        this.addCommand("SetOrderListById", new SetOrderListById());
        this.addCommand("GetOrderListById", new GetOrderListById());
        this.addCommand("ConnectClient", new ConnectClient());
        this.addCommand("DisconnectClient", new DisconnectClient());
        this.addCommand("LoginWithID", new LoginWithID());
        this.addCommand("GeneralLogin",new GeneralLogin());
        this.addCommand("CreateNewOrder",new CreateNewOrder());
        this.addCommand("ExportReport",new ExportReport());
        this.addCommand("RegisterUser",new RegisterUser());
    }

    /**
     * Gets a command instance from the dictionary for execution
     * @param key the name of the command as a string
     * @return A command instance to be executed
     */
    public ServerCommand getCommand(String key) {
        return commandDictionary.get(key);
    }

    /**
     * Adds a new command to the dictionary
     * @param key the name of the command as a string
     * @param command an instance of the command to save in the dictionary
     */
    public void addCommand(String key, ServerCommand command) {
        commandDictionary.put(key, command);
    }
}
