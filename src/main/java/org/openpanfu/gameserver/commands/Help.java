package org.openpanfu.gameserver.commands;

import org.openpanfu.gameserver.User;

public class Help implements ICommand {
	@Override
	public void onExecution(User invoker, String[] parameters) {
		String alert = "GameServer commands:" + "\r\n";
		String[] commandsAndDescriptions = Commands.listCommands();
		for (String description : commandsAndDescriptions) {
			alert += "    " + description + "\r\n";
		}
		invoker.sendAlert(alert);
	}

	@Override
	public String getDescription() {
		return "!help - Lists all the available commands.";
	}
}
