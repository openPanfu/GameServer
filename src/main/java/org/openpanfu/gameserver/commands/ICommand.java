package org.openpanfu.gameserver.commands;

import org.openpanfu.gameserver.User;

public interface ICommand {
    /**
     *
     * @param invoker The user that invoked the command
     * @param parameters the parameters that the user provided
     */
    public void onExecution(User invoker, String[] parameters);
    public String getDescription();
}
