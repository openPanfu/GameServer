package org.openpanfu.gameserver.commands;

import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.util.Logger;

import java.util.HashMap;

public class Commands {
    private static HashMap<String, ICommand> handlers = new HashMap<String, ICommand>();

    /**
     *
     * @param handle The handle the command should go by
     * @param commandExecutor the ICommand instance that will be called when the command is invoked by a user
     * @return
     */
    public static boolean registerCommand(String handle, ICommand commandExecutor)
    {
        handle = handle.toLowerCase();
        if(!handlers.containsKey(handle)) {
            if(handle.chars().allMatch(Character::isLetter)) {
                handlers.put(handle, commandExecutor);
                return true;
            } else {
                Logger.error(String.format("Attempted to register command %s, this was denied because the handle contains invalid characters. (Only letters are allowed!)", handle));
            }
        } else {
            Logger.error(String.format("Attempted to register command %s, this was denied because a command has already been registered with that handle.", handle));
        }
        return false;
    }

    public static void executeCommand(User sender, String handle, String[] arguments)
    {
        if(handle.chars().allMatch(Character::isLetter) && handlers.containsKey(handle)) {
            handlers.get(handle).onExecution(sender, arguments);
            return;
        }
        sender.sendAlert(String.format("Unknown command: %s..", handle));
    }
}
